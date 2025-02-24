package com.spring.dataingestion.converter;

import com.spring.core.event.WinTrafficEvent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToWinTrafficEventConverter implements Converter<String, WinTrafficEvent> {

    @Override
    public WinTrafficEvent convert(String source) {
        String[] split = source.split("\\r?\\n");
        WinTrafficEvent winTrafficEvent = WinTrafficEvent.builder()
                .EthernetDestinationAddress(split[1].split(": ")[1].isBlank() ? "" : split[1].split(": ")[1])
                .EthernetSourceAddress(split[2].split(": ")[1].isBlank() ? "" : split[2].split(": ")[1])
                .type(split[3].split(": ")[1].isBlank() ? "" : split[3].split(": ")[1])
                .version(split[5].split(": ")[1].isBlank() ? "" : split[5].split(": ")[1])
                .IHL(split[6].split(": ")[1].isBlank() ? "" : split[6].split(": ")[1])
                .TOS(split[7].split("TOS: ")[1].isBlank() ? "" : split[7].split("TOS: ")[1])
                .totalLength(split[8].split(": ")[1].isBlank() ? "" : split[8].split(": ")[1])
                .identification(split[9].split(": ")[1].isBlank() ? null : Integer.parseInt(split[9].split(": ")[1]))
                .flags(split[10].split(": ")[1].isBlank() ? "" : split[10].split(": ")[1])
                .fragmentOffset(split[11].split(": ")[1].isBlank() ? "" : split[11].split(": ")[1])
                .TTL(split[12].split(": ")[1].isBlank() ? 0 : Integer.parseInt(split[12].split(": ")[1]))
                .protocol(split[13].split(": ")[1].isBlank() ? "" : split[13].split(": ")[1])
                .headerChecksum(split[14].split(": ")[1].isBlank() ? "" : split[14].split(": ")[1])
                .IPSourceAddress(split[15].split(": ")[1].isBlank() ? "" : split[15].split(": ")[1].replaceAll("/", ""))
                .IPDestinationAddress(split[16].split(": ")[1].isBlank() ? "" : split[16].split(": ")[1].replaceAll("/", ""))
                .build();
        if (split[17].contains("TCP Header")) {
            winTrafficEvent.setTCPSourcePort(split[18].split(": ")[1].isBlank() ? "" : split[18].split(": ")[1]);
            winTrafficEvent.setTCPDestinationPort(split[19].split(": ")[1].isBlank() ? "" : split[19].split(": ")[1]);
            winTrafficEvent.setSequenceNumber(split[20].split(": ")[1].isBlank() ? null : Long.parseLong(split[20].split(": ")[1]));
            winTrafficEvent.setAcknowledgmentNumber(split[21].split(": ")[1].isBlank() ? null : Long.parseLong(split[21].split(": ")[1]));
            winTrafficEvent.setDataOffset(split[22].split(": ")[1].isBlank() ? "" : split[22].split(": ")[1]);
            winTrafficEvent.setReserved(split[23].split(": ")[1].isBlank() ? null : Integer.parseInt(split[23].split(": ")[1]));
            winTrafficEvent.setURG(Boolean.parseBoolean(split[24].split(": ")[1]));
            winTrafficEvent.setACK(Boolean.parseBoolean(split[25].split(": ")[1]));
            winTrafficEvent.setPSH(Boolean.parseBoolean(split[26].split(": ")[1]));
            winTrafficEvent.setRST(Boolean.parseBoolean(split[27].split(": ")[1]));
            winTrafficEvent.setSYN(Boolean.parseBoolean(split[28].split(": ")[1]));
            winTrafficEvent.setFIN(Boolean.parseBoolean(split[29].split(": ")[1]));
            winTrafficEvent.setWindow(split[30].split(": ")[1].isBlank() ? null : Integer.parseInt(split[30].split(": ")[1]));
            winTrafficEvent.setTCPChecksum(split[31].split(": ")[1].isBlank() ? "" : split[31].split(": ")[1]);
            winTrafficEvent.setUrgentPointer(split[32].split(": ")[1].isBlank() ? null : Integer.parseInt(split[32].split(": ")[1]));
            if (split.length == 35) {
                winTrafficEvent.setHexStream(split[34].split(": ")[1].isBlank() ? "" : split[34].split(": ")[1]);
            }
        } else {
            winTrafficEvent.setUDPSourcePort(split[18].split(": ")[1].isBlank() ? "" : split[18].split(": ")[1]);
            winTrafficEvent.setUDPDestinationPort(split[19].split(": ")[1].isBlank() ? "" : split[19].split(": ")[1]);
            winTrafficEvent.setLength(split[20].split(": ")[1].isBlank() ? "" : split[20].split(": ")[1]);
            winTrafficEvent.setUDPChecksum(split[21].split(": ")[1].isBlank() ? "" : split[21].split(": ")[1]);
            if (split.length == 24) {
                winTrafficEvent.setHexStream(split[23].split(": ")[1].isBlank() ? "" : split[23].split(": ")[1]);
            }
        }
        return winTrafficEvent;
    }
}
