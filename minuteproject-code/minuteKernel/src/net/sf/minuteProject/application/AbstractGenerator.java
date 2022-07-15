package net.sf.minuteProject.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;

import net.sf.minuteProject.configuration.bean.AbstractConfigurationRoot;
import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Resource;
import net.sf.minuteProject.configuration.bean.ResourceTarget;
import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.Targets;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;
import net.sf.minuteProject.configuration.bean.model.data.DataModelFactory;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.system.Plugin;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;
import net.sf.minuteProject.plugin.environment.EnvironmentUtils;
import net.sf.minuteProject.plugin.format.I18nUtils;
import net.sf.minuteProject.plugin.readme.ReadmeUtils;
import net.sf.minuteProject.utils.BslaLibraryUtils;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.DatabaseUtils;
import net.sf.minuteProject.utils.EnumUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.MinuteProjectUtils;
import net.sf.minuteProject.utils.ModelUtils;
import net.sf.minuteProject.utils.ReferenceUtils;
import net.sf.minuteProject.utils.RoutineUtils;
import net.sf.minuteProject.utils.SqlUtils;
import net.sf.minuteProject.utils.TableUtils;
import net.sf.minuteProject.utils.TemplateUtils;
import net.sf.minuteProject.utils.TestUtils;
import net.sf.minuteProject.utils.TriggerUtils;
import net.sf.minuteProject.utils.URLUtils;
import net.sf.minuteProject.utils.ViewUtils;
import net.sf.minuteProject.utils.WebUtils;
import net.sf.minuteProject.utils.action.ActionUtils;
import net.sf.minuteProject.utils.catalog.TechnologyCatalogUtils;
import net.sf.minuteProject.utils.criteria.OrderingUtils;
import net.sf.minuteProject.utils.enrichment.EnrichmentUtils;
import net.sf.minuteProject.utils.enrichment.SemanticReferenceUtils;
import net.sf.minuteProject.utils.io.FileUtils;
import net.sf.minuteProject.utils.io.UpdatedAreaUtils;
import net.sf.minuteProject.utils.java.JavaUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;
import net.sf.minuteProject.utils.property.PropertyUtils;
import net.sf.minuteProject.utils.scheduler.SchedulerUtils;
import net.sf.minuteProject.utils.sql.QueryParamLinkUtils;
import net.sf.minuteProject.utils.sql.QueryUtils;
import net.sf.minuteProject.utils.sql.StatementUtils;
import net.sf.minuteProject.utils.velocity.VelocityUtils;

/**
 * @author Florian Adler
 * 
 */
public abstract class AbstractGenerator implements Generator {

	private static final String CATALOG = "catalog";
	protected static final String SCOPE_DATAMODEL_FUNCTION = "function";
	protected static final String SCOPE_DATAMODEL_ENTITY = "entity";
	protected static final String SCOPE_DATAMODEL_FIELD = "field";
	protected static final String SCOPE_DATAMODEL_PACKAGE = "package";
	protected static final String SCOPE_DATAMODEL_APPLICATION = "application";
	protected static final String SCOPE_FOREIGNKEY_APPLICATION = "foreignkey";
	protected static final String SCOPE_DATAMODEL_MODEL = "model";
	protected static final String SCOPE_DATAMODEL_FUNCTION_INPUT = "function-input-entity";
	protected static final String SCOPE_DATAMODEL_FUNCTION_OUTPUT = "function-output-entity";
	protected static final String SCOPE_TARGET_TEMPLATE = "target";
	protected static final String SCOPE_TRANSFER_ENTITY_TEMPLATE = "transfer-entity";
	protected static final String SCOPE_ACTION_TEMPLATE = "action";
	protected static final String QUERY_ACTION_TEMPLATE = "query";
	protected static final String QUERY_PACKAGE_ACTION_TEMPLATE = "query-package";
	protected static final String PIVOT_QUERY_ACTION_TEMPLATE = "query-pivot";
	protected static final String SCOPE_WSDL = "wsdl";
	protected static final String SCOPE_WSDL_ENTITY = "wsdl-entity";
	protected static final String SCOPE_WSDL_SERVICE = "wsdl-service";
	protected static final String SDD_INPUT_BEAN_TEMPLATE = "sdd-input-bean";
	protected static final String SDD_COMPOSITE_TEMPLATE = "composite";
	protected static final String SDD_INPUT_COMPOSITE_TEMPLATE = "sdd-input-composite-bean";
	protected static final String SDD_OUTPUT_COMPOSITE_TEMPLATE = "sdd-output-composite-bean";
	protected static final String SDD_OUTPUT_BEAN_TEMPLATE = "sdd-output-bean";
	protected static final String SCOPE_ENVIRONMENT = "environment";

