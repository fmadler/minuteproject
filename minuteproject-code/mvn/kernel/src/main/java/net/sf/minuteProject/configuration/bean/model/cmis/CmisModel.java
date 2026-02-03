package net.sf.minuteProject.configuration.bean.model.cmis;

import net.sf.minuteProject.configuration.bean.Model;

public class CmisModel {

	private Model model;
	private CmisSource cmisSource;
	
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public CmisSource getCmisSource() {
		return cmisSource;
	}
	public void setCmisSource(CmisSource cmisSource) {
		this.cmisSource = cmisSource;
		this.cmisSource.setCmisModel(this);
	}
	
}
