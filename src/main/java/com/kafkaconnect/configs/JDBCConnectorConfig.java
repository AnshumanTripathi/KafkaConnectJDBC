package com.kafkaconnect.configs;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class JDBCConnectorConfig extends AbstractConfig {

  private static final String TOPIC = "jdbc-source";
  private static final String TOPIC_DOC = "Kafka Topic to write to";

  private static final String ID = "user_id";
  private static final String ID_DOC = "User Id";

  private static final String FIRST_NAME = "first_name";
  private static final String FIRST_NAME_DOC = "User's first name";

  private static final String CONNECTION_URL = "connection.url";
  private static final String CONNECTION_URL_DOC = "JDBC Connection URL";

  private static final String CONNECTION_USER = "connection.user";
  private static final String CONNECTION_USER_DOC = "DB User";

  private static final String CONNECTION_PASSWORD = "connection.password";
  private static final String CONNECTION_PASSWORD_DOC = "Connection Password";

  private static final String CONNECTION_CLASS = "connector.class";
  private static final String CONNECTION_CLASS_DOC = "Connector Class";

  private static final String INCREMENTING_COLUMN_NAME = "incrementing_column_name";
  private static final String INCREMENTING_COLUMN_NAME_DOC = "Incrementing Column";

  public JDBCConnectorConfig(ConfigDef definition, Map<String, String> parsedConfig) {
    super(definition, parsedConfig);
  }

  public JDBCConnectorConfig(Map<String, String> parsedConfig) {
   this(conf(), parsedConfig);
  }

  public static ConfigDef conf() {
    return new ConfigDef()
            .define(TOPIC, ConfigDef.Type.STRING, "jdbc-source",ConfigDef.Importance.HIGH, TOPIC_DOC)
            .define(CONNECTION_URL, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, CONNECTION_URL_DOC)
            .define(CONNECTION_USER, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, CONNECTION_USER_DOC)
            .define(CONNECTION_PASSWORD, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, CONNECTION_PASSWORD_DOC)
            .define(CONNECTION_CLASS, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, CONNECTION_CLASS_DOC)
            .define(INCREMENTING_COLUMN_NAME, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, INCREMENTING_COLUMN_NAME_DOC);

  }

  public static String getTOPIC() {
    return TOPIC;
  }

  public static String getID() {
    return ID;
  }

  public static String getFirstName() {
    return FIRST_NAME;
  }
}
