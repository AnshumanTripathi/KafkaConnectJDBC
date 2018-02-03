# Kafka JDBC Connector


An implementation for the a Kafka JDBC connector to be used with Kafka Connect to be used with MySQL as Source and Pluggable Sink.

## Pre-requisites

- Confluent Platform 4.0
- MySQL

## Usage

 - Clone the repository
 - `mvn clean install`
 - `mvn clean package`
 - Rename `ConnectorSink.properites.example` to `ConnectorSink.properties` and update the configurations in it
 - Rename `ConnectorSource.properites.example` to `ConnectorSource.properties` and update the configurations in it
 - Rename `sink_worker.properites.example` to `sink_worker.properties` and update the configurations in it
 - Rename `source_worker.properites.example` to `source_worker.properties` and update the configurations in it
 - Start Source `./connect-standalone <Path-to-project>/kafka-connect-mysql-source/src/main/config/source_worker.properties <Path-to-project>/kafka-connect-mysql-source/src/main/config/ConnectorSource.properties `
 - Start Sink `./connect-standalone <Path-to-project>/Documents/kafka-connect-mysql-source/src/main/config/source_worker.properties <Path-to-project>/Documents/kafka-connect-mysql-source/src/main/config/ConnectorSource.properties `