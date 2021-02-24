package net.sf.minuteProject.configuration.bean.enumeration;

public enum Cache {
   
	READ_ONLY("read-only"), READ_WRITE("read-write");
   
	private final String value;
	
	Cache (String value) {
		this.value = value;
	}
	public static Cache getCache(String value) {
		for (Cache cache : Cache.values()) {
			if (cache.name().equalsIgnoreCase(value)) {
				return cache;
			}
		}
		return null;
	}
	public String getValue() {
		return value;
	}
	
}
