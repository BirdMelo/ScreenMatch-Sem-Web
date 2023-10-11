package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.classes.Episode;
import br.com.alura.screenmatch.model.records.R_Episode;
import br.com.alura.screenmatch.model.records.SeasonData;
import br.com.alura.screenmatch.model.records.SerieData;
import br.com.alura.screenmatch.model.links.OmdbLinks;
import br.com.alura.screenmatch.service.DataConverter;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
				4 - ver top 10 episodios da série
				5 - ver lista de episódios por ano
				
				Escolha:\s""");
        int choice = WRITE.nextInt();

        var serie = LINKS.serieLink(name);

        SerieData serieData = CONVERTER.getDatum(serie, SerieData.class);

        for (int i = 1; i <= serieData.seasons(); i++) {
            var season = LINKS.seasonLink(name, i);
            SeasonData seasonData = CONVERTER.getDatum(season, SeasonData.class);
            SEASONS.add(seasonData);
        }
//        List<R_Episode> epList = SEASONS.stream()
//                .flatMap(s -> s.episodes().stream())
//                .collect(Collectors.toList());
        List<Episode> episodes = SEASONS.stream()
                .flatMap(s -> s.episodes().stream()
                        .map(d-> new Episode(s.season(), d))
                ).collect(Collectors.toList());

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
                R_Episode epData = CONVERTER.getDatum(episod, R_Episode.class);
                System.out.println(epData);

                break;
            case 2:
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
//                 SEASONS.forEach(s -> {
//                     System.out.printf("Temporada: %d%n",s.season());
//                     s.episodes().forEach(e -> System.out.printf(
//                             "Ep: %d | Nome: %s | Avaliação: %s%n",
//                             e.number(),e.title(),e.rating())
//                     );
//                 });
                episodes.forEach(System.out::println);

                break;
            case 4:

//                epList.stream()
//                        .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
//                        .sorted(Comparator.comparing(R_Episode::rating).reversed())
//                        .limit(10)
//                        .forEach(e -> System.out.printf(
//                                "Ep: %d | Nome: %s | Avaliação: %s%n",
//                                e.number(),e.title(),e.rating()));

                episodes = SEASONS.stream()
                        .flatMap(s -> s.episodes().stream()
                                .map(d-> new Episode(s.season(), d))
                        )
                        .sorted(Comparator.comparing(Episode::getRating).reversed())
                        .limit(10)
                        .collect(Collectors.toList());

                episodes.forEach(System.out::println);

                break;
            case 5:
                System.out.print("Digite o ano: ");
                var year = WRITE.nextInt();
                WRITE.nextLine();

                LocalDate date = LocalDate.of(year,1,1);

                episodes.stream()
                        .filter(e -> e.getRelease() != null && e.getRelease().isAfter(date))
                        .forEach(System.out::println);
                break;
            default:
                System.out.println("Escolha entre 1 e 5");
        }

        System.out.println("\nFim do processo :D");
    }
}
