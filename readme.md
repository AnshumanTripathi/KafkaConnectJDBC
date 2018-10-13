# Kafka JDBC Connector


An implementation for the a Kafka JDBC connector to be used with Kafka Connect to be used with MySQL as Source and Pluggable Sink.

## Pre-requisites

- Docker

## Usage

 - Clone the repository
 - Execute the following script to build and start the Kafka Connect pipeline
 ```
    kafka-connect-mysql-source$ chmod u+x build_and_deploy.sh
    $ ./build_and_deploy.sh
 ```
 - Logs can be monitored using 
 ```
    $ docker container logs -f kafka-connect
 ```
 - Register source connector
 ```
    kafka-connect-mysql-source$ curl -X POST -H "Content-Type: application/json" -d "@src/main/resources/jdbc-source.json" http://localhost:8083/connectors
 ```
 This would register the connector and will create a topic `jdbc-source` if none is provided in the configs.
 - Register sink connector
 ```
     kafka-connect-mysql-source$ curl -X POST -H "Content-Type: application/json" -d "@src/main/resources/jdbc-sink.json" http://localhost:8083/connectors
  ```
  - To stop the pipeline 
  ```
    kafka-connect-mysql-source$ docker-compose down
  ```