package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.ResourceNotFoundException;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteDao quoteDao;

  private Quote savedQuote;

  @Before
  public void setUp() throws Exception {
    quoteDao.deleteAll();

    savedQuote = new Quote();
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("AAPL");
    savedQuote.setLastPrice(10.1d);

    quoteService.saveQuote(savedQuote);
  }

  @Test
  public void updateMarketData() throws ResourceNotFoundException {
    quoteService.updateMarketData();

    assertTrue(quoteDao.findById("AAPL").isPresent());
  }

  @Test
  public void saveQuotes() {
    List<String> tickers = new ArrayList<>();
    tickers.add("AAPL");
    tickers.add("AMZN");

    assertEquals(2, quoteService.saveQuotes(tickers).size());
  }

  @Test
  public void saveQuote() {
    savedQuote = new Quote();
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("AMZN");
    savedQuote.setLastPrice(10.1d);
    quoteService.saveQuote(savedQuote);

    assertEquals(2, quoteService.findAllQuotes().size());
  }

  @Test
  public void findIexQuoteByTicker() {
    assertEquals("AAPL", quoteService.findIexQuoteByTicker("AAPL").getSymbol());

    try {
      quoteService.findIexQuoteByTicker("ASJD");
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    } catch (Exception ex) {
      fail();
    }
  }

  @Test
  public void findAllQuotes() {
    assertEquals(1, quoteService.findAllQuotes().size());
  }

  @After
  public void delete() {
    quoteDao.deleteAll();
  }
}