package net.sf.minuteProject.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
		assertTrue ("error '"+s+"' should be equal to "+getterSetterClassName,s.equals(getterSetterClassName));
		s = FormatUtils.getJavaVariableNameForGetterAndSetterFromJavaName(getterSetterClassName2);
		assertTrue ("error '"+s+"' should be equal to "+getterSetterClassName,s.equals(getterSetterVariableName2));
		s = FormatUtils.getJavaVariableNameForGetterAndSetterFromJavaName(getterSetterClassName3);
		assertTrue ("error '"+s+"' should be equal to "+getterSetterClassName,s.equals(getterSetterVariableName3));
	}
	@Test
	public void testFirstUpperCaseOnly () {
		String s = FormatUtils.firstUpperCaseOnly("s");
		assertTrue ("S".equals(s));
		s = FormatUtils.firstUpperCaseOnly("PRODUCTID");
		assertTrue ("Productid".equals(s));
	}	
	@Test
	public void testGetInUnderscore () {
		String s = FormatUtils.getInUnderscore("abc-def-ghi");
		assertTrue (s, "abc_def_ghi".equals(s));
	}	
	@Test
	public void testGetShortNameFromVerbose() {
		String resultName1 = FormatUtils.getShortNameFromVerbose(name1);
		assertTrue(resultName1, resultName1.equals(shortNameName1));

		String resultName2 = FormatUtils.getShortNameFromVerbose(name2);
		assertTrue(resultName2, resultName2.equals(shortNameName2));
	}
	@Test
	public void testGetEachWordFirstLetterUpper() {
		String resultName1 = FormatUtils.getShortNameFromVerbose(name1);
		assertTrue(resultName1, resultName1.equals(shortNameName1));
	}
	@Test
	public void testEliminateMultipleSequenceOfChar() {
		String result = FormatUtils.eliminateMultipleSequenceOfChar(expressionToTrim1, '_', 'b', 'd');
		assertTrue(result.equals("_basdasd_dasdsa_dadsd_"));
		result = FormatUtils.trimExpression(result, "_");
		assertTrue(result.equals("basdasd_dasdsa_dadsd"));
	}

	@Test
	public void testStripToSizeRemovingBackend () {
		String result = FormatUtils.stripToSizeRemovingLeft(expressionToTrim2, 30);
		assertTrue (result.equals(expressionToTrim2Assert));
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
		assertTrue("result ="+result+" , while expecting "+expectation , result.equals(expectation));	
	}
}
