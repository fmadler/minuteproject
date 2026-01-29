package net.sf.minuteProject.utils.code;

import org.assertj.core.api.Assertions;

public class RestrictedCodeUtilsTest  {

	public static final String EXPECTED_RESULT_FINAL_CONVERSION = "Narrative_Pa_rt_s_and_A_nnexes_wro_ng_def_i_ned";                                                                	
	
	public static final String INPUT_VALUE1 = "Narrative#$,,,.... Pa.rt.s #$and A^^&^$##@*&^nnexes_wro,,ng,,def,.i,.!@!#$#@ned.,  ";
			
	public void testConvertToValidJava() {
		Assertions.assertThat(RestrictedCodeUtils.convertToValidJava(INPUT_VALUE1)).isEqualTo(
				EXPECTED_RESULT_FINAL_CONVERSION);
	}
	
	public void testConvertToValidJavaWithUpperCase() {
		Assertions.assertThat(RestrictedCodeUtils.convertToValidJavaWithUpperCase(INPUT_VALUE1)).isEqualTo(
				EXPECTED_RESULT_FINAL_CONVERSION.toUpperCase());
	}
						

}
