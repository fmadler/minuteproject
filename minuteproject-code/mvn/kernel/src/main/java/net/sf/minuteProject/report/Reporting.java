package net.sf.minuteProject.report;

import java.util.List;

public interface Reporting {
    void add(ReportEntry reportEntry);
    List<ReportEntry> reportEntries();
}
