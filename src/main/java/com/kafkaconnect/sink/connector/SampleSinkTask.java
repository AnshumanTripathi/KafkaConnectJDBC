package com.kafkaconnect.sink.connector;

import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

public class SampleSinkTask extends SinkTask {
    private static final Logger logger = Logger.getLogger(SampleSinkConnector.class.getName());
    @Override
    public String version() {
        return null;
    }

    @Override
    public void start(Map<String, String> map) {
        logger.info("In Sink Task");
    }

    @Override
    public void put(Collection<SinkRecord> collection) {
        Iterator<SinkRecord> iterator = collection.iterator();
        while(iterator.hasNext()) {
            //Received Record. Process it or forward to
            logger.info("Received Value :" + String.valueOf(iterator.next()));
        }
    }

    @Override
    public void stop() {

    }
}
