package net.sf.minuteProject.application;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.FunctionModel;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.StatementModel;
import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.Targets;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;
import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.environment.Environment;
import net.sf.minuteProject.configuration.bean.environment.Environments;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Component;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Function;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.configuration.bean.model.statement.CmisQueryModel;
import net.sf.minuteProject.configuration.bean.model.statement.Composite;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.configuration.bean.model.statement.QueryModel;
import net.sf.minuteProject.configuration.bean.model.statement.QueryPivot;
import net.sf.minuteProject.configuration.bean.service.Scope;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;
import net.sf.minuteProject.utils.ServiceUtils;
import net.sf.minuteProject.utils.TemplateUtils;
import net.sf.minuteProject.utils.io.UpdatedAreaUtils;

/**
 * @author Florian Adler
 * 
 */
public class ModelGenerator extends AbstractGenerator {

	private static Logger logger = Logger.getLogger(ModelGenerator.class);
	public static final String GENERATOR_MODEL_RULES = "net/sf/minuteProject/configuration/model-config-rules.xml";
	public static final String GENERATOR_MODEL_PROPERTY_RULES = "net/sf/minuteProject/configuration/model-property-config-rules.xml";

	private Model model;

	private String modelConfig;

	public String getModelConfig() {
		return modelConfig;
	}

	public void setModelConfig(String modelConfig) {
		this.modelConfig = modelConfig;
	}

	/**
	 * Constructs the generator with its configuration used by command line
	 * 
	 * @param configurationFile
	 */
	public ModelGenerator(String configurationFile) {
		super(configurationFile);
	}

	/**
	 * Constructor for integration with BasicIntegrationConfiguration used by
	 * swing client
	 * 
	 * @param bic
	 */
	public ModelGenerator(BasicIntegrationConfiguration bic) {
		super(bic);
	}

	/**
	 * Constructor for integration with BasicIntegrationConfiguration used by
	 * swing client
	 * 
	 * @param bic
	 */
	public ModelGenerator(Model model) {
		this.model = model;
	}

	@Override
	public Configuration getConfigurationRoot() {
		return new Configuration();
	}

	@Override
	public String getConfigurationRulesFile() {
		return GENERATOR_MODEL_RULES;
	}

	@Override
	public String getPropertyConfigurationRulesFile() {
		return GENERATOR_MODEL_PROPERTY_RULES;
	}

	public static void main(String args[]) {
		String config;
		if (args.length < 1) {
			System.exit(1);
		}
		config = args[0];
		Date startDate = new Date();
		logger.info("start time = " + startDate);
		ModelGenerator generator = new ModelGenerator(config);
		try {
			generator.generate();
		} catch (MinuteProjectException e) {
			generator.exit("");
		}
		Date endDate = new Date();
		logger.info("end time = " + endDate);
		logger.info("time taken : " + (endDate.getTime() - startDate.getTime())
				/ 1000 + "s.");
	}

	protected void generate(Configuration configuration)
			throws MinuteProjectException {
		Model model = getEnrichedModel(configuration.getModel());
		// generate for target
		generate(model, false);
	}

	public void generate(Model model, boolean targetLoaded)
			throws MinuteProjectException {
		Targets targets = null;
		if (!targetLoaded) {
			if (hasTarget()) {
				// loadAndGenerate(model.getConfiguration().getTarget());
				final Target target = model.getConfiguration().getTarget();
				loadTarget(model.getConfiguration(), target);
				
				applyTargetConventionAndGenerate(target);
			}
			if (hasTargets()) {
				targets = model.getConfiguration().getTargets();
				// loadAndGenerate(targets);
				//TODO LOG outputdirRoot (resolved)
				targets.getOutputdirRoot();

				Configuration configuration = loadTargets(targets);
				applyTargetConventionAndGenerate(configuration.getTarget());
			}
			targetLoaded = true;
		} else {
			applyTargetConventionAndGenerate(model.getConfiguration()
					.getTarget());
		}
		if (hasPostGenerationAction(targets)) {
			executePostGenerationAction(targets);
		}
	}

	private void executePostGenerationAction(Targets targets) {
		targets.getPostGenerationAction().run();
	}

