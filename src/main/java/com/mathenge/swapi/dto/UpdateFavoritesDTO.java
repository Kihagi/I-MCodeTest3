package com.mathenge.swapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Mathenge on 4/14/2020
 */
@Getter
@Setter
@ToString
public class UpdateFavoritesDTO {

    @JsonProperty("character_url")
    @NotNull
    @NotEmpty(message = "First Name is mandatory")
    private String characterUrl;

    @JsonProperty("action")
    @NotNull
    @NotEmpty(message = "Action is mandatory")
    private String action;
}
