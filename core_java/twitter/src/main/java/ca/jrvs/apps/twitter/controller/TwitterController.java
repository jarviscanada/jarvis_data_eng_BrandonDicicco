package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;
import org.springframework.util.StringUtils;

public class TwitterController implements Controller {

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";

  private Service service;

  public TwitterController(Service service) {
    this.service = service;
  }
  /**
   * Parse user argument and post a tweet by calling service classes
   *
   * @param args
   * @return a posted tweet
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public Tweet postTweet(String[] args) {
    // Check if correct amount of arguments was given
    if (args.length != 3)
      throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");

    String tweetText = args[1];
    String[] coordinates = args[2].split(COORD_SEP);

    if (coordinates.length != 2 || StringUtils.isEmpty(tweetText)) {
      throw new IllegalArgumentException(
          "Tweet text must be provided, along with valid location format. \n USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    }

    Float longitude = null, latitude = null;

    try {
      latitude = Float.parseFloat(coordinates[0]);
      longitude = Float.parseFloat(coordinates[1]);

    } catch (Exception ex) {
      throw new IllegalArgumentException("Location format incorrect. \n USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    }

    Tweet newTweet = TweetUtil.createTweet(tweetText, longitude, latitude);

    return service.postTweet(newTweet);
  }

  /**
   * Parse user argument and search a tweet by calling service classes
   *
   * @param args
   * @return a tweet
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public Tweet showTweet(String[] args) {
    // fields section is optional
    if (args.length != 2 && args.length != 3)
      throw new IllegalArgumentException("USAGE: TwitterCLIApp show \"tweet_id\" \"[field1,field2,...]\"");

    String id = args[1];
    String[] fields = null;

    // Only parse fields if given
    if (args.length == 3) {
      fields = args[2].split(COMMA);
    }

    return service.showTweet(id, fields);
  }

  /**
   * Parse user argument and delete tweets by calling service classes
   *
   * @param args
   * @return a list of deleted tweets
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if (args.length != 2)
      throw new IllegalArgumentException("USAGE: TwitterCLIApp delete \"[id1,id2,...]\"");

    String[] ids = args[1].split(COMMA);

    return service.deleteTweets(ids);
  }
}
