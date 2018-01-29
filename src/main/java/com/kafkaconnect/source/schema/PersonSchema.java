package com.kafkaconnect.source.schema;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class PersonSchema {

    private static final String ID = "id";
    private static final String NAME = "first_name";
    private static final String SCHEMA_NAME = "PersonSchema";

    public static final Schema PERSON_SCHEMA_KEY = SchemaBuilder.struct().name("com.kafkaconnect.source.connector")
            .version(1)
            .field(ID, Schema.INT32_SCHEMA)
            .optional()
            .build();

    public static final Schema PERSON_SCHEMA_VALUE = SchemaBuilder.struct().name(SCHEMA_NAME)
            .version(1)
            .field(ID, Schema.INT32_SCHEMA)
            .field(NAME, Schema.STRING_SCHEMA)
            .optional()
            .build();

    public static String getID() {
        return ID;
    }

    public static String getNAME() {
        return NAME;
    }

}
