package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest {

  private TwitterService service;
  private Tweet testTweet;

  @Before
  public void setup() {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    HttpHelper twitterHttpHelper =  new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    CrdDao<Tweet, String> dao = new TwitterDao(twitterHttpHelper);
    this.service = new TwitterService(dao);

    // Setup tweet
    this.testTweet = new Tweet();
    String hashtag = "#Test";
    String text = "@BrandonDiCicco1 hello this is a test tweet2 " + hashtag + " " + System.currentTimeMillis();
    float lon = -1f;
    float lat = 1f;
    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(new Float[]{lon, lat});
    coordinates.setType("Point");
    testTweet.setText(text);
    testTweet.setCoordinates(coordinates);
  }
  @Test
  public void postTweet() {
    Tweet responseTweet = service.postTweet(testTweet);

    assertEquals(testTweet.getText(), responseTweet.getText());
    assertNotNull(responseTweet.getCoordinates());
    assertEquals(2, responseTweet.getCoordinates().getCoordinates().length);
    assertEquals(new Float(-1), responseTweet.getCoordinates().getCoordinates()[0]);
    assertEquals(new Float(1), responseTweet.getCoordinates().getCoordinates()[1]);
    assertTrue("#Test".contains(responseTweet.getEntities().getHashtags()[0].getText()));
  }

  @Test
  public void showTweet() {
    String[] fields = {"created_at", "text"};
    Tweet showFieldsTweet = service.showTweet("1427293637801566208", fields);

    assertEquals("POST_Request_Test2", showFieldsTweet.getText());
    assertNull(showFieldsTweet.getFavoriteCount());
  }

  @Test
  public void deleteTweets() {
    Tweet deleteThisTweet = service.postTweet(testTweet);
    String deleteId = deleteThisTweet.getIdStr();
    String[] ids = {deleteId};

    List<Tweet> deletedTweets = service.deleteTweets(ids);

    assertNotNull(deletedTweets);
    assertEquals(1, deletedTweets.size());
    assertEquals(deleteThisTweet.getId(), deletedTweets.get(0).getId());
  }
}