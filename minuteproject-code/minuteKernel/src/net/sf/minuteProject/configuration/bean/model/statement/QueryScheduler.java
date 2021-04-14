package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.Getter;
import lombok.Setter;
import net.sf.minuteProject.configuration.bean.enrichment.Action;

@Getter @Setter
public class QueryScheduler extends QueryAdapter {

	private QueryParams queryParams;
	private Query query;
	private String reportCopyFileTo, reportCopyFileToDir, reportChannel, reportFrom, reportTo, reportCc, reportBcc, reportScalarColumn, cron;
	private String processorClass, processorName;
	private int reportThresholdMinRecord;
	private String reportImportance;
	private boolean reportAddOrigin;
	private boolean reportAddEnvironment;
	private boolean reportAddNumberOfRecords;
	private String reportTemplateType;
	private String reportTemplateHeaderName;
	private String reportTemplateName;
	private List<Action> actions;

	public QueryParams getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(QueryParams queryParams) {
		this.queryParams = queryParams;
		this.queryParams.setFilterName(getName());
		for (QueryParam queryParam : getQueryParams().getQueryParams()) {
			queryParam.setMandatory(false);
		}
	}

	public String getReportChannel() {
		return reportChannel;
	}

	public void setReportChannel(String reportChannel) {
		this.reportChannel = reportChannel;
	}

	public String getReportFrom() {
		return reportFrom;
	}

	public void setReportFrom(String reportFrom) {
		this.reportFrom = reportFrom;
	}

	public String getReportTo() {
		return reportTo;
	}

	public void setReportTo(String reportTo) {
		this.reportTo = reportTo;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public int getReportThresholdMinRecord() {
		return reportThresholdMinRecord;
	}

	public void setReportThresholdMinRecord(int reportThresholdMinRecord) {
		this.reportThresholdMinRecord = reportThresholdMinRecord;
	}

	public String getReportCc() {
		return reportCc;
	}

	public void setReportCc(String reportCc) {
		this.reportCc = reportCc;
	}

	public String getReportBcc() {
		return reportBcc;
	}

	public void setReportBcc(String reportBcc) {
		this.reportBcc = reportBcc;
	}
	
	public boolean hasThresholdMinRecord() {
		return reportThresholdMinRecord != 0;
	}

	public String getProcessorClass() {
		return processorClass;
	}

	public void setProcessorClass(String processorClass) {
		this.processorClass = processorClass;
	}

	public String getProcessorName() {
		return processorName;
	}

	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}
	
	public List<Action> getActions() {
		if (actions == null)
			actions = new ArrayList<Action>();
		return actions;
	}

	public void addAction(Action action) {
		getActions().add(action);
	}

	public String getReportImportance() {
		return reportImportance;
	}

	public void setReportImportance(String reportImportance) {
		this.reportImportance = reportImportance;
	}
	
	
	public String getReportScalarColumn() {
		return reportScalarColumn;
	}

	public void setReportScalarColumn(String reportScalarColumn) {
		this.reportScalarColumn = reportScalarColumn;
	}

	public boolean isReportAddOrigin() {
		return reportAddOrigin;
	}

	public void setReportAddOrigin(boolean reportAddOrigin) {
		this.reportAddOrigin = reportAddOrigin;
	}

	public boolean isAlert() {
		return StringUtils.isNotEmpty(reportImportance) && reportImportance.equalsIgnoreCase("alert");
	}
	
	public boolean isScalarColumnReport() {
		return StringUtils.isNotEmpty(reportScalarColumn);
	}
	
	public boolean hasOrigin() {
		return isReportAddOrigin();
	}
	
	public boolean hasEnvironment() {
		return isReportAddEnvironment();
	}
	
	public boolean hasNumberOfRecords() {
		return isReportAddNumberOfRecords();
	}
}
