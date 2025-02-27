package com.spring.dataprocessing.config;

import com.spring.dataprocessing.filter.TrafficFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrafficFilterConfig {

    @Value("${spring.filter.traffic.ethernetDestinationAddress}")
    private String EthernetDestinationAddress;

    @Value("${spring.filter.traffic.ethernetSourceAddress}")
    private String EthernetSourceAddress;

    @Value("${spring.filter.traffic.minTotalLength}")
    private Integer minTotalLength;

    @Value("${spring.filter.traffic.maxTotalLength}")
    private Integer maxTotalLength;

    @Value("${spring.filter.traffic.protocol}")
    private String protocol;

    @Value("${spring.filter.traffic.IPSourceAddress}")
    private String IPSourceAddress;

    @Value("${spring.filter.traffic.IPDestinationAddress}")
    private String IPDestinationAddress;

    @Value("${spring.filter.traffic.TCPSourcePort}")
    private String TCPSourcePort;

    @Value("${spring.filter.traffic.TCPDestinationPort}")
    private String TCPDestinationPort;

    @Value("${spring.filter.traffic.URG}")
    private Boolean URG;

    @Value("${spring.filter.traffic.ACK}")
    private Boolean ACK;

    @Value("${spring.filter.traffic.PSH}")
    private Boolean PSH;

    @Value("${spring.filter.traffic.RST}")
    private Boolean RST;

    @Value("${spring.filter.traffic.SYN}")
    private Boolean SYN;

    @Value("${spring.filter.traffic.FIN}")
    private Boolean FIN;

    @Value("${spring.filter.traffic.UDPSourcePort}")
    private String UDPSourcePort;

    @Value("${spring.filter.traffic.UDPDestinationPort}")
    private String UDPDestinationPort;

    @Bean
    public TrafficFilter trafficFilter() {
        return TrafficFilter.builder()
                .EthernetDestinationAddress(EthernetDestinationAddress)
                .EthernetSourceAddress(EthernetSourceAddress)
                .minTotalLength(minTotalLength)
                .maxTotalLength(maxTotalLength)
                .protocol(protocol)
                .IPSourceAddress(IPSourceAddress)
                .IPDestinationAddress(IPDestinationAddress)
                .TCPSourcePort(TCPSourcePort)
                .TCPDestinationPort(TCPDestinationPort)
                .URG(URG)
                .ACK(ACK)
                .PSH(PSH)
                .RST(RST)
                .SYN(SYN)
                .FIN(FIN)
                .UDPSourcePort(UDPSourcePort)
                .UDPDestinationPort(UDPDestinationPort)
                .build();
    }
}