	private static Logger logger = Logger.getLogger(AbstractGenerator.class);
	private String configurationFile;
	private String templatePath;
	private String templateLibPath;
	private Boolean isTemplateLibPathToReset = true;
	private Boolean isTemplatePathToReset = true;
	private BasicIntegrationConfiguration bic;

	/*
	 * context object
	 */
	private CommonUtils commonUtils = new CommonUtils();
	private ConvertUtils convertUtils = new ConvertUtils();
	private ColumnUtils columnUtils = new ColumnUtils();
	private ViewUtils viewUtils = new ViewUtils();
	private FormatUtils formatUtils = new FormatUtils();
	private BslaLibraryUtils bslaLibraryUtils = new BslaLibraryUtils();
	private DatabaseUtils databaseUtils = new DatabaseUtils();
	private ModelUtils modelUtils = new ModelUtils();
	private URLUtils urlUtils = new URLUtils();
	private TestUtils testUtils = new TestUtils();
	private WebUtils webUtils = new WebUtils();
	private SqlUtils sqlUtils = new SqlUtils();
	private TableUtils tableUtils = new TableUtils();
	private ReferenceUtils referenceUtils = new ReferenceUtils();
	private EnumUtils enumUtils = new EnumUtils();
	private I18nUtils i18nUtils = new I18nUtils();
	private UpdatedAreaUtils updatedAreaUtils = new UpdatedAreaUtils();
	private JavaUtils javaUtils = new JavaUtils();
	private RoutineUtils routineUtils = new RoutineUtils();
	private StatementUtils statementUtils = new StatementUtils();
	private TriggerUtils triggerUtils = new TriggerUtils();
	private QueryUtils queryUtils = new QueryUtils();
	private SemanticReferenceUtils semanticReference = new SemanticReferenceUtils();
	private VelocityUtils velocityUtils = new VelocityUtils();
	private FileUtils fileUtils = new FileUtils();
	private OrderingUtils orderingUtils = new OrderingUtils();
	private EnrichmentUtils enrichmentUtils = new EnrichmentUtils();
	private MinuteProjectUtils minuteprojectUtils = new MinuteProjectUtils();
	private ActionUtils actionUtils = new ActionUtils();

	/**
	 * The default constructor get the value of the configuration to which the
	 * generator is associated
	 * 
	 * @param configurationFile
	 */
	public AbstractGenerator(String configurationFile) {
		this.configurationFile = configurationFile;
		resetTemplatePath();
	}

	public AbstractGenerator(BasicIntegrationConfiguration bic) {
		this.bic = bic;
		resetTemplatePath();
	}

	public AbstractGenerator() {
	}

	/**
	 * gets the configuration file that is to be loaded
	 * 
	 * @return String
	 */
	public String getConfigurationFile() {
		return this.configurationFile;
	}

	/**
	 * gets the configuration rule file that is to be loaded
	 * 
	 * @return
	 */
	public abstract String getConfigurationRulesFile();

	/**
	 * gets the configuration rule file that is to be loaded
	 * 
	 * @return
	 */
	public abstract String getPropertyConfigurationRulesFile();

	/**
	 * gets the configuration root element
	 * 
	 * @return AbstractConfiguration
	 */
	public abstract Configuration getConfigurationRoot();

