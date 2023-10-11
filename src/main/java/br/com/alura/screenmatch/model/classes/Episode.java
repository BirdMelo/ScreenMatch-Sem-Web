package br.com.alura.screenmatch.model.classes;

import br.com.alura.screenmatch.model.records.R_Episode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Episode {
    private final String title;
    private final int season;
    private final int number;
    private double rating;
    private LocalDate release;

    public Episode(int season, R_Episode dataEp){
        this.season = season;
        this.title = dataEp.title();
        this.number = dataEp.number();
        try {
            this.rating = Double.parseDouble(dataEp.rating());
        }catch (NumberFormatException e){
            this.rating = 0;
        }
        try {
            this.release = LocalDate.parse(dataEp.release());
        } catch (DateTimeParseException e){
            this.release = null;
        }

    }

    public String getTitle() {
        return title;
    }

    public int getSeason() {
        return season;
    }

    public int getNumber() {
        return number;
    }

    public double getRating() {
        return rating;
    }

    public LocalDate getRelease() {
        return release;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("Temporada: %d | Episódio: %d | Nome: %s | Lançamento: %s | Avaliação: %.2f",
                season,number, title,release.format(formatBR),rating);
    }
}
