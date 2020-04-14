package com.mathenge.swapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mathenge on 4/14/2020
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
@Setter
public class CharacterDetailsViewDTO {

    private CharacterDTO characterInfo;

    private List<SpeciesDTO> speciesList;

    private List<FilmDTO> filmList;

    private List<VehicleDTO> vehicleList;

    private List<StarshipDTO> starshipList;
}
