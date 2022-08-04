package net.sf.minuteProject.utils.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ParserUtilsTest {
	
	@Test
	public void testProperty() {
		String s = "${xxx}";
		String prop = ParserUtils.getProperty(s);
		assertTrue(prop, prop.equals("xxx"));
	}

}
