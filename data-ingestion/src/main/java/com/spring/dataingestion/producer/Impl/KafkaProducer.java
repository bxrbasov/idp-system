package com.spring.dataingestion.producer.Impl;

import com.spring.dataingestion.event.WinLogEvent;
import com.spring.dataingestion.event.WinTrafficEvent;
import com.spring.dataingestion.producer.Producer;
import com.spring.dataingestion.services.Impl.WinPcapNetworkTrafficCapture;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.ConversionService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaProducer implements Producer {

    private static final Logger log = LogManager.getLogger(KafkaProducer.class);

    private final ConversionService conversionService;
    private final KafkaTemplate<String, WinTrafficEvent> kafkaTrafficTemplate;
    private final KafkaTemplate<String, WinLogEvent> kafkaLogTemplate;

    @Override
    public void sendLogEvent(String logSource) {
        WinLogEvent winLogEvent = conversionService.convert(logSource, WinLogEvent.class);
        CompletableFuture<SendResult<String, WinLogEvent>> future = kafkaLogTemplate.send("log-events-topic", winLogEvent);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.atError().log("Failed to send log event: {}", winLogEvent);
            } else {
                log.atInfo().log("Successfully sent log event: {}", winLogEvent);
            }
        });
    }

    @Override
    public void sendTrafficEvent(String trafficSource) {
        WinTrafficEvent winTrafficEvent = conversionService.convert(trafficSource, WinTrafficEvent.class);
        CompletableFuture<SendResult<String, WinTrafficEvent>> future = kafkaTrafficTemplate.send("traffic-events-topic", winTrafficEvent);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.atError().log("Failed to send traffic event: {}", winTrafficEvent);
            } else {
                log.atInfo().log("Successfully sent traffic event: {}", winTrafficEvent);
            }
        });
    }
}
