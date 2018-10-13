package com.kafkaconnect.source.connector;

import com.kafkaconnect.configs.JDBCConnectorConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JDBCSourceConnector extends SourceConnector {

    private JDBCSourceConnectorContext context;
    private JDBCConnectorConfig config;


    @Override
    public String version() {
        return null;
    }

    @Override
    public void start(Map<String, String> map) {
        config = new JDBCConnectorConfig(map);
        context = new JDBCSourceConnectorContext(map);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return JDBCSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int i) {
        ArrayList<Map<String, String>> configs = new ArrayList<>();
        configs.add(config.originalsStrings());
        return configs;
    }

    @Override
    public void stop() {
        context.disconnect();
        //End DB connections or stop source connector or something
    }

    @Override
    public ConfigDef config() {
        return JDBCConnectorConfig.conf();
    }
}
