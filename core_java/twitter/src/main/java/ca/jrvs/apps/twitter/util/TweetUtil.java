package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

public class TweetUtil {

  public static Tweet createTweet(String text, Float lon, Float lat) {
    Tweet tweet = new Tweet();
    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(new Float[]{lon, lat});
    coordinates.setType("Point");

    tweet.setText(text);
    tweet.setCoordinates(coordinates);

    return tweet;
  }
}
