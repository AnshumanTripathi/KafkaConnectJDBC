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
    private JDBCConnectorConfig config;
    private static final Logger logger = Logger.getLogger(SampleSinkConnector.class.getName());

    /***
     * @return version of the Connector
     */
    @Override
    public String version() {
        return null;
    }

    /**
     * @param map Configs parsed from the Config File
     */
    @Override
    public void start(Map<String, String> map) {
        logger.info("Checking for topic "+map.get(JDBCConnectorConfig.getTOPIC()));
        config = new JDBCConnectorConfig(map);
    }

    /**
     * @return Returns the Task implementation for this Connector.
     */
    @Override
    public Class<? extends Task> taskClass() {
        return SampleSinkTask.class;
    }

    /**
     *
     * @param i Max Tasks
     * @return Returns a set of configurations for Tasks based on the current configuration, producing at most count configurations.
     */

    @Override
    public List<Map<String, String>> taskConfigs(int i) {
        ArrayList<Map<String, String>> configs = new ArrayList<>();
        configs.add(config.originalsStrings());
        return configs;
    }

    @Override
    public void stop() {

    }

    /**
     * Validate the connector configuration values against configuration definitions.
     * @return List of Config, each Config contains the updated configuration information given the current configuration values.
     */
    @Override
    public ConfigDef config() {
        logger.info("Setting up configurations");
        return JDBCConnectorConfig.conf();
    }
}
