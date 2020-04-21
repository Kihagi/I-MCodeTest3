package com.mathenge.swapi.service;

import com.mathenge.swapi.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

/**
 * @author Mathenge on 4/14/2020
 */
@Service
public class ExternalApiRequestServiceImpl implements ExternalApiRequestService {

    private Logger logger = LoggerFactory.getLogger(ExternalApiRequestServiceImpl.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${swapi-url}")
    private String swapiBaseUrl;

    @Override
    public Optional<CharacterDTO> getCharacterById(long id) {
        HttpHeaders headers = new HttpHeaders();
        String requestUrl = swapiBaseUrl.concat("people/").concat(Long.toString(id));
        HttpEntity<String> request = new HttpEntity<>(headers);
        CharacterDTO characterDTO = null;
        try {
            ResponseEntity<CharacterDTO> response = restTemplate.exchange(
                    requestUrl, HttpMethod.GET, request, CharacterDTO.class);
            characterDTO = response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            logger.error("Fetch character request encountered error {}", httpClientOrServerExc.getMessage());
        }
        return Optional.ofNullable(characterDTO);
    }

    @Override
    public Optional<AllCharactersDTO> getAllCharacters(int page) {
        HttpHeaders headers = new HttpHeaders();
        String requestUrl = swapiBaseUrl.concat("people/");

        HttpEntity<String> request = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(requestUrl)
                .queryParam("page", page);
        UriComponents uriComponents = builder.build();

        AllCharactersDTO allCharactersDTO = null;
        try {
            ResponseEntity<AllCharactersDTO> response = restTemplate.exchange(
                    uriComponents.toUri(), HttpMethod.GET, request, AllCharactersDTO.class);
            allCharactersDTO = response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            logger.error("Fetch all characters request encountered error {}", httpClientOrServerExc.getMessage());
        }
        return Optional.ofNullable(allCharactersDTO);
    }

    @Override
    public CharacterDTO getCharacterByUrl(String url) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        CharacterDTO characterDTO = null;
        try {
            ResponseEntity<CharacterDTO> response = restTemplate.exchange(
                    url, HttpMethod.GET, request, CharacterDTO.class);
            characterDTO = response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            logger.error("Fetch character request encountered error {}", httpClientOrServerExc.getMessage());
        }
        return characterDTO;
    }

    @Override
    public FilmDTO getFilmByUrl(String url) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        FilmDTO filmDTO = null;
        try {
            ResponseEntity<FilmDTO> response = restTemplate.exchange(
                    url, HttpMethod.GET, request, FilmDTO.class);
            filmDTO = response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            logger.error("Fetch character request encountered error {}", httpClientOrServerExc.getMessage());
        }
        return filmDTO;
    }

    @Override
    public SpeciesDTO getSpeciesByUrl(String url) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        SpeciesDTO speciesDTO = null;
        try {
            ResponseEntity<SpeciesDTO> response = restTemplate.exchange(
                    url, HttpMethod.GET, request, SpeciesDTO.class);
            speciesDTO = response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            logger.error("Fetch character request encountered error {}", httpClientOrServerExc.getMessage());
        }
        return speciesDTO;
    }

    @Override
    public VehicleDTO getVehicleByUrl(String url) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        VehicleDTO vehicleDTO = null;
        try {
            ResponseEntity<VehicleDTO> response = restTemplate.exchange(
                    url, HttpMethod.GET, request, VehicleDTO.class);
            vehicleDTO = response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            logger.error("Fetch character request encountered error {}", httpClientOrServerExc.getMessage());
        }
        return vehicleDTO;
    }

    @Override
    public StarshipDTO getStartshipByUrl(String url) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        StarshipDTO starshipDTO = null;
        try {
            ResponseEntity<StarshipDTO> response = restTemplate.exchange(
                    url, HttpMethod.GET, request, StarshipDTO.class);
            starshipDTO = response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            logger.error("Fetch character request encountered error {}", httpClientOrServerExc.getMessage());
        }
        return starshipDTO;
    }
}
