package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

public class ResourceTarget extends AbstractConfiguration {
	
	private Target target;
	List<Resource> resources;
	private String outputdir;
	private String templatedirRoot;
	private boolean isGenerable;
	

	public void setIsGenerable(boolean b) {
		this.isGenerable = b;	
	}

	public boolean isGenerable() {
		return isGenerable;
	}
	
	public List<Resource> getResources() {
		if (resources==null)
			resources = new ArrayList<Resource>();
		return resources;
	}
	
	public void addResource (Resource resource) {
		resource.setResourceTarget(this);
		getResources().add(resource);
	}

	public String getOutputdir() {
		String outputdirRoot = getTarget().getOutputdirRoot();
		return outputdirRoot+"/"+outputdir;
	}

	public void setOutputdir(String outputdir) {
		this.outputdir = outputdir;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public Target getTarget() {
		return target;
	}

	public void setRootdir(String templatedirRoot) {
		this.templatedirRoot = templatedirRoot;
	}

	public String getTemplatedirRoot() {
		return templatedirRoot;
	}

	public void setTemplatedirRoot(String templatedirRoot) {
		this.templatedirRoot = templatedirRoot;
	}
	
	
	
}