	private boolean hasPostGenerationAction(Targets targets) {
		return targets != null && targets.getPostGenerationAction() != null;
	}

	public Model getEnrichedModel(Model model) {
		setModel(model);
		loadModel(model);
		applyConventions(model);
		applyLimitations(model);
		return model;
	}

	private void applyLimitations(Model model) {
		model.getBusinessModel().applyLimitations();
	}

	protected void applyConventions(Model model) {
		if (model.hasBusinessModel())
			model.getBusinessModel().applyConventions();
		if (model.hasStatementModel())
			model.getStatementModel().applyConventions();
	}

	protected boolean hasTarget() {
		return model.getConfiguration().hasTarget();
	}

	protected boolean hasTargets() {
		return model.getConfiguration().hasTargets();
	}

	protected void loadAndGenerate(Target target) throws MinuteProjectException {
		loadTarget(model.getConfiguration(), target);
		applyTargetConventionAndGenerate(model.getConfiguration().getTarget());
	}

	protected void loadAndGenerate(Targets targets)
			throws MinuteProjectException {
		Configuration configuration = loadTargets(targets);
		applyTargetConventionAndGenerate(configuration.getTarget());
	}

	private Configuration loadTargets(Targets targets)
			throws MinuteProjectException {
		Target targetFinal = new Target();
		Configuration configuration = model.getConfiguration();
		for (Target target : targets.getTargets()) {
			// use configuration.target as temp storage of processed target
			loadTarget(configuration, target);
			configuration.getTarget().setIsGenerable(target.isGenerable());
			targetFinal.complement(configuration.getTarget());
			targetFinal.complementAdditional(target);
			configuration.setTarget(new Target()); // reset target storage
		}
		configuration.setTarget(targetFinal);
		setTemplatePackageRoot(configuration);
		return configuration;
	}

	private void setTemplatePackageRoot(Configuration configuration) {
		// for (Target target : configuration.getTargets().getTargets()) {

		for (TemplateTarget templateTarget : configuration.getTarget()
				.getTemplateTargets()) {
			for (Template template : templateTarget.getTemplates()) {
				// template.setPackageRoot(model.getPackageRoot());
			}
		}

	}

	private void applyTargetConventionAndGenerate(Target target)
			throws MinuteProjectException {
		//TODO LOG outputdirRoot (resolved)
		logger.info (">> absolute directory for target "+target.getName() + ": "+target.getAbsoluteRootDir(target.getOutputdirRoot()));
		applyTargetConvention(target);
		generate(target);
	}

	private void applyTargetConvention(Target target) {
		model.getConfiguration().applyConventions();
	}

	protected void loadRdbmsModel(Model model) {
		// load model
		model.getDataModel().loadDatabase();

		// complement model
		BusinessModel businessModel = model.getBusinessModel();
		businessModel.secureEntityType();
		businessModel.complementDataModelWithTables();
		businessModel.complementDataModelWithTransferEntitiesEnrichment();
		if (model.hasFunctionModel()) {
			FunctionModel functionModel = model.getFunctionModel();
			functionModel.complementFunctionWithFunctionEntity();
		}
		if (model.hasStatementModel()) {
			StatementModel statementModel = model.getStatementModel();
			statementModel.complementStatement();
		}
	}
	
	protected void loadCmisModel(Model model) {
		//load model
		if (model.hasStatementModel()) {
			StatementModel statementModel = model.getStatementModel();
			for (Query query : statementModel.getQueries().getQueries()) {
				query.setQueryModel(new CmisQueryModel());
			}
			statementModel.complementStatement();
		}
	}
	
