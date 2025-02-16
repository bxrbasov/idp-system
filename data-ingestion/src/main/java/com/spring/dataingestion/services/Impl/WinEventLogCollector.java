package com.spring.dataingestion.services.Impl;

import com.spring.dataingestion.producer.Impl.KafkaProducer;
import com.spring.dataingestion.services.SystemLogCollector;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class WinEventLogCollector implements SystemLogCollector {

    private static final Logger log = LogManager.getLogger(WinEventLogCollector.class);
    private static final String command = "Get-WinEvent -LogName \"System\", \"Application\" | Select-Object -First 1 |Format-List *";

    private final KafkaProducer kafkaProducer;
    private Thread thread;

    @Override
    public void startCollecting() {
        if (thread != null && thread.isAlive()) {
            log.atInfo().log("The thread is alive.");
            return;
        }

        thread = new Thread(() -> {
            log.atInfo().log("Starting Collecting.");
            while (true) {
                log.atInfo().log("Using PowerShell with command: {}", command);

                String source = executePowerShellCommand(command);
                log.atInfo().log(source);

                kafkaProducer.sendLogEvent(source);
                try {
                    log.atInfo().log("Waiting for command...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.atWarn().log("Thread interrupted: {}", e.getMessage());
                    break;
                }
            }
        });
        thread.start();
        log.atInfo().log("Collector is started.");
    }

    @Override
    public void stopCollecting() {
        if (thread == null || !thread.isAlive()) {
            log.atInfo().log("The thread isn't alive.");
            return;
        }

        thread.interrupt();
        log.atInfo().log("Collector is stopped.");
    }

    public static String executePowerShellCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"powershell", command});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
        } catch (Exception e) {
            log.atError().log("Error while executing powershell command: {}", e.getMessage());
        }
        return output.toString();
    }
}
