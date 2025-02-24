package com.spring.core.event;

import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrafficEvent {
    // Ethernet Header:
    private String EthernetDestinationAddress;
    private String EthernetSourceAddress;
    private Integer totalLength;
    private String protocol;
    private String IPSourceAddress;
    private String IPDestinationAddress;
    // TCP Header
    private String TCPSourcePort;
    private String TCPDestinationPort;
    private Boolean URG;
    private Boolean ACK;
    private Boolean PSH;
    private Boolean RST;
    private Boolean SYN;
    private Boolean FIN;
    // UDP Header
    private String UDPSourcePort;
    private String UDPDestinationPort;
    // data (n bytes)
    private String hexStream;
}
