package net.sf.minuteProject.utils.format;

import net.sf.minuteProject.plugin.format.I18nUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
	

	@Test
	public void testPlurialize () {
		String s = I18nUtils.plurialize(ACTIVITY);
		Assertions.assertThat(s).isEqualTo(ACTIVITIES);
		s = I18nUtils.plurialize(Y);
		Assertions.assertThat(s).isEqualTo(IES);
	}
	@Test
	public void testGetI18nFromDBNameStripPrefix () {
		String s = I18nUtils.getI18nFromDBNameStripPrefix(nameWithUnderscore);
		Assertions.assertThat(s).isEqualTo(test);
		s = I18nUtils.getI18nFromDBNameStripPrefix(nameWithoutUnderscore);
		Assertions.assertThat(nameWithoutUnderscore).isEqualTo(s);
		s = I18nUtils.getI18nFromDBNameStripPrefix(null);
	}

	@Test
	public void testGetI18nFromDBNameStripSufix () {
		String s = I18nUtils.getI18nFromDBNameStripSufix(nameWithoutUnderscore, true);
		Assertions.assertThat(testtest).isEqualTo(s);
	}

	@Test
	public void testGetSingural () {
		String s = I18nUtils.singularize(FINDER_FILTERS);
		Assertions.assertThat(s).isEqualTo(FINDER_FILTER_lowercase);
	}
}
