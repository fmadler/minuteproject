package net.sf.minuteProject.application;

import net.sf.minuteProject.exception.MinuteProjectException;

public class LoadDbModelFromConfiguration {

	private String configuration;
	private ModelViewGenerator modelViewGenerator;
	
	public String load () {
		modelViewGenerator = new ModelViewGenerator(configuration);
		try {
			modelViewGenerator.load();
		} catch (MinuteProjectException e) {
			// TODO Auto-generated catch block
			return e.getError();
		}
		return "SUCCESSFUL";
	}
	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	
}
