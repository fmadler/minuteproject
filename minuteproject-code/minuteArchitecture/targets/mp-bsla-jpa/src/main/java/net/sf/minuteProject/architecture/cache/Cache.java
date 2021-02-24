package net.sf.minuteProject.architecture.cache;

public interface Cache {

	public void clean();

	public void put(String key, Object o);

	public void remove(String key);

	public void flush();

	public void setRefreshDelay(Long delay);
	
	public Object get(String key);
	
}

