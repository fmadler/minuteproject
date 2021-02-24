package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.List;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ReferenceUtils;
import net.sf.minuteProject.utils.TableUtils;
import net.sf.minuteProject.utils.enrichment.EnrichmentUtils;
import net.sf.minuteProject.utils.java.JavaUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

import org.apache.commons.lang.StringUtils;

public class ColumnNamingConvention extends ModelConvention {

	public final static String APPLY_STRIP_COLUMN_NAME_SUFFIX="apply-strip-column-name-suffix";
	public final static String APPLY_STRIP_COLUMN_NAME_PREFIX="apply-strip-column-name-prefix";
	public final static String APPLY_FIX_PRIMARY_KEY_COLUMN_NAME_WHEN_NO_AMBIGUITY="apply-fix-primary-key-column-name-when-no-ambiguity";
	public final static String APPLY_FIX_PRIMARY_KEY_COLUMN_NAME_WHEN_NO_AMBIGUITY_AND_NOT_NATURAL="apply-fix-primary-key-column-name-when-no-ambiguity-and-not-natural";
	public final static String APPLY_STRIP_FIELD_NAME_PREFIX_WHEN_MATCHING_ENTITY_NAME  ="apply-strip-field-name-prefix-when-matching-entity-name";
	public final static String APPLY_STRIP_FIELD_NAME_PREFIX_WHEN_MATCHING_ENTITY_ALIAS ="apply-strip-field-name-prefix-when-matching-entity-alias";