	// protected abstract void generate(Configuration configuration) throws
	// Exception;
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.minuteProject.application.Generator#load(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public final Configuration load(String configuration, String rules)
			throws Exception {
		Configuration abstractConfiguration = getConfigurationRoot();
		abstractConfiguration.setConfigurationFileInClassPath(configuration);
		InputStream configurationInputStream = getConfigurationInputStream(configuration);
		loadConfiguration(abstractConfiguration, configurationInputStream,
				rules);
		return abstractConfiguration;
	}

	private InputStream getConfigurationInputStream(String configurationFileName) {
		return getClass().getClassLoader().getSystemResourceAsStream(
				configurationFileName);
	}

	/**
	 * Starting from the target it loads another target into
	 * abstractconfigurationRooet
	 */
	@Override
	public void loadTarget(AbstractConfigurationRoot abstractConfigurationRoot,
			Target target) throws MinuteProjectException {
		try {
			loadConfiguration(
					abstractConfigurationRoot,
					getTargetConfigurationInputStream(
							abstractConfigurationRoot, target),
					GENERATOR_TARGET_RULES);
		} catch (Exception e) {
			throwException(
					e,
					"CANNOT LOAD TARGET FILE "
							+ resolveFileAbsolutePath(
									abstractConfigurationRoot, target)
							+ " - CHECK IT IS IN THE CLASSPATH AND WELL FORMATTED");
		}
		complementWithTargetInfo(abstractConfigurationRoot, target);
	}

	public void complementWithTargetInfo(
			AbstractConfigurationRoot abstractConfigurationRoot, Target target) {
		Target target2 = abstractConfigurationRoot.getTarget();
		// ERROR target2.setTargets(target.getTargets());
		target2.setDir(target.getDir());
		target2.setCanonicalDir(target.getCanonicalDir());
		target2.setOutputdirRoot(target.getOutputdirRoot());
		target2.setTemplatedirRoot(target.getTemplatedirRoot());
		target2.getProperties().addAll(target.getProperties());
		target.setAbstractConfigurationRoot(abstractConfigurationRoot);
	}

	protected InputStream getTargetConfigurationInputStream(
			AbstractConfigurationRoot abstractConfigurationRoot, Target target)
			throws FileNotFoundException {
		String filePath = resolveFileAbsolutePath(abstractConfigurationRoot,
				target);
		String dirPath = FileUtils.stripFileName(filePath);
		target.setCanonicalDir(dirPath);
		return new FileInputStream(new File(filePath));
	}

	private String resolveFileAbsolutePath(
			AbstractConfigurationRoot abstractConfigurationRoot, Target target) {
		String dir = target.getDir();
		if (dir != null) { // absolute path provided
			return dir + "/" + target.getFileName();
		} else {// relative path
			String result = FileUtils
					.getFileFullPath(abstractConfigurationRoot
							.getConfigurationFileInClassPath(), dir, target
							.getFileName());
			return result;
		}
	}

	protected void loadConfiguration(Object object, InputStream input,
			String rules) throws Exception {
		URL rulesURL = getClass().getClassLoader().getResource(rules);
		Digester digester = DigesterLoader.createDigester(rulesURL);
		digester.push(object);
		digester.parse(input);
	}

	public void generate() throws MinuteProjectException {
		// load configuration
		// load targets and target
		Configuration configuration = load();
		generate(configuration);
	}

	void generate(Configuration configuration) throws MinuteProjectException {
		throw new MinuteProjectException("generate method shall be implemented");
	}

	/**
	 * load the configuration root element
	 * 
	 * @return AbstractConfiguration
	 * @throws Exception
	 * @throws Exception
	 */
	public final Configuration load() throws MinuteProjectException {
		if (getConfigurationFile() != null)
			return loadFromConfigurationFile();
		return loadFromBIC();
	}

