package net.sf.minuteProject.utils.catalog;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.Targets;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.loader.catalog.technologycatalog.node.Technologies;
import net.sf.minuteProject.loader.catalog.technologycatalog.node.Technology;
import net.sf.minuteProject.loader.catalog.technologycatalog.node.TechnologyCatalog;


public class TechnologyCatalogUtilsTest {

	Configuration configuration;
	TechnologyCatalog technologyCatalog;
	@Before
	public void init() {
		configuration = new Configuration();
		Targets t = new Targets();
		t.setCatalogEntry("JPA2");
		t.setOutputdirRoot("../../JPA2");
		t.setTemplatedirRoot("../../template");
		configuration.setTargets(t);

	}
	
	@Test
	public void testLoad () throws MinuteProjectException {
		Targets targets = TechnologyCatalogUtils.getTargets(
				configuration.getTargets().getCatalogEntry(), 
				"catalog", 
				configuration.getTargets().getOutputdirRoot(), 
				configuration.getTargets().getTemplatedirRoot());
		assertNotNull(targets);
		assertTrue(targets.getTargets().size()>0);
	}
	@Test
	public void testTechnologyCatalog () throws MinuteProjectException {
		Technology a = new Technology();
		a.setName("A");
		a.setDependsOnTargets("B,C");
		Technology b = new Technology();
		b.setName("B");
		Technology c = new Technology();
		c.setName("C");		
		Technologies technologies = new Technologies();
		technologies.addTechnology(a);
		technologies.addTechnology(b);
		technologies.addTechnology(c);
		technologyCatalog = new TechnologyCatalog();
		technologyCatalog.setTechnologies(technologies);
	}
}
