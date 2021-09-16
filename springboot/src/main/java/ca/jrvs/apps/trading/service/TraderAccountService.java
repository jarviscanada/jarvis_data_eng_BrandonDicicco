package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  private TraderDao traderDao;
  private AccountDao accountDao;
  private PositionDao positionDao;
  private SecurityOrderDao securityOrderDao;

  @Autowired
  public TraderAccountService(TraderDao traderDao, AccountDao accountDao, PositionDao positionDao, SecurityOrderDao securityOrderDao) {
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.positionDao = positionDao;
    this.securityOrderDao = securityOrderDao;
  }

  public TraderAccountView createTraderAndAccount(Trader trader) {
    if (trader == null || trader.getId() != null || trader.getCountry() == null || trader.getDob() == null ||
        trader.getEmail() == null || trader.getFirstName() == null || trader.getLastName() == null) {
      throw new IllegalArgumentException("Trader cannot have null fields except for ID");
    }

    Trader createdTrader = traderDao.save(trader);
    Account account = new Account();
    account.setAmount(0d);
    account.setTraderId(createdTrader.getId());
    accountDao.save(account);

    TraderAccountView traderAccountView = new TraderAccountView();
    traderAccountView.setAccount(account);
    traderAccountView.setTrader(createdTrader);

    return traderAccountView;
  }

  public void deleteTraderById(Integer traderId) {
    if (traderId == null || !traderDao.existsById(traderId)) {
      throw new IllegalArgumentException("TraderID must be valid and exist");
    }

    Account traderAccount = accountDao.findById(traderId).get();

    if (traderAccount.getAmount() != 0) {
      throw new IllegalArgumentException("Cannot delete a trader whose balance is >0");
    }

    List<Position> traderPositions = positionDao.findAllById(traderId);

    for (Position position : traderPositions) {
      if (position.getPosition() > 0) {
        throw new IllegalArgumentException("All positions for the trader to be deleted must be closed");
      }
    }

    securityOrderDao.deleteByAccountId(traderAccount.getId());
    accountDao.deleteById(traderAccount.getId());
    traderDao.deleteById(traderId);
  }

  public Account deposit(Integer traderId, Double fund) {
    if (traderId == null || fund <= 0 || !accountDao.existsById(traderId)) {
      throw new IllegalArgumentException("TraderID must exist and be valid. Funds to deposit must be > 0");
    }

    Account account = accountDao.findById(traderId).get();

    Double updatedBalance = account.getAmount() + fund;

    account.setAmount(updatedBalance);
    accountDao.updateAccountBalance(traderId, updatedBalance);

    return account;
  }

  public Account withdraw(Integer traderId, Double fund) {
    if (traderId == null || fund <= 0 || !accountDao.existsById(traderId)) {
      throw new IllegalArgumentException("TraderID must exist and be valid. Funds to withdraw must be > 0");
    }

    Account account = accountDao.findById(traderId).get();

    Double updatedBalance = account.getAmount() - fund;

    if (updatedBalance < 0) {
      throw new IllegalArgumentException("Not enough funds to withdraw. Available funds: " + account.getAmount());
    }

    account.setAmount(updatedBalance);
    accountDao.updateAccountBalance(traderId, updatedBalance);

    return account;

  }
}
