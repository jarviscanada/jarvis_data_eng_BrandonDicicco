package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {

  private TwitterDao dao;
  private TwitterService service;
  private TwitterController controller;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    HttpHelper twitterHttpHelper =  new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    this.dao = new TwitterDao(twitterHttpHelper);
    this.service = new TwitterService(dao);
    this.controller = new TwitterController(service);
  }

  @Test
  public void postTweet() {
    String text = "@BrandonDiCicco1 hello this is a test tweet " + System.currentTimeMillis();
    String[] validPost = {"post", text, "50:30"};
    Tweet tweet = controller.postTweet(validPost);

    assertNotNull(tweet);
    assertEquals(new Float(50), tweet.getCoordinates().getCoordinates()[0]);
    assertEquals(new Float(30), tweet.getCoordinates().getCoordinates()[1]);
    assertEquals(text, tweet.getText());
  }

  @Test
  public void showTweet() {
    String text = "@BrandonDiCicco1 hello this is a test tweet " + System.currentTimeMillis();
    String[] validPost = {"post", text, "50:30"};
    Tweet postTweet = controller.postTweet(validPost);
    String id = postTweet.getIdStr();
    String fields = "created_at,id,text";
    String[] args = {"show", id, fields};
    Tweet showTweet = controller.showTweet(args);

    assertNotNull(showTweet);
    assertNull(showTweet.getFavoriteCount());
    assertNotNull(showTweet.getCreatedAt());
    assertEquals(postTweet.getId(), showTweet.getId());
  }

  @Test
  public void deleteTweet() {
    String text1 = "@BrandonDiCicco1 hello this is a test tweet " + System.currentTimeMillis();
    String[] validPost1 = {"post", text1, "50:30"};
    Tweet postTweet1 = controller.postTweet(validPost1);
    String id1 = postTweet1.getIdStr();

    String text2 = "@BrandonDiCicco1 hello this is a test tweet2 " + System.currentTimeMillis();
    String[] validPost2 = {"post", text2, "50:30"};
    Tweet postTweet2 = controller.postTweet(validPost2);
    String id2 = postTweet2.getIdStr();

    String ids = id1 + "," + id2;
    String[] args = {"delete", ids};
    List<Tweet> deletedTweets = controller.deleteTweet(args);

    assertEquals(2, deletedTweets.size());
    assertEquals(postTweet1.getId(), deletedTweets.get(0).getId());
    assertEquals(postTweet2.getId(), deletedTweets.get(1).getId());
  }
}