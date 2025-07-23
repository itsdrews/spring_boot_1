package learner.drew.ScreenMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShowData(@JsonAlias("Title") String title,
                       @JsonAlias("totalSeason") Integer numberOfSeasons,
                       @JsonAlias("imdbRating") String rating) {}
