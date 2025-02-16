package com.spring.dataingestion.services.Impl;

import com.spring.dataingestion.producer.Impl.KafkaProducer;
import com.spring.dataingestion.services.NetworkTrafficCapture;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pcap4j.core.*;
import org.pcap4j.packet.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class WinPcapNetworkTrafficCapture implements NetworkTrafficCapture {

    private static final Logger log = LogManager.getLogger(WinPcapNetworkTrafficCapture.class);

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final List<PcapHandle> handles = new ArrayList<>();

    private final KafkaProducer kafkaProducer;
    private Thread thread;

    @Override
    public void startCapture() {
        if (thread != null && thread.isAlive()) {
            log.warn("The thread is alive.");
            return;
        }

        List<Callable<PcapHandle>> tasks = getTasks();
        log.atInfo().log("Get tasks for ThreadPool: {}", tasks);

        thread = new Thread(() -> {
            try {
                executor.invokeAll(tasks);
                log.atInfo().log("Put tasks into ThreadPool.");
            } catch (InterruptedException e) {
                log.atError().log("Interrupted while waiting for tasks: {}", tasks);
                throw new RuntimeException(e);
            }
        });

        thread.start();
        log.atInfo().log("Capture is started.");
    }

    @Override
    public void stopCapture() {
        if (thread == null || !thread.isAlive()) {
            log.warn("The thread isn't alive.");
            return;
        }

        if (!handles.isEmpty()) {
            for (PcapHandle handle : handles) {
                try (handle) {
                    handle.breakLoop();
                    log.atInfo().log("Break loop completed.");
                } catch (NotOpenException e) {
                    log.atError().log("Failed to close handle interface: {}", e.getMessage());
                    throw new RuntimeException(e);
                } finally {
                    log.atInfo().log("Handle interface is closed.");
                }
            }
        }

        executor.shutdownNow();
        thread.interrupt();
        log.atInfo().log("Capture is finished.");
    }

    private List<Callable<PcapHandle>> getTasks() {
        List<Callable<PcapHandle>> tasks = new ArrayList<>();

        List<PcapNetworkInterface> networkInterfaces = getNetworkInterfaces();
        log.atInfo().log("Network interfaces is selected: {}", networkInterfaces);

        PacketListener packetListener = getPacketListener();
        log.atInfo().log("Packet listener is selected: {}", packetListener);

        int i = 0;
        while (++i < networkInterfaces.size()) {

            PcapNetworkInterface nif = networkInterfaces.get(i);
            log.atInfo().log("Network interface is selected: {} ({})", nif.getName(), nif.getDescription());

            PcapHandle handle = getPcapHandle(nif);
            log.atInfo().log("Pcap handle is selected: {}", handle);

            tasks.add(() -> {
                try {
                    handles.add(handle);
                    handle.loop(-1, packetListener);
                    log.atInfo().log("Loop completed.");
                } catch (PcapNativeException | InterruptedException | NotOpenException e) {
                    log.atWarn().log("Failed to loop packet by handle: {}", e.getMessage());
                }
                return handle;
            });
        }
        return tasks;
    }

    private List<PcapNetworkInterface> getNetworkInterfaces() {
        List<PcapNetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = Pcaps.findAllDevs();
            if (networkInterfaces.isEmpty()) {
                log.atError().log("Network interfaces not found");
                throw new RuntimeException("Try to get network interfaces list later");
            }
        } catch (PcapNativeException e) {
            log.atError().log("Failed to select network interface: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return networkInterfaces;
    }

    private PcapHandle getPcapHandle(PcapNetworkInterface nif) {
        int snapshotLength = 65536;
        PcapNetworkInterface.PromiscuousMode promiscousMode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
        int timeout = 10;
        PcapHandle handle = null;

        try {
            handle = nif.openLive(snapshotLength, promiscousMode, timeout);
            log.atInfo().log("Handle interface is selected: {}", handle);
        } catch (PcapNativeException e) {
            log.atError().log("Failed to open handle interface: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        if (false) {
            String filter = "tcp and port 80";
            try {
                handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
                log.atInfo().log("Filter is selected: {}", filter);
            } catch (NotOpenException | PcapNativeException e) {
                log.atError().log("Failed to set filter: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return handle;
    }

    private PacketListener getPacketListener() {
        PacketListener packetListener = packet -> {
            EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
            if (ethernetPacket != null) {

                IpV4Packet ipv4Packet = packet.get(IpV4Packet.class);
                if (ipv4Packet != null) {

                    TcpPacket tcpPacket = packet.get(TcpPacket.class);
                    if (tcpPacket != null) {
                        String source = String.valueOf(packet);
                        log.atInfo().log("TCP packet recieved:\n{}", source);

                        kafkaProducer.sendTrafficEvent(source);
                    }

                    UdpPacket udpPacket = packet.get(UdpPacket.class);
                    if (udpPacket != null) {
                        String source = String.valueOf(packet);
                        log.atInfo().log("UDP packet recieved:\n{}", source);

                        kafkaProducer.sendTrafficEvent(source);
                    }
                }
            }
        };
        return packetListener;
    }
}
