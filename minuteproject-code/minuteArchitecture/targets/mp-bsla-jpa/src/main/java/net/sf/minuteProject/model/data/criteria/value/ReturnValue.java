package net.sf.minuteProject.model.data.criteria.value;

public abstract class ReturnValue<T> {

	private T t;
	
	public T value() {
		return (t!=null)?t:getDefaultValue();
	}
	
	public void setValue (T t) {
		this.t = t;
	}
	
	abstract protected void setDefaultValue () ;
	
	abstract protected T getDefaultValue () ;
	
	
}
