package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

  @Mock
  TwitterService service;

  @InjectMocks
  TwitterController controller;

  @Test
  public void postTweet() {
    // Good case
    String goodText = "Test tweet" + System.currentTimeMillis();
    Float lon = 10f;
    Float lat = 20f;
    Tweet goodTweet = TweetUtil.createTweet(goodText, lon, lat);

    when(service.postTweet(any())).thenReturn(goodTweet);
    String[] goodArgs = {"post", goodText, lon + ":" + lat};

    Tweet postTweet = controller.postTweet(goodArgs);
    assertNotNull(postTweet);
    assertEquals(goodText, postTweet.getText());
    assertEquals(lon, postTweet.getCoordinates().getCoordinates()[0]);

    // Wrong # of args
    String[] wrongNumArgs = {"post"};
    try {
      controller.postTweet(wrongNumArgs);

    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    // Invalid # of coords
    String[] wrongCoordsArgs = {"post", goodText, lon + ""};
    try {
      controller.postTweet(wrongCoordsArgs);

    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    // Empty text
    String[] emptyTextArgs = {"post", "", lon + ":" + lat};
    try {
      controller.postTweet(emptyTextArgs);

    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    // Invalid coords format
    String[] invalidCoordsArgs = {"post", goodText, "asdjsa" + ":" + "oqwej"};
    try {
      controller.postTweet(invalidCoordsArgs);

    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

  }

  @Test
  public void showTweet() {
    // Good case
    String goodText = "Test tweet" + System.currentTimeMillis();
    Float lon = 10f;
    Float lat = 20f;
    Tweet goodTweet = TweetUtil.createTweet(goodText, lon, lat);
    goodTweet.setIdStr("100000");

    when(service.showTweet(any(), any())).thenReturn(goodTweet);
    String[] goodArgs = {"show", goodTweet.getIdStr(), "id,text"};

    Tweet showGoodTweet = controller.showTweet(goodArgs);
    assertNotNull(showGoodTweet);
    assertEquals(goodTweet.getId(), showGoodTweet.getId());
    assertEquals(goodTweet.getText(), showGoodTweet.getText());

    // Wrong # of args
    String[] badArgs = {"show"};

    try {
      controller.showTweet(badArgs);
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

  }

  @Test
  public void deleteTweet() {
    // Good case
    List<Tweet> deletedTweets = new ArrayList<>();
    String goodText = "Test tweet" + System.currentTimeMillis();
    Float lon = 10f;
    Float lat = 20f;
    Tweet deleteTweet = TweetUtil.createTweet(goodText, lon, lat);
    deleteTweet.setIdStr("10000");
    deletedTweets.add(deleteTweet);

    when(service.deleteTweets(any())).thenReturn(deletedTweets);
    String[] goodArgs = {"delete", deleteTweet.getIdStr()};

    List<Tweet> returnedDeletedTweets = controller.deleteTweet(goodArgs);
    assertEquals(1, returnedDeletedTweets.size());
    assertEquals(deleteTweet.getId(), returnedDeletedTweets.get(0).getId());

    // Wrong # of args
    String[] badArgs = {"delete"};

    try {
      controller.deleteTweet(badArgs);
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }
  }
}