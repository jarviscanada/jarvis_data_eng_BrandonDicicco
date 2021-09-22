package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {

  private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);

  private static final String VIEW_NAME = "position";
  private static final String ID_COLUMN = "account_id";

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public PositionDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public String getTableName() {
    return VIEW_NAME;
  }

  public String getIdColumnName() {
    return ID_COLUMN;
  }

  Class<Position> getEntityClass() {
    return Position.class;
  }

  public List<Position> findAllById(Integer id) {
    String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + ID_COLUMN + " =?";
    return getJdbcTemplate().query(selectSql, BeanPropertyRowMapper.newInstance(getEntityClass()), id);
  }

  public List<Position> findAll() {
    String selectSql = "SELECT * FROM " + getTableName();
    return getJdbcTemplate().query(selectSql, BeanPropertyRowMapper.newInstance(getEntityClass()));
  }
}
