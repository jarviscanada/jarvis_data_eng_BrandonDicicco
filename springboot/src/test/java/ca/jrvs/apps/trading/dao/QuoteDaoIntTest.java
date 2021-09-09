package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao quoteDao;

  private Quote savedQuote;
  private Quote savedQuote2;

  @Before
  public void insertOne() {
    savedQuote = new Quote();
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("aapl");
    savedQuote.setLastPrice(10.1d);
    quoteDao.save(savedQuote);

    savedQuote2 = new Quote();
    savedQuote2.setAskPrice(10d);
    savedQuote2.setAskSize(10);
    savedQuote2.setBidPrice(10.2d);
    savedQuote2.setBidSize(10);
    savedQuote2.setId("msft");
    savedQuote2.setLastPrice(10.1d);
    quoteDao.save(savedQuote2);
  }

  @Test
  public void findById() {
    Optional<Quote> testFind = quoteDao.findById("aapl");
    assertTrue(testFind.isPresent());
    assertEquals(10,testFind.get().getAskSize(), 0.0);
  }

  @Test
  public void existsById() {
    boolean testFind = quoteDao.existsById("aapl");
    assertTrue(testFind);
  }


  @Test
  public void count() {
    long foundCount = quoteDao.count();
    assertEquals(2, foundCount);
  }
  @After
  public void deletAll() {
    quoteDao.deleteAll();
  }
}
