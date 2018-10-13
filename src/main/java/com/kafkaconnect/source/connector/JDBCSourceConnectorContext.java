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
    private static final String CONNECTION_PARAMAS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_URL = JDBCConnectorConfig.getConnectionUrl() +
            JDBCConnectorConfig.getDatabase() + CONNECTION_PARAMAS;

    private final AtomicBoolean isClosed = new AtomicBoolean();

    private final Connection conn;
    private long lastPolledId;

    JDBCSourceConnectorContext(final Map<String, String> props) {
        this.lastPolledId = 0L;
        isClosed.set(true);
        conn = connect(props);
    }

    private Connection connect(Map<String, String> props)  {
        Connection conn = null;
        try {
            Class.forName(DRIVER_CLASS_NAME);
            String url = props.get(JDBCConnectorConfig.getConnectionUrl()) +
                    props.get(JDBCConnectorConfig.getDatabase()) + CONNECTION_PARAMAS;
            logger.info("Connection URL" + url);
            conn = DriverManager.getConnection(url, props.get(JDBCConnectorConfig.getConnectionUser()),
                    props.get(JDBCConnectorConfig.getConnectionPassword()));
            isClosed.set(false);
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Exception encountered while connecting to database.", e);
        }
        return conn;
    }

    public void disconnect() {
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

}
