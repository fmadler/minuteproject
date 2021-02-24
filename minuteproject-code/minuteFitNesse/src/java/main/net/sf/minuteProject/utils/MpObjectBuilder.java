package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.TableDDLUtils;

public class MpObjectBuilder {

	public static Table getTable(String name, String alias) {
		Table t = new TableDDLUtils(getDDLUtilsTable(name));
		t.setAlias(alias);
		return t;
	}
	
	public static org.apache.ddlutils.model.Table getDDLUtilsTable(String name) {
		org.apache.ddlutils.model.Table t = new org.apache.ddlutils.model.Table();
		t.setName(name);
		return t;
	}
}