	@Override
	public void apply(BusinessModel model) {
		if (APPLY_STRIP_FIELD_NAME_PREFIX_WHEN_MATCHING_ENTITY_NAME.equals(type) || APPLY_STRIP_FIELD_NAME_PREFIX_WHEN_MATCHING_ENTITY_ALIAS.equals(type)) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					applyMatch (table);
				}
			}
		} else if (APPLY_STRIP_COLUMN_NAME_SUFFIX.equals(type) || APPLY_STRIP_COLUMN_NAME_PREFIX.equals(type)) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					apply (table);
				}
			}
		} else if (APPLY_FIX_PRIMARY_KEY_COLUMN_NAME_WHEN_NO_AMBIGUITY.equals(type) || APPLY_FIX_PRIMARY_KEY_COLUMN_NAME_WHEN_NO_AMBIGUITY_AND_NOT_NATURAL.equals(type))  {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					applyFixPk (table);
				}
			}			
		} 
		// move to generic
		else if (BeanNamingConvention.APPLY_FIELD_ALIAS_BASED_ON_CAMEL_CASE.equals(type))  {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					applyCamelCaseAlias (table);
				}
			}
		}
		
	}

	private void applyCamelCaseAlias(Table table) {
		applyCamelCaseAlias(table.getColumns());
		applyCamelCaseAlias(table.getPrimaryKeyColumns());
		applyCamelCaseAlias(table.getParents());
//		applyChild(table.getChildren());
	}
	
	private void applyCamelCaseAlias(Column [] columns) {
		for (Column column : columns)
			applyCamelCaseAlias (column);	
	}

	private void applyCamelCaseAlias(Reference[] references) {
		for (Reference reference : references) {
			applyCamelCaseAlias (reference);
		}
	}

	private void applyCamelCaseAlias(Reference reference) {
		Column column = reference.getLocalColumn();
		applyCamelCaseAlias (column);
		column = reference.getForeignColumn();
		applyCamelCaseAlias (column);
	}

	protected void applyCamelCaseAlias(Column column) {
		if (FormatUtils.isCamelCaseAlias(column)) {
			column.setAlias(FormatUtils.decamelCaseForSqlAliasing(column.getName()));
		}
	}

	private void applyMatch (Table table) {
		for (Column column : table.getColumns())
			apply (table, column);	
	}

	private void apply(Table table, Column column) {
		if (APPLY_STRIP_FIELD_NAME_PREFIX_WHEN_MATCHING_ENTITY_NAME.equals(type))
			applyMatchEntityName(table, column);
		else
			applyMatchEntityAlias(table, column);
	}

	private void applyMatchEntityName(Table table, Column column) {
		column.setAlias(StringUtils.removeStart(column.getAlias().toLowerCase(), table.getName().toLowerCase()).toUpperCase());
	}

	private void applyMatchEntityAlias(Table table, Column column) {
		column.setAlias(StringUtils.removeStart(column.getAlias().toLowerCase(), table.getAlias().toLowerCase()).toUpperCase());
	}

	private void applyFixPk(Table table) {
		if (defaultValue==null) return;
		if (table.getPrimaryKeyColumns().length>1) return;
		if (APPLY_FIX_PRIMARY_KEY_COLUMN_NAME_WHEN_NO_AMBIGUITY_AND_NOT_NATURAL.equals(type) 
				&& ColumnUtils.isNaturalPk(TableUtils.getPrimaryFirstColumn(table))) 
			return;
		for (Column column : table.getColumns()) {
			if (column.getAlias().toLowerCase().equals(defaultValue.toLowerCase()))
				return;
		}		
		String firstColumn = null;
		for (Column column : table.getPrimaryKeyColumns()) {
			applyFixPk (column);
			firstColumn=column.getName();
			continue; //only one
		}	
		// also of the column in attribute
		Column column = ColumnUtils.getColumn(table, firstColumn) ;
		if (column!=null)
			column.setAlias(defaultValue);	
		
		applyFixPkParent (table.getParents());
	}

	public void setPatternToStrip (String s) {
		setDefaultValue(s);
	}
	
	private void apply(Table table) {
		apply(table.getColumns());
// use reference and not copy
//		apply(table.getPrimaryKeyColumns());
//		apply(table.getAttributes());
		//apply(table.getNoPrimaryKeyNoForeignKeyColumns());
// check if reference is used for parent
		applyParent(table.getParents());
		applyChild(table.getChildren());
	}

	private void applyParent(Reference[] references) {
		for (Reference reference : references) {
			applyParent (reference);
		}
	}

	private void applyParent(Reference reference) {
		Column column = reference.getLocalColumn();
		apply (column);
		column = reference.getForeignColumn();
		apply (column);
	}
	
	private void applyFixPkParent(Reference[] references) {
		for (Reference reference : references) {
			applyFixPkParent (reference);
		}
	}
	
	private void applyFixPkParent(Reference reference) {
		Column column = reference.getForeignColumn();
//		if (isConventionApplicable(column))
		applyFixPk (column);
	}
	private void applyChild(Reference[] references) {
		for (Reference reference : references) {
			applyChild (reference);
		}
	}
	
	private void applyChild(Reference reference) {
		Column column = reference.getForeignColumn();
//		if (isConventionApplicable(column))
			apply (column);
	}

	private void apply(Column[] columns) {
		for (Column column : columns) {
			if (isConventionApplicable(column)) {
				apply (column);
//				if (column.isPrimaryKey()) {
//					for (Column c : column.getTable().getPrimaryKeyColumns()) {
//						if (c.getName().toLowerCase().equals(column.getName().toLowerCase())) {
//							apply (c);
//						}
//					}
//				}
			}
		}
	}

	private boolean isConventionApplicable(Column column) {
		
		String proposedName = getProposedName (column).toLowerCase();
		if (JavaUtils.isReservedWord(proposedName)) {
			return false;
		}
		int cpt=0;
		for (Column col:column.getTable().getColumns()) {
			if (col.getAlias().toLowerCase().equals(proposedName))
				cpt++;
		}
//		for (Reference ref:column.getTable().getParents()) {
//			if (ref.getLocalColumn().getAlias().toLowerCase().equals(proposedName))
//				cpt++;
//		}
		for (Reference ref:column.getTable().getChildren()) {
			if (ref.getLocalColumn().getAlias().toLowerCase().equals(proposedName))
				cpt++;
		}
		for (Reference ref:EnrichmentUtils.getLinkedTargetReferenceByMany2Many(column.getTable())) {
			if (ref.getLocalColumn().getAlias().toLowerCase().equals(proposedName))
				cpt++;
		}
		// check duplicate a short version (a full version would be to propose all the alias and eliminate duplicate)
		return (cpt>0)? false:true;
//		return (column.getAlias().equals(column.getName()));
	}

	private String getProposedName(Column column) {
		for (String s : ParserUtils.getList(defaultValue)) {
			String proposedName = getProposedName(column, s);
			if (!proposedName.equals(column.getAlias())) return proposedName;
		}
		return column.getAlias();
	}
	
	private String getProposedName(Column column, String s) {
		s = s.toLowerCase();
		String name = column.getName().toLowerCase();
		if (APPLY_STRIP_COLUMN_NAME_SUFFIX.equals(type)) {
			if (name.endsWith(s) && !name.equals(s)) {
				return StringUtils.removeEnd(StringUtils.removeEnd(name, s), "_");
			}
		}
		if (APPLY_STRIP_COLUMN_NAME_PREFIX.equals(type)) {
			if (name.startsWith(s) && !name.equals(s)) {
				return StringUtils.removeEnd(StringUtils.removeStart(name, s), "_");
			}
		}
		//remove remaining underscore
		
		return column.getAlias();
	}

	private void applyFixPk(Column column) {
		applyFixPk(column, defaultValue);
	}
	
	private void applyFixPk(Column column, String value) {
		setNewColumnValue (column, column.getName(), value);
	}
	
	private void apply(Column column) {
		String proposedName= getProposedName(column);
		if (JavaUtils.isReservedWord(proposedName)) {
			return;
		}
		setNewColumnValue(column, name, proposedName);
/*		for (String s : ParserUtils.getList(defaultValue)) {
			if (apply (column, s)) return;
		}
		*/
	}
/*
	private boolean apply(Column column, String s) {
		if (APPLY_STRIP_COLUMN_NAME_SUFFIX.equals(type))
			return applyStripSuffix (column, s);
		if (APPLY_STRIP_COLUMN_NAME_PREFIX.equals(type))
			return applyStripPrefix (column, s);
		return true;
	}

	private boolean applyStripPrefix(Column column, String s) {
		s = s.toLowerCase();
		String name = column.getName().toLowerCase();
		if (name.startsWith(s) && !name.equals(s)) {
			String newName = StringUtils.removeStart(name, s);
			setNewColumnValue(column, name, newName);
			return true;
		}
		return false;
	}

	private boolean applyStripSuffix(Column column, String s) {
		s = s.toLowerCase();
		String name = column.getName().toLowerCase();
		if (name.endsWith(s) && !name.equals(s)) {
			String newName = StringUtils.removeEnd(name, s);
			setNewColumnValue(column, name, newName);
			return true;
		}
		return false;
	}
	*/

	private void setNewColumnValue (Column column, String name, String newName) {
		column.setAlias(newName);
//		setReferenceColumnAlias(column, name, newName);		
	}
	
	private void setReferenceColumnAlias(Column column, String name, String newName) {
		ReferenceUtils.setReferenceColumnAlias(column, name, newName);
	}

}
