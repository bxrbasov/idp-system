package com.spring.dataingestion;

import com.spring.dataingestion.services.Impl.WinEventLogCollector;
import com.spring.dataingestion.services.Impl.WinPcapNetworkTrafficCapture;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DataIngestionApplication implements CommandLineRunner {

    private final WinPcapNetworkTrafficCapture winPcapNetworkTrafficCapture;
//    Пока отключен до лучших времен
    private final WinEventLogCollector winEventLogCollector;

    public static void main(String[] args) {
        SpringApplication.run(DataIngestionApplication.class, args);
    }

    @Override
    public void run(String... args) {
        winPcapNetworkTrafficCapture.startCapture();
    }

    @PreDestroy
    public void destroy() {
        winPcapNetworkTrafficCapture.stopCapture();
    }
}
