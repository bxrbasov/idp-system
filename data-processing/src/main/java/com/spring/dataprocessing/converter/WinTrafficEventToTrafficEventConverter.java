package com.spring.dataprocessing.converter;

import com.spring.core.event.TrafficEvent;
import com.spring.core.event.WinTrafficEvent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WinTrafficEventToTrafficEventConverter implements Converter<WinTrafficEvent, TrafficEvent> {

    @Override
    public TrafficEvent convert(WinTrafficEvent source) {
        return TrafficEvent.builder()
                .EthernetDestinationAddress(source.getEthernetDestinationAddress())
                .EthernetSourceAddress(source.getEthernetSourceAddress())
                .totalLength(Integer.parseInt(source.getTotalLength().replace(" [bytes]", "")))
                .protocol(source.getProtocol())
                .IPDestinationAddress(source.getIPDestinationAddress())
                .IPSourceAddress(source.getIPSourceAddress())
                .TCPDestinationPort(source.getTCPDestinationPort())
                .TCPSourcePort(source.getTCPSourcePort())
                .URG(source.getURG())
                .ACK(source.getACK())
                .PSH(source.getPSH())
                .RST(source.getRST())
                .SYN(source.getSYN())
                .FIN(source.getFIN())
                .UDPDestinationPort(source.getUDPDestinationPort())
                .UDPSourcePort(source.getUDPSourcePort())
                .hexStream(source.getHexStream())
                .build();
    }
}
