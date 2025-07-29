package learner.drew.ScreenMatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Double rating;
    private Integer episodeNumber;
    private LocalDate releaseDate;



    public Episode(Integer numeroTemporada, EpisodeData e) {
        this.season = numeroTemporada;
        this.title = e.titulo();
        this.episodeNumber = e.numero();
        try{
            this.rating = Double.valueOf(e.avaliacao());

        }catch(NumberFormatException ex) {
            this.rating = 0.0;
        }
        try{
            this.releaseDate = LocalDate.parse(e.dataLancamento());

        }catch (DateTimeParseException ex){
            this.releaseDate= null;
        }

    }

    public Integer getSeason() {
        return season;
    }


    public String getTitle() {
        return title;
    }

    public Double getRating() {
        return rating;
    }


    public Integer getEpisodeNumber() {
        return episodeNumber;
    }


    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {
        return
                "season: " + season +
                ", title: " + title + '\'' +
                ", rating: " + rating +
                ", episodeNumber: " + episodeNumber +
                ", releaseDate: " + releaseDate;
    }

}
