package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.SerieData;
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
		var link = new ApiConsumption();
		var json = link.obterDados("https://www.omdbapi.com/?t=the+wheel+of+time&apikey=ff4bb4a8");
		System.out.println(json);
		DataConverter converter = new DataConverter();
		SerieData data = converter.getDatum(json,SerieData.class);
		System.out.println(data);
	}
}
