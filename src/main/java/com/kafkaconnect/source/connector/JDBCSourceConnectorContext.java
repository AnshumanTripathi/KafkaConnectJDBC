package com.kafkaconnect.source.connector;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicBoolean;

public class JDBCSourceConnectorContext {

    private static Connection conn = null;
    private static AtomicBoolean closed = new AtomicBoolean(false);
    private static JDBCSourceConnectorContext instance = null;
    private static long lastPolledId = 0;


    private JDBCSourceConnectorContext(){}

    public static JDBCSourceConnectorContext getInstance() {
        if (instance == null) {
            return new JDBCSourceConnectorContext();
        } else {
            return instance;
        }
    }

    public static Connection getConn() {
        return conn;
    }

    public static void setConn(Connection conn) {
        JDBCSourceConnectorContext.conn = conn;
    }

    public static AtomicBoolean isClosed() {
        return closed;
    }

    public static long getLastPolledId() {
        return lastPolledId;
    }

    public static void setLastPolledId(long lastPolledId) {
        JDBCSourceConnectorContext.lastPolledId = lastPolledId;
    }


}
