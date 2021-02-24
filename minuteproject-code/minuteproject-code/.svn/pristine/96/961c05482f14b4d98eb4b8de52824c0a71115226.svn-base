package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.utils.ModelUtils;
import net.sf.minuteProject.utils.StringUtils;

public class Application extends GeneratorQualifier{

	private List<Model> models;
	private List<Model> rdbmsModels;
	
	public List<Model> getModels() {
		if (models == null) models = new ArrayList<Model>();
		return models;
	}
	
	public List<Model> getRdmsModels() {
		if (rdbmsModels == null) {
			rdbmsModels = new ArrayList<Model>();
			for (Model model : getModels()) {
				if (model.hasDataModel()) {
					rdbmsModels.add(model);
				}
			}
		}
		return rdbmsModels;
	}
	
	public boolean hasCmisModel() {
		for (Model model : getModels()) {
			if (model.hasCmisModel()) {
				return true;
			}
		}
		return true;
	}
	
	public void addModel (Model model) {
		model.setApplication(this);
		getModels().add(model);
	}
	
}
