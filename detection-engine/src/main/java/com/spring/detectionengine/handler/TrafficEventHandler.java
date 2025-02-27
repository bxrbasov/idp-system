package com.spring.detectionengine.handler;

import com.spring.core.event.TrafficEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "traffic-events-topic")
public class TrafficEventHandler {

    private static final Logger log = LoggerFactory.getLogger(TrafficEventHandler.class);

    @KafkaHandler
    public void handle(TrafficEvent TrafficEvent) {
        log.atInfo().log("Received traffic event: {}", TrafficEvent);
    }
}
