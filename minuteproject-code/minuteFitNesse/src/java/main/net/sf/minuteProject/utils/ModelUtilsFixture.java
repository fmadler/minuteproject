package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.Condition;
import net.sf.minuteProject.configuration.bean.GenerationCondition;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.TableDDLUtils;
import fit.ColumnFixture;

public class ModelUtilsFixture extends ColumnFixture {

	public String defaultGenerationType, regex, expression, type, startsWith, endsWith;
	public String tableName, tableAlias;
	public Boolean isView;

	public boolean isToGenerate() {
		return ModelUtils.isToGenerate(getGenerationCondition(), getTable());
	}

	private GenerationCondition getGenerationCondition() {
		GenerationCondition generationCondition = new GenerationCondition();
		generationCondition.setDefaultType(defaultGenerationType);
		generationCondition.addCondition(getCondition());
		return generationCondition;
	}

	private Condition getCondition() {
		Condition condition = new Condition();
		condition.setStartsWith(startsWith);
		condition.setEndsWith(endsWith);
		condition.setRegex(regex);
		condition.setType(type);
		return condition;
	}

	private Table getTable() {
		Table t = new TableDDLUtils(getDDLUtilsTable());
		t.setAlias(tableAlias);
		return t;
	}
	
	private org.apache.ddlutils.model.Table getDDLUtilsTable() {
		org.apache.ddlutils.model.Table t = new org.apache.ddlutils.model.Table();
		t.setName(tableName);
		t.setType((isView)?"VIEW":"TABLE");
		return t;
	}
	
}
