package net.sf.minuteProject.configuration.bean;

import net.sf.minuteProject.configuration.bean.model.cmis.CmisModel;
import net.sf.minuteProject.utils.ModelUtils;
import net.sf.minuteProject.utils.StringUtils;
import net.sf.minuteProject.utils.property.PropertyUtils;

public class Model extends GeneratorQualifier{
	
	private static final String DEFAUTMODEL_VALUE = "defautmodel";
	
	private Application application;
	private DataModel dataModel;
	private CmisModel cmisModel;
	private BusinessModel businessModel;
	private FunctionModel functionModel;
	private StatementModel statementModel;
	private WebServiceModel webServiceModel;

	public WebServiceModel getWebServiceModel() {
		if (webServiceModel==null) {
			webServiceModel=new WebServiceModel();
//			webServiceModel.setModel(this);
		}		
		return webServiceModel;
	}

	public void setWebServiceModel(WebServiceModel webServiceModel) {
		this.webServiceModel = webServiceModel;
		this.webServiceModel.setModel(this);
	}

	public BusinessModel getBusinessModel() {
		if (businessModel==null) {
			businessModel=new BusinessModel();
			businessModel.setModel(this);
		}
		return businessModel;
	}

	public void setBusinessModel(BusinessModel businessModel) {
		businessModel.setModel(this);
		this.businessModel = businessModel;
	}
	
	public boolean hasBusinessModel() {
		if (businessModel!=null)
			return true;
		return false;
	}

	public CmisModel getCmisModel() {
		return cmisModel;
	}
	
	public void setCmisModel(CmisModel cmisModel) {
		cmisModel.setModel(this);
		this.cmisModel = cmisModel;
	}
	
	public boolean hasCmisModel() {
		if (cmisModel!=null)
			return true;
		return false;
	}
	
	public boolean hasRdbmsModel() {
		return hasDataModel();
	}
	
	public boolean hasDataModel() {
		if (dataModel!=null)
			return true;
		return false;
	}
	
	public DataModel getDataModel() {
		return dataModel;
	}

	public FunctionModel getFunctionModel() {
		return functionModel;
	}

	public void setFunctionModel(FunctionModel functionModel) {
		functionModel.setModel(this);
		this.functionModel = functionModel;
	}
	
	public boolean hasFunctionModel() {
		if (functionModel!=null)
			return true;
		return false;
	}
	
	public boolean hasStatementModel() {
		if (statementModel!=null
			&& statementModel.getQueries().getQueries().size()>0)
			return true;
		return false;
	}

	public void setDataModel(DataModel dataModel) {
		dataModel.setModel(this);
		this.dataModel = dataModel;
	}

	public String getTechnicalPackage(Template template) {
		String packageRoot2 = ModelUtils.getPackageRoot(template);
		StringBuffer sb = new StringBuffer(packageRoot2);
		if ((template.getAddModelDirName()==null 
			|| template.getAddModelDirName().equals("")
			|| template.getAddModelDirName().equals("true"))	
			&& 
			!(template.getApplicationSpecific()!=null && template.getApplicationSpecific().equals("true"))
			) {
				sb.append((StringUtils.isEmpty(packageRoot2)? getName() :"."+getName()));
			}
		boolean isEmpty = StringUtils.isEmpty(sb.toString());
		String technicalPackage = template.getTechnicalPackage();
		if (technicalPackage!=null && !technicalPackage.equals(""))
			sb.append(isEmpty?technicalPackage:"."+technicalPackage);		
		return sb.toString();
	}

	public String getName() {
		if (StringUtils.isEmpty(name)){
			name= DEFAUTMODEL_VALUE;
		}
		return name;
	}

	public boolean isUsingDefaultName() {
		return DEFAUTMODEL_VALUE.equals(getName());
	}

	public StatementModel getStatementModel() {
		if (statementModel==null)
			statementModel = new StatementModel();
		return statementModel;
	}

	public void setStatementModel(StatementModel statementModel) {
		this.statementModel = statementModel;
		this.statementModel.setModel(this);
	}

	public boolean hasBusinessPackages () {
		return !getBusinessModel().getBusinessPackage().getPackages().isEmpty();
	}
	
	public boolean hasBusinessPackageViews () {
		return !getBusinessModel().getBusinessPackage().getPackageViews().isEmpty();
	}
	
	public boolean hasBusinessPackageTransferEntities () {
		return !getBusinessModel().getBusinessPackage().getPackageTransferEntities().isEmpty();
	}
	
	public boolean hasStatements () {
		return !getStatementModel().getQueries().getQueries().isEmpty();
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
	
	public String getVersion() {
		return (application==null)?version:application.getVersion();
	}

	public void setVersion(String version) {
		this.version = PropertyUtils.convertValueIfSystemOrEnvironmentVariable(version);
	}

	public String getPackageRoot() {
		return (application==null)? packageRoot:application.getPackageRoot()+"."+packageRoot;
	}

	public void setPackageRoot(String packageRoot) {
		this.packageRoot = packageRoot;
	}
	
	public Configuration getConfiguration() {
		return configuration!=null?configuration:application.getConfiguration();
	}
}
