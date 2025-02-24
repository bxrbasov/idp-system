package com.spring.core.event;

import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WinLogEvent {
    private String message;
    private Integer id;
    private Integer version;
    private Integer qualifiers;
    private Integer level;
    private Integer task;
    private Integer opcode;
    private Long keywords;
    private Integer recordId;
    private String providerName;
    private String providerId;
    private String logName;
    private Integer processId;
    private Integer threadId;
    private String machineName;
    private String userId;
    private LocalDateTime timeCreated;
    private String activityId;
    private String relatedActivityId;
    private String containerLog;
    private String matchedQueryIds;
    private String bookmark;
    private String levelDisplayName;
    private String opcodeDisplayName;
    private String taskDisplayName;
    private String keywordsDisplayNames;
    private String properties;
}
