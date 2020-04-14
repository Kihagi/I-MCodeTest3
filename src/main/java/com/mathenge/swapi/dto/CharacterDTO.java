package com.mathenge.swapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Mathenge on 4/14/2020
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CharacterDTO {

    @JsonProperty("name")
    public String name;

    @JsonProperty("height")
    public String height;

    @JsonProperty("mass")
    public String mass;

    @JsonProperty("hair_color")
    public String hairColor;

    @JsonProperty("skin_color")
    public String skinColor;

    @JsonProperty("eye_color")
    public String eyeColor;

    @JsonProperty("birth_year")
    public String birthYear;

    @JsonProperty("gender")
    public String gender;

    @JsonProperty("homeworld")
    public String homeworld;

    @JsonProperty("films")
    public List<String> films = null;

    @JsonProperty("species")
    public List<String> species = null;

    @JsonProperty("vehicles")
    public List<String> vehicles = null;

    @JsonProperty("starships")
    public List<String> starships = null;

    @JsonProperty("created")
    public String created;

    @JsonProperty("edited")
    public String edited;

    @JsonProperty("url")
    public String url;

    private int favorite = 0;
}
