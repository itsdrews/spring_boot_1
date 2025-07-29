package learner.drew.ScreenMatch.model;

import learner.drew.ScreenMatch.service.ApiConsumerService;
import learner.drew.ScreenMatch.service.DataConverterService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

        for(int i=0; i<dados.numberOfSeasons();i++){
            int seasonNumber = i+1;
            json = apiConsumerService.obterDados(ENDERECO+name+"&Season="+seasonNumber+API_KEY);
            SeasonData season  = dataConverterService.getData(json, SeasonData.class);
            temporadas.add(season);
        }


        List<EpisodeData> episodios = temporadas.stream()
                .flatMap(t -> t.listaDeEpisodios().stream())
                .collect(Collectors.toList());

//        episodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(EpisodeData::avaliacao)
//                .reversed())
//                .map(e -> e.titulo().toUpperCase())
//                .limit(10)
//                .forEach(System.out::println);

        List<Episode> episodeList = temporadas.stream()
                .flatMap(t -> t.listaDeEpisodios().stream()
                        .map(d -> new Episode(t.numero(), d)))
                .collect(Collectors.toList());

       // episodeList.forEach(System.out::println);
        /*z
        System.out.println("A partir de qual ano?");
        var ano = sc.nextInt();
        sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.of(ano,1,1);
        episodeList.stream()
                .filter(e -> e.getReleaseDate() !=null && e.getReleaseDate().isAfter(data))
                .forEach(e ->
                        System.out.println(
                                "Temporada: " + e.getSeason() +
                                        ", Episódio: " + e.getTitle()+
                                        ", Data de Lançamento: " + e.getReleaseDate().format(formatter)
                        )

                );

    */
        System.out.println("Digite um nome de episódio para ser achado: ");
        var trechoTitulo = sc.nextLine();

        Optional<Episode> episodioAchado= episodeList.stream()
                .filter(e ->e.getTitle().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if(episodioAchado.isPresent()){
            System.out.println("Episodio encontrado! \nTemporada: ");
            System.out.println(episodioAchado.get().getSeason());
        }else{
            System.out.println("Episodio nao encontrado!");
        }
        Map<Integer, Double> avaliacoesPorTemporada = episodeList.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getRating)));

        DoubleSummaryStatistics est = episodeList.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));

        System.out.println("Média: "+ est.getAverage()+
                "\nMelhor episódio: " +est.getMax());

        //System.out.println(avaliacoesPorTemporada);
    }
}
