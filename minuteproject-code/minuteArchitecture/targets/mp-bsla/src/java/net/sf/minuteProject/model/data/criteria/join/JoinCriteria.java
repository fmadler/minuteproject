package net.sf.minuteProject.model.data.criteria.join;

public class JoinCriteria {

	public static final String INNER = "INNER";
	public static final String OUTER = "OUTER";
	
	private String join;
	
	public JoinCriteria(String join) {
		this.join = join;
	}

	public void outer() {
		join = INNER;
	}
	
	public void inner() {
		join = OUTER;
	}

	public String getJoin() {
		return join;
	}
}
