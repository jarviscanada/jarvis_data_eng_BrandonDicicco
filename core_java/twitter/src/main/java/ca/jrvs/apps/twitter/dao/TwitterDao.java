package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TwitterDao implements CrdDao<Tweet, String> {

  // URI constants
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy/";

  // URI symbols
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  // HTTP response code
  private static final int HTTP_OK = 200;

  final Logger logger = LoggerFactory.getLogger(TwitterDao.class);

  private final HttpHelper httpHelper;


  @Autowired
  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  /**
   * Create an entity(Tweet) to the underlying storage
   *
   * @param entity entity that to be created
   * @return created entity
   */
  @Override
  public Tweet create(Tweet entity) {
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    String longitude = entity.getCoordinates().getCoordinates()[0].toString();
    String latitude = entity.getCoordinates().getCoordinates()[1].toString();

    String createTweet = API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + percentEscaper.escape(entity.getText()) + AMPERSAND
        + "long" + EQUAL + longitude + AMPERSAND + "lat" + EQUAL + latitude;

    URI uri = createUri(createTweet);
    HttpResponse response = httpHelper.httpPost(uri);
    return createTweetObject(response, HTTP_OK);
  }

  /**
   *  Convert an HTTP reponse to Tweet object
   * @param response HTTP response
   * @param expectedStatusCode expected status of the HTTP response
   * @return tweet object parsed from response
   */
  public Tweet createTweetObject(HttpResponse response, Integer expectedStatusCode) {
    Tweet tweet = null;

    int actualStatus = response.getStatusLine().getStatusCode();

    // If response is not OK
    if (actualStatus != expectedStatusCode) {
      try {
        logger.info(EntityUtils.toString(response.getEntity()));

      } catch (IOException ex) {
        logger.error("No entity from response");
      }

      throw new RuntimeException("Unexpected HTTP status: " + actualStatus);
    }

    if (response.getEntity() == null) {
      throw new RuntimeException("Response has no body");
    }

    // Convert entity to JSON string of the object
    String json;
    try {
      json = EntityUtils.toString(response.getEntity());

    } catch (IOException ex) {
      throw new RuntimeException("Could not convert entity to string", ex);
    }

    // Convert JSON to Tweet
    try {
      tweet = JsonParser.toObjectFromJson(json, Tweet.class);
    } catch (IOException ex) {
      throw new RuntimeException("Could not convert JSON string to Tweet object", ex);
    }

    return tweet;
  }

  /**
   * Method to create a URI object with custom logging
   * @param uri string to create URI
   * @return created URI
   */
  private URI createUri(String uri) {
    try {
      return new URI(uri);

    } catch (URISyntaxException ex) {
      logger.error("Error when creating new URI. Check formatting.", ex);
    }

    return null;
  }

  /**
   * Find an entity(Tweet) by its id
   *
   * @param tweetId entity id
   * @return Tweet entity
   */
  @Override
  public Tweet findById(String tweetId) {
    String findTweet = API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + tweetId;
    URI uri = createUri(findTweet);
    HttpResponse response = httpHelper.httpGet(uri);
    return createTweetObject(response, HTTP_OK);
  }

  /**
   * Delete an entity(Tweet) by its ID
   *
   * @param tweetId of the entity to be deleted
   * @return deleted entity
   */
  @Override
  public Tweet deleteById(String tweetId) {
    String deleteTweet = API_BASE_URI + DELETE_PATH + tweetId + ".json";
    URI uri = createUri(deleteTweet);
    HttpResponse response = httpHelper.httpPost(uri);
    return createTweetObject(response, HTTP_OK);
  }
}
