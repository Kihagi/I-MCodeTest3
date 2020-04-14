package com.mathenge.swapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Mathenge on 4/14/2020
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class SpeciesDTO {

    @JsonProperty("name")
    public String name;

    @JsonProperty("classification")
    public String classification;

    @JsonProperty("designation")
    public String designation;

    @JsonProperty("average_height")
    public String averageHeight;

    @JsonProperty("skin_colors")
    public String skinColors;

    @JsonProperty("hair_colors")
    public String hairColors;

    @JsonProperty("eye_colors")
    public String eyeColors;

    @JsonProperty("average_lifespan")
    public String averageLifespan;

    @JsonProperty("homeworld")
    public Object homeworld;

    @JsonProperty("language")
    public String language;

    @JsonProperty("created")
    public String created;

    @JsonProperty("edited")
    public String edited;

    @JsonProperty("url")
    public String url;
}
