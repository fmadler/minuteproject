package net.sf.minuteProject.configuration.bean.enrichment.validation;

public interface Validation<CoreElement> {

	public default String getGroup() {
		return "";
	};
	
}
