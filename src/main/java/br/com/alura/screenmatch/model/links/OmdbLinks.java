package br.com.alura.screenmatch.model.links;

import br.com.alura.screenmatch.model.SerieData;
import br.com.alura.screenmatch.service.DataConverter;

public class OmdbLinks {
    DataConverter converter = new DataConverter();
    public SerieData serieLink(String name){
        String serie = String.format("https://www.omdbapi.com/?t=%s&apikey=ff4bb4a8",
                name.trim().toLowerCase().replace(" ","+"));
        SerieData serieData = converter.getDatum(serie,SerieData.class);
        return  serieData;
    }
    public String seasonLink(String name, int season){
        if(season>0) {
            return String.format("https://www.omdbapi.com/?t=%s&season=%d&apikey=ff4bb4a8",
                    name.trim().toLowerCase().replace(" ", "+"), season);
        }
        return "0";
    }
    public String epLink(String name, int season, int ep){
        return String.format("https://www.omdbapi.com/?t=%s&season=%d&episode=%d&apikey=ff4bb4a8",
                name.trim().toLowerCase().replace(" ","+"),season,ep);
    }
}
