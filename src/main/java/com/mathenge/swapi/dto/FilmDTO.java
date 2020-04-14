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
public class FilmDTO {

    @JsonProperty("title")
    public String title;

    @JsonProperty("episode_id")
    public Integer episodeId;

    @JsonProperty("opening_crawl")
    public String openingCrawl;

    @JsonProperty("director")
    public String director;

    @JsonProperty("producer")
    public String producer;

    @JsonProperty("release_date")
    public String releaseDate;

    @JsonProperty("created")
    public String created;

    @JsonProperty("edited")
    public String edited;
}
