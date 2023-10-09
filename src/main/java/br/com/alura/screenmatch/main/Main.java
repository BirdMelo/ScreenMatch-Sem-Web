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
    private final OmdbLinks LINKS = new OmdbLinks();
    private final List<SeasonData> SEASONS = new ArrayList<>();
    private final DataConverter CONVERTER = new DataConverter();
    private final Scanner WRITE = new Scanner(System.in);
    public void showMenu(){
        System.out.print("Nome da série: ");
        String name = WRITE.nextLine();

        System.out.print("""
				1 - ver dados da serie
				2 - ver as temporadas e seus epsodios
				3 - ver um episodio específico
				
				Escolha:\s""");
        int choice = WRITE.nextInt();

        var serie = LINKS.serieLink(name);
        SerieData serieData = CONVERTER.getDatum(serie, SerieData.class);
        switch (choice) {
            case 1:

                System.out.println(serieData);

                break;
            case 3:

                System.out.println("Qual temporada?");
                int w_season = WRITE.nextInt();
                System.out.println("Qual episodio?");
                int w_ep = WRITE.nextInt();

                var episod = LINKS.epLink(name, w_season, w_ep);
                EpData epData = CONVERTER.getDatum(episod, EpData.class);
                System.out.println(epData);

                break;
            case 2:

                for (int i = 1; i <= serieData.seasons(); i++) {
                    var season = LINKS.seasonLink(name, i);
                    SeasonData seasonData = CONVERTER.getDatum(season, SeasonData.class);
                    SEASONS.add(seasonData);
                }

//                sem forEach
//                for (int i = 0; i< serieData.seasons(); i++){
//                    System.out.printf("Temporada: %d%n",
//                            seasons.get(i).season());
//                    List<EpData> epOfSeason = seasons.get(i).episodes();
//                    for (EpData data : epOfSeason) {
//                        System.out.printf("""
//                                        Ep: %d | Nome: %s%n""",
//                                data.number(), data.title());
//                    }
//                }
                 SEASONS.forEach(s -> {
                     System.out.printf("Temporada: %d%n",s.season());
                     s.episodes().forEach(e -> {
                         System.out.printf("Ep: %d | Nome: %s%n",e.number(),e.title());
                     });
                 });

                break;
            default:
                System.out.println("Escolha entre 1 e 3");
        }
        System.out.println("\nFim do processo :D");
    }
}
