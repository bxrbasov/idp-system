package com.spring.dataingestion.converter;

import com.spring.dataingestion.event.WinLogEvent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class StringToWinLogEventConverter implements Converter<String, WinLogEvent> {

    private static final String[] parameters = {
            "ProviderId", "LogName", "ProcessId", "ThreadId", "MachineName", "UserId", "TimeCreated", "ActivityId",
            "RelatedActivityId", "ContainerLog", "MatchedQueryIds", "Bookmark", "LevelDisplayName",
            "OpcodeDisplayName", "TaskDisplayName", "KeywordsDisplayNames", "Properties",
            "Message", "RecordId", "Version", "Qualifiers", "Level", "Task", "Opcode", "Keywords", "ProviderName" , "Id"
    };

    @Override
    public WinLogEvent convert(String source) {
        for (String parameter : parameters) {
            source = source.replace(parameter, " ");
        }
        String[] split = source.split(" : ");
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].trim();
        }
        return WinLogEvent.builder()
                .message(split[1].isBlank() ? null : split[1])
                .id(split[2].isBlank() ? null : Integer.parseInt(split[2]))
                .version(split[3].isBlank() ? null : Integer.parseInt(split[3]))
                .qualifiers(split[4].isBlank() ? null : Integer.parseInt(split[4]))
                .level(split[5].isBlank() ? null : Integer.parseInt(split[5]))
                .task(split[6].isBlank() ? null : Integer.parseInt(split[6]))
                .opcode(split[7].isBlank() ? null : Integer.parseInt(split[7]))
                .keywords(split[8].isBlank() ? null : Long.parseLong(split[8]))
                .recordId(split[9].isBlank() ? null : Integer.parseInt(split[9]))
                .providerName(split[10].isBlank() ? null : split[10])
                .providerId(split[11].isBlank() ? null : split[11])
                .logName(split[12].isBlank() ? null : split[12])
                .processId(split[13].isBlank() ? null : Integer.parseInt(split[13]))
                .threadId(split[14].isBlank() ? null : Integer.parseInt(split[14]))
                .machineName(split[15].isBlank() ? null : split[15])
                .userId(split[16].isBlank() ? null : split[16])
                .timeCreated(getDateTime(split[17]))
                .activityId(split[18].isBlank() ? null : split[18])
                .relatedActivityId(split[19].isBlank() ? null : split[19])
                .containerLog(split[20].isBlank() ? null : split[20])
                .matchedQueryIds(split[21].isBlank() ? null : split[21])
                .bookmark(split[22].isBlank() ? null : split[22])
                .levelDisplayName(split[23].isBlank() ? null : split[23])
                .opcodeDisplayName(split[24].isBlank() ? null : split[24])
                .taskDisplayName(split[25].isBlank() ? null : split[25])
                .keywordsDisplayNames(split[26].isBlank() ? null : split[26])
                .properties(split[27].isBlank() ? null : split[27])
                .build();
    }

    private LocalDateTime getDateTime(String date) {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm:ss");
        try {
            return LocalDateTime.parse(date, formatter1);
        } catch (DateTimeParseException e) {
            return LocalDateTime.parse(date, formatter2);
        }
    }
}
