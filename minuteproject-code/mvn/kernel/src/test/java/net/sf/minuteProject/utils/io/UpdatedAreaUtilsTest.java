package net.sf.minuteProject.utils.io;

import java.util.HashMap;
import java.util.Map;

import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;
import static net.sf.minuteProject.utils.io.UpdatedAreaUtils.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class UpdatedAreaUtilsTest  {

	Template template;
	TemplateTarget templateTarget;
	Map<String, String> updatedArea= new HashMap<String, String>();
	
	@BeforeEach
	public void setUp() {
		templateTarget = new TemplateTarget();
		templateTarget.setOutputdir("test");
		templateTarget.setDir("test");
		templateTarget.setRootdir("test");
		template = new Template(templateTarget);
		template.setFileExtension("java");
		template.setUpdatable(true);
		template.setHasUpdatableNature(true);

	}
	
	private void populateUpdatedAreaWith (String key, String value){
		updatedArea = new HashMap<String, String>();
		updatedArea.put(key, value);
	}
	@Test
	public void testImportUpdatedAreas() {
		
		populateUpdatedAreaWith(IMPORT,"test");
		String importArea = getImportSnippet(template, updatedArea);
		String testValue = getAddedAreaSnippet(template,IMPORT, "test").getContent();
		Assertions.assertThat(testValue).isEqualTo(importArea);

		populateUpdatedAreaWith("dummy","");
		String defaultValue = getAddedAreaSnippet(template,IMPORT, null).getContent();		
		importArea = getImportSnippet(template, updatedArea);
		Assertions.assertThat(defaultValue).isEqualTo(importArea);

		populateUpdatedAreaWith(UpdatedAreaUtils.IMPORT,"");
		importArea = getImportSnippet(template, updatedArea);
		Assertions.assertThat(defaultValue).isEqualTo(importArea);

	}
	
	
}
