package com.mathenge.swapi.service;

import com.mathenge.swapi.dto.*;

import java.util.Optional;

/**
 * @author Mathenge on 4/14/2020
 */
public interface ExternalApiRequestService {

    Optional<CharacterDTO> getCharacterById(long id);

    Optional<AllCharactersDTO> getAllCharacters(int page);

    CharacterDTO getCharacterByUrl(String url);

    FilmDTO getFilmByUrl(String url);

    SpeciesDTO getSpeciesByUrl(String url);

    VehicleDTO getVehicleByUrl(String url);

    StarshipDTO getStartshipByUrl(String url);
}
