package com.spring.dataingestion.config;

import com.spring.core.event.WinLogEvent;
import com.spring.core.event.WinTrafficEvent;
import com.spring.dataingestion.converter.StringToWinLogEventConverter;
import com.spring.dataingestion.converter.StringToWinTrafficEventConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class ConverterConfig {

    @Bean
    public StringToWinLogEventConverter stringToWinLogEventConverter() {
        return new StringToWinLogEventConverter();
    }

    @Bean
    public StringToWinTrafficEventConverter stringToWinTrafficEventConverter() {
        return new StringToWinTrafficEventConverter();
    }

    @Bean
    public ConversionService conversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(String.class, WinLogEvent.class, stringToWinLogEventConverter());
        conversionService.addConverter(String.class, WinTrafficEvent.class, stringToWinTrafficEventConverter());
        return conversionService;
    }
}
