package net.sf.minuteProject.configuration.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.minuteProject.configuration.bean.system.Plugin;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.io.FileUtils;

public class TemplateTarget extends AbstractConfiguration implements Comparable<TemplateTarget>{
	
	public static final String MP_GENERATION_OUTPUT = "MP-GENERATION-OUTPUT";
	private String rootdir, absoluteRootDir, canonicalDir;
	private String templatedir;
	private List<String> templatedirRefs;
	private String dir;
	private String outputdir, outputdirRoot;
	private String outputdirNameBuilderPlugin, outputdirNameBuilderMethod;
	private String tool;
	private String libdir;
	private Target target;
	private List <Template> templates;
	protected String packageRoot;
	private boolean belongToPackage;
	private Boolean isGenerable;
	private Integer priority;
	

	private static Logger logger = Logger.getLogger(Template.class);

	public String getPluginName (GeneratorBean bean, String builderPlugin, String builderMethod) {
		if (builderPlugin!=null && builderMethod!=null) {
			// lookup builder in the plugin
			Plugin plugin = getFileBuilderPlugin(builderPlugin);
			if (plugin!=null) {
				String result = getPluginBuildFileName (plugin, builderMethod, bean);
				if (result != null)
					return result;
			}
		}	
		return null;
	}
	protected Plugin getFileBuilderPlugin (String fileNameBuilderPlugin) {
		List<Plugin> plugins = this.getTarget().getPlugins();
		for (Plugin plugin : plugins) {
			if (plugin.getName().equals(fileNameBuilderPlugin))
				return plugin;
		}		
		return null;
	}
	
	protected String getPluginBuildFileName (Plugin plugin, String fileNameBuilderMethod, GeneratorBean bean) {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		try {
			Class clazz = cl.loadClass(plugin.getClassName());
			Object pluginObject = clazz.newInstance();
			Class arg [] = new Class [2];
			arg [0] = Template.class;
			arg [1] = GeneratorBean.class;
			Object obj [] = new Object [2];
			obj [0] = this;
			obj [1] = bean;
			Method method = clazz.getMethod(fileNameBuilderMethod, arg);
			String result = (String) method.invoke(pluginObject, obj);
			return result;
		} catch (ClassNotFoundException e) {
			logger.info("cannot find plugin "+plugin.getName()+" via class "+plugin.getClassName());
			e.printStackTrace();
		} catch (InstantiationException e) {
			logger.info("cannot instantiate plugin "+plugin.getName()+" via class "+plugin.getClassName());
		} catch (IllegalAccessException e) {
			logger.info("cannot access plugin "+plugin.getName()+" via class "+plugin.getClassName());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			logger.info("cannot access plugin method "+plugin.getName()+" via method "+fileNameBuilderMethod+ " security exception "+e.getMessage());
//			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			logger.info("cannot access plugin method "+plugin.getName()+" via method "+fileNameBuilderMethod+ " NoSuchMethodException exception "+e.getMessage());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			logger.info("cannot access plugin method "+plugin.getName()+" via method "+fileNameBuilderMethod+ " IllegalArgumentException exception "+e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.info("cannot access plugin method "+plugin.getName()+" via method "+fileNameBuilderMethod+ " InvocationTargetException exception "+e.getMessage());
		}
		return null;
	}
	
	public String getPropertyValue(String name) {
		String s = super.getPropertyValue(name);
		if (s!=null) return s;
		if (target!=null) return target.getPropertyValue(name);
		return null;
		//TODO change order for overriding purpose + unit test
/*		String s = target.getPropertyValue(name);
		return (s!=null)?s:super.getPropertyValue(name);*/
	}

	public String getTemplateTargetPropertyValue(String name) {
		String s = getPropertyValue(name);
		if (s!=null) return s;
		if (target!=null) return target.getTargetPropertyValue(name);
		return null;
	}
	
	public Property getTemplateTargetPropertyByName(String name) {
		Property p = getPropertyByName(name);
		if (p!=null) return p;
		if (target!=null) return target.getTemplateTargetPropertyByName(name);
		return null;
	}
	
	public String getPackageRoot() {
		if (packageRoot==null && getTarget()!=null){
			Configuration configuration = (Configuration) getTarget().getAbstractConfigurationRoot();
			setPackageRoot(configuration.getModel().getPackageRoot());
		}
		return packageRoot;
	}

	public void setPackageRoot(String packageRoot) {
		this.packageRoot = packageRoot;
	}

	public void addTemplate (Template template) {
		if (templates==null)
			templates=new ArrayList<Template>();
		template.setTemplateTarget(this);
		template.setOutputdir(getOutputdir());
		if (template!=null && template.getPackageRoot()==null)
			template.setPackageRoot(getPackageRoot());
		templates.add(template);
	}
	
