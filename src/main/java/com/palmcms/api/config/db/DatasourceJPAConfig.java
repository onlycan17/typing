package com.palmcms.api.config.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author by dotkebi on 2017. 5. 5..
 */
@Configuration
@EnableTransactionManagement
public class DatasourceJPAConfig {

  @Autowired
  private PoolProperties poolProperties;

  @Bean
  @Primary
  public DataSource dataSource() {
    return HikariDatasourceBuilder.build(poolProperties);
  }

}
