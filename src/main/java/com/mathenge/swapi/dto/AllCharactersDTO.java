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
public class AllCharactersDTO {

    @JsonProperty("count")
    public Integer count;

    @JsonProperty("next")
    public String next;

    @JsonProperty("previous")
    public Object previous;

    @JsonProperty("results")
    public List<CharacterDTO> characters = null;
}
