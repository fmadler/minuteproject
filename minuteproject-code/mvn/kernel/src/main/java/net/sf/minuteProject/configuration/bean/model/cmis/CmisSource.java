package net.sf.minuteProject.configuration.bean.model.cmis;

public class CmisSource {

//	<repository-url>http://repotst.trade.cec.eu.int:8080/repo/atom11</repository-url>
//	<repository-id>atompub</repository-id>
//	<repository-binding>atompub</repository-binding>
//	<username>adlerfl</username>
//	<password></password>	
//	<folder>adlerfl</folder>
	
	private CmisModel cmisModel;
	
	private String repositoryUrl, repositoryId, repositoryBinding, username, password, folder;

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	public String getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}

	public String getRepositoryBinding() {
		return repositoryBinding;
	}

	public void setRepositoryBinding(String repositoryBinding) {
		this.repositoryBinding = repositoryBinding;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public CmisModel getCmisModel() {
		return cmisModel;
	}

	public void setCmisModel(CmisModel cmisModel) {
		this.cmisModel = cmisModel;
	}
	
	
}
