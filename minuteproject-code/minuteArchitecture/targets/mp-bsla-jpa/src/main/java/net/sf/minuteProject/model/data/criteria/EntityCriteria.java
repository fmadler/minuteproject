package net.sf.minuteProject.model.data.criteria;

import net.sf.minuteProject.architecture.bsla.domain.AbstractDomainObject;
import net.sf.minuteProject.model.data.criteria.constant.EntityMatchType;
import net.sf.minuteProject.model.data.criteria.constant.OperandType;

public class EntityCriteria<T extends AbstractDomainObject> {

	private final T entity;
	private final EntityMatchType matchType;
	private final OperandType operandType;
	private final Boolean caseSensitivenessType;
	
	public EntityCriteria(
			T entity, 
			EntityMatchType matchType,
			OperandType operandType, 
			Boolean caseSensitivenessType) {
		super();
		this.entity = entity;
		this.matchType = matchType;
		this.operandType = operandType;
		this.caseSensitivenessType = caseSensitivenessType;
	}

	public T getEntity() {
		return entity;
	}

	public EntityMatchType getMatchType() {
		return matchType;
	}

	public OperandType getOperandType() {
		return operandType;
	}

	public Boolean getCaseSensitivenessType() {
		return caseSensitivenessType;
	}
	
	@Override
	public String toString() {
		return "EntitySort [entity=" + entity + ", EntityMatchType=" + matchType + " OperandType=" + operandType +" caseSensitivenessType=" + caseSensitivenessType +  "]";
	}
}
