package net.sf.minuteproject.model.db;

import java.util.Map;

import net.sf.minuteproject.model.db.type.FieldType;

public class Column {

	private String name;
	private FieldType type;
	private boolean isMandatory;
	private int length;
	
	public Column(String name, FieldType type, boolean isMandatory, int length) {
		super();
		this.name = name;
		this.type = type;
		this.isMandatory = isMandatory;
		this.length = length;
	}
	
	public boolean isMandatory() {
		return isMandatory;
	}
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	
}
