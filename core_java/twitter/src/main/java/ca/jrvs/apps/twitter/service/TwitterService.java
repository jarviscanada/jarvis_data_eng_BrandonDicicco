package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;

public class TwitterService implements Service {

  private final CrdDao dao;

  public TwitterService(CrdDao dao) {
    this.dao = dao;
  }
  /**
   * Validate and post a user input Tweet
   *
   * @param tweet tweet to be created
   * @return created tweet
   * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long
   *                                  out of range
   */
  @Override
  public Tweet postTweet(Tweet tweet) {
    validatePostTweet(tweet);

    return (Tweet) dao.create(tweet);
  }

  /**
   * Validate that a tweet has valid text length and longitude/latitude pair
   * @param tweet to be validated
   */
  public void validatePostTweet(Tweet tweet) {
    Float tweetLong = tweet.getCoordinates().getCoordinates()[0];
    Float tweetLat = tweet.getCoordinates().getCoordinates()[1];
    Float longRange = 180.0f;
    Float latRange = 90.0f;
    int textLength = tweet.getText().length();

    // Validate. Check length
    if (textLength > 140) {
      throw new IllegalArgumentException("Tweet text exceeds the maximum length of 140 characters");
    }
    // Check longitude and latitude
    else if (tweetLong > longRange || tweetLong < -longRange || tweetLat > latRange || tweetLat < -latRange) {
      throw new IllegalArgumentException("Invalid longitude/latitude");
    }
  }
  /**
   * Search a tweet by ID
   *
   * @param id     tweet id
   * @param fields set fields not in the list to null
   * @return Tweet object which is returned by the Twitter API
   * @throws IllegalArgumentException if id or fields param is invalid
   */
  @Override
  public Tweet showTweet(String id, String[] fields) {
    // Validate id first
    validateTweetId(id);

    // Find wanted tweet.
    Tweet getTweet = (Tweet) dao.findById(id);
    Tweet tweetWithNulls = new Tweet();

    // Set only fields that aren't null on the tweet to be returned
    for (String field : fields) {
      if (field.equals("created_at")) tweetWithNulls.setCreatedAt(getTweet.getCreatedAt());
      else if (field.equals("id")) tweetWithNulls.setId(getTweet.getId());
      else if (field.equals("id_str")) tweetWithNulls.setIdStr(getTweet.getIdStr());
      else if (field.equals("text")) tweetWithNulls.setText(getTweet.getText());
      else if (field.equals("entities")) tweetWithNulls.setEntities(getTweet.getEntities());
      else if (field.equals("coordinates")) tweetWithNulls.setCoordinates(getTweet.getCoordinates());
      else if (field.equals("retweet_count")) tweetWithNulls.setRetweetCount(getTweet.getRetweetCount());
      else if (field.equals("favorite_count")) tweetWithNulls.setFavoriteCount(getTweet.getFavoriteCount());
      else if (field.equals("favorited")) tweetWithNulls.setFavorited(getTweet.isFavorited());
      else if (field.equals("retweeted")) tweetWithNulls.setRetweeted(getTweet.isRetweeted());
      else throw new IllegalArgumentException("Invalid field given: " + field);
    }

    return tweetWithNulls;
  }

  /**
   * Delete Tweet(s) by id(s).
   *
   * @param ids tweet IDs which will be deleted
   * @return A list of Tweets
   * @throws IllegalArgumentException if one of the IDs is invalid.
   */
  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> deletedTweets = new ArrayList<>();

    // Check id of every tweet to be deleted.
    for (String id : ids) {
      validateTweetId(id);
      deletedTweets.add( (Tweet) dao.deleteById(id));
    }

    return deletedTweets;
  }

  /**
   * Validates that a given id is a valid long representation
   * @param id id to be validated
   */
  public void validateTweetId(String id) {
    try {
      Long.parseLong(id);

    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("Invalid id(s) found in the input");
    }
  }
}
