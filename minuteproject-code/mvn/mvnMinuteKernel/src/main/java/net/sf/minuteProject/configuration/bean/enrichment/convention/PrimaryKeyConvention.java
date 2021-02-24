package net.sf.minuteProject.configuration.bean.enrichment.convention;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;

public abstract class PrimaryKeyConvention<T extends Table> extends FieldConvention {

	public static final String APPLY_PK_ON_ENTITY_WITH_TWO_COLUMN_AS_FK = "apply-primary-key-on-entity-with-two-columns-only-and-foreign-key-otherwise-specified";
//
	public static final String APPLY_DEFAULT_PK_OTHERWISE_FIRST_FIELD_IS_PK = "apply-default-primary-key-otherwise-first-one";
	public static final String APPLY_PK_ON_FIRST_FIELDS_AND_OTHER_MATCHING = "apply-primary-key-on-first-fields-and-other-matching";
	
	private String defaultPrimaryKeyNames;
	private int maxFields=1;
	private int numberOfFirstFields=1;
	
	public String getDefaultPrimaryKeyNames() {
		return defaultPrimaryKeyNames;
	}
	public void setDefaultPrimaryKeyNames(String defaultPrimaryKeyNames) {
		this.defaultPrimaryKeyNames = defaultPrimaryKeyNames;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultPrimaryKeyNames = defaultValue;
	}
	
	public void applyDefaultPkConvention(BusinessModel model) {
		if (model.getBusinessPackage()!=null) {
			for (T t : getEntity(model)) {
				if (t.getPrimaryKeyColumns().length==0)
					applyDefaultPkConvention (t);
			}
		}
	}
	public void applyPkOnM2MConvention(BusinessModel model) {
		if (model.getBusinessPackage()!=null) {
			for (T t : getEntity(model)) {
				applyPkOnM2MConvention (t);
			}
		}	
	}
	
	private void applyPkOnM2MConvention(T t) {
		if (t.getPrimaryKeyColumns().length==0 
			&& t.getColumns().length==2
			&& ColumnUtils.isForeignKey(t.getColumns()[0])
			&& ColumnUtils.isForeignKey(t.getColumns()[1])
			)		
			t.setPrimaryKeys(t.getColumns());
	}
	
	protected void applyDefaultPkConvention(T t) {
		t.setPrimaryKeys(getVirtualPrimaryKey(t));
	}
	
	protected Column[] getVirtualPrimaryKey(T t) {
		List<Column> pks = getPksByDefaultPrimaryKeyNames(t);
		if (pks==null || pks.isEmpty())
			pks = getPksByFirstColumn(t);
		return (Column[])pks.toArray(new Column[pks.size()]);
	}
	
	protected List<Column> getPksByFirstColumn(T t) {
		List<Column> pks = new ArrayList<Column>();
		if (t.getColumnCount()>0) {
			Column column = t.getColumn(0);
			if (column!=null)
				pks.add(column);	
		}
		return pks;
	}
	
	protected List<Column> getPksByDefaultPrimaryKeyNames(T t) {
		List<Column> pks = new ArrayList<Column>();
		String pk = getDefaultPrimaryKeyNames();
		if (pk!=null) {
			pk = StringUtils.remove(pk, " ");
			for (String columnName : getDefaultPrimaryKeyNames().split(",")) {
				Column column = ColumnUtils.getColumn(t, columnName);
				if (column!=null)
					pks.add(column);
			}
		}
		return pks;
	}
	
	@Override
	public void apply(BusinessModel model) {
		if (APPLY_PK_ON_ENTITY_WITH_TWO_COLUMN_AS_FK.equals(type)) {
			applyPkOnM2MConvention(model);
		}
		else if (APPLY_DEFAULT_PK_OTHERWISE_FIRST_FIELD_IS_PK.equals(type)) {
			applyDefaultPkConvention(model);
		}
		else if (APPLY_PK_ON_FIRST_FIELDS_AND_OTHER_MATCHING.equals(type)
			&& isValid()) {
			applyPkOnFirstFieldAndOtherMatching(model);
		}
	}

	protected void applyPkOnFirstFieldAndOtherMatching(BusinessModel model) {
		if (model.getBusinessPackage()!=null) {
			for (T t : getEntity(model)) {
				applyPkOnFirstFieldAndOtherMatching (t);
			}
		}
	}
	protected void applyPkOnFirstFieldAndOtherMatching(T t) {
		t.setPrimaryKeys(getPrimaryKeyFirstFieldAndOtherMatching(t));
	}
	
	protected Column[] getPrimaryKeyFirstFieldAndOtherMatching(T t) {
		List<Column> columns = new ArrayList<Column>();
		int cpt = 1;
		for (Column column : t.getColumns()) {
			if (cpt<=maxFields) {
				if (cpt<=numberOfFirstFields) {
					columns.add(column);
					cpt++;
				}
				else if (match (column)) {
					columns.add(column);
					cpt++;
				}
				
			}
		}
		return columns.toArray(new Column[columns.size()]);
	}
	
	
	protected abstract List<T> getEntity(BusinessModel model);
	public int getMaxFields() {
		return maxFields;
	}
	public void setMaxFields(int maxFields) {
		this.maxFields = maxFields;
	}
	public int getNumberOfFirstFields() {
		return numberOfFirstFields;
	}
	public void setNumberOfFirstFields(int numberOfFirstFields) {
		this.numberOfFirstFields = numberOfFirstFields;
	}



}
