package com.spring.dataprocessing.handler;

import com.spring.core.event.WinTrafficEvent;
import com.spring.dataprocessing.processor.DataProcessor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "win-traffic-events-topic")
@RequiredArgsConstructor
public class WinTrafficEventHandler {

    private static final Logger log = LoggerFactory.getLogger(WinTrafficEventHandler.class);
    private final DataProcessor dataProcessor;

    @KafkaHandler
    public void handle(WinTrafficEvent winTrafficEvent) {
        log.atInfo().log("Received traffic event.");
        dataProcessor.filter(winTrafficEvent);
    }
}
