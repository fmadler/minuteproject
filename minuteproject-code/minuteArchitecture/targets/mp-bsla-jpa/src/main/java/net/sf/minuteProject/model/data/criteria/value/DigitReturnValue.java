package net.sf.minuteProject.model.data.criteria.value;

import net.sf.minuteProject.model.data.criteria.face.IFunction;

public abstract class DigitReturnValue<T> extends ReturnValue<T> implements IFunction{

	DigitReturnValue<T> avg;
	
	abstract protected DigitReturnValue<T> getDefault ();
	
}
