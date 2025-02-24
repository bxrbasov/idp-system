package com.spring.dataingestion.producer.Impl;

import com.spring.core.event.WinLogEvent;
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
public class WinLogEventProducer implements Producer {

    private static final Logger log = LogManager.getLogger(WinLogEventProducer.class);

    private final ConversionService conversionService;
    private final KafkaTemplate<String, Object> kafkaLogTemplate;

    @Override
    public void send(String source) {
        WinLogEvent winLogEvent = conversionService.convert(source, WinLogEvent.class);
        CompletableFuture<SendResult<String, Object>> future = kafkaLogTemplate.send("win-log-events-topic", winLogEvent);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.atError().log("Failed to send log event: {}", winLogEvent);
            } else {
                log.atInfo().log("Successfully sent log event: {}", winLogEvent);
            }
        });
    }
}