	protected void loadModel(Model model) {
		//load model
		if (model.hasCmisModel()) {
			loadCmisModel(model);
		} else if (model.hasDataModel()){
			loadRdbmsModel(model); 
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.minuteProject.application.Generator#generate(net.sf.minuteProject
	 * .configuration.bean.Template)
	 */
	public void generate(Template template) throws MinuteProjectException {
		if (!template.isActive()) {
			logger.info(">>> template is inactive ");
			return;
		}
		String scopeSpecificValue = template.getScopeSpecificValue();
		if (template.getFieldSpecific().equals("true")
				|| SCOPE_DATAMODEL_FIELD.equals(scopeSpecificValue))
			generateArtifactsByField(template);
		else if (template.getEntitySpecific().equals("true")
				|| SCOPE_DATAMODEL_ENTITY.equals(scopeSpecificValue))
			generateArtifactsByEntity(template);
		else if (template.getPackageSpecific().equals("true")
				|| SCOPE_DATAMODEL_PACKAGE.equals(scopeSpecificValue))
			generateArtifactsByPackage(template);
		else if (template.getModelSpecific().equals("true")
				|| SCOPE_DATAMODEL_MODEL.equals(scopeSpecificValue))
			generateArtifactsByModel(template);
		else if (template.getServiceSpecific().equals("true"))
			generateArtifactsByService(template);
		else if (template.isApplicationScope())
			generateArtifactsByApplication(template);
		else if (SCOPE_FOREIGNKEY_APPLICATION.equals(scopeSpecificValue))
			generateArtifactsByForeignKey(template);
		else if (template.getComponentSpecific().equals("true"))
			generateArtifactsByComponent(template);
		else {
			if (SCOPE_DATAMODEL_FUNCTION_INPUT.equals(scopeSpecificValue))
				generateArtifactsByFunction(template, Direction.IN);
			else if (SCOPE_DATAMODEL_FUNCTION_OUTPUT.equals(scopeSpecificValue))
				generateArtifactsByFunction(template, Direction.OUT);
			else if (SCOPE_DATAMODEL_FUNCTION.equals(scopeSpecificValue))
				generateArtifactsByFunction(template);
			else if (SCOPE_TARGET_TEMPLATE.equals(scopeSpecificValue))
				generateArtifactsByTargetTemplate(template);
			else if (SCOPE_TRANSFER_ENTITY_TEMPLATE.equals(scopeSpecificValue))
				generateArtifactsByTransferEntity(template);
			else if (SCOPE_ACTION_TEMPLATE.equals(scopeSpecificValue))
				generateArtifactsByAction(template);
			else if (QUERY_ACTION_TEMPLATE.equals(scopeSpecificValue))
				generateArtifactsByQuery(template);
			else if (QUERY_PACKAGE_ACTION_TEMPLATE.equals(scopeSpecificValue))
				generateArtifactsByQueryPackage(template);
			else if (PIVOT_QUERY_ACTION_TEMPLATE.equals(scopeSpecificValue))
				generateArtifactsByQueryPivot(template);
			else if (SDD_COMPOSITE_TEMPLATE.equals(scopeSpecificValue))
				generateArtifactsBySddCompositeBean(template, Direction.IN);
			else if (SDD_INPUT_COMPOSITE_TEMPLATE.equals(scopeSpecificValue))
				generateArtifactsBySddCompositeBean(template, Direction.IN);
			else if (SDD_OUTPUT_COMPOSITE_TEMPLATE.equals(scopeSpecificValue))
				generateArtifactsBySddCompositeBean(template, Direction.OUT);
			else if (SDD_INPUT_BEAN_TEMPLATE.equals(scopeSpecificValue))
				generateArtifactsBySddBean(template, Direction.IN);
			else if (SDD_OUTPUT_BEAN_TEMPLATE.equals(scopeSpecificValue))
				generateArtifactsBySddBean(template, Direction.OUT);
			else if (SCOPE_ENVIRONMENT.equals(scopeSpecificValue))
				generateEnvironment(template);
		}
	}

	private void generateArtifactsByAction(Template template)
			throws MinuteProjectException {
		for (Table table : getModel().getBusinessModel().getBusinessPackage()
				.getTransferEntities()) {
			for (Action action : table.getActions()) {
				writeTemplateResult(action, template);
			}
		}
	}

	private void generateArtifactsByQuery(Template template)
			throws MinuteProjectException {
		if (getModel().getStatementModel() != null
				&& getModel().getStatementModel().getQueries() != null) {
			for (Query query : getModel().getStatementModel().getQueries().getQueries()) {
				if (isToGenerate(query, template))
					writeTemplateResult(query, template);
			}
		}
	}
	
	private void generateArtifactsByQueryPackage(Template template)
			throws MinuteProjectException {
		if (getModel().getStatementModel() != null
				&& getModel().getStatementModel().getSddPackage() != null) {
			for (Package pack : getModel().getStatementModel().getSddPackage().getQueryPackages()) {
				if (isToGenerate(pack, template))
					writeTemplateResult(pack, template);
			}
		}
	}
	
	private void generateArtifactsByQueryPivot(Template template) throws MinuteProjectException {
		if (getModel().getStatementModel()!=null && getModel().getStatementModel().getQueries()!=null) {
			for (Query<QueryModel> query : getModel().getStatementModel().getQueries().getQueries()) {
				for (QueryPivot pivot : query.getPivots()) {
					if (isToGenerate(pivot, template))
						writeTemplateResult(pivot, template);
				}
			}
		}
	}

	protected void generateArtifactsByTargetTemplate(Template template)
			throws MinuteProjectException {
		writeTemplateResult(getModel().getConfiguration(), template);
	}
	
	protected void generateEnvironment(Template template)
			throws MinuteProjectException {
		Environments environments = getModel().getConfiguration().getEnvironments();
		if (environments!=null) {
			for (Environment environment : environments.getEnvironments()) {
				writeTemplateResult(environment, template);
			}
		}
	}

	protected void generateArtifactsByModel(Template template)
			throws MinuteProjectException {
		if (isToGenerate(getModel(), template))
//			if (isToGenerate(getModel(), template))
				writeTemplateResult(getModel(), template);
	}

	protected void generateArtifactsByPackage(Template template)
			throws MinuteProjectException {
		List<Package> packages = getModel().getBusinessModel()
				.getBusinessPackage().getPackages();
		for (Package pack : packages) {
			writeTemplateResult(pack, template);
		}
	}

	protected void generateArtifactsByField(Template template)
			throws MinuteProjectException {
		for (Table table : getModel().getBusinessModel().getBusinessPackage()
				.getTables()) {
			generateArtifactsByField(template, table);
		}
		for (Table table : getModel().getBusinessModel().getBusinessPackage()
				.getTransferEntities()) {
			generateArtifactsByField(template, table);
		}
	}

	protected void generateArtifactsByField(Template template, Table table)
			throws MinuteProjectException {
		table = getDecoratedTable(table);
		for (Column column : table.getColumns()) {
			if (isToGenerate(column, template))
				writeTemplateResult(column, template);
		}
	}

	protected void generateArtifactsByTransferEntity(Template template)
			throws MinuteProjectException {
		for (Table table : getModel().getBusinessModel().getBusinessPackage()
				.getTransferEntities()) {
			generateArtifactsByEntity(table, template);
		}
	}

	protected void generateArtifactsByEntity(Template template)
			throws MinuteProjectException {
		for (Table table : getModel().getBusinessModel().getBusinessPackage()
				.getTables()) {
			generateArtifactsByEntity(table, template);
		}
	}

	protected void generateArtifactsByEntity(Table table, Template template)
			throws MinuteProjectException {
		table = getDecoratedTable(table);
		if (isToGenerate(table, template))
			writeTemplateResult(table, template);
	}

	protected void generateArtifactsByService(Template template)
			throws MinuteProjectException {
		for (Scope scope : getModel().getBusinessModel().getService()
				.getScopes()) {
			if (ServiceUtils.isToGenerate(template, scope))
				writeTemplateResult(scope, template);
		}
	}

	private void generateArtifactsByApplication(Template template)
			throws MinuteProjectException {
		if (isToGenerate(getModel(), template)
				&& getModel().getConfiguration().isSingleModel()) {
			template.setAddModelDirName("false");
			writeTemplateResult(getModel(), template);
		}
	}

	private void generateArtifactsByForeignKey(Template template)
			throws MinuteProjectException {
		for (Table table : getModel().getBusinessModel().getBusinessPackage()
				.getTables()) {
			for (ForeignKey foreignKey : table.getForeignKeys()) {
				generateArtifactsByForeignKey(foreignKey, template);
			}
		}
	}

	protected void generateArtifactsByForeignKey(ForeignKey foreignKey,
			Template template) throws MinuteProjectException {
		// if (isToGenerate(foreignKey, template))
		writeTemplateResult(foreignKey, template);
	}

	protected void generateArtifactsByComponent(Template template)
			throws MinuteProjectException {
		writeTemplateResult(getModel().getConfiguration(), template);
	}

	protected void generateArtifactsByFunction(Template template) throws MinuteProjectException {	
		if (getModel().hasDataModel()) {
			for (Function function : getModel().getDataModel().getDatabase().getFunctions()) {
				writeTemplateResult(function, template);
			}
		}
	}

	protected void generateArtifactsByFunction(Template template,
			Direction direction) throws MinuteProjectException {
		for (Function function : getModel().getDataModel().getDatabase()
				.getFunctions()) {
			List<Direction> functionDirections = function.getDirections();
			// for (Direction dir : direction) { // dir has to be put in the
			// correct order IN or OUT before NONE, INOUT
			for (Direction fdir : functionDirections) {
				if (direction.equals(fdir)) {
					writeTemplateResult(function.getEntity(direction), template);
					// break;
				}
			}
			// }
		}
	}

	protected void generateArtifactsBySddBean(Template template, Direction direction) throws MinuteProjectException {
		StatementModel statementModel = getModel().getStatementModel();
		if (statementModel != null) {
			for (Query query : statementModel.getQueries().getQueries()) {
				if (isIndirectionCompatible(query, template)) {
					Table table = query.getEntity(direction);
					if (table.getDatabase()==null) {//possible when queryParamLink used before declared
						table.setDatabase(statementModel.getModel().getDataModel().getDatabase());
					}
					table = getDecoratedTable(table);
					if (isToGenerate(table, template)) {
						writeTemplateResult(table, template);
					}
				}
			}
		}
	}

	protected void generateArtifactsBySddCompositeBean(Template template,
			Direction direction) throws MinuteProjectException {
		StatementModel statementModel = getModel().getStatementModel();
		if (statementModel != null) {
			for (Composite composite : statementModel.getComposites()
					.getComposites()) {
				writeTemplateResult(composite.getComposite(direction), template);
			}
		}
	}

	protected void writeTemplateResult(GeneratorBean bean, Template template)
			throws MinuteProjectException {
		// enable cache
		bean.enableCache();
		// velocity bean manipulation
		String outputFilename = template
				.getGeneratorOutputFileNameForConfigurationBean(bean, template);
		// context
		VelocityContext context = getVelocityContext(template);
		Map<String, String> updatedAreas = TemplateUtils.getUpdatedAreas(
				template, bean);
		if (updatedAreas != null) {
			if (updatedAreas
					.containsKey(UpdatedAreaUtils.MP_MANAGED_STOP_GENERATING))
				return; // stop generating directive
			context.put("updatedAreas", updatedAreas);
		}
		String beanName = getAbstractBeanName(bean);
		context.put(beanName, bean);
		if (bean instanceof Component) {
			Component component = (Component) bean;
			Table table = component.getTable();
			context.put("table", table);
		}
		if (bean instanceof Function) {
			context.put("function", bean);
			context.put("table", ((Function) bean).getEntity(Direction.ANY)); // to
																				// give
																				// access
																				// to
																				// model
			// context.put("table", bean);
		}
		if (beanName.equals("view"))
			context.put("table", bean);
		context.put("template", template);
		putCommonContextObject(context, template);
		//
		try {
			produce(context, template, outputFilename);
		} catch (Exception ex) {
			logger.error("ERROR on template "+template.getName()+" - "+template.getTemplateFileName()+" - on bean "+bean.getName());
			ex.printStackTrace();
			throwException(ex, "ERROR : " + ex.getMessage());
			// logger.error("ERROR : "+ex.getMessage());
			// throw ex;
		}
	}

	protected void putCommonContextObject(VelocityContext context,
			Template template) {
		putStandardContextObject(context);
		putPluginContextObject(context, template);
		context.put("model", model);
		context.put("configuration", model.getConfiguration());
	}

	protected void putStandardContextObject(VelocityContext context) {
		super.putStandardContextObject(context);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}
