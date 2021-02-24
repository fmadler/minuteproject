package net.sf.minuteproject.velocity;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.junit.Test;

public class VelocityTest {

	/**
	 * Entry Point
	 * 
	 * @param args
	 */
	@Test
	public void velocityTest() {
		Properties p = new Properties();
		p.setProperty("resource.loader", "string");
		p.setProperty("resource.loader.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
		

		Velocity.init(p);

		Template t = getTemplate("template/foo.vm");

		VelocityContext ctx = new VelocityContext();
		ctx.put("place", "world");

		Writer w = new StringWriter();
		t.merge(ctx, w);
		System.out.println(w.toString());
	}
	@Test
	public void velocityTestWithExtProps() {
		ExtendedProperties p = new ExtendedProperties();
//		p.setProperty(RuntimeConstants.RESOURCE_LOADER, "string");
//		p.setProperty("classpath.resource.loader.class",StringResourceLoader.class.getName());
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,"template");

		
		Velocity.setExtendedProperties(p);
		
		Template t = getTemplate("template/foo.vm");
		
		VelocityContext ctx = new VelocityContext();
		ctx.put("place", "world");
		
		Writer w = new StringWriter();
		t.merge(ctx, w);
		System.out.println(w.toString());
	}
	@Test
	public void velocityTestWithLib() {
		ExtendedProperties p = new ExtendedProperties();
//		p.setProperty(RuntimeConstants.RESOURCE_LOADER, "string");
//		p.setProperty("classpath.resource.loader.class",StringResourceLoader.class.getName());
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,"template");
		p.setProperty(Velocity.VM_LIBRARY,"template/lib/BAR_LIB.vm");
		
		Velocity.setExtendedProperties(p);
		
		Template t = getTemplate("template/bar.vm");
		
		VelocityContext ctx = new VelocityContext();
		ctx.put("place", "world");
		
		Writer w = new StringWriter();
		t.merge(ctx, w);
		System.out.println(w.toString());
	}

	/**
	 * Get a template from the cache; if template has not been loaded, load it
	 * 
	 * @param templatePath
	 * @return
	 */
	private static Template getTemplate(final String templatePath) {
		if (!Velocity.resourceExists(templatePath)) {
			StringResourceRepository repo = StringResourceLoader.getRepository();
			repo.putStringResource(templatePath, getTemplateFromResource(templatePath));
		}
		return Velocity.getTemplate(templatePath);
	}

	/**
	 * Read a template into memory
	 *
	 * @param templatePath
	 * @return
	 */
	private static String getTemplateFromResource(final String templatePath) {
		try {
			InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(templatePath);
			return IOUtils.toString(stream, "UTF-8");
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}