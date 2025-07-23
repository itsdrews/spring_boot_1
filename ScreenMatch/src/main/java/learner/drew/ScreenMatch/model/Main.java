package learner.drew.ScreenMatch.model;

import learner.drew.ScreenMatch.service.ApiConsumerService;
import learner.drew.ScreenMatch.service.DataConverterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private final String ENDERECO = "https://www.omdbapi.com/?t="; // Fixo para buscar série,filme
    private final String API_KEY = "&apikey=5a958904"; // TOKEN para consumir api
    private Scanner sc = new Scanner(System.in); // Scanner
    private ApiConsumerService apiConsumerService =  new ApiConsumerService();
    private DataConverterService dataConverterService =  new DataConverterService();

    public void menu(){
        System.out.println("Digite o nome da série para buscar: ");
        var name = sc.nextLine();
        if (name.contains(" ")){
           name = name.replaceAll("\\s","+"); // Substituir espaços por +
        }
        var endereco = ENDERECO +name+ API_KEY; // Concatenar o novo endereço
        var json = apiConsumerService.obterDados(endereco); // Consumir API
        ShowData dados = dataConverterService.getData(json, ShowData.class); // Conversor para classe usando genérico <T> T
        System.out.println(dados);

        List<SeasonData> temporadas = new ArrayList<>();

        for(int i=1;i<dados.numberOfSeasons();i++){
            String enderecoNovo = ENDERECO + name + "&season=" + i+ API_KEY;
            json = apiConsumerService.obterDados(enderecoNovo);
            var dadosTemporada = dataConverterService.getData(json, SeasonData.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);


    }
}
