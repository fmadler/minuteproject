package net.sf.minuteProject.configuration.bean.target;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class TargetParams extends AbstractConfiguration {

	private List<TargetParam> targetParams;

	public List<TargetParam> getTargetParams() {
		if (targetParams==null)
			return new ArrayList<TargetParam>();
		return targetParams;
	}

	public void setTargetParams(List<TargetParam> targetParams) {
		this.targetParams = targetParams;
	}
	
	public void addTargeParam (TargetParam targetParam) {
		getTargetParams().add(targetParam);
	}
	
}