	public List<Template> getTemplates() {
		if (templates==null) templates = new ArrayList<Template>();
		return templates;
	}
	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}
	
	public String getDir() {
		if (getRootdir()==null && getTemplatedir()==null)
			return dir;
		return getRootdir();
	}
	
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	public void setOutputdirRoot(String outputdirRoot) {
		this.outputdirRoot = outputdirRoot;
	}
	
	public String getPluginOutputdir () {
		return getPluginName(null, outputdirNameBuilderPlugin, outputdirNameBuilderMethod);
	}
	
	public String getOutputdir() {
		String outputdirRoot = getOutputdirRoot();
//		outputdir = getPluginOutputdir();
		if (outputdir==null && outputdirRoot==null) {
			String name;
			if (getTarget()!=null &&
				getTarget().getAbstractConfigurationRoot()!=null && 
				getTarget().getAbstractConfigurationRoot().getName()!=null) 
				name = getTarget().getAbstractConfigurationRoot().getName();
			else
				name = "project";
			outputdir = getDir()+"/"+MP_GENERATION_OUTPUT+"/"+name;
		}
		return (outputdirRoot!=null)?outputdirRoot+"/"+outputdir:outputdir;
	}
	
	private String getOutputdirRoot() {
		if (outputdirRoot!=null)
			return outputdirRoot;
		if (getTarget()!=null)
			return getTarget().getOutputdirRoot();	
		return null;
	}

	public void setOutputdir(String outputdir) {
		this.outputdir = outputdir;
	}

	public String getLibdir() {
		return libdir;
	}

	public void setLibdir(String libdir) {
		this.libdir = libdir;
	}

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}
	
	public Template getTemplate(String name) {
		List list = getTemplates();
		if (list!=null)
			for (int i = 0; i<list.size();i++) {
				Template template = (Template)list.get(i);
				if (template.getName().equals(name))
					return template;
			}
		return null;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public boolean isBelongToPackage() {
		return belongToPackage;
	}

	public void setBelongToPackage(boolean belongToPackage) {
		this.belongToPackage = belongToPackage;
	}

	public String getRootdir() {
		if (rootdir == null)
			rootdir = getTarget().getTemplatedirRoot();
		return rootdir;
	}
	
	public String getAbsoluteRootDir() {
		if (absoluteRootDir==null)
			absoluteRootDir = getAbsoluteRootDir(getRootdir());
		return absoluteRootDir;
	}
	
	private String getAbsoluteRootDir(String rootDir) {
		return FileUtils.getAbsoluteDir(rootDir, getRootdir(), target.getDir());
//		if (rootDir==null)
//			rootDir = getRootdir();
//		String targetDir = target.getDir();
//		absoluteRootDir = FileUtils.getAbsolutePathFromPath(rootDir, targetDir);		
//		return absoluteRootDir;
	}

	public void setRootdir(String rootdir) {
		this.rootdir = rootdir;
	}

	public String getTemplateFullDir() {
		if (templatedir!=null)
			return getRootdir()+"/"+templatedir;
		return getRootdir();
	}

	public void setTemplatedir(String templatedir) {
		this.templatedir = templatedir;
	}

	public String getTemplatedir() {
		return templatedir;
	}

	public List<String> getTemplatedirRefs() {
		if (templatedirRefs==null)
			templatedirRefs = new ArrayList<String>();
		return templatedirRefs;
	}

	public void addTemplatedirRef(String templatedirRef) {
		if (templatedirRef!=null)
			getTemplatedirRefs().add(templatedirRef);
	}

	public String getCanonicalDir() {
		return canonicalDir;
	}

	public void setCanonicalDir(String canonicalDir) {
		this.canonicalDir = canonicalDir;
	}
	public boolean isGenerable() {
		if (isGenerable==null) isGenerable = true;
		return isGenerable;
	}	

	public void setIsGenerable(Boolean isGenerable) {
		this.isGenerable = isGenerable;
	}

	public String getOutputdirNameBuilderPlugin() {
		return outputdirNameBuilderPlugin;
	}

	public void setOutputdirNameBuilderPlugin(String outputdirNameBuilderPlugin) {
		this.outputdirNameBuilderPlugin = outputdirNameBuilderPlugin;
	}

	public String getOutputdirNameBuilderMethod() {
		return outputdirNameBuilderMethod;
	}

	public void setOutputdirNameBuilderMethod(String outputdirNameBuilderMethod) {
		this.outputdirNameBuilderMethod = outputdirNameBuilderMethod;
	}
	@Override
	public int compareTo(TemplateTarget o) {
		return - getPriority() + o.getPriority();
	}
	
	public Integer getPriority() {
		if (priority==null)
			priority=0;
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