	public final Configuration loadFromConfigurationFile()
			throws MinuteProjectException {
		Configuration configuration = null;
		try {
			configuration = load(getConfigurationFile(),
					getConfigurationRulesFile());
			//
			// String outputdirRoot = null;
			// Targets ts = configuration.getTargets();
			// if (ts!=null)
			// outputdirRoot = ts.getOutputdirRoot();
			//
			if (configuration.hasTechnologyCatalogEntry()) {
				for (String catalogEntry : ParserUtils.getList(configuration
						.getTargets().getCatalogEntry())) {
					logger.info("load catalog-entry " + catalogEntry);
					// String outputDir =
					// (outputdirRoot!=null)?outputdirRoot:configuration.getTargets().getOutputdirRoot(catalogEntry);
					String outputDir = getGeneratorOuputDir(configuration,
							catalogEntry);
					Targets targets = TechnologyCatalogUtils.getTargets(
							catalogEntry, CATALOG, outputDir, configuration
									.getTargets().getTemplatedirRoot());
					appendTargets(configuration, targets);
				}
			}

		} catch (Exception e) {
			throwException(e, "CANNOT LOAD CONFIGURATION FILE "
					+ getConfigurationFile()
					+ " - CHECK IT IS IN THE CLASSPATH");
		}
		return configuration;
	}

	public String getGeneratorOuputDir(Configuration configuration,
			String catalogEntry) {
		String outputdirRoot = null;
		Targets ts = configuration.getTargets();
		if (ts != null) {
			outputdirRoot = ts.getOutputdirRoot();
		}
		if (outputdirRoot != null) {
			if (ts.getAppendCatalogEntryDirToOutputDirRoot())
				return outputdirRoot + "/" + catalogEntry;
			else
				return outputdirRoot;
		} else {
			return ts.getOutputdirRoot(catalogEntry);
		}
	}
	
	public boolean isIndirectionCompatible (GeneratorBean bean, Template template) {
		if (bean.isIndirection() && !template.isIndirectionCompatible()) 
			return false;
		return true;
	}

