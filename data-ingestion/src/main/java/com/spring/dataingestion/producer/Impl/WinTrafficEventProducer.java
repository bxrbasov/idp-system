package com.spring.dataingestion.producer.Impl;

import com.spring.core.event.WinTrafficEvent;
import com.spring.dataingestion.producer.Producer;
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
public class WinTrafficEventProducer implements Producer {

    private static final Logger log = LogManager.getLogger(WinTrafficEventProducer.class);

    private final ConversionService conversionService;
    private final KafkaTemplate<String, Object> kafkaTrafficTemplate;

    @Override
    public void send(String source) {
        WinTrafficEvent winTrafficEvent = conversionService.convert(source, WinTrafficEvent.class);
        CompletableFuture<SendResult<String, Object>> future = kafkaTrafficTemplate.send("win-traffic-events-topic", winTrafficEvent);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.atError().log("Failed to send traffic event: {}", winTrafficEvent);
            } else {
                log.atInfo().log("Successfully sent traffic event: {}", winTrafficEvent);
            }
        });
    }
}
