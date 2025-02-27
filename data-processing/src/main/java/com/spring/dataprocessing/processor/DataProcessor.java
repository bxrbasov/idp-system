package com.spring.dataprocessing.processor;

import com.spring.core.event.WinTrafficEvent;
import com.spring.dataprocessing.filter.TrafficFilter;
import com.spring.dataprocessing.handler.WinTrafficEventHandler;
import com.spring.dataprocessing.producer.TrafficEventProducer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataProcessor {

    private static final Logger log = LoggerFactory.getLogger(DataProcessor.class);
    private final TrafficFilter trafficFilter;
    private final TrafficEventProducer trafficEventProducer;

    public void filter(WinTrafficEvent winTrafficEvent) {

        log.atInfo().log("Filtering traffic event");

        if (!trafficFilter.getEthernetDestinationAddress().isBlank() &&
                !trafficFilter.getEthernetDestinationAddress().equals(winTrafficEvent.getEthernetDestinationAddress())) {return;}
        if (!trafficFilter.getEthernetSourceAddress().isBlank() &&
                !trafficFilter.getEthernetSourceAddress().equals(winTrafficEvent.getEthernetSourceAddress())) {return;}
        if (trafficFilter.getMinTotalLength() != null &&
                trafficFilter.getMinTotalLength() > Integer.parseInt(winTrafficEvent.getTotalLength().replace(" [bytes]", ""))) {return;}
        if (trafficFilter.getMaxTotalLength() != null &&
                trafficFilter.getMaxTotalLength() < Integer.parseInt(winTrafficEvent.getTotalLength().replace(" [bytes]", ""))) {return;}
        if (!trafficFilter.getProtocol().isBlank() &&
                !winTrafficEvent.getProtocol().contains(trafficFilter.getProtocol())) {return;}
        if (!trafficFilter.getIPSourceAddress().isBlank() &&
                !trafficFilter.getIPSourceAddress().equals(winTrafficEvent.getIPSourceAddress())) {return;}
        if (!trafficFilter.getIPDestinationAddress().isBlank() &&
                !trafficFilter.getIPDestinationAddress().equals(winTrafficEvent.getIPDestinationAddress())) {return;}
        if (!trafficFilter.getTCPSourcePort().isBlank() &&
                !trafficFilter.getTCPSourcePort().equals(winTrafficEvent.getTCPSourcePort())) {return;}
        if (!trafficFilter.getTCPDestinationPort().isBlank() &&
                !trafficFilter.getTCPDestinationPort().equals(winTrafficEvent.getTCPDestinationPort())) {return;}
        if (trafficFilter.getURG() != null &&
                !trafficFilter.getURG().equals(winTrafficEvent.getURG())) {return;}
        if (trafficFilter.getACK() != null &&
                !trafficFilter.getACK().equals(winTrafficEvent.getACK())) {return;}
        if (trafficFilter.getPSH() != null &&
                !trafficFilter.getPSH().equals(winTrafficEvent.getPSH())) {return;}
        if (trafficFilter.getRST() != null &&
                !trafficFilter.getRST().equals(winTrafficEvent.getRST())) {return;}
        if (trafficFilter.getSYN() != null &&
                !trafficFilter.getSYN().equals(winTrafficEvent.getSYN())) {return;}
        if (trafficFilter.getFIN() != null &&
                !trafficFilter.getFIN().equals(winTrafficEvent.getFIN())) {return;}
        if (!trafficFilter.getUDPSourcePort().isBlank() &&
                !trafficFilter.getUDPSourcePort().equals(winTrafficEvent.getUDPSourcePort())) {return;}
        if (!trafficFilter.getUDPDestinationPort().isBlank() &&
                !trafficFilter.getUDPDestinationPort().equals(winTrafficEvent.getUDPDestinationPort())) {return;}

        trafficEventProducer.produce(winTrafficEvent);
    }

}
