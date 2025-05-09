package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class EntityNamingConvention extends BeanNamingConvention<Table> {

	public final String APPLY_STRIP_TABLE_NAME_SUFFIX="apply-strip-table-name-suffix";
	public final String APPLY_STRIP_TABLE_NAME_PREFIX="apply-strip-table-name-prefix";
	public final String APPLY_APPEND_TABLE_NAME_SUFFIX="apply-append-table-name-suffix";
	public final String APPLY_APPEND_TABLE_NAME_PREFIX="apply-append-table-name-prefix";
	
	@Override
	public void apply(BusinessModel model) {
		super.apply(model);
		if (APPLY_STRIP_TABLE_NAME_SUFFIX.equals(type) || 
			APPLY_STRIP_TABLE_NAME_PREFIX.equals(type) ||
			APPLY_APPEND_TABLE_NAME_SUFFIX.equals(type) ||
			APPLY_APPEND_TABLE_NAME_PREFIX.equals(type) 
			) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					if (isConventionApplicable (table))
						apply (table);
				}
			}
		}
	}
	

	@Override
	protected List<Table> getBeans(BusinessModel model) {
		List<Table> tables = new ArrayList<Table>();
		if (model.getBusinessPackage()!=null) {
			return model.getBusinessPackage().getEntities();
		}
		return tables;
	}


	public void setPatternToStrip (String s) {
		setDefaultValue(s);
	}

	private boolean isConventionApplicable(Table table) {
		return true;
//		return (table.getAlias().toLowerCase().equals(table.getName().toLowerCase()));
	}

	private void apply(Table table) {
		for (String s : ParserUtils.getListLowerCase(defaultValue)) {
			if (apply (table, s)) return;
		}		
	}

	private boolean apply(Table table, String s) {
		if (APPLY_STRIP_TABLE_NAME_SUFFIX.equals(type))
			return applyStripSuffix (table, s);
		if (APPLY_STRIP_TABLE_NAME_PREFIX.equals(type))
			return applyStripPrefix (table, s);
		if (APPLY_APPEND_TABLE_NAME_SUFFIX.equals(type))
			return applyAppendSuffix (table, s);
		if (APPLY_APPEND_TABLE_NAME_PREFIX.equals(type))
			return applyAppendPrefix (table, s);
		return true;
	}
	
	private boolean applyAppendSuffix(Table table, String s) {
		String alias = table.getAlias();
		String newName = alias+s;
		table.setAlias(newName);
		performReferenceUpdate(table, name, newName);
		return true;
	}

	private boolean applyAppendPrefix(Table table, String s) {
		String alias = table.getAlias();
		String newName = s+alias;
		table.setAlias(newName);
		performReferenceUpdate(table, name, newName);
		return true;
	}

	private boolean applyStripPrefix(Table table, String s) {
		String name = table.getAlias().toLowerCase();
		if (name.startsWith(s) && !name.equals(s)) {
			String newName = StringUtils.removeStart(name, s);
			table.setAlias(newName);
			performReferenceUpdate(table, name, newName);
			return true;
		}
		return false;
	}

	private boolean applyStripSuffix(Table table, String s) {
		String name = table.getAlias().toLowerCase();
		if (name.endsWith(s) && !name.equals(s)) {
			String newName = StringUtils.removeEnd(name, s);
			table.setAlias(newName);
			performReferenceUpdate(table, name, newName);
			return true;
		}
		return false;
	}

	//TODO check + apply for replace-name-with-alias
	private void performReferenceUpdate(Table table, String name, String newName) {
		for (ForeignKey fk : table.getForeignKeys()) {
			for (Reference ref : fk.getReferences()) {
				if (ref.getLocalTable()!=null && name.equals(ref.getLocalTable().getName())) 
					ref.getLocalTable().setAlias(newName);
				//break;
			}
		}
		
	}

}
