package net.sf.minuteProject.model.data.criteria.syntax;

import net.sf.minuteProject.model.data.criteria.Criteria;

public interface Syntax {

	public void and(Criteria criteria);
	
	public void or (Criteria criteria);
	
}
