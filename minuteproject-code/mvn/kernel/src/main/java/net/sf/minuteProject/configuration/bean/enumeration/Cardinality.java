package net.sf.minuteProject.configuration.bean.enumeration;

public enum Cardinality {
	ONE_TO_ONE, ONE_TO_MANY;
	

	public boolean isOneResult() {
		return this == Cardinality.ONE_TO_ONE;
	}
	
	public boolean isManyResults() {
		return !isOneResult();
	}
	
}
