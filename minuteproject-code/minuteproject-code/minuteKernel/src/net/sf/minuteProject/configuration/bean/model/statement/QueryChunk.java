package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class QueryChunk extends AbstractConfiguration{
	
	private String sampleValue;
	private List<QueryChunkValue> queryChunkValues;
	private List<QueryChunkParam> queryChunkParams;
	private QueryChunkValue defaultValue;
	
	public String getSampleValue() {
		return sampleValue;
	}

	public void setSampleValue(String sampleValue) {
		this.sampleValue = sampleValue;
	}

	public List<QueryChunkValue> getQueryChunkValues() {
		if (queryChunkValues == null)
			queryChunkValues = new ArrayList<QueryChunkValue>();
		return queryChunkValues;
	}

	public void addQueryChunkValue(QueryChunkValue queryChunkValue) {
		queryChunkValue.setQueryChunk(this);
		if (queryChunkValue.isDefault())
			defaultValue = queryChunkValue;
		getQueryChunkValues().add(queryChunkValue);
	}
	
	public List<QueryChunkParam> getQueryChunkParams() {
		if (queryChunkParams == null)
			queryChunkParams = new ArrayList<QueryChunkParam>();
		return queryChunkParams;
	}
	
	public void addQueryChunkParam(QueryChunkParam queryChunkParam) {
		queryChunkParam.setQueryChunk(this);
		getQueryChunkParams().add(queryChunkParam);
	}

	public QueryChunkValue getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(QueryChunkValue defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean hasDefault() {
		return defaultValue != null;
	}
	//http://stackoverflow.com/questions/63150/whats-the-best-way-to-build-a-string-of-delimited-items-in-java
	public String getDistinctValues () {
		List<String> values = new ArrayList<String>();
		for (QueryChunkValue chunkValue : getQueryChunkValues()) {
			values.add(chunkValue.getName());
		}
		return StringUtils.join(values, ',');
	}
}
