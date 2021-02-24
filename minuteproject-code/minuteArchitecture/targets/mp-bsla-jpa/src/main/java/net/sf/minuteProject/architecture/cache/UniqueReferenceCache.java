package net.sf.minuteProject.architecture.cache;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;

public class UniqueReferenceCache {//implements Cache {
	
	private Long delay;
	private final long delayDefault = 1000*300;
	private final int  maxKeyDefault = 100;
	private Integer  maxKeys;
	
	private static MultiKeyMap table = new MultiKeyMap();
	private static MultiKeyMap timeTable = new MultiKeyMap();
    
    public Object get(Object pkObject, Object ukObject) {
    	Date date = (Date)timeTable.get(pkObject, ukObject);
    	if ((date != null) && ((new Date()).getTime() - date.getTime() > getDelay())) {
    		timeTable.remove(pkObject, ukObject);
    		if (table.containsKey(pkObject, ukObject))
    			table.remove(pkObject, ukObject);
    		return null;
    	}
        return table.get(pkObject, ukObject);
    }
    
    /* (non-Javadoc)
	 * @see net.sf.minuteProject.architecture.cache.Cache2#clean()
	 */
    public void clean() {
		for (MapIterator iter = timeTable.mapIterator(); iter.hasNext();) {
			MultiKey keys = (MultiKey) iter.next();
			removeIfNecessary(keys.getKey(0), keys.getKey(1));
		}    	
    }
    
    private void removeIfNecessary(Object pkObject, Object ukObject) {
    	Date date = (Date)timeTable.get(pkObject, ukObject);
    	if ((date != null) && ((new Date()).getTime() - date.getTime() > getDelay())) {
    		timeTable.remove(pkObject, ukObject);
    		if (table.containsKey(pkObject, ukObject))
    			table.remove(pkObject, ukObject);
    	}
    }
    
    /* (non-Javadoc)
	 * @see net.sf.minuteProject.architecture.cache.Cache2#put(java.lang.String, java.lang.Object)
	 */
    public void put(Object fullObject, Object... key) {
    	put (key[0], key[1], fullObject);
    }
    
    public void put(Object pkObject, Object ukObject, Object fullObject) {
    	if (timeTable.size()>getMaxKeys()) //to limit the size of the table (it is no the best algo ;))
    		flush();
    	putEffective(pkObject, ukObject, fullObject);
    }
    
    private void putEffective(Object pkObject, Object ukObject, Object fullObject) {
    	timeTable.put(pkObject, ukObject, new Date());
    	table.put(pkObject, ukObject, fullObject);
    }
    /* (non-Javadoc)
	 * @see net.sf.minuteProject.architecture.cache.Cache2#remove(java.lang.String)
	 */
    public void put(Object... key) {
    	remove (key[0], key[1]);
    }
    
    public void remove(Object pkObject, Object ukObject) {
    	timeTable.remove(pkObject, ukObject);
    	table.remove(pkObject, ukObject); 
    }
    
    /* (non-Javadoc)
	 * @see net.sf.minuteProject.architecture.cache.Cache2#flush()
	 */
    public void flush() {
    	timeTable.clear();
    	table.clear();
    }

	public long getDelay() {
		if (delay==null)
			setDelay(delayDefault);
		return delay;
	}

	public void setDelay(Long delay) {
		this.delay = delay;
	}
    
	public void setRefreshDelay(Long delay) {
		setDelay(delay);
	}

	public Integer getMaxKeys() {
		if (maxKeys==null)
			setMaxKeys(maxKeyDefault);
		return maxKeys;
	}

	public void setMaxKeys(Integer maxKeys) {
		this.maxKeys = maxKeys;
	}
	
	
}