	protected boolean isToGenerate (GeneratorBean bean, Template template) {
		if (!isIndirectionCompatible(bean, template)) 
			return false;
		if (template.isToGenerateBasedOnModelType(bean)){
			if (template.getCheckTemplateToGenerate() != null
				&& template.getCheckTemplateToGenerate().equals("true")) {
				if (!template.isToGenerate(bean)) {
					return false;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
		return true;
	}

	private void appendTargets(Configuration configuration, Targets targets) {
		List<Property> props = configuration.getTargets().getProperties();
		for (Target target : targets.getTargets()) {
			target.addProperties(props); // TODO make override in template not
											// only append at target level
			configuration.getTargets().addTarget(target);
			target.addProperty(new Property("catalog-entry", configuration
					.getTargets().getCatalogEntry()));
		}
	}

	public final Configuration loadFromBIC() throws MinuteProjectException {
		Configuration abstractConfiguration = null;
		if (bic != null)
			abstractConfiguration = bic.getConfiguration();
		else
			throwException("NO CONFIGURATION PROVIDED");
		return abstractConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.minuteProject.application.Generator#generate(net.sf.minuteProject
	 * .configuration.bean.AbstractConfiguration,
	 * net.sf.minuteProject.configuration.bean.Target)
	 */
	@Override
	public void generate(Target target) throws MinuteProjectException {
		if (target.isGenerable()) {
			// for (TemplateTarget templateTarget : target.getTemplateTargets())
			// {
			// set priority
			for (TemplateTarget templateTarget : target
					.getOrderedByPriorityTemplateTargets()) {
				if (templateTarget.isGenerable()) {
					logger.info("> output dir "+target.getOutputdirRoot());
					logger.info("> template target set: "
							+ templateTarget.getName() + " in "
							+ templateTarget.getOutputdir());
					if (templateTarget != null) {
						// if (templateTarget.getTemplates()!=null) {
						// generate template
						for (Template template : templateTarget.getTemplates()) {
							logger.info(">> template: " + template.getName()
									+ " in " + template.getOutputdir());
							this.generate(template);
						}
						// }

					}
				}
			}
			for (ResourceTarget resourceTarget : target.getResourceTargets()) {
				if (resourceTarget.isGenerable()) {
					for (Resource resource : resourceTarget.getResources()) {
						if (resource.isGenerable())
							copy(resource);
					}
				}
			}
		}
	}

	private void copy(Resource resource) throws MinuteProjectException {
		// TODO improve copy process
		// relative path
		// + resource type
		String resourceFileName = resource.getResourceFileName();
		String templateOutputDir = resource.getResourceTarget().getOutputdir();
		final String outputDir = resource.getOutputdirRoot()
				+ templateOutputDir;
		String outputFile = outputDir + "/" + resourceFileName;

		String resourceRelativePath = Targets.getResourcedirRoot();
		String filePath = FileUtils.getFilePath(resourceRelativePath + "/"
				+ resourceFileName);
		try {
			// org.apache.commons.io.FileUtils.copyDirectory(new File(dirPath),
			// new File(outputDir));

			File input = new File(filePath);
			if (input.exists()) {
				org.apache.commons.io.FileUtils.forceMkdir(new File(outputDir));
				org.apache.commons.io.FileUtils.copyFileToDirectory(input,
						new File(outputDir));
				// org.apache.commons.io.FileUtils.copyFile(input, new
				// File(outputFile));
				logger.info(">>resource: " + filePath + " copied to dir "
						+ outputFile);
			} else
				logger.info(">>resource: " + filePath + " does not exist ");
			/**/
		} catch (IOException e) {

			throw new MinuteProjectException("cannot copy file " + filePath
					+ " to " + templateOutputDir + " - error :"
					+ e.getMessage());
		}
	}

	protected VelocityContext getVelocityContext(Template template) {
		// Properties p = new Properties();

		// Velocity.clearProperty(Velocity.FILE_RESOURCE_LOADER_PATH);
		// Velocity.clearProperty(Velocity.VM_LIBRARY);
		// p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,getTemplatePath(template));
		// p.setProperty(Velocity.VM_LIBRARY,getTemplateRelativeLibPath(template));

		VelocityContext context = new VelocityContext();
		try {
			Velocity.setExtendedProperties(getExtendedProperties(template));
			// Velocity.addProperty(Velocity.FILE_RESOURCE_LOADER_PATH,getTemplatePath(template));
			// Velocity.addProperty(Velocity.VM_LIBRARY,getTemplateRelativeLibPath(template));
			// Velocity.init(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return context;
	}

	private ExtendedProperties getExtendedProperties(Template template) {
		ExtendedProperties extendedProperties = new ExtendedProperties();
		extendedProperties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,
				getTemplatePath(template));
		extendedProperties.setProperty(Velocity.VM_LIBRARY,
				getTemplateRelativeLibPath(template));
		// extendedProperties.setProperty(RuntimeConstants.RESOURCE_LOADER,
		// "string");
		// extendedProperties.setProperty("classpath.resource.loader.class",StringResourceLoader.class.getName());
		return extendedProperties;
	}

	private ExtendedProperties getClasspathLoaderProperties(Template template) {
		ExtendedProperties extendedProperties = new ExtendedProperties();
		extendedProperties.setProperty(RuntimeConstants.RESOURCE_LOADER,
				"classpath");
		extendedProperties.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		return extendedProperties;
	}

	private ExtendedProperties getStringLoaderEmbeddedProperties(
			Template template) {
		ExtendedProperties extendedProperties = new ExtendedProperties();
		extendedProperties.setProperty(RuntimeConstants.RESOURCE_LOADER,
				"string");
		extendedProperties.setProperty("classpath.resource.loader.class",
				StringResourceLoader.class.getName());
		return extendedProperties;
	}

	protected void putPluginContextObject(VelocityContext context,
			Template template) {
		List<Plugin> plugins = template.getTemplateTarget().getTarget()
				.getPlugins();
		for (Plugin plugin : plugins) {
			ClassLoader cl = ClassLoader.getSystemClassLoader();
			try {
				Class clazz = cl.loadClass(plugin.getClassName());
				Object velocityObject = clazz.newInstance();
				context.put(plugin.getName(), velocityObject);
			} catch (ClassNotFoundException e) {
				logger.info("cannot find plugin " + plugin.getName()
						+ " via class " + plugin.getClassName());
				e.printStackTrace();
			} catch (InstantiationException e) {
				logger.info("cannot instantiate plugin " + plugin.getName()
						+ " via class " + plugin.getClassName());
			} catch (IllegalAccessException e) {
				logger.info("cannot access plugin " + plugin.getName()
						+ " via class " + plugin.getClassName());
			}
		}
	}

	protected String getTemplatePath(Template template) {
		if ((templatePath == null || templatePath.equals(""))
				&& isTemplatePathToReset) {
			isTemplatePathToReset = false;
			Configuration c = (Configuration) template.getTemplateTarget()
					.getTarget().getAbstractConfigurationRoot();
			Hashtable<String, String> ht = new Hashtable<String, String>();
			TemplateTarget templateTarget = template.getTemplateTarget();
			Target target = templateTarget.getTarget();

			for (TemplateTarget templateTarget2 : target.getTemplateTargets()) {
				String absoluteRootDir = templateTarget2.getAbsoluteRootDir();
				if (absoluteRootDir != null) {
					ht.put(absoluteRootDir, absoluteRootDir);
				}
				String templateFullDir = templateTarget2.getTemplateFullDir();
				if (templateFullDir != null) {
					ht.put(templateFullDir, templateFullDir);
				}
			}
			for (String templateAssociated : target.getTemplatedirRefs()) {
				for (String absoluteRootDir : target
						.getAbsoluteRootDirs(templateAssociated))
					ht.put(absoluteRootDir, absoluteRootDir);
			}
			templatePath = getVelocityPath(ht, null);// getVelocityPath(ht,
														// c.getCatalogDir());
		}
		return templatePath;
	}

	private String getTemplateRelativeLibPath(Template template) {
		if (templateLibPath == null && isTemplateLibPathToReset) {
			isTemplateLibPathToReset = false;
			Configuration c = (Configuration) template.getTemplateTarget()
					.getTarget().getAbstractConfigurationRoot();
			Hashtable<String, String> ht = new Hashtable<String, String>();
			TemplateTarget templateTarget = template.getTemplateTarget();
			Target target = templateTarget.getTarget();
			// StringBuffer sb = new StringBuffer();
			for (TemplateTarget templateTarget2 : target.getTemplateTargets()) {
				String libdir = templateTarget2.getLibdir();
				if (libdir != null && !libdir.equals("")) {
					ht.put(libdir, libdir);
					// sb.append(templateTarget2.getLibdir());
					// sb.append(","); //TODO change for last element
				}
			}

			templateLibPath = getVelocityPath(ht, null);
		}
		return templateLibPath;
	}

	private String getVelocityPath(Hashtable<String, String> ht, String prefix) {
		StringBuffer sb = new StringBuffer();
		Enumeration<String> e = ht.elements();
		while (e.hasMoreElements()) {
			if (prefix != null)
				sb.append(prefix + "/");
			sb.append(e.nextElement());
			if (e.hasMoreElements())
				sb.append(",");
		}
		return sb.toString();
	}

	protected void produce(VelocityContext context, Template template,
			String outputFilename) throws Exception {
		org.apache.velocity.Template velocityTemplate = getVelocityTemplate(
				template, outputFilename);
		writeFile(context, velocityTemplate, outputFilename, template);
		writeFilePostProcessing(template, outputFilename);
		template.increaseNumberOfGeneratedArtifacts();
	}

	private void writeFilePostProcessing(Template template,
			String outputFilename) {
		String chmod = template.getChmod();
		if (chmod != null && !chmod.equals("")) {
			File file = new File(outputFilename);
			try {
				Runtime.getRuntime().exec(
						"chmod " + chmod + " " + file.getCanonicalPath());
			} catch (IOException e) {
				// do nothing example on windows chmod does not exist
			}
		}
	}

	private org.apache.velocity.Template getVelocityTemplate(Template template,
			String outputFilename) throws Exception {
		org.apache.velocity.Template velocityTemplate = null;
		// Properties p = new Properties();
		// extendedProperties.setProperty(RuntimeConstants.RESOURCE_LOADER,
		// "string");
		// extendedProperties.setProperty("classpath.resource.loader.class",StringResourceLoader.class.getName());
		//
		// p.setProperty("resource.loader", "string");
		// p.setProperty("resource.loader.class",
		// "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
		// Velocity.init(p);
		try {
			velocityTemplate = Velocity.getTemplate(template
					.getTemplateFileName());
		} catch (ResourceNotFoundException rnfe) {
			System.out.println("Error : cannot find template "
					+ template.getTemplateFileName());
		} catch (ParseErrorException pee) {
			System.out.println("Error : Syntax error in template "
					+ template.getTemplateFileName() + ":" + pee);
		}
		return velocityTemplate;
	}

	private void writeFile(VelocityContext context,
			org.apache.velocity.Template velocityTemplate,
			String outputFilename, Template template) throws Exception {
		FileOutputStream fos = new FileOutputStream(outputFilename);
		try (
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
		) {

			String licence = template.getLicence();
			if (template.isLicenceAtBeginning() && licence != null)
				writer.append(licence);
			if (velocityTemplate != null)
				velocityTemplate.merge(context, writer);
			if (!template.isLicenceAtBeginning() && licence != null)
				writer.append(licence);
			writer.flush();
			//writer.close();
		}
	}

	protected String getAbstractBeanName(GeneratorBean bean) {
		String beanName = StringUtils.lowerCase(bean.getClass().getName());
		beanName = StringUtils.substring(beanName,
				beanName.lastIndexOf(".") + 1);
		// TODO change
		if (beanName.equals("tableddlutils")
				|| beanName.equals("tableumlnotation"))
			return "table";
		if (beanName.equals("columnddlutils"))
			return "column";
		if (beanName.equals("viewddlutils"))
			return "view";
		if (beanName.equals("componentddlutils"))
			return "component";
		if (beanName.equals("functionddlutils"))
			return SCOPE_DATAMODEL_FUNCTION;
		if (beanName.equals("foreignkeyddlutils"))
			return SCOPE_FOREIGNKEY_APPLICATION;
		if (beanName.equals("wsdlmodelmetro"))
			return "wsdlmodel";
		return beanName;
	}

	protected Table getDecoratedTable(Table table) {
		return DataModelFactory.getTable(table);
	}

	protected void writeTemplateResult(GeneratorBean bean, Template template)
			throws MinuteProjectException {
		String outputFilename = template
				.getGeneratorOutputFileNameForConfigurationBean(bean, template);
		VelocityContext context = getVelocityContext(template);
		String beanName = getAbstractBeanName(bean);
		context.put(beanName, bean);
		context.put("template", template);
		putCommonContextObject(context, template);
		try {
			produce(context, template, outputFilename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("ERROR on template " + template.getName()
					+ " - on bean " + bean.getName());
			throwException(e, e.getMessage());
		}
	}

	protected void putCommonContextObject(VelocityContext context,
			Template template) {
		putStandardContextObject(context);
		putPluginContextObject(context, template);
	}

	protected void putStandardContextObject(VelocityContext context) {
		context.put("convertUtils", new ConvertUtils());
		context.put("commonUtils", new CommonUtils());
		context.put("viewUtils", new ViewUtils());
		context.put("formatUtils", new FormatUtils());
		context.put("bslaLibraryUtils", new BslaLibraryUtils());
		context.put("databaseUtils", new DatabaseUtils());
		context.put("modelUtils", new ModelUtils());
		context.put("templateUtils", new TemplateUtils());
		context.put("propertyUtils", new PropertyUtils());
		context.put("convertUtils", getConvertUtils());
		context.put("commonUtils", getCommonUtils());
		context.put("columnUtils", getColumnUtils());
		context.put("viewUtils", getViewUtils());
		context.put("formatUtils", getFormatUtils());
		context.put("bslaLibraryUtils", getBslaLibraryUtils());
		context.put("modelUtils", getModelUtils());
		context.put("URLUtils", getUrlUtils());
		context.put("TestUtils", getTestUtils());
		context.put("WebUtils", getWebUtils());
		context.put("sqlUtils", getSqlUtils());
		context.put("tableUtils", getTableUtils());
		context.put("testUtils", getTestUtils());
		context.put("referenceUtils", referenceUtils);
		context.put("enumUtils", enumUtils);
		context.put("i18nUtils", i18nUtils);
		context.put("updatedAreaUtils", updatedAreaUtils);
		context.put("javaUtils", javaUtils);
		context.put("routineUtils", routineUtils);
		context.put("statementUtils", statementUtils);
		context.put("triggerUtils", triggerUtils);
		context.put("queryUtils", queryUtils);
		context.put("queryParamLinkUtils", new QueryParamLinkUtils());
		context.put("semanticReferenceUtils", semanticReference);
		context.put("velocityUtils", velocityUtils);
		context.put("fileUtils", fileUtils);
		context.put("orderingUtils", orderingUtils);
		context.put("enrichmentUtils", enrichmentUtils);
		context.put("minuteprojectUtils", minuteprojectUtils);
		context.put("actionUtils", actionUtils);
		context.put("readmeUtils", new ReadmeUtils());
		context.put("schedulerUtils", new SchedulerUtils());
		context.put("environmentUtils", new EnvironmentUtils());
	}

	protected void exit(String message) {
		// logger.error(message);
		System.exit(-1);
	}

	protected void throwException(Exception e, String error)
			throws MinuteProjectException {
		logger.error(error);
		e.printStackTrace();
		MinuteProjectException mpe = new MinuteProjectException();
		mpe.setError(error);
		if (e != null)
			mpe.setMessage(e.getMessage());
		throw mpe;
	}

	protected void throwException(String error) throws MinuteProjectException {
		throwException(null, error);
	}

	public void resetTemplatePath() {
		isTemplateLibPathToReset = true;
		isTemplatePathToReset = true;
	}

	public BslaLibraryUtils getBslaLibraryUtils() {
		if (bslaLibraryUtils == null)
			bslaLibraryUtils = new BslaLibraryUtils();
		return bslaLibraryUtils;
	}

	public ColumnUtils getColumnUtils() {
		if (columnUtils == null)
			columnUtils = new ColumnUtils();
		return columnUtils;
	}

	public CommonUtils getCommonUtils() {
		if (commonUtils == null)
			commonUtils = new CommonUtils();
		return commonUtils;
	}

	public ConvertUtils getConvertUtils() {
		if (convertUtils == null)
			convertUtils = new ConvertUtils();
		return convertUtils;
	}

	public DatabaseUtils getDatabaseUtils() {
		if (databaseUtils == null)
			databaseUtils = new DatabaseUtils();
		return databaseUtils;
	}

	public FormatUtils getFormatUtils() {
		if (formatUtils == null)
			formatUtils = new FormatUtils();
		return formatUtils;
	}

	public ModelUtils getModelUtils() {
		if (modelUtils == null)
			modelUtils = new ModelUtils();
		return modelUtils;
	}

	public SqlUtils getSqlUtils() {
		if (sqlUtils == null)
			sqlUtils = new SqlUtils();
		return sqlUtils;
	}

	public TableUtils getTableUtils() {
		if (tableUtils == null)
			tableUtils = new TableUtils();
		return tableUtils;
	}

	public TestUtils getTestUtils() {
		if (testUtils == null)
			testUtils = new TestUtils();
		return testUtils;
	}

	public URLUtils getUrlUtils() {
		if (urlUtils == null)
			urlUtils = new URLUtils();
		return urlUtils;
	}

	public ViewUtils getViewUtils() {
		if (viewUtils == null)
			viewUtils = new ViewUtils();
		return viewUtils;
	}

	public WebUtils getWebUtils() {
		if (webUtils == null)
			webUtils = new WebUtils();
		return webUtils;
	}

}
