package net.sf.minuteProject.architecture.cache;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;

public class DomainReferenceCache <T>{//implements Cache {
	
	private final String PK_TO_OBJECT 	= "PK_TO_OBJECT";
	private final String PK_TO_UK_ 		= "PK_TO_UK_";
	private final String UK_TO_OBJECT 	= "UK_TO_OBJECT";
	private final String UK_TO_PK		= "UK_TO_PK";
	
	private UniqueReferenceCache uniqueReferenceCache = new UniqueReferenceCache(); 
	/**
	 * put insert the full object for a set of searchable mapping keys the first passed is the primarykey of the object,
	 * the others are the uk
	 * @param fullObject
	 * @param key
	 */
	public void put(T fullObject, T... key) {
		int size = key.length;
		for (int i = 0; i < size-1; i++) {
			put (fullObject, key[0], key[i+1], i+1);
		}
	}
	
	public void put(T fullObject, T pkObject, T ukObject, int iteration) {
		if (iteration==1)
			uniqueReferenceCache.put(pkObject, PK_TO_OBJECT, fullObject);
		uniqueReferenceCache.put(pkObject, getPkToUk(iteration), ukObject);
		uniqueReferenceCache.put(ukObject, UK_TO_OBJECT, fullObject);
		uniqueReferenceCache.put(ukObject, UK_TO_PK, pkObject);
	}
	
	public T getObjectByPk(T pkObject) {
		return (T)uniqueReferenceCache.get(pkObject, PK_TO_OBJECT);
	}
	
	public T getUkByPk(T pkObject, int iteration) {
		return (T)uniqueReferenceCache.get(pkObject, getPkToUk(iteration));
	}

	public T getObjectByUk(T ukObject) {
		return (T)uniqueReferenceCache.get(ukObject, UK_TO_OBJECT);
	}

	public T getPkByUk(T ukObject) {
		return (T)uniqueReferenceCache.get(ukObject, UK_TO_PK);
	}
	
	private String getPkToUk (int iteration) {
		return PK_TO_UK_+iteration;
	}
}
