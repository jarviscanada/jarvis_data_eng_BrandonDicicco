package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.util.Lists;
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
public class AccountDaoIntTest {

  @Autowired
  private AccountDao accountDao;

  @Autowired TraderDao traderDao;

  private Account savedAccount;
  private Trader savedTrader;

  @Before
  public void setUp() throws Exception {
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
  }

  @After
  public void tearDown() throws Exception {
    accountDao.deleteById(savedAccount.getId());
    traderDao.deleteById(savedTrader.getId());

  }

  @Test
  public void findAllById() {
    List<Account> accounts = Lists
        .newArrayList(accountDao.findAllById(Arrays.asList(savedAccount.getId(), -1)));
    assertEquals(1, accounts.size());
    assertEquals(savedAccount.getAmount(), accounts.get(0).getAmount());
  }

  @Test
  public void updateAccountBalance() {
    assertEquals(savedAccount.getAmount(), accountDao.findById(savedAccount.getId()).get().getAmount());
    accountDao.updateAccountBalance(savedAccount.getId(), 50.00);
    assertEquals(50.00, accountDao.findById(savedAccount.getId()).get().getAmount(), 0);
  }
}