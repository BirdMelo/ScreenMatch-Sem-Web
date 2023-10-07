package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.EpData;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SerieData;
import br.com.alura.screenmatch.model.links.OmdbLinks;
import br.com.alura.screenmatch.service.DataConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private OmdbLinks links = new OmdbLinks();
    private List<SeasonData> seasons = new ArrayList<>();
    private DataConverter converter = new DataConverter();
    private Scanner write = new Scanner(System.in);
    public void showMenu(){
        System.out.print("Nome da série: ");
        String name = write.nextLine();

        System.out.print("""
				1 - ver dados da serie
				2 - ver as temporadas e seus epsodios
				3 - ver um episodio específico
				
				Escolha:\s""");
        int choice = write.nextInt();

        var serie = links.serieLink(name);
        SerieData serieData = converter.getDatum(serie, SerieData.class);
        switch (choice) {
            case 1:

                System.out.println(serieData);

                break;
            case 3:

                System.out.println("Qual temporada?");
                int w_season = write.nextInt();
                System.out.println("Qual episodio?");
                int w_ep = write.nextInt();

                var episod = links.epLink(name, 1, 3);
                EpData epData = converter.getDatum(episod, EpData.class);
                System.out.println(epData);

                break;
            case 2:

                for (int i = 1; i <= serieData.seasons(); i++) {
                    var season = links.seasonLink(name, i);
                    SeasonData seasonData = converter.getDatum(season, SeasonData.class);
                    seasons.add(seasonData);
                }

                for (int i = 0; i< serieData.seasons(); i++){
                    System.out.printf("Temporada: %d\n",
                            seasons.get(i).season());
                    List<EpData> epOfSeason = seasons.get(i).episodes();
                    for (EpData data : epOfSeason) {
                        System.out.printf("""
                                        Ep: %d | Nome: %s%n""",
                                data.number(), data.title());
                    }
                }
//                 seasons.forEach(s -> s.episodes().forEach(e -> System.out.println(e.title())));

                break;
            default:
                System.out.println("Escolha entre 1 e 3");
        }
        System.out.println("\nFim do processo :D");
    }
}
