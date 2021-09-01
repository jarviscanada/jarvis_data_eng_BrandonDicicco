package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataRetrievalFailureException;

public class MarketDataDaoIntTest {

  private MarketDataDao dao;

  @Before
  public void setUp() throws Exception {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(50);
    cm.setDefaultMaxPerRoute(50);
    MarketDataConfig marketDataConfig = new MarketDataConfig();
    marketDataConfig.setHost("https://cloud.iexapis.com/v1/");
    marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));

    dao = new MarketDataDao(cm, marketDataConfig);
  }

  @Test
  public void findById() {
    // Good path
    String ticker = "AAPL";
    IexQuote iexQuote = dao.findById(ticker).get();
    assertEquals(ticker, iexQuote.getSymbol());
  }

  @Test
  public void findAllById() throws IOException {
    // Good path
    List<IexQuote> quoteList = dao.findAllById(Arrays.asList("AAPL", "FB"));
    assertEquals(2, quoteList.size());
    assertEquals("AAPL", quoteList.get(0).getSymbol());

    // Bad path
    try {
      dao.findAllById(Arrays.asList("FB2", "AAPL"));
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    } catch (Exception ex) {
      fail("Wrong exception thrown");
    }
  }
}