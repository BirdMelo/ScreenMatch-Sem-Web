package br.com.alura.screenmatch.model.records;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieData(@JsonAlias("Title") String title,
                        @JsonAlias("imdbRating") String rating,
                        @JsonAlias("totalSeasons") Integer seasons) {
}
