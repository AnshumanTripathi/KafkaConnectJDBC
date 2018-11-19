package com.kafkaconnect.source.connector;

import com.kafkaconnect.configs.JDBCConnectorConfig;
import com.kafkaconnect.source.models.Person;
import com.kafkaconnect.source.schema.PersonSchema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JDBCSourceTask extends SourceTask {
  private static final Logger logger = Logger.getLogger(JDBCSourceTask.class.getName());
  private Map<String, String> props;
  private JDBCSourceConnectorContext context;

  /**
   * @return implementation version of Task
   */
  @Override
  public String version() {
    return JDBCSourceTask.class.getPackage().getImplementationVersion();
  }

  @Override
  public void start(Map<String, String> props) {
    this.props = props;
    this.context = new JDBCSourceConnectorContext(props);
  }

  @Override
  public List<SourceRecord> poll() {
    final ArrayList<Person> people = new ArrayList<>();
    try {
      final ResultSet rs = context.getConnection().createStatement()
          .executeQuery(String.format("SELECT * FROM person WHERE id > %s", context.getLastPolledId()));
      while (rs.next()) {
        final Person person = new Person(rs.getInt("id"), rs.getString("first_name"));
        people.add(person);
      }
    } catch (SQLException e) {
      logger.error("Exception encountered while fetching records from the database", e);
    }
    return generateSourceRecords(people);
  }


  @Override
  public void stop() {
    context.closeConnection();
    context.disconnect();
  }

  private List<SourceRecord> generateSourceRecords(List<Person> people) {
    final ArrayList<SourceRecord> records = new ArrayList<>();
    if (!context.isClosed()) {
      final HashMap<String, String> sourcePartitionMap = new HashMap<>();
      sourcePartitionMap.put("table", props.get("table"));
      final HashMap<String, String> sourceOffsetMap = new HashMap<>();
      sourceOffsetMap.put("Id", JDBCConnectorConfig.getID());

      for (Person person : people) {
        context.setLastPolledId(person.getId());
        records.add(new SourceRecord(
            sourcePartitionMap,
            sourceOffsetMap,
            JDBCConnectorConfig.getTOPIC(),
            null,
            PersonSchema.PERSON_SCHEMA_KEY,
            buildKey(person),
            PersonSchema.PERSON_SCHEMA_VALUE,
            buildRecordValue(person)
        ));
      }
    }
    return records;

  }


  private Struct buildKey(Person person) {
    return new Struct(PersonSchema.PERSON_SCHEMA_KEY)
        .put("id", (person.getId()));
  }

  private Struct buildRecordValue(Person person) {
    return new Struct(PersonSchema.PERSON_SCHEMA_VALUE)
        .put(PersonSchema.getID(), (person.getId()))
        .put(PersonSchema.getNAME(), person.getFirstName());
  }
}
