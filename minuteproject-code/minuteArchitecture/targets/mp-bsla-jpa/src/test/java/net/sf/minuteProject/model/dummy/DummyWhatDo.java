package net.sf.minuteProject.model.dummy;

import net.sf.minuteProject.model.data.criteria.AbstractDomainObjectWhat;
import net.sf.minuteProject.model.data.criteria.function.IntegerFunction;
import net.sf.minuteProject.model.data.criteria.value.IntegerValue;

public class DummyWhatDo extends AbstractDomainObjectWhat{

	public DummyWhatDo() {
		super("dummyDo");
	}

	public DummyWhatDo(IntegerValue salaryV) {
		super("dummyDo");
		this.salaryV = salaryV;
	}
	
	private IntegerFunction salary;
	private IntegerValue salaryV;

	public IntegerValue getSalary() {
//		return setSalary();
//		salaryV = new IntegerValue(getWdoc().getWfc("salary"));
		return salaryV;
	}

	public IntegerFunction setSalary() {
		salary = new IntegerFunction(getWdoc().setWfc("salary"));
		return salary;
	}
	
	public void setSalaryValue(IntegerValue salaryV) {
		this.salaryV = salaryV;
	}
}
