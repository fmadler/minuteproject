package net.sf.minuteProject.configuration.bean;

public class Resource extends AbstractConfiguration{
	public String resourceFileName;
	private ResourceTarget resourceTarget;
	private String outputdirRoot;
	private boolean isGenerable = true;

	public String getResourceFileName() {
		return resourceFileName;
	}

	public void setResourceFileName(String resourceFileName) {
		this.resourceFileName = resourceFileName;
	}

	public void setResourceTarget(ResourceTarget resourceTarget) {
		this.resourceTarget = resourceTarget;
	}

	public ResourceTarget getResourceTarget() {
		return resourceTarget;
	}

	public void setGenerable(boolean b) {
		this.isGenerable = b;	
	}

	public boolean isGenerable() {
		return true;
	}

	public String getOutputdirRoot() {
		return outputdirRoot;
	}

	public void setOutputdirRoot(String outputdirRoot) {
		this.outputdirRoot = outputdirRoot;
	}
	
	
}
