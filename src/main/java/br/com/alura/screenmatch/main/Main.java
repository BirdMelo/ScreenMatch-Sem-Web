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

        System.out.print("""
                1   -   Ver Dados série
                
                2   -   Ver temporadas e episódios
                
                3   -   Procurar episódio por temporada e Número
                
                4   -   Procurar episódio por nome
                
                5   -   Top 10 episódios da série
                
                6   -   Procurar episódios aparti de um ano
                
                7   -   Média de avaliações por temporada.
                
                Escolha:\s""");

        int choise = WRITE.nextInt();
        WRITE.nextLine();

        if (choise==1) {
            System.out.printf("Nome: %s | Temporadas: %d | Avaliação: %s%n",
                    serieData.title(), serieData.seasons(), serieData.rating());
        } else if (choise<=7) {

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

            if (choise==2){

                //        Temporadas e Episódios
                System.out.println("Toda a serie");
                episodes.forEach(System.out::println);
            } else if (choise == 3) {
                //Por temporada e número de episódio:
                System.out.println("Qual temporada?");
                int w_season = WRITE.nextInt();
                System.out.println("Qual episodio?");
                int w_ep = WRITE.nextInt();
                WRITE.nextLine();

                var episod = LINKS.epLink(name, w_season, w_ep);
                R_Episode epData = CONVERTER.getDatum(episod, R_Episode.class);
                System.out.println(epData);
            } else if (choise == 4) {

                //        Ep Por nome
                System.out.println("Episódio: ");
                var partTitle = WRITE.nextLine().trim();
                System.out.println(partTitle);
                Optional<Episode> epSearched = episodes.stream()
                        .filter(e -> e.getTitle().toLowerCase().contains(partTitle.toLowerCase()))
                        .findFirst();

                if (epSearched.isPresent()) {
                    System.out.print("Episódio encontrado: ");
                    System.out.println(epSearched.get());
                } else {
                    System.out.println("Episódio não encontrado.");
                }
            } else if (choise == 5) {

                //        Top 10:
                System.out.println("Top 10:");
                epList.stream()
                        .filter(e-> !e.rating().equalsIgnoreCase("N/A"))
//                        .peek(e-> System.out.println("Filtro 'N/A': "+ e))
                        .sorted(Comparator.comparing(R_Episode::rating))
//                        .peek(e-> System.out.println("Filtro ordenar: "+ e))
                        .limit(10)
//                        .peek(e-> System.out.println("Filtro limitar: "+ e))
                        .map(e->e.title().toUpperCase())
//                        .peek(e-> System.out.println("Filtro mapeamento: "+ e))
                        .forEach(System.out::println);

//                episodes = SEASONS.stream()
//                        .flatMap(s -> s.episodes().stream()
//                                .map(d-> new Episode(s.season(), d))
//                        )
//                        .sorted(Comparator.comparing(Episode::getRating).reversed())
//                        .limit(10)
//                        .collect(Collectors.toList());
//                episodes.forEach(System.out::println);
            } else if (choise == 6) {

                //        Pesquisar por ano:

                System.out.print("Digite o ano: ");
                var year = WRITE.nextInt();
                WRITE.nextLine();

                LocalDate date = LocalDate.of(year,1,1);

                episodes.stream()
                        .filter(e -> e.getRelease() != null && e.getRelease().isAfter(date))
                        .forEach(System.out::println);

            } else if (choise == 7) {

                Map<Integer,Double> avgSeason = episodes.stream()
                        .filter( e -> e.getRating()>0)
                        .collect(Collectors.groupingBy(Episode::getSeason,
                                Collectors.averagingDouble(Episode::getRating)));
                System.out.println(avgSeason);

                DoubleSummaryStatistics est = episodes.stream()
                        .filter(e -> e.getRating()>0)
                        .collect(Collectors.summarizingDouble(Episode::getRating));
                System.out.printf("""
                        Média: %.1f
                        Melhor episódio: %.1f
                        Pior episódio: %.1f
                        Quantidade de episódios avaliados: %d""",
                        est.getAverage(),est.getMax(),est.getMin(),est.getCount());
            }
        }
        System.out.println("\nFim do processo :D");
    }
}
