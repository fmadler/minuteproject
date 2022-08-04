package net.sf.minuteProject.configuration.bean.enrichment.validation;

import lombok.Data;

@Data
public class Range<T> {
	
	private final T min;
	private final T max;
	
	Range(T min, T max) {
		this.min = min;
		this.max = max;
	}
}
