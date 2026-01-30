package net.sf.minuteProject.file;

import net.sf.minuteProject.configuration.bean.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class DirectoryTest {

	private static final String USER_OUTPUTDIR_Targets = "/User/outputdir/targets";
	private static final String USER_OUTPUTDIR_Target = "/target";
	private static final String USER_OUTPUTDIR_TemplateTarget = "/templateTarget";
	Targets targets;
	Target target;
	TemplateTarget templateTarget;
	Template template;
	Model model;
	
	@BeforeEach
	public void init(){
		targets = mock(Targets.class);
		target = mock(Target.class);
		templateTarget = mock(TemplateTarget.class);
		template = mock (Template.class);
		model = mock (Model.class);
		

		targets.addTarget(target);
		target.addTemplateTarget(templateTarget);
		templateTarget.addTemplate(template);
	}
	
	@Test
	public void testOutputdirFromTargets() {
		targets.setOutputdirRoot(USER_OUTPUTDIR_Targets);

	}
	
	@Test
	public void testOutputdirFromTargetsAndTarget() {
		targets.setOutputdirRoot(USER_OUTPUTDIR_Targets);
		target.setOutputdirRoot(USER_OUTPUTDIR_Targets);
	}
	
	@Test
	public void testOutputdirFromTargetsAndTargetAndTemplateTarget() {
		targets.setOutputdirRoot(USER_OUTPUTDIR_Targets);
		target.setOutputdirRoot(USER_OUTPUTDIR_Targets);
		templateTarget.setOutputdirRoot(USER_OUTPUTDIR_TemplateTarget);
		templateTarget.setOutputdir(USER_OUTPUTDIR_TemplateTarget);
	}

}
