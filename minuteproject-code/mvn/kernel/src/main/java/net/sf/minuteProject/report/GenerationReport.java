package net.sf.minuteProject.report;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GenerationReport implements Reporting {
    List<ReportEntry> reportEntries = new CopyOnWriteArrayList<>();

    @Override
    public void add(ReportEntry reportEntry) {
        reportEntries.add(reportEntry);
    }

    @Override
    public List<ReportEntry> reportEntries() {
        return reportEntries;
    }
}
