package br.com.alura.screenmatch.model.links;

import br.com.alura.screenmatch.service.ApiConsumption;


public class OmdbLinks {
    final String ADDRESS = "https://www.omdbapi.com/?";
    final String API_KEY = "&apikey=ff4bb4a8";
    ApiConsumption link = new ApiConsumption();
    private String replace(String name){
        return name.trim().toLowerCase().replace(" ","+");
    }
    public String serieLink(String name){
        return link.getData(String.format(ADDRESS+"t=%s"+API_KEY,
                replace(name)));
    }
    public String seasonLink(String name, int season){
        return link.getData(String.format(ADDRESS+"t=%s&season=%d"+API_KEY,
                replace(name), season));
    }
    public String epLink(String name, int season, int ep){
        return link.getData(String.format(ADDRESS+"t=%s&season=%d&episode=%d"+API_KEY,
                replace(name),season,ep));
    }
}
