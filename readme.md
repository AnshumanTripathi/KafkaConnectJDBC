# Kafka JDBC Connector


An implementation for the a Kafka JDBC connector to be used with Kafka Connect to be used with MySQL as Source and Pluggable Sink.

## Pre-requisites

- Confluent Platform 4.0
- MySQL

## Usage

 - Clone the repository
 - `mvn clean install`
 - `mvn clean package`
 - Start Source `./connect-standalone ~/Documents/kafka-connect-mysql-source/src/main/config/source_worker.properties ~/Documents/kafka-connect-mysql-source/src/main/config/ConnectorSource.properties `
 - Start Sink `./connect-standalone ~/Documents/kafka-connect-mysql-source/src/main/config/source_worker.properties ~/Documents/kafka-connect-mysql-source/src/main/config/ConnectorSource.properties `