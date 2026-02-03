package net.sf.minuteProject.utils.validation;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import net.sf.minuteProject.configuration.bean.enrichment.validation.EntityValidationTwoFieldDependency;
import net.sf.minuteProject.configuration.bean.enrichment.validation.FieldValidationAmongValue;
import net.sf.minuteProject.configuration.bean.enrichment.validation.ValidationPattern;

public class ValidationUtilsTest {

	@Test
	public void testAnnotations() {
		ValidationPattern vp = new ValidationPattern();
		vp.setType("EMAIL");
		List<String> javaValidationAnnotations = ValidationUtils.getJavaValidationAnnotations(vp);
		Assertions.assertThat(javaValidationAnnotations).hasSize(1);
		String regex = "@Email";
		Assertions.assertThat(javaValidationAnnotations.get(0)).isEqualTo(regex);
		
		List<String> javaImportValidationAnnotations = ValidationUtils.getJavaImportValidationAnnotations(vp);
		String importS = "javax.validation.constraints.Email";
		Assertions.assertThat(javaImportValidationAnnotations.get(0)).isEqualTo(importS);
	}
	
	@Test
	public void testEmail() {
		FieldValidationAmongValue fvav = new FieldValidationAmongValue();
		fvav.setValues("red,green,blue");
		List<String> javaValidationAnnotations = ValidationUtils.getJavaValidationAnnotations(fvav);
		Assertions.assertThat(javaValidationAnnotations).hasSize(1);
		//error runtime java.lang.NoSuchMethodError: org.hamcrest.Matcher.describeMismatch(Ljava/lang/Object;Lorg/hamcrest/Description;)
		//assertThat(javaValidationAnnotations.get(0), is(equalTo("@Pattern (regexp=\"red|green|blue\"")));
		String regex = "@Pattern (regexp=\"red|green|blue\", flags=Pattern.Flag.CASE_INSENSITIVE)";
		Assertions.assertThat(javaValidationAnnotations.get(0)).isEqualTo(regex);
	}
	
	@Test
	public void testEntityValidationTwoFieldDependency() {
		EntityValidationTwoFieldDependency evtfd = new EntityValidationTwoFieldDependency();
		evtfd.setFirstFieldName("ageMin");
		evtfd.setSecondFieldName("ageMax");
		evtfd.setOperand("GREATER_THAN");
		List<String> javaValidationAnnotations = ValidationUtils.getJavaValidationAnnotations(evtfd);
		//assertThat(javaValidationAnnotations, hasSize(1));
		Assertions.assertThat(javaValidationAnnotations).hasSize(1);
		Assertions.assertThat(javaValidationAnnotations).hasSize(1);
		//error runtime java.lang.NoSuchMethodError: org.hamcrest.Matcher.describeMismatch(Ljava/lang/Object;Lorg/hamcrest/Description;)
		//assertThat(javaValidationAnnotations.get(0), is(equalTo("@Pattern (regexp=\"red|green|blue\"")));
		String regex = "@FieldCompare (first=\"ageMin\", second=\"ageMax\", operator=CompareOperatorEnum.GREATER_THAN)";
		Assertions.assertThat(javaValidationAnnotations.get(0)).isEqualTo(regex);
	}
}
