package com.spring.dataingestion.producer;

public interface Producer {

    void sendLogEvent(String logSource);

    void sendTrafficEvent(String trafficSource);
}
