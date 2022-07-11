package net.sf.minuteProject.utils.rule;

import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.enrichment.rule.Constraint;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.CommonUtils;

import java.util.*;

class AblUtils {

	AblUtils(){}
	
	String getPackageList (Model model, String targetTemplateName) {
		List<String> packageNames = new ArrayList<>(getPackageWithRulesList(model, targetTemplateName));
		//packageNames.toListString()
		StringBuffer sb = new StringBuffer();
		Template template = CommonUtils.getTemplate(model.getConfiguration(), targetTemplateName);
		for (int i=0; i<packageNames.size(); i++) {
			sb.append(packageNames.get(i));
			if (i<packageNames.size()-1)
				sb.append (",");
		}
		return sb.toString();
	}
	
	public static Collection<String> getPackageWithRulesList (Model model, String targetTemplateName) {
		Map<String, String> packageNames = new HashMap<String, String>();
		Template template = CommonUtils.getTemplate(model.getConfiguration(), targetTemplateName);
		for (Table table : model.getBusinessModel().getBusinessPackage().getEntities()) {
			List<Constraint> constraints = table.getConstraints();
			if (constraints!=null && constraints.size()>0) {
				String packageName = CommonUtils.getPackageName(table, template);
				packageNames.put(packageName, packageName);
			}
		}
		return packageNames.values();
	}
	
	String getConstraintName (Table table, Constraint constraint) {
		return constraint.getName(table);
	}
	
	String getActionName (Table table, Action action) {
		return action.getName(table);
	}
	
/*	String getDerivationName (Column column, Derivation derivation) {
		return derivation.getName(column);
	}
	*/
/*	boolean isToGenerateBasedRulePresence (Template template, GeneratorBean bean) {
		if (bean instanceof Table) {
			Table table = (Table) bean
			if (table.getActions()!=null && table.getActions().size()>0)
				return true
			if (table.getConstraints()!=null && table.getConstraints().size()>0)
				return true
			return hasDerivation(table)
		}
		return false
	}

	boolean hasDerivation(Table table) {
		for (Column column : table.getAttributes()) {
			if (column.getDerivations()!=null && column.getDerivations().size()>0)
				return true
		}
		return false
	}*/
	
}
