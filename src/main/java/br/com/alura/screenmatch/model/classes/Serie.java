package br.com.alura.screenmatch.model.classes;

import br.com.alura.screenmatch.model.records.R_Serie;

public class Serie {
    private String TITLE;
    private double RATING;
    private int T_SEASON;

    public Serie(String title, R_Serie dataSerie){
        this.TITLE = title;
        this.T_SEASON = dataSerie.seasons();
        this.RATING = Double.parseDouble(dataSerie.rating());
    }
}
