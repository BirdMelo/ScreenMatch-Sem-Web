package br.com.alura.screenmatch.model.links;

import br.com.alura.screenmatch.model.EpData;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SerieData;
import br.com.alura.screenmatch.service.ApiConsumption;
import br.com.alura.screenmatch.service.DataConverter;

import java.util.ArrayList;
import java.util.List;

public class OmdbLinks {
    List<SeasonData> seasons = new ArrayList<>();
    ApiConsumption link = new ApiConsumption();
    DataConverter converter = new DataConverter();
    public SerieData serieLink(String name){
        String serie = String.format("https://www.omdbapi.com/?t=%s&apikey=ff4bb4a8",
                name.trim().toLowerCase().replace(" ","+"));

        String x = link.getData(serie);
        SerieData serieData = converter.getDatum(x,SerieData.class);
        return serieData;
    }
    public List<SeasonData> seasonLink(String name, int season){
        String replace = name.trim().toLowerCase().replace(" ","+");
        SeasonData seasonData;
        if(season>0) {
            String ss = String.format("https://www.omdbapi.com/?t=%s&season=%d&apikey=ff4bb4a8", replace, season);
            String x = link.getData(ss);

            seasonData = converter.getDatum(x,SeasonData.class);
            seasons.add(seasonData);
            return seasons;
        }else {
            String serie = String.format("https://www.omdbapi.com/?t=%s&apikey=ff4bb4a8", replace);
            SerieData data = converter.getDatum(serie, SerieData.class);
            for (int i = 1; i<= data.seasons(); i++){
                String ss = String.format("https://www.omdbapi.com/?t=%s&season=%d&apikey=ff4bb4a8", replace, i);
                String x = link.getData(ss);

                seasonData = converter.getDatum(x,SeasonData.class);
                seasons.add(seasonData);
            }
            return seasons;
        }
    }
    public EpData epLink(String name, int season, int ep){
        String episode = String.format("https://www.omdbapi.com/?t=%s&season=%d&episode=%d&apikey=ff4bb4a8",
                name.trim().toLowerCase().replace(" ","+"),season,ep);

        String x = link.getData(episode);
        EpData epData = converter.getDatum(x, EpData.class);

        return epData;
    }
}
