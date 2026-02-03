package net.sf.minuteProject.configuration.bean.target;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

public class ImportTarget extends AbstractConfiguration {

	private String action, targetDir, targetName;
	private List<TargetParam> targetParams;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTargetDir() {
		return targetDir;
	}

	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

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
