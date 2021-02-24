package net.sf.minuteProject.plugin.jsf;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.plugin.presentation.PresentationUtils;
import net.sf.minuteProject.utils.FormatUtils;
import static net.sf.minuteProject.plugin.presentation.PresentationUtils.*;

public class PrimefacesUtils extends PresentationUtils{

	public static int getDisplayColumns(Column column) {
		if (isTextArea(column))
			return Math.min(column.getSizeAsInt(),MAX_COLUMNS_DISPLAY_SIZE_TEXTAREA);
		return PresentationUtils.getDisplayColumns(column);
	}

	public static int getDisplayRows(Column column) {
		if (isTextArea(column))
			return MAX_ROWS_DISPLAY_SIZE_TEXTAREA;
		return PresentationUtils.getDisplayRows(column);
	}
	
	public static String getAccessUrl(String name) {
		return FormatUtils.stripSpecialCharacterForJavaCompliance(name+"_ACCESS_URL");
	}
}
