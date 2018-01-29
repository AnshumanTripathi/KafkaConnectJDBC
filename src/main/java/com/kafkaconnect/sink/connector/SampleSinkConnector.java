package com.kafkaconnect.sink.connector;

import com.kafkaconnect.configs.JDBCConnectorConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SampleSinkConnector extends SinkConnector {
    private static final Logger logger = Logger.getLogger(SampleSinkConnector.class.getName());
    private JDBCConnectorConfig config;

    @Override
    public String version() {
        return null;
    }

    @Override
    public void start(Map<String, String> map) {
        logger.info("Starting Task.");
        config = new JDBCConnectorConfig(map);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return SampleSinkTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int i) {
        System.out.println("In TaskConfigs");
        ArrayList<Map<String, String>> configs = new ArrayList<>();
        configs.add(config.originalsStrings());
        return configs;
    }

    @Override
    public void stop() {

    }

    @Override
    public ConfigDef config() {
        System.out.println("Setting up configurations");
        return JDBCConnectorConfig.conf();
    }
}
