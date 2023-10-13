package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.classes.Episode;
import br.com.alura.screenmatch.model.records.R_Episode;
import br.com.alura.screenmatch.model.records.SeasonData;
import br.com.alura.screenmatch.model.records.R_Serie;
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

        var serie = LINKS.serieLink(name);

        R_Serie serieData = CONVERTER.getDatum(serie, R_Serie.class);

        for (int i = 1; i <= serieData.seasons(); i++) {
            var season = LINKS.seasonLink(name, i);
            SeasonData seasonData = CONVERTER.getDatum(season, SeasonData.class);
            SEASONS.add(seasonData);
        }

        List<R_Episode> epList = SEASONS.stream()
                .flatMap(s->s.episodes().stream())
                .collect(Collectors.toList());

        List<Episode> episodes = SEASONS.stream()
                .flatMap(s -> s.episodes().stream()
                        .map(d-> new Episode(s.season(), d))
                ).collect(Collectors.toList());

//        um ep especifico
        System.out.println("Qual temporada?");
        int w_season = WRITE.nextInt();
        System.out.println("Qual episodio?");
        int w_ep = WRITE.nextInt();

        var episod = LINKS.epLink(name, w_season, w_ep);
        R_Episode epData = CONVERTER.getDatum(episod, R_Episode.class);
        System.out.println(epData);

        System.out.println("Toda a serie");
        episodes.forEach(System.out::println);

        System.out.print("Epsodio: ");
        var partTitle = WRITE.nextLine();
        WRITE.nextLine();
        Optional<Episode> epSearched = episodes.stream()
                .filter(e -> e.getTitle().toLowerCase().contains(partTitle.toLowerCase()))
                .findFirst();
        if (epSearched.isPresent()) {
            System.out.print("Episódio encontrado: ");
            System.out.println(epSearched.get());
        } else {
            System.out.println("Episódio não encontrado.");
        }

//        System.out.println("Top 10:");
//        epList.stream()
//                .filter(e-> !e.rating().equalsIgnoreCase("N/A"))
////                .peek(e-> System.out.println("Filtro 'N/A': "+ e))
//                .sorted(Comparator.comparing(R_Episode::rating))
////                .peek(e-> System.out.println("Filtro ordenar: "+ e))
//                .limit(10)
////                .peek(e-> System.out.println("Filtro limitar: "+ e))
//                .map(e->e.title().toUpperCase())
////                .peek(e-> System.out.println("Filtro mapeamento: "+ e))
//                .forEach(System.out::println);


//        episodes = SEASONS.stream()
//                .flatMap(s -> s.episodes().stream()
//                        .map(d-> new Episode(s.season(), d))
//                )
//                .sorted(Comparator.comparing(Episode::getRating).reversed())
//                .limit(10)
//                .collect(Collectors.toList());
//        episodes.forEach(System.out::println);


//        System.out.print("Digite o ano: ");
//        var year = WRITE.nextInt();
//        WRITE.nextLine();
//
//        LocalDate date = LocalDate.of(year,1,1);
//
//        episodes.stream()
//                .filter(e -> e.getRelease() != null && e.getRelease().isAfter(date))
//                .forEach(System.out::println);

        System.out.println("\nFim do processo :D");
    }
}
