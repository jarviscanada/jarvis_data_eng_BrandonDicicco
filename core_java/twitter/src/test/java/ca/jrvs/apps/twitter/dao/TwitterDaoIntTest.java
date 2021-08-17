package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {

  private TwitterDao dao;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    HttpHelper twitterHttpHelper =  new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    this.dao = new TwitterDao(twitterHttpHelper);
  }

  @Test
  public void create() throws Exception {
    String hashtag = "#Test";
    String text = "@BrandonDiCicco1 hello this is a test tweet2 " + hashtag + " " + System.currentTimeMillis();
    Float lon = -1f;
    Float lat = 1f;

    Tweet postTweet = new Tweet();
    Coordinates coordinates = new Coordinates();

    coordinates.setCoordinates(new Float[]{lon, lat});
    coordinates.setType("Point");

    postTweet.setText(text);
    postTweet.setCoordinates(coordinates);

    Tweet tweet = dao.create(postTweet);

    assertEquals(text, tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().length);
    assertEquals(lon, tweet.getCoordinates().getCoordinates()[0]);
    assertEquals(lat, tweet.getCoordinates().getCoordinates()[1]);
    assertTrue(hashtag.contains(tweet.getEntities().getHashtags()[0].getText()));
  }

  @Test
  public void findById() {
    String hashtag = "#Test";
    String text = "@BrandonDiCicco1 Find Tweet Test " + hashtag;
    Float lon = -1f;
    Float lat = 1f;

    Tweet postTweet = new Tweet();
    Coordinates coordinates = new Coordinates();

    coordinates.setCoordinates(new Float[]{lon, lat});
    coordinates.setType("Point");

    postTweet.setText(text);
    postTweet.setCoordinates(coordinates);

    Tweet tweet = dao.create(postTweet);
    Tweet findTweet = dao.findById(tweet.getIdStr());

    assertEquals(text, findTweet.getText());
    assertNotNull(findTweet.getCoordinates());
    assertEquals(2, findTweet.getCoordinates().getCoordinates().length);
    assertEquals(lon, findTweet.getCoordinates().getCoordinates()[0]);
    assertEquals(lat, findTweet.getCoordinates().getCoordinates()[1]);
    assertTrue(hashtag.contains(findTweet.getEntities().getHashtags()[0].getText()));
  }

  @Test
  public void deleteById() {
    String hashtag = "#Test";
    String text = "@BrandonDiCicco1 Delete Tweet Test " + hashtag;
    Float lon = -1f;
    Float lat = 1f;

    Tweet postTweet = new Tweet();
    Coordinates coordinates = new Coordinates();

    coordinates.setCoordinates(new Float[]{lon, lat});
    coordinates.setType("Point");

    postTweet.setText(text);
    postTweet.setCoordinates(coordinates);

    Tweet tweet = dao.create(postTweet);
    Tweet deletedTweet = dao.deleteById(tweet.getIdStr());

    assertEquals(text, deletedTweet.getText());
    assertNotNull(deletedTweet.getCoordinates());
    assertEquals(2, deletedTweet.getCoordinates().getCoordinates().length);
    assertEquals(lon, deletedTweet.getCoordinates().getCoordinates()[0]);
    assertEquals(lat, deletedTweet.getCoordinates().getCoordinates()[1]);
    assertTrue(hashtag.contains(deletedTweet.getEntities().getHashtags()[0].getText()));
  }
}