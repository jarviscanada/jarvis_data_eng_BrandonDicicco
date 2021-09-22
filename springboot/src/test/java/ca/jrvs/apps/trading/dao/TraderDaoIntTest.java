package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
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
public class TraderDaoIntTest {

  @Autowired
  private TraderDao traderDao;

  private Trader savedTrader;
  private Trader savedTrader2;


  @Before
  public void insertTestData() {
    savedTrader = new Trader();
    savedTrader.setCountry("Canada");
    savedTrader.setDob(new Date(System.currentTimeMillis()));
    savedTrader.setEmail("brandondicicco97@gmail.com");
    savedTrader.setFirstName("Brandon");
    savedTrader.setLastName("DiCicco");
    traderDao.save(savedTrader);

    savedTrader2 = new Trader();
    savedTrader2.setCountry("Canada");
    savedTrader2.setDob(new Date(System.currentTimeMillis()));
    savedTrader2.setEmail("brandondicicco97@gmail.com");
    savedTrader2.setFirstName("Brandon");
    savedTrader2.setLastName("DiCicco");
    traderDao.save(savedTrader2);
  }

  @After
  public void deleteOne() {
    traderDao.deleteById(savedTrader.getId());
  }

  @Test
  public void findAllById() {
    List<Trader> traders = Lists.newArrayList(traderDao.findAllById(Arrays.asList(savedTrader.getId(), -1)));
    assertEquals(1, traders.size());
    assertEquals(savedTrader.getCountry(), traders.get(0).getCountry());
  }

  @Test
  public void count() {
    assertEquals(2, traderDao.count());
  }
}