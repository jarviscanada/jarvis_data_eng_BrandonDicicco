package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import java.sql.Date;
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
public class TraderAccountServiceIntTest {

  private TraderAccountView savedView;
  private Trader trader;

  @Autowired
  private TraderAccountService traderAccountService;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private AccountDao accountDao;

  @Before
  public void setUp() throws Exception {
    trader = new Trader();
    trader.setCountry("Canada");
    trader.setDob(new Date(System.currentTimeMillis()));
    trader.setEmail("brandondicicco97@gmail.com");
    trader.setFirstName("Brandon");
    trader.setLastName("DiCicco");

    savedView = traderAccountService.createTraderAndAccount(trader);
  }

  @After
  public void tearDown() throws Exception {
    accountDao.deleteAll();
    traderDao.deleteAll();
  }

  @Test
  public void deleteTraderById() {
    int traderId = savedView.getTrader().getId();

    try {
      traderAccountService.deleteTraderById(5000);
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    } catch (Exception ex) {
      fail();
    }

    try {
      traderAccountService.deleteTraderById(traderId);
    } catch (IllegalArgumentException ex) {
      fail();
    } catch (Exception ex) {
      fail();
    }
  }

  @Test
  public void depositWithdraw() {
    int traderId = savedView.getTrader().getId();
    assertEquals(0, accountDao.findById(traderId).get().getAmount(), 0);

    traderAccountService.deposit(traderId, 50.00);

    assertEquals(50, accountDao.findById(traderId).get().getAmount(), 0);

    traderAccountService.withdraw(traderId, 30.00);

    assertEquals(20, accountDao.findById(traderId).get().getAmount(), 0);
  }
}