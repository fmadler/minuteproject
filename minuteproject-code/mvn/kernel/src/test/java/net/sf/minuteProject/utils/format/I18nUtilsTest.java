package net.sf.minuteProject.utils.format;

import net.sf.minuteProject.plugin.format.I18nUtils;
import org.assertj.core.api.Assertions;

public class I18nUtilsTest  {

	private static final String nameWithUnderscore = "TEST_TEST";
	private static final String nameWithoutUnderscore = "TESTTEST";
	private static final String test = "Test";
	private static final String testtest = "Testtest";
	
	private static final String ACTIVITY = "activity";
	private static final String ACTIVITIES = "activities";
	private static final String FINDER_FILTERS = "FINDER_FILTERS";
	private static final String FINDER_FILTER_lowercase = "finder_filter";

	private static final String Y = "y";
	private static final String IES = "ies";	
	
	
	
	public void testPlurialize () {
		String s = I18nUtils.plurialize(ACTIVITY);
		Assertions.assertThat(s+ " should be equal to "+ACTIVITIES).isEqualTo(s.equals(ACTIVITIES));
		s = I18nUtils.plurialize(Y);
		Assertions.assertThat(s+ " should be equal to "+IES).isEqualTo(s.equals(IES));
	}
	
	public void testGetI18nFromDBNameStripPrefix () {
		String s = I18nUtils.getI18nFromDBNameStripPrefix(nameWithUnderscore);
		Assertions.assertThat("result = "+s+" while input = "+nameWithUnderscore).isEqualTo(test.equals(s));
		s = I18nUtils.getI18nFromDBNameStripPrefix(nameWithoutUnderscore);
		Assertions.assertThat(nameWithoutUnderscore.equals(s));
		s = I18nUtils.getI18nFromDBNameStripPrefix(null);
	}
	
	
	public void testGetI18nFromDBNameStripSufix () {
		String s = I18nUtils.getI18nFromDBNameStripSufix(nameWithoutUnderscore, true);
		Assertions.assertThat("result = "+s+" while input = "+nameWithoutUnderscore).isEqualTo(testtest.equals(s));
	}
	
	
	public void testGetSingural () {
		String s = I18nUtils.singularize(FINDER_FILTERS);
		Assertions.assertThat("result = "+s+" while input = "+FINDER_FILTERS).isEqualTo(FINDER_FILTER_lowercase.equals(s));
	}
}
