package com.spring.dataingestion.config;

import com.spring.core.event.WinLogEvent;
import com.spring.core.event.WinTrafficEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final Environment environment;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.producer.bootstrap-servers"));
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, environment.getProperty("spring.kafka.producer.acks"));
        configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.delivery.timeout.ms"));
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.linger.ms"));
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.request.timeout.ms"));
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        return configProps;
    }

    @Bean
    public ProducerFactory<String, Object> producer() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Object> kafka() {
        return new KafkaTemplate<>(producer());
    }

    @Bean
    public NewTopic createLogEventTopic() {
        return TopicBuilder.name("win-log-events-topic")
                .partitions(3)
                .replicas(1)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

    @Bean
    public NewTopic createTrafficEventTopic() {
        return TopicBuilder.name("win-traffic-events-topic")
                .partitions(3)
                .replicas(1)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

}
