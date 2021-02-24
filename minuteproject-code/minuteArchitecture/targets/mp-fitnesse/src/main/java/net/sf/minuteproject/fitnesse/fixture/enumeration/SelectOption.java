package net.sf.minuteproject.fitnesse.fixture.enumeration;

public enum SelectOption {

	DISTINCT ("DISTINCT"),
	COUNT ("COUNT"),
	COUNT_DISTINCT ("COUNT DISTINCT"),
	MAX ("MAX"),
	MIN ("MIN");
	
	private final String value;

	SelectOption(String v) {
		value = v;
	}

	public String value() {
		return value;
	}
	
	public static SelectOption fromValue(String v) {
		if (v==null)
			return null;
		String val = v.toUpperCase();
		for (SelectOption c : SelectOption.values()) {
			if (c.value.toUpperCase().equals(val)) {
				return c;
			}
		}
		return null;
	}
}
