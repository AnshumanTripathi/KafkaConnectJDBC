package com.kafkaconnect.source.connector;

import com.kafkaconnect.configs.JDBCConnectorConfig;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class JDBCSourceConnectorContext {
  private static final Logger logger = Logger.getLogger(JDBCSourceConnectorContext.class.getName());

  private final AtomicBoolean isClosed = new AtomicBoolean();
  private final JDBCConnectorConfig config;

  private final Connection conn;
  private long lastPolledId;

  JDBCSourceConnectorContext (final Map<String, String> props) {
    this(new JDBCConnectorConfig(props));
  }

  JDBCSourceConnectorContext(final JDBCConnectorConfig config) {
    this.lastPolledId = 0L;
    this.config = config;
    isClosed.set(true);
    this.conn = connect();
  }

  private Connection connect() {
    if (this.conn != null) {
      return this.conn;
    }

    Connection conn = null;
    try {
      Class.forName(config.getString(JDBCConnectorConfig.getJdbcConnectorDriverClass()));
      final String url = getConnectionUrl(config);
      conn = DriverManager.getConnection(url, config.getString(JDBCConnectorConfig.getConnectionUser()),
          config.getPassword(JDBCConnectorConfig.getConnectionPassword()).value());
      isClosed.set(false);
    } catch (ClassNotFoundException | SQLException e) {
      logger.error("Exception encountered while connecting to database.", e);
    }
    return conn;
  }

  void disconnect() {
    isClosed.set(true);
    try {
      conn.close();
    } catch (SQLException e) {
      logger.error("Exception encountered while closing database connection");
    }
  }

  Connection getConnection() throws SQLException {
    if (conn == null) {
      throw new SQLException("Failed to create a successfull mysql connection");
    }
    return conn;
  }

  Boolean isClosed() {
    return isClosed.get();
  }

  void closeConnection() {
    isClosed.set(true);
  }

  long getLastPolledId() {
    return lastPolledId;
  }

  void setLastPolledId(long lastPolledId) {
    this.lastPolledId = lastPolledId;
  }

  private String getConnectionUrl(final JDBCConnectorConfig config) {
    return config.getString(JDBCConnectorConfig.getConnectionUrl()) +
        config.getString(JDBCConnectorConfig.getDatabase()) +
        config.getString(JDBCConnectorConfig.getConnectionUrlParams());
  }

}
