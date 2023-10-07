package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.model.EpData;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(
        @JsonAlias("Title") String title,
        @JsonAlias("Season") int season,
        @JsonAlias("Episodes") List<EpData> episodes
) {
}
