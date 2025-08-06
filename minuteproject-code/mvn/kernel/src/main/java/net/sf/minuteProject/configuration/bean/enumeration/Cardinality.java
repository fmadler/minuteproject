package net.sf.minuteProject.configuration.bean.enumeration;

public enum Cardinality {
	ONE, MANY;
	

	public boolean isOneResult() {
		return this == Cardinality.ONE;
	}
	
	public boolean isManyResults() {
		return !isOneResult();
	}
	
}
