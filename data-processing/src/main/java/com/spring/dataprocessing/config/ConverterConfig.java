package com.spring.dataprocessing.config;

import com.spring.core.event.TrafficEvent;
import com.spring.core.event.WinTrafficEvent;
import com.spring.dataprocessing.converter.WinTrafficEventToTrafficEventConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class ConverterConfig {

    @Bean
    public WinTrafficEventToTrafficEventConverter winTrafficEventToTrafficEventConverter() {
        return new WinTrafficEventToTrafficEventConverter();
    }

    @Bean
    public ConversionService conversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(WinTrafficEvent.class, TrafficEvent.class, winTrafficEventToTrafficEventConverter());
        return conversionService;
    }
}
