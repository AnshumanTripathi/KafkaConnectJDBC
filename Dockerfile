FROM confluentinc/cp-kafka-connect:4.0.1-1

RUN mkdir /opt/jdbc-connector
ENV CONNECT_PLUGIN_PATH  /opt/jdbc-connector

COPY target/connector-jar-with-dependencies.jar  /opt/jdbc-connector
