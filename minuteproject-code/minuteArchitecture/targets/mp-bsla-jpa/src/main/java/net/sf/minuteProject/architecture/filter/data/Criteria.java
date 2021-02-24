package net.sf.minuteProject.architecture.filter.data;

import java.util.ArrayList;
import java.util.List;

public class Criteria {

	private List<ClauseCriterion> clauseCriterions;
	private OrderCriteria orderCriteria;

	public List<ClauseCriterion> getClauseCriterions() {
		if (clauseCriterions==null)
			clauseCriterions = new ArrayList<ClauseCriterion>();
		return clauseCriterions;
	}

	public void setClauseCriterions(List<ClauseCriterion> clauseCriterions) {
		this.clauseCriterions = clauseCriterions;
	}
	
	public void addCriterion (ClauseCriterion clauseCriterion) {
		getClauseCriterions().add(clauseCriterion);
	}

	public OrderCriteria getOrderCriteria() {
		return orderCriteria;
	}

	public void setOrderCriteria(OrderCriteria orderCriteria) {
		this.orderCriteria = orderCriteria;
	}
	
	
}
