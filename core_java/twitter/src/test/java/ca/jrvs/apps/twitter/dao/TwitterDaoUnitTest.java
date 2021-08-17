package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

  @Mock
  HttpHelper mockHelper;

  @InjectMocks
  TwitterDao dao;

  static final String tweetJsonStr = "{\n"
      + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
      + "   \"id\":1097607853932564480,\n"
      + "   \"id_str\":\"1097607853932564480\",\n"
      + "   \"text\":\"test with loc223\",\n"
      + "   \"entities\":{\n"
      + "        \"hashtags\":[],\n"
      + "        \"user_mentions\":[]\n"
      + "   },\n"
      + "   \"coordinates\":null,\n"
      + "   \"retweet_count\":0,\n"
      + "   \"favorite_count\":0,\n"
      + "   \"favorited\":false,\n"
      + "   \"retweeted\":false\n"
      + "}";

  @Test
  public void postTweet() throws Exception {
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

    // Test failed request
    when (mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("Mock"));

    try {
      dao.create(postTweet);
      fail();

    } catch (RuntimeException ex) {
      assertTrue(true);
    }

    // Test successful tweet
    // Mock the createTweetObject() method with a Spy
    when (mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);

    doReturn(expectedTweet).when(spyDao).createTweetObject(any(), anyInt());
    Tweet tweet = spyDao.create(postTweet);
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void deleteTweet() throws Exception {
    // Test failure case
    when (mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("Mock"));
    try {
      dao.deleteById("");
      fail();

    } catch (RuntimeException ex) {
      assertTrue(true);
    }

    // Test successful case
    when (mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);

    doReturn(expectedTweet).when(spyDao).createTweetObject(any(), anyInt());
    Tweet deletedTweet = spyDao.deleteById("1097607853932564480");
    assertNotNull(deletedTweet);
    assertNotNull(deletedTweet.getText());
    assertEquals("1097607853932564480", deletedTweet.getIdStr());

  }

  @Test
  public void showTweet() throws Exception {
    // Test failure case
    when (mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("Mock"));
    try {
      dao.findById("");
      fail();

    } catch (RuntimeException ex) {
      assertTrue(true);
    }

    // Test successful case
    when (mockHelper.httpGet(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);

    doReturn(expectedTweet).when(spyDao).createTweetObject(any(), anyInt());
    Tweet findTweet = spyDao.deleteById("1097607853932564480");
    assertNotNull(findTweet);
    assertNotNull(findTweet.getText());
    assertEquals("1097607853932564480", findTweet.getIdStr());
  }
}