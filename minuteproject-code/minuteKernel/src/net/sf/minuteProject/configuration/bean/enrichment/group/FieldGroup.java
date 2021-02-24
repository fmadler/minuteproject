package net.sf.minuteProject.configuration.bean.enrichment.group;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.utils.parser.ParserUtils;

@Getter
public class FieldGroup extends AbstractConfiguration {

	private List<String> fieldList;
	private String fields;
	private boolean isVisible;

//	public void addField (String field) {
//		getFields().add(field);
//	}
	
	public List<String> getFieldList() {
		if (fieldList==null) {
			fieldList = new ArrayList<String>();
			fieldList.addAll(ParserUtils.getList(fields));
		}	
		return fieldList;
	}
	
	public void setFields(String fields) {
		this.fields = fields;
//		setFieldsList(ParserUtils.getList(fields));
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
}
