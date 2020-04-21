package com.mathenge.swapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mathenge.swapi.dto.*;
import com.mathenge.swapi.exception.GenericRuntimeException;
import com.mathenge.swapi.pagination.DataTableRequest;
import com.mathenge.swapi.pagination.DataTableResults;
import com.mathenge.swapi.service.ExternalApiRequestService;
import com.mathenge.swapi.util.CustomResponse;
import com.mathenge.swapi.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Mathenge on 4/14/2020
 */
@RestController
public class MainController {

    @Autowired
    private ExternalApiRequestService externalApiRequestService;

    @Autowired
    private Helper helper;

    @Autowired
    ObjectMapper mapper;

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    private static List<String> favoriteCharacters =new ArrayList<>();

    @GetMapping("/characters/paginated")
    @ResponseBody
    public String listCharactersPaginated(HttpServletRequest request, HttpServletResponse response) {

        DataTableRequest<CharacterDTO> dataTableInRQ = new DataTableRequest<>(request);
        //Get requested page number
        int start = dataTableInRQ.getStart();
        int length = dataTableInRQ.getLength();
        int page = (start/length)+1;

        //Get all characters by page
        Optional<AllCharactersDTO> optionalAllCharactersDTO = externalApiRequestService.getAllCharacters(page);
        if (!optionalAllCharactersDTO.isPresent()) {
            throw new GenericRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while fetching characters");
        }
        AllCharactersDTO allCharactersDTO = optionalAllCharactersDTO.get();

        //Get list of characters
        List<CharacterDTO> characterDTOList = allCharactersDTO.getCharacters();
        for (CharacterDTO characterDTO : characterDTOList) {
            characterDTO.setFavorite(checkFavoriteCharacterExists(characterDTO.getUrl()));
        }

        //Build datatable results
        DataTableResults<CharacterDTO> dataTableResult = new DataTableResults<>();
        dataTableResult.setDraw(dataTableInRQ.getDraw());
        dataTableResult.setListOfDataObjects(characterDTOList);
        if (!Helper.isObjectEmpty(characterDTOList)) {
            dataTableResult.setRecordsTotal(allCharactersDTO.getCount().toString());
            if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
                dataTableResult.setRecordsFiltered(allCharactersDTO.getCount().toString());
            } else {
                dataTableResult.setRecordsFiltered(Integer.toString(characterDTOList.size()));
            }
        }
        return new Gson().toJson(dataTableResult);
    }

    @GetMapping("/characters")
    public ResponseEntity<Object> fetchCustomer(@Valid @NotBlank @RequestParam String id) throws IOException {

        logger.info("The url received :::::::::::: {}", id);

        CharacterDTO characterDTO = externalApiRequestService.getCharacterByUrl(id);
        characterDTO.setFavorite(checkFavoriteCharacterExists(characterDTO.getUrl()));

        //Prepare character details dto
        CharacterDetailsViewDTO characterDetailsViewDTO = new CharacterDetailsViewDTO();
        characterDetailsViewDTO.setCharacterInfo(characterDTO);
        //Get character species
        List<SpeciesDTO> speciesList = new ArrayList<>();
        for (String speciesUrl : characterDTO.getSpecies()) {
            SpeciesDTO speciesDTO = externalApiRequestService.getSpeciesByUrl(speciesUrl);
            speciesList.add(speciesDTO);
        }
        characterDetailsViewDTO.setSpeciesList(speciesList);
        //Get character films
        List<FilmDTO> filmDTOList = new ArrayList<>();
        for (String filmUrl : characterDTO.getFilms()) {
            FilmDTO filmDTO = externalApiRequestService.getFilmByUrl(filmUrl);
            filmDTOList.add(filmDTO);
        }
        characterDetailsViewDTO.setFilmList(filmDTOList);
        //Get character vehicles
        List<VehicleDTO> vehicleDTOList = new ArrayList<>();
        for (String vehicleUrl : characterDTO.getVehicles()) {
            VehicleDTO vehicleDTO = externalApiRequestService.getVehicleByUrl(vehicleUrl);
            vehicleDTOList.add(vehicleDTO);
        }
        characterDetailsViewDTO.setVehicleList(vehicleDTOList);
        //Get character vehicles
        List<StarshipDTO> starshipDTOList = new ArrayList<>();
        for (String starshipUrl : characterDTO.getStarships()) {
            StarshipDTO starshipDTO = externalApiRequestService.getStartshipByUrl(starshipUrl);
            starshipDTOList.add(starshipDTO);
        }
        characterDetailsViewDTO.setStarshipList(starshipDTOList);

        return helper.buildSuccessResponseEntity(new CustomResponse(OK.value(), "Customer view data successfully fetched",
                new ObjectMapper().readValue(mapper.writeValueAsString(characterDetailsViewDTO), HashMap.class)), OK);
    }

    @PostMapping(value = "/characters/favorite")
    @ResponseBody
    public ResponseEntity<Object> updateFavoriteCharacters(@Valid @RequestBody UpdateFavoritesDTO updateFavoritesDTO) {
        logger.info("Params received ::::::::: {}", updateFavoritesDTO);
        switch(updateFavoritesDTO.getAction().toUpperCase()) {
            case "ADD":
                if (getFavoriteCharacters().size() >= 5) {
                    throw new GenericRuntimeException(CONFLICT.value(), "Maximum number of favorites exceeded!");
                } else if (checkFavoriteCharacterExists(updateFavoritesDTO.getCharacterUrl()) != 0) {
                    throw new GenericRuntimeException(CONFLICT.value(), "Character already exists in favorites");
                } else
                    addFavoriteCharacter(updateFavoritesDTO.getCharacterUrl());
                break;
            case "DELETE":
                if (checkFavoriteCharacterExists(updateFavoritesDTO.getCharacterUrl()) == 0) {
                    throw new GenericRuntimeException(CONFLICT.value(), "Character is not in favorites");
                } else
                    removeFavoriteCharacter(updateFavoritesDTO.getCharacterUrl());
                break;
        }

        return helper.buildSuccessResponseEntity(new CustomResponse(OK.value(), "Successfully added to favorites"), OK);
    }

    private static List<String> getFavoriteCharacters() {
        return favoriteCharacters;
    }
    private static void addFavoriteCharacter(String url) {
        favoriteCharacters.add(url);
    }
    private static void removeFavoriteCharacter(String url) {
        favoriteCharacters.remove(url);
    }
    private static int checkFavoriteCharacterExists(String url) {
        List<String> result = favoriteCharacters.stream()
                .filter(url::equals)
                .collect(Collectors.toList());
        return result.size();
    }
}
