package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

  @Mock
  CrdDao dao;

  @InjectMocks
  TwitterService service;

  @Test
  public void postTweet() {
    when(dao.create(any())).thenReturn(new Tweet());

    // Successful case
    Tweet passTweet = service.postTweet(TweetUtil.createTweet("Testing Post Unit Test", 50.0f, 0.0f));
    assertNotNull(passTweet);

    // Text over limit
    String largeText = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    try {
      passTweet = service.postTweet(TweetUtil.createTweet(largeText, 50.0f, 0.0f));

    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    // Longitude out of bounds
    Float outOfBoundsLong = 300f;

    try {
      passTweet = service.postTweet(TweetUtil.createTweet("Testing Post Unit Test", outOfBoundsLong, 0.0f));

    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    // Latitude out of bounds
    Float outOfBoundsLat = -100f;

    try {
      passTweet = service.postTweet(TweetUtil.createTweet("Testing Post Unit Test", 50.0f, outOfBoundsLat));

    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }
  }

  @Test
  public void showTweet() {
    Tweet testTweet = new Tweet();
    testTweet.setText("Test");
    testTweet.setIdStr("12345678");
    String[] testFields = {"text", "id_str"};

    when(dao.findById(any())).thenReturn(testTweet);

    // Successful case
    Tweet responseTweet = service.showTweet(testTweet.getIdStr(), testFields);

    assertNotNull(responseTweet);
    assertNotNull(responseTweet.getText());
    assertNull(responseTweet.getCreatedAt());

    // Invalid field

    try {
      Tweet invalidTweet = service.showTweet(testTweet.getIdStr(), new String[]{"invalid"});

    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

  }

  @Test
  public void deleteTweets() {
    Tweet testTweet = new Tweet();
    testTweet.setText("Test");
    testTweet.setIdStr("12345678");

    when(dao.deleteById(any())).thenReturn(testTweet);

    // Successful delete
    List<Tweet> deletedTweets = service.deleteTweets(new String[]{testTweet.getIdStr()});
    assertNotNull(deletedTweets);
    assertEquals(1, deletedTweets.size());
    assertEquals(testTweet.getIdStr(), deletedTweets.get(0).getIdStr());
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteTweetInvalidId() {
    String[] ids = {"aaaaaaaaaaaaaaa"};
    service.deleteTweets(ids);
  }
}