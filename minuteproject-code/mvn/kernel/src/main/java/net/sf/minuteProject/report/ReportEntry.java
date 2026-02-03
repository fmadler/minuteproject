package net.sf.minuteProject.report;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReportEntry {
    LocalDateTime time;
    ReportEntryType type;
    ReportEntryCategory category;
    String element;
    String elementValue;
    String message;
}
