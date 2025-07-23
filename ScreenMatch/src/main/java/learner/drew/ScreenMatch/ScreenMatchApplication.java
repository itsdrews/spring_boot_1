package learner.drew.ScreenMatch;

import learner.drew.ScreenMatch.model.EpisodeData;
import learner.drew.ScreenMatch.model.ShowData;
import learner.drew.ScreenMatch.service.ApiConsumerService;
import learner.drew.ScreenMatch.service.DataConverterService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		var consumoApi = new ApiConsumerService();
		var endereco = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&&episode=1-&apikey=5a958904");
		var converteDados = new DataConverterService();
		ShowData data = converteDados.getData(endereco, ShowData.class);
		System.out.println(data);
		// TODO: Pegar os dados da api para um episode e consumir/mostrar no terminal
		// Java 17
		var episode = converteDados.getData(endereco, EpisodeData.class);
		System.out.println(episode);



	}

}
