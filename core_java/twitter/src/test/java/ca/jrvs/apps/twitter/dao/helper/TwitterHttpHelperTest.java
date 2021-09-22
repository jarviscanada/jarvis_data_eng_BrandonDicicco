package ca.jrvs.apps.twitter.dao.helper;

import static org.junit.Assert.*;

import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class TwitterHttpHelperTest {
  TwitterHttpHelper twitterHttpHelper;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    twitterHttpHelper =  new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
  }

  @Test
  public void httpPost() throws Exception {
    URI postRequest = new URI("https://api.twitter.com/1.1/statuses/update.json?status=POST_Request_Test10");
    HttpResponse response = twitterHttpHelper.httpPost(postRequest);
    assertEquals(200, response.getStatusLine().getStatusCode());
  }

  @Test
  public void httpGet() throws Exception {
    URI getRequest = new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=BrandonDiCicco1");
    HttpResponse response = twitterHttpHelper.httpGet(getRequest);
    assertEquals(200, response.getStatusLine().getStatusCode());
  }
}