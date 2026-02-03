package net.sf.minuteProject.configuration.bean.model.statement;

public interface Validation {
	
	/**
	 * @return autonomous is set to true when related to the bean only, 
	 * false is linked to external validation such as uniqueness in DB
	 */
	default boolean isAutonomous () {
		return true;
	}
}
