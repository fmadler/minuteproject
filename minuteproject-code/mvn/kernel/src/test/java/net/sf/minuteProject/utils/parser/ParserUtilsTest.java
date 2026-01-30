package net.sf.minuteProject.utils.parser;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParserUtilsTest {
	
	@Test
	public void testProperty() {
		String s = "${xxx}";
		String prop = ParserUtils.getProperty(s);
		Assertions.assertThat(prop).isEqualTo("xxx");
	}

}
