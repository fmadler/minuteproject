package net.sf.minuteProject.model.data.criteria;

import net.sf.minuteProject.model.data.criteria.constant.EntityMatchType;
import net.sf.minuteProject.model.data.criteria.constant.OperandType;

public class EntityCriteria<T> {

	private T entity;
	private EntityMatchType matchType;
	private OperandType operandType;
	private Boolean caseSensitivenessType;
	
	public EntityCriteria(T entity, EntityMatchType matchType,
			OperandType operandType, Boolean caseSensitivenessType) {
		super();
		this.entity = entity;
		this.matchType = matchType;
		this.operandType = operandType;
		this.caseSensitivenessType = caseSensitivenessType;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public EntityMatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(EntityMatchType matchType) {
		this.matchType = matchType;
	}

	public OperandType getOperandType() {
		return operandType;
	}

	public void setOperandType(OperandType operandType) {
		this.operandType = operandType;
	}

	public Boolean getCaseSensitivenessType() {
		return caseSensitivenessType;
	}

	public void setCaseSensitivenessType(Boolean caseSensitivenessType) {
		this.caseSensitivenessType = caseSensitivenessType;
	}
	
	@Override
	public String toString() {
		return "EntitySort [entity=" + entity + ", EntityMatchType=" + matchType + " OperandType=" + operandType +" caseSensitivenessType=" + caseSensitivenessType +  "]";
	}
}
