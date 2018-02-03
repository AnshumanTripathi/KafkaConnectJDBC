package com.kafkaconnect.source.connector;

import com.kafkaconnect.source.models.Person;
import com.kafkaconnect.configs.JDBCConnectorConfig;
import com.kafkaconnect.source.schema.PersonSchema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JDBCSourceTask extends SourceTask {
    private static Connection conn;

    @Override
    public String version() {
        return null;
    }

    @Override
    public void start(Map<String, String> map) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");
            JDBCSourceConnectorContext.setConn(conn);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {

        ArrayList<Person> people = new ArrayList<>();

        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM person WHERE id > " + JDBCSourceConnectorContext.getLastPolledId());
            while (rs.next()) {

                people.add(new Person(rs.getInt("id"), rs.getString("first_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generateSourceRecords(people);
    }


    @Override
    public void stop() {
        JDBCSourceConnectorContext.isClosed().set(true);
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<SourceRecord> generateSourceRecords(List<Person> people) {
        ArrayList<SourceRecord> records = new ArrayList<>();

        if (!JDBCSourceConnectorContext.isClosed().get()) {
            HashMap<String, String> sourcePartitionMap = new HashMap<>();
            sourcePartitionMap.put("table", "person");
            HashMap<String, String> sourceOffsetMap = new HashMap<>();
            sourceOffsetMap.put("Id", JDBCConnectorConfig.getID());

            for (Person person : people) {
                JDBCSourceConnectorContext.setLastPolledId(person.getId());
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
                .put("id", ((int) person.getId()));
    }

    private Struct buildRecordValue(Person person) {
        return new Struct(PersonSchema.PERSON_SCHEMA_VALUE)
                .put(PersonSchema.getID(), ((int) person.getId()))
                .put(PersonSchema.getNAME(), person.getFirstName());
    }

    public static Connection getConn() {
        return conn;
    }
}
