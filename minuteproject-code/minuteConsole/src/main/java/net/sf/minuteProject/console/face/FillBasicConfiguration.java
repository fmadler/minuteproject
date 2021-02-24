package net.sf.minuteProject.console.face;

import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;

public interface FillBasicConfiguration {

	public void fill (BasicIntegrationConfiguration bic) throws MinuteProjectException;
}
