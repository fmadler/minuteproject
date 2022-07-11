package net.sf.minuteProject.utils.wsdl;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.WebServiceModel;
import net.sf.minuteProject.configuration.bean.model.webservice.WsdlModel;
import org.apache.commons.lang.StringUtils;

public class WsdlUtils {

//	#set($wsdlDirectory=$wsdlUtils.getWsdlDirectory($model))
//	#set($wsdlFile=$wsdlUtils.getWsdlFile($model))
	public static String getWsdlDirectory (Model model) {
		return model.getWebServiceModel().getWsdl().getDir();
	}

	public static String getWsdlRootDirectory (Model model) {
		return model.getWebServiceModel().getWsdl().getRootdir();
	}

	public static boolean hasWsdlRootDirectory (Model model) {
		String s = model.getWebServiceModel().getWsdl().getRootdir();
		return s!=null && !s.equals ("");
	}

	public static String getWsdlFile (Model model) {
		return getWsdlFile (model.getWebServiceModel());
	}
	
	static String getWsdlFile (WebServiceModel webServiceModel) {

		return webServiceModel.getWsdl().getFile();
	}

	static String getWsdlFileWithoutExtension (WebServiceModel webServiceModel) {
		return StringUtils.removeEnd (webServiceModel.getWsdl().getFile(), ".wsdl");
	}
	
	static String getWsdlFileRootDir (WebServiceModel webServiceModel) {
		return webServiceModel.getWsdl().getRootdir();
	}

	static String getWsdlFileDir (WebServiceModel webServiceModel) {
		return StringUtils.replace (webServiceModel.getWsdl().getDir(), "\\", "/");
	}
	
/*
to migrate from java
	public static Environment getEnvironment(WsdlModel wsdlModel, String environmentName) {
		Configuration configuration = getModel(wsdlModel).getConfiguration();
		return configuration.getEnvironmentByName(environmentName);
	}*/
	public static Model getModel(WsdlModel wsdlModel) {
		return wsdlModel.getWebServiceModel().getModel();
	}
}
