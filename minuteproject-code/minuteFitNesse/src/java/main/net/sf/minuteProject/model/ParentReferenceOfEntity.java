package net.sf.minuteProject.model;

import java.util.ArrayList;
import java.util.List;

public class ParentReferenceOfEntity {

	ParentReferenceOfEntity (String test) {
		
	}
	public void table(List<List<String>> table) {
	    //optional function
	}
	  
	public List<Object> query() {
		ArrayList<Object> al = new ArrayList<Object>();
		al.add (getListOfRef());
		return al;
	}

	private List<Object> getListOfRef() {
		List<Object> list = new ArrayList<Object>();
		list.add(new SimpleRef ("userId", "user_"));
		list.add(new SimpleRef ("portletId", "portlet"));
		return list;
	}
	
	private class SimpleRef {
		String localColumnName;
		String parentTableName;
		public String getLocalColumnName() {
			return localColumnName;
		}
		public void setLocalColumnName(String localColumnName) {
			this.localColumnName = localColumnName;
		}
		public String getParentTableName() {
			return parentTableName;
		}
		public void setParentTableName(String parentTableName) {
			this.parentTableName = parentTableName;
		}
		public SimpleRef(String localColumnName, String parentTableName) {
			super();
			this.localColumnName = localColumnName;
			this.parentTableName = parentTableName;
		}
		
	}

}
