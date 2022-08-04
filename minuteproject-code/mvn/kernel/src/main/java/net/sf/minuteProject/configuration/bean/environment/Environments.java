package net.sf.minuteProject.configuration.bean.environment;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Configuration;

public class Environments extends AbstractConfiguration{

	private String key, store, storeEntity;
	private String configQueryId, configQueryResultKey, configQueryResultValue;
	private List<Environment> environments;
	private Configuration configuration;

	public List<Environment> getEnvironments() {
		if (environments==null) environments = new ArrayList<Environment>();
		return environments;
	}

	public void setEnvironments(List<Environment> environments) {
		this.environments = environments;
	}
	
	public void addEnvironment (Environment environment) {
		environment.setEnvironments(this);
		getEnvironments().add(environment);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getStoreEntity() {
		return storeEntity;
	}

	public void setStoreEntity(String storeEntity) {
		this.storeEntity = storeEntity;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String getPropertyPlaceholder() {
		return "environment-${envTarget}.properties";
//		return StringUtils.isNotEmpty(storeEntity)?storeEntity : 
//			configuration.getApplication()!=null?"environment"+configuration.getApplication().getName()+".properties":
//			"environment.properties";
	}

	public String getConfigQueryId() {
		return configQueryId;
	}

	public void setConfigQueryId(String configQueryId) {
		this.configQueryId = configQueryId;
	}

	public String getConfigQueryResultKey() {
		return configQueryResultKey;
	}

	public void setConfigQueryResultKey(String configQueryResultKey) {
		this.configQueryResultKey = configQueryResultKey;
	}

	public String getConfigQueryResultValue() {
		return configQueryResultValue;
	}

	public void setConfigQueryResultValue(String configQueryResultValue) {
		this.configQueryResultValue = configQueryResultValue;
	}
	
	
}
