package net.sf.minuteProject.utils.lang;

import org.assertj.core.api.Assertions;

public class XmlCharEntityReferencesUtilsTest  {

	public static final String EXPECTED_RESULT_FINAL_CONVERSION = "Narrative Parts & Annexes";

	public static final String INPUT_VALUE1 = "Narrative Parts &amp; Annexes";

	public void testConvertXmlCharEntityReferencesUtils() {
		Assertions.assertThat(XmlCharEntityReferencesUtils.convertToValidPlainTextFromXml(INPUT_VALUE1)).isEqualTo(EXPECTED_RESULT_FINAL_CONVERSION);
	}

}
