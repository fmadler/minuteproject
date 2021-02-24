package net.sf.minuteProject.model.dummy;

import net.sf.minuteProject.model.data.criteria.AbstractDomainObjectWhere;
import net.sf.minuteProject.model.data.criteria.type.DateCriteria;
import net.sf.minuteProject.model.data.criteria.type.IntegerCriteria;
import net.sf.minuteProject.model.data.criteria.type.StringCriteria;

public class DummyDo extends AbstractDomainObjectWhere {

	public DummyDo() {
		super("dummyDo");
//		setBeanPath("dummyDo"); //entity name
	}

	private StringCriteria department;
	private StringCriteria status;
	private IntegerCriteria salary;
	private DateCriteria birthdate;// = new DateCriteria();

	public DateCriteria getBirthdate() {
		birthdate = new DateCriteria(wdoc.getWfc("birthdate"));
		return birthdate;
	}

	public StringCriteria getDepartment() {
		return department;
	}

	public void setDepartment(StringCriteria department) {
		this.department = department;
	}

	public StringCriteria getStatus() {
		return status;
	}

	public void setStatus(StringCriteria status) {
		this.status = status;
	}

	public IntegerCriteria getSalary() {
		return salary;
	}

	public void setSalary(IntegerCriteria salary) {
		this.salary = salary;
	}

	public void setBirthdate(DateCriteria birthdate) {
		this.birthdate = birthdate;
	}

	
//	public void setBirthdate(DateCriteria birthdate) {
//		this.birthdate = birthdate;
//	}
	
	
}
