package com.spring.dataprocessing.filter;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Builder
public class TrafficFilter {
    private String EthernetDestinationAddress;
    private String EthernetSourceAddress;
    private Integer minTotalLength;
    private Integer maxTotalLength;
    private String protocol;
    private String IPSourceAddress;
    private String IPDestinationAddress;
    private String TCPSourcePort;
    private String TCPDestinationPort;
    private Boolean URG;
    private Boolean ACK;
    private Boolean PSH;
    private Boolean RST;
    private Boolean SYN;
    private Boolean FIN;
    private String UDPSourcePort;
    private String UDPDestinationPort;
}
