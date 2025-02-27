package com.spring.dataprocessing.producer;

import com.spring.core.event.TrafficEvent;
import com.spring.core.event.WinTrafficEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class TrafficEventProducer {

    private static final Logger log = LoggerFactory.getLogger(TrafficEventProducer.class);
    private final ConversionService conversionService;
    private final KafkaTemplate<String, Object> kafkaTrafficTemplate;

    public void produce(WinTrafficEvent winTrafficEvent) {
        TrafficEvent trafficEvent = conversionService.convert(winTrafficEvent, TrafficEvent.class);
        log.atInfo().log("Producing traffic event: {}", trafficEvent);
        CompletableFuture<SendResult<String, Object>> future = kafkaTrafficTemplate.send("traffic-events-topic", trafficEvent);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.atError().log("Failed to send traffic event: {}", trafficEvent);
            } else {
                log.atInfo().log("Successfully sent traffic event: {}", trafficEvent);
            }
        });
    }
}
