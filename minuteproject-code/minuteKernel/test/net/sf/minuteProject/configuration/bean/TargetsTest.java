package net.sf.minuteProject.configuration.bean;

import static junit.framework.Assert.*;
import net.sf.minuteProject.configuration.bean.system.Property;

import org.junit.Before;
import org.junit.Test;

public class TargetsTest {

	Configuration configuration;
	
	@Before 
	public void init() {
		configuration = new Configuration();
		Model model = new Model();
		model.setPackageRoot("test.it");
		configuration.setModel(model);
	}
	@Test
	public void testProperties() {
		Targets targets = new Targets();
		targets.setAbstractConfigurationRoot(configuration);
		String name = "environment";
		String value = "remote";
		targets.addProperty(new Property(name,value));
		Target target = new Target();
		targets.addTarget(target);
		String p1 = target.getPropertyValue(name);
		assertNotNull(p1);
		assertTrue(value.equals(p1));
		
		TemplateTarget templateTarget = new TemplateTarget();
		target.addTemplateTarget(templateTarget);
		String p2 = templateTarget.getPropertyValue(name);
		assertNotNull(p2);
		assertTrue(value.equals(p2));		
		
		Template template = new Template(templateTarget);
		templateTarget.addTemplate(template);
		String p3 = template.getPropertyValue(name);
		assertNotNull(p3);
		assertTrue(value.equals(p3));		
		String p4 = template.getPropertyValue(name,"local");
		assertNotNull(p4);
		assertTrue(value.equals(p4));		
	}
}
