package net.sf.minuteProject.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormatUtilsTest {

	private String name1 = "this is a test";
	private String shortNameName1 = "ThisIsATest";

	private String name2 = "this-is another-test";
	private String shortNameName2 = "ThisIsAnotherTest";

	private String expressionToTrim1 = "____bbbbbbbasdasd____dasdsa___daddddddsd__";
	private String expressionToTrim2 = "123456789012345678901234567890----";
	private String expressionToTrim2Assert = "123456789012345678901234567890";
	
	private final String getterSetterClassName = "TSecUser";
	private final String getterSetterClassName2 = "TtSecUser";
	private final String getterSetterVariableName2 = "ttSecUser";
	private final String getterSetterClassName3 = "t";
	private final String getterSetterVariableName3 = "t";
	
	private final String camelSayHi = "SayHi";
	private final String camelSay_Hi = "Say_Hi";
	private final String camelSayHI = "SayHI";
	private final String camelSAY_HI = "SAY_HI";
	private final String camelSAY_H_I = "SAY_H_I";
	private final String camelsayHI = "sayHI";
	
	
	@Test
	public void testGetterSetterVariable () {
		String s = FormatUtils.getJavaVariableNameForGetterAndSetterFromJavaName(getterSetterClassName);
		Assertions.assertThat (getterSetterClassName).isEqualTo(s);
		s = FormatUtils.getJavaVariableNameForGetterAndSetterFromJavaName(getterSetterClassName2);
		Assertions.assertThat (getterSetterVariableName2).isEqualTo(s);
		s = FormatUtils.getJavaVariableNameForGetterAndSetterFromJavaName(getterSetterClassName3);
		Assertions.assertThat (getterSetterClassName3).isEqualTo(s);
	}
	@Test
	public void testFirstUpperCaseOnly () {
		String s = FormatUtils.firstUpperCaseOnly("s");
		Assertions.assertThat ("S".equals(s));
		s = FormatUtils.firstUpperCaseOnly("PRODUCTID");
		Assertions.assertThat ("Productid".equals(s));
	}	
	@Test
	public void testGetInUnderscore () {
		String s = FormatUtils.getInUnderscore("abc-def-ghi");
		Assertions.assertThat ("abc_def_ghi").isEqualTo(s);
	}	
	@Test
	public void testGetShortNameFromVerbose() {
		String resultName1 = FormatUtils.getShortNameFromVerbose(name1);
		Assertions.assertThat(resultName1).isEqualTo(shortNameName1);

		String resultName2 = FormatUtils.getShortNameFromVerbose(name2);
		Assertions.assertThat(resultName2).isEqualTo(shortNameName2);
	}
	@Test
	public void testGetEachWordFirstLetterUpper() {
		String resultName1 = FormatUtils.getShortNameFromVerbose(name1);
		Assertions.assertThat(resultName1).isEqualTo(shortNameName1);
	}
	@Test
	public void testEliminateMultipleSequenceOfChar() {
		String result = FormatUtils.eliminateMultipleSequenceOfChar(expressionToTrim1, '_', 'b', 'd');
		Assertions.assertThat(result.equals("_basdasd_dasdsa_dadsd_"));
		result = FormatUtils.trimExpression(result, "_");
		Assertions.assertThat(result.equals("basdasd_dasdsa_dadsd"));
	}

	@Test
	public void testStripToSizeRemovingBackend () {
		String result = FormatUtils.stripToSizeRemovingLeft(expressionToTrim2, 30);
		Assertions.assertThat (result.equals(expressionToTrim2Assert));
	}
	
	@Test
	public void testDecamelise() {
		String result = FormatUtils.insertUnderscoreforCamelCaseSeparation(camelSayHi);
		assertEqualTrue(result, camelSay_Hi);
		result = FormatUtils.decamelCaseForSqlAliasing(camelSayHi);
		assertEqualTrue(result, camelSAY_HI);
		result = FormatUtils.decamelCaseForSqlAliasing(camelSayHI);
		assertEqualTrue(result, camelSAY_H_I);
		result = FormatUtils.getJavaNameVariable(camelSAY_H_I);
		assertEqualTrue(result, camelsayHI);
	}
	
	private void assertEqualTrue(String result, String expectation) {
		Assertions.assertThat(result).isEqualTo(expectation);
	}
}
