package net.sf.minuteProject.model.data.criteria.value;

public class LongValue extends DigitReturnValue<Long>{

	public LongValue(Long i) {
		setValue(i);
	}

	@Override
	protected void setDefaultValue() {
		setValue(getDefaultValue());
	}

	@Override
	protected Long getDefaultValue() {
		return Long.valueOf(0);
	}

	@Override
	protected DigitReturnValue<Long> getDefault() {
		return new LongValue(getDefaultValue());
	}
}
