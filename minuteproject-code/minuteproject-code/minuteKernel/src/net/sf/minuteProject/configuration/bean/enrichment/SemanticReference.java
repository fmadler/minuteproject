package net.sf.minuteProject.configuration.bean.enrichment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enrichment.path.SqlPath;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.utils.FormatUtils;

public class SemanticReference extends AbstractConfiguration {

	private List<SqlPath> sqlPaths;
	private SemanticReference summary;

	public List<SqlPath> getSqlPaths() {
		if (sqlPaths==null)
			sqlPaths = new ArrayList<SqlPath>();
		return sqlPaths;
	}

	public void setSqlPaths(List<SqlPath> sqlPaths) {
		this.sqlPaths = sqlPaths;
	}
	
	public void addSqlPath(SqlPath sqlPath) {
		if (!getSqlPaths().contains(sqlPath))
			getSqlPaths().add(sqlPath);
	}
	
	public List<String> getSemanticReferenceSqlPath () {
		List<String> list = new ArrayList<String>();
		for (SqlPath sqlPath : getSqlPaths()) {
			list.add(sqlPath.getPath());
		}
		return list;
	}

	public List<String> getSemanticReferenceBeanPath () {
		List<String> list = new ArrayList<String>();
		for (String sqlPath : getSemanticReferenceSqlPath()) {
			list.add(sqlPath);//getBeanPath(sqlPath));//getBeanPath(sqlPath));
		}
		return list;
	}

//	private String getBeanPath(String sqlPath) {
//		StringBuffer sb = new StringBuffer();
//		String[] split = StringUtils.split(sqlPath, ".");
//		for (int i = 0; i < split.length; i++) {
//			String s = split[i];
//			sb.append(FormatUtils.getJavaNameVariableFirstLetter(s));
//			if (i!=split.length)
//				sb.append(".");
//		}
//		return sb.toString();
//	}

	public SemanticReference getSummary() {
		if (summary==null)
			summary = new SemanticReference();
		return summary;
	}

	public void setSummary(SemanticReference summary) {
		this.summary = summary;
	}
	
}
