package net.sf.minuteProject.model;

import org.codehaus.groovy.antlr.java.JavaTokenTypes;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.TableUtils;
import fit.ColumnFixture;

public class ModelTypeColumnFixture extends ColumnFixture {

	private static Model model;
	private static Database database;
	private Column c;
	private Table t;

	String table, column;

	private String error, dbType, javaType, javaFullType;

	public ModelTypeColumnFixture() {
		model = NavigateModelSequenceFixture.getModel();
		database = NavigateModelSequenceFixture.getDatabase();
	}

	private void setTable(String table) {
		if (database == null) {
			error = "missing database";
		} else {
			t = TableUtils.getEntity(database, table);
			if (t == null)
				error = "missing table " + table;
		}
	}

	private void setColumn(String column) {
		if (error == null) {
			c = ColumnUtils.getColumn(t, column);
			System.out.println(c);
			if (c == null) {
				error = "missing column " + column;
				return;
			}
			dbType = c.getType();
			javaType = ConvertUtils.getJavaTypeClassFromDBType(c);
			javaFullType = ConvertUtils.getJavaTypeFromDBFullType(c);
		}
	}

	public String performComputation() {
		error=null;
		setTable(table);
		if (error != null)
			return error;
		setColumn(column);
		if (error != null)
			return error;
		return "SUCCESSFUL";
	}

	public static Model getModel() {
		return model;
	}

	public static void setModel(Model model) {
		ModelTypeColumnFixture.model = model;
	}

	public static Database getDatabase() {
		return database;
	}

	public static void setDatabase(Database database) {
		ModelTypeColumnFixture.database = database;
	}

	public String checkDbType() {
		if (error != null)
			return error;
		if (c == null) {
			error = "missing column " + column;
			return error;
		}
		return c.getType();
	}

	// public void setDbType(String dbType) {
	// this.dbType = dbType;
	// }
	public String checkJavaType() {
		if (error != null)
			return error;
		return javaType;
	}

	// public void setJavaType(String javaType) {
	// this.javaType = javaType;
	// }
	public String checkJavaFullType() {
		if (error != null)
			return error;
		return javaFullType;
	}
	// public void setJavaFullType(String javaFullType) {
	// this.javaFullType = javaFullType;
	// }

}
