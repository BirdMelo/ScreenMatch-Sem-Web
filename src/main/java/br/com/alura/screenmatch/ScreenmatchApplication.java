package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.EpData;
import br.com.alura.screenmatch.model.SerieData;
import br.com.alura.screenmatch.model.links.OmdbLinks;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.service.ApiConsumption;
import br.com.alura.screenmatch.service.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		OmdbLinks links = new OmdbLinks();
		var link = new ApiConsumption();
		DataConverter converter = new DataConverter();

		var serie = links.serieLink("The Wheel of Time");
		System.out.println(serie);
		var season = links.seasonLink("The Wheel of Time",0);
		System.out.println(season);
		var episod = links.epLink("The Wheel of Time",1,3);
		System.out.println(episod);
	}
}
