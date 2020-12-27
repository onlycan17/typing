package com.palmcms.api.config.db;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * @author by dotkebi on 2018. 7. 22..
 */
public class HikariDatasourceBuilder {

  public static DataSource build(PoolProperties poolProperties) {
    var dataSource = new HikariDataSource();

    dataSource.setJdbcUrl(poolProperties.getJdbcUrl());
    dataSource.setUsername(poolProperties.getUsername());
    dataSource.setPassword(poolProperties.getPassword());
    dataSource.setDriverClassName(poolProperties.getDriverClassName());
    dataSource.setAutoCommit(poolProperties.getHikari().isAutoCommit());

    dataSource.setMaximumPoolSize(poolProperties.getHikari().getMaximumPoolSize());
    dataSource.setMinimumIdle(poolProperties.getHikari().getMinimumIdle());

    return dataSource;
  }

}
