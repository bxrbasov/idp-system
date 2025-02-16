package com.spring.dataprocessing.event.consume;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Builder
public class WinTraffic {
    // Ethernet Header:
    private String EthernetDestinationAddress;
    private String EthernetSourceAddress;
    private String type;
    // IP Header
    private String version;
    private String IHL;
    private String TOS;
    private String totalLength;
    private Integer identification;
    private String flags;
    private String fragmentOffset;
    private Integer TTL;
    private String protocol;
    private String headerChecksum;
    private String IPSourceAddress;
    private String IPDestinationAddress;
    // TCP Header
    private String TCPSourcePort;
    private String TCPDestinationPort;
    private Long sequenceNumber;
    private Long acknowledgmentNumber;
    private String dataOffset;
    private Integer reserved;
    private Boolean URG;
    private Boolean ACK;
    private Boolean PSH;
    private Boolean RST;
    private Boolean SYN;
    private Boolean FIN;
    private Integer window;
    private String TCPChecksum;
    private Integer urgentPointer;
    // UDP Header
    private String UDPSourcePort;
    private String UDPDestinationPort;
    private String length;
    private String UDPChecksum;
    // data (n bytes)
    private String hexStream;
}
