package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import com.google.common.collect.Lists;
import java.sql.Date;
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
public class PositionDaoIntTest {

  @Autowired
  private SecurityOrderDao securityOrderDao;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private QuoteDao quoteDao;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private PositionDao positionDao;

  private SecurityOrder savedOrder;
  private Account savedAccount;
  private Quote savedQuote;
  private Trader savedTrader;

  @Before
  public void setUp() throws Exception {
    savedQuote = new Quote();
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("aapl");
    savedQuote.setLastPrice(10.1d);
    quoteDao.save(savedQuote);

    savedTrader = new Trader();
    savedTrader.setCountry("Canada");
    savedTrader.setDob(new Date(System.currentTimeMillis()));
    savedTrader.setEmail("brandondicicco97@gmail.com");
    savedTrader.setFirstName("Brandon");
    savedTrader.setLastName("DiCicco");
    traderDao.save(savedTrader);

    savedAccount = new Account();
    savedAccount.setTraderId(savedTrader.getId());
    savedAccount.setAmount(20.00);
    accountDao.save(savedAccount);

    savedOrder = new SecurityOrder();
    savedOrder.setAccountId(savedAccount.getId());
    savedOrder.setNotes("Order for Apple");
    savedOrder.setPrice(10.00);
    savedOrder.setSize(1);
    savedOrder.setTicker("aapl");
    savedOrder.setStatus("FILLED");
    securityOrderDao.save(savedOrder);
  }

  @After
  public void tearDown() throws Exception {
    securityOrderDao.deleteById(savedOrder.getId());
    accountDao.deleteById(savedAccount.getId());
    traderDao.deleteById(savedTrader.getId());
    quoteDao.deleteById(savedQuote.getId());
  }

  @Test
  public void findAllById() {
    List<Position> positions = Lists
        .newArrayList(positionDao.findAllById(savedAccount.getId()));
    assertEquals(1, positions.size());
    assertEquals(savedOrder.getSize(), positions.get(0).getPosition());
  }

  @Test
  public void findAll() {
    List<Position> positions = Lists
        .newArrayList(positionDao.findAll());
    assertEquals(1, positions.size());
    assertEquals(savedOrder.getSize(), positions.get(0).getPosition());
  }
}