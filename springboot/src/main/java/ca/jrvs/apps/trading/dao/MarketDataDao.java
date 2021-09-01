package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.helpers.JsonParser;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
  private final String IEX_BATCH_URL;

  private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
  private HttpClientConnectionManager httpClientConnectionManager;

  private static final int HTTP_NOT_FOUND = 404;
  private static final int HTTP_OK = 200;

  @Autowired
  public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
    this.httpClientConnectionManager = httpClientConnectionManager;
    IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
  }



  @Override
  public Optional<IexQuote> findById(String ticker) {
    Optional<IexQuote> iexQuote;
    List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

    if (quotes.size() == 0) {
      return Optional.empty();
    } else if (quotes.size() == 1) {
      iexQuote = Optional.of(quotes.get(0));
    } else {
      throw new DataRetrievalFailureException("Unexpected number of quotes");
    }

    return iexQuote;
  }

  @Override
  public List<IexQuote> findAllById(Iterable<String> tickers) {
    String tickerSymbols = String.join(",", tickers);
    String url = String.format(IEX_BATCH_URL, tickerSymbols);

    //HTTP response
    String response = executeHttpGet(url)
        .orElseThrow(() -> new IllegalArgumentException("Invalid ticker"));

    //Array of JSON documents
    JSONObject IexQuotesJson = new JSONObject(response);

    //Get number of documents
    if (IexQuotesJson.length() == 0) {
      throw new IllegalArgumentException("Invalid ticker");
    }

    List<IexQuote> quotes = new ArrayList<>();

    for (String ticker : tickers) {
      if (IexQuotesJson.getJSONObject(ticker).getJSONObject("quote").toString().equals("null")) {
        throw new IllegalArgumentException("Given ticker is invalid - " + ticker);
      }

      try {
        String quote = IexQuotesJson.getJSONObject(ticker).getJSONObject("quote").toString();
        IexQuote quoteObj = JsonParser.toObjectFromJson(quote, IexQuote.class);
        quotes.add(quoteObj);

      } catch (IOException ex) {
        logger.error("Unable to deserialize JSON", ex);
      }
    }

    return quotes;
  }


  private Optional<String> executeHttpGet(String url) throws DataRetrievalFailureException {
    URI uri = null;

    try {
      uri = new URI(url);

    } catch (URISyntaxException ex) {
      logger.error("Error when creating new URI using " + url, ex);
    }

    HttpGet request = new HttpGet(uri);
    HttpResponse response = null;

    try {
      response = getHttpClient().execute(request);

    } catch (IOException ex) {
      logger.error("Error when executing HTTP GET request", ex);
    }

    String responseString = getResponseBody(response);

    if (responseString == null) {
      return Optional.empty();
    }

    return Optional.of(responseString);
  }

  private String getResponseBody(HttpResponse response) {
    int actualStatus = response.getStatusLine().getStatusCode();

    if (actualStatus == HTTP_NOT_FOUND) {
      return null;
    }

    if (actualStatus != HTTP_OK) {
      try {
        logger.info(EntityUtils.toString(response.getEntity()));

      } catch (IOException ex) {
        logger.error("No entity from response");
      }
      throw new DataRetrievalFailureException("HTTP Response not OK. Response is " + actualStatus);
    }

    if (response.getEntity() == null) {
      throw new DataRetrievalFailureException("Response has no body");
    }

    String responseJson;
    try {
      responseJson = EntityUtils.toString(response.getEntity());

    } catch (IOException ex) {
      throw new DataRetrievalFailureException("Could not convert entity to string", ex);
    }

    return responseJson;
  }

  private CloseableHttpClient getHttpClient() {
    return HttpClients.custom()
        .setConnectionManager(httpClientConnectionManager)
        .setConnectionManagerShared(true)
        .build();
  }

  @Override
  public boolean existsById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public Iterable<IexQuote> findAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public long count() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(IexQuote iexQuote) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends IexQuote> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public <S extends IexQuote> S save(S s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
