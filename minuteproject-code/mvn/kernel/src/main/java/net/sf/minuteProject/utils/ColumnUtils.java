package net.sf.minuteProject.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class ColumnUtils {
	
	private static final String _TRANSIENT = "_TRANSIENT";
	public static String CHECK_CONSTRAINT_PROPERTY_TAG = "checkconstraint";
	
	public static boolean hasDefaultValue (Column column) {
		return hasDefaultValue(column, false);
	}
	
	public static boolean hasDefaultValue (Column column, boolean useTemporal) {
		return (column.getDefaultValue()!=null && 
				!ConvertUtils.getJavaTypeMask(column, "1", useTemporal).equals("null")) // check that there is a possible convertion
				?true:false;
	}
	
	public static String getDefaultValue (Column column) {
		return getDefaultValue(column, false);
	}
	public static String getFormatedDefaultValue (Column column) {
		return getFormatedDefaultValue(column, false);
	}
	public static String getFormatedDefaultValue (Column column, boolean useTemporal) {
//		return (ColumnUtils.isNumeric(column))?column.getDefaultValue():"\""+column.getDefaultValue()+"\"";
		return getFormatedMask(column, column.getDefaultValue(), useTemporal);
	}
	public static String getDefaultValue (Column column, boolean useTemporal) {
//		return (ColumnUtils.isNumeric(column))?column.getDefaultValue():"\""+column.getDefaultValue()+"\"";
		return getMask(column, column.getDefaultValue(), useTemporal);
	}

	public static String getMask (Column column, String value) {
		return getMask(column, value, false);
	}
	public static String getMask (Column column, String value, boolean useTemporal) {
		return ConvertUtils.getJavaTypeMask(column, value, useTemporal);
	}
	public static String getFormatedMask (Column column, String value, boolean useTemporal) {
		return ConvertUtils.getJavaTypeMaskFormated(column, value, useTemporal);
	}
	public static String getMaskWithExpression (Column column, String expression, boolean useTemporal) {
		return ConvertUtils.getJavaTypeMaskExpression(column, expression, useTemporal);
	}
	
	public static List<String> getColumnNames(Table table) {
		List<String> columnNames = new ArrayList<String>();
		for (Column column : table.getColumns()) {
			columnNames.add(column.getName());
		}
		return columnNames;
	}
	public static Column getColumn(Table table, String columnName) {
		if (table!=null && columnName!=null) {
			columnName = columnName.toUpperCase();
			int maxColumn = table.getColumns().length;
			for (int i = 0; i < maxColumn; i++) {
				Column column = table.getColumns()[i];
				if (column.getName().toUpperCase().equals(columnName.toUpperCase()))
					return column;
			}
		}
		return null;		
	}
	
	public static String getPrimaryKeyClassName (Table table, String columnName) {
		Column column = getPrimaryKeyColumn(table, columnName);
		if (column==null) return "GET PRIMARY KEY COLUMN should not be null";
		return FormatUtils.getJavaName(column.getAlias());
	}
	
	public static Column getPrimaryKeyColumn(Table table, String columnName) {
		if (table!=null && columnName!=null) {
			columnName = columnName.toUpperCase();
			int maxColumn = table.getPrimaryKeyColumns().length;
			for (int i = 0; i < maxColumn; i++) {
				Column column = table.getPrimaryKeyColumns()[i];
				if (column.getName().toUpperCase().equals(columnName))
					return column;
			}
		}
		return null;		
	}
	
	public static boolean isNaturalPk(Column column) {
		if (column!=null && column.isPrimaryKey() && 
		   !(
				column.getType().equals("INT") || 
				column.getType().equals("BIGINT") || 
				column.getType().equals("INTEGER") ||
				column.getType().equals("NUMBER") ||
				column.getType().equals("DECIMAL") ||
				column.getType().equals("SHORT") ||
				column.getType().equals("SMALLINT") ||
				column.getType().equals("REAL") ||
				column.getType().equals("VARBINARY") ||
				column.getType().equals("DOUBLE")
				)
			)
			return true;
		return false;
	}
	
	public static boolean isTime(Column column) {
		if (column!=null && 
			(
				column.getType().equals("DATE") || 
				column.getType().equals("TIMESTAMP") || 
				column.getType().equals("TIME")
				)
			)
			return true;
		return false;
	}
	

	public static boolean isNumeric(Column column) {
		if (column!=null && 
			(
				column.getType().equals("INT") || 
				column.getType().equals("BIGINT") || 
				column.getType().equals("INTEGER") ||
				column.getType().equals("NUMBER") ||
				column.getType().equals("NUMERIC") ||
				column.getType().equals("DECIMAL") ||
				column.getType().equals("SHORT") ||
				column.getType().equals("SMALLINT") ||
				column.getType().equals("REAL") ||
	//						column.getType().equals("VARBINARY") ||
				column.getType().equals("DOUBLE")
				)
			)
			return true;
		return false;
	}


	public static boolean isClob (Column column) {
		if (column!=null && 
				(
					column.getType().equals("CLOB") || 
					column.getType().equals("TEXT") 
					)
				)
				return true;
			return false;
	}
	
	public static boolean isLob (Column column) {
		return isClob(column) || isBlob(column);
	}
	
	private static boolean isBlob(Column column) {
		if (column!=null && 
				(
					column.getType().equals("BLOB") || 
					column.getType().equals("LONGVARBINARY") 
					)
				)
				return true;
			return false;
	}

	public static boolean isPkUserProvided (Column column) {
		return isNaturalPk(column);
	}
	
	public static boolean isUnique (Column column) {
		return TableUtils.isUnique(column.getTable(), column);
	}
	
	public static boolean isForeignKey (Column column) {
		if (column==null) {
			return false;
		}
		Table table = column.getTable();
		return isForeignKey(column, table);
	}

	public static boolean isForeignKey (Column column, Table table) {
		Reference[] reference = table.getParents();
		for (int i = 0; i < reference.length; i++) {
			if (reference[i].getLocalColumnName().equals(column.getName()))
				return true;
		}
		return false;
	}
	
	public static Table getForeignTable (Column column) {
		Reference reference = getReference(column);
		return (reference!=null)? reference.getForeignTable():null;
	}

	private static Reference getReference (Column column) {
		return ReferenceUtils.getReference(column);
	}
	
	public static boolean isDecimalPrecisionColumn(Column column) {
		if (column==null) return false;
		return column.getScale()>0;
	}
	
	public static boolean isLengthPrecisionColumn(Column column) {
		return isString(column);
	}
	
	public static boolean isString(Column column) {
		if (column==null || column.getType()==null) return false;
		if (
				column.getType().equals("CHAR") || 
				column.getType().equals("NCHAR") || 
				column.getType().equals("CHAR2") ||
				column.getType().equals("VARCHAR") ||
				column.getType().equals("NVARCHAR") ||
				column.getType().equals("VARCHAR2") ||
				column.getType().equals("VARGRAPHIC") ||
				column.getType().equals("VARGRAPHIC2") ||
				column.getType().equalsIgnoreCase("LONGVARCHAR") ||
				column.getType().equals("CLOB")
		)
			return true;
		return false;
	}
	
	public static boolean isStringColumn (Column column) {
		return isString(column);
	}
	
	public static String getMethodInputParameters (Column columns[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < columns.length; i++) {
			if (i!=0)
				sb.append(", ");
			sb.append(CommonUtils.getFullType2(columns[i]));
			sb.append(" ");
			sb.append(CommonUtils.getJavaVariableName(columns[i].getName()));
		}
		return sb.toString();
	}
	
	public static String getDefaultStuffingForColumn (Column column) {
		return getDefaultStuffingForColumn(column, false);
	}
	
	public static String getDefaultStuffingForColumn (Column column, boolean useTemporal) {
		String type = column.getType();
		if (type.equals("TIMESTAMP"))
			return (useTemporal)?"new java.util.Date()": "new Timestamp(new Date().getTime())";
		return ConvertUtils.getJavaDefaultMask(column);
//		if (type.equals("CHAR") || 
//			type.equals("CHAR2") ||
//			type.equals("VARCHAR") ||
//			type.equals("VARCHAR2") ||
//			type.equals("VARGRAPHIC") ||
//			type.equals("VARGRAPHIC2") 
//			//type.equals("CLOB")
//				)
//			return "\"\"";
////		if (type.equals("INT") ||
////			type.equals("SMALLINT") ||
////			type.equals("INTEGER") )
////			return "Integer.valueOf(\"-1\")";
//		if (isInteger(column))
//			return "Integer.valueOf(\"-1\")"; 
////		if (type.equals("BIGINT") || 
////			type.equals("LONG")   ||
////			type.equals("NUMBER") ||
////			type.equals("DECIMAL") )
////			return "Long.valueOf(\"-1\")";
//		if (isLong(column))
//			return "Long.valueOf(\"-1\")";
//		if (type.equals("FLOAT"))
//			return "java.math.BigDecimal.valueOf(-1)";
//		if (type.equals("DATE")) 
//			return "new java.util.Date()";
//		if (type.equals("CLOB") ||
//			type.equals("BLOB"))
//			return "null";
//		return "\"\"";
	}
	
	public static boolean isInteger(Column column) {
		String type = column.getType();
		return (type.equals("INT") ||
			type.equals("SMALLINT") ||
			type.equals("INTEGER") )?true:false;
	}
	
	public static boolean isLong(Column column) {
		String type = column.getType();
		return (type.equals("BIGINT") ||
				type.equals("LONG") ||
				type.equals("DECIMAL") ||
				type.equals("NUMBER") )?true:false;
	}

	public static boolean isDouble(Column column) {
		String type = column.getType();
		return type.equals("DOUBLE");
	}

	public static boolean isTimeColumn (Column column) {
		if (column!=null && column.getType()!=null) {
			if (column.getType().equalsIgnoreCase("DATE") || 
				column.getType().equalsIgnoreCase("TIME") || 
				column.getType().equalsIgnoreCase("DATETIME") || 
				isTimeStampColumn(column))
				return true;		
		}
		return false;
	}

	public static boolean isDate (Column column) {
		if (column!=null && column.getType()!=null) {
			if (column.getType().equalsIgnoreCase("DATE"))
				return true;
		}
		return false;
	}
	
	public static Property getCheckConstraintProperty (Column column) {
		return column.getPropertyByTag(CHECK_CONSTRAINT_PROPERTY_TAG);
	}

	public static List<Property> getCheckConstraintValues (Column column) {
		Property checkConstraint = getCheckConstraintProperty(column);
		if (checkConstraint!=null)
			return checkConstraint.getProperties();
		return null;
	}
	
	public static boolean hasCheckConstraint (Column column) {
		if (getCheckConstraintProperty(column)!=null)
			return true;
		return false;
	}

	public static boolean hasFormulaStereotype (Column column) {
		if (hasStereotype(column) && column.getStereotype().getFormula()!=null)
			return true;
		return false;
	}
	
	public static boolean hasStereotype (Column column) {
		if (column.getStereotype()!=null)
			return true;
		return false;		
	}

	public static boolean hasTrigger(Column column) {
//		return (hasTriggerProperty(column) || (column.getTriggers()!=null && column.getTriggers().size()>0));
		return ((column.getTriggers()!=null && column.getTriggers().size()>0));
	}

//	private static boolean hasTriggerProperty(Column column) {
//		for (Property property : column.getProperties()) {
//			if (isTrigger(property))
//				return true;
//		}
//		return false;
//	}
//
//	private static boolean isTrigger(Property property) {
//		return PropertyUtils.isTriggerTag(property);
//	}

	public static boolean belongsToCompositePrimaryKeyNotMany2Many(Column column) {
		return TableUtils.isCompositePrimaryKeyNotMany2Many(column.getTable()) &&
			 isPartOfCompositePrimaryKey(column);
	}
	
	public static boolean isPartOfCompositePrimaryKey(Column column) {
		if (column!=null) {
			Column[] primaryKeyColumns = column.getTable().getPrimaryKeyColumns();
			if (primaryKeyColumns.length<2) {
				// check that primary key is also a fk
				if (primaryKeyColumns.length==1) {
				    if (!ColumnUtils.isForeignKey(primaryKeyColumns[0]))
				    	return false;
				} else
					return false;
			}
			String columnLowerCase = column.getName().toLowerCase();
			for (Column col : primaryKeyColumns) {
				if (col.getName().toLowerCase().equals(columnLowerCase))
					return true;
			}
		}
		return false;
	}

	public static boolean isForeignKeyAndNotPartOfCompositeForeignKey(Column column) {
		return (isForeignKey(column) && !isPartOfCompositeForeignKey(column));
	}

	private static boolean isPartOfCompositeForeignKey(Column column) {
		for (ForeignKey fk : column.getTable().getForeignKeys()) {
			return ForeignKeyUtils.containsLocalColumn(fk, column);
		}
		return false;
	}

	public static boolean isEnumColumn(Column column) {
		return true;
	}

	public static boolean isUsingDefaultAlias(Column column) {
		// TODO Auto-generated method stub
		return (column.getName().toLowerCase().equals(column.getAlias().toLowerCase()));
	}

	public static String getJavaVariableColumnAlias(Column column) {
		return (column!=null)?FormatUtils.getJavaNameVariable(column.getAlias()):"ERROR_NULL_COLUMN_CANNOT_FORMAT_ALIAS";
	}

	public static String asNameStringList(List<Column> beans) {
		StringBuffer sb = new StringBuffer();
		int cpt=0;
		int size = beans.size();
		for (GeneratorBean bean : beans) {
			sb.append(bean.getName());
			if (cpt<size) {
				sb.append(",");
				cpt++;
			}
		}
		return sb.toString();
	}

	public static boolean isTimeStampColumn(Column column) {
		if (column.getType().equals("TIMESTAMP") || 
			column.getType().equals("TIMESTAMPTZ"))
			return true;		
		return false;
	}

	public static Boolean isBoolean(Column column) {
		String type = column.getType().toUpperCase();
		if (type.equals("BOOLEAN") ||
				type.equals("BIT") ||
			(type.equals("CHAR") && column.getSizeAsInt()<=1))
			return true;
		return false;
	}
	
	public static boolean isColumnEmbeddedInEntity (Column column) {
		boolean primaryKey = column.isPrimaryKey();
		return (!primaryKey || (primaryKey && !TableUtils.isCompositePrimaryKeyNotMany2Many(column.getTable())));
	}
	
	public static boolean isColumnEmbeddedInPrimaryKey (Column column) {
		return (column.isPrimaryKey() && TableUtils.isCompositePrimaryKeyNotMany2Many(column.getTable()));
	}

	public static String getTransientName(String name) {
		return name+_TRANSIENT;
	}

	public static boolean hasTransientColumnName(Column column) {
		return (column!=null && column.getName()!=null && column.getName().endsWith(_TRANSIENT));
	}
	
	
	public Column getAssociatedTransientColumn(Column column) {
		for (Column c : column.getTable().getColumns()) {
			if (c.getName().equals(column.getName()+_TRANSIENT))
				return c;
		}
		return null;
	}
	
	public static String getTransientColumnNameRoot(Column column) {
		return StringUtils.remove(column.getName(), _TRANSIENT);
	}
	
	public static Column getTransientColumnRoot(Column column) {
		for (Reference reference : column.getTable().getParents()) {
			if (reference.getLocalColumnName().equals(getTransientColumnNameRoot(column))) {
				return reference.getLocalColumn();
			}
		}
		return null;
	}
	 
	public static boolean hasPropertyTagStartsWith (Column column, String value) {
		return column.getProperties().stream()
				.anyMatch(u -> u.tag!=null && u.tag.startsWith(value))
				;
	}
	
	public static boolean isLink(Column column) {
		if (column==null)
			return false;
		return (column.getQueryParamLink()!=null)?true:false; 
	}

	public static List<Column> getColumns(Table table, String columns) {
		List<Column> cols = new ArrayList<Column>();
		for (String columnName : ParserUtils.getList(columns)) {
			Column column = getColumn(table, columnName);
			if (column!=null) 
				cols.add(column);
		}
		return cols;
	}
	
	public static Column getFirstColumn (Table table) {
		if (table.getColumnCount()>0) {
			return table.getColumn(0);
		}
		return null;
	}
	
	public static int getPrecision(Column column) {
		return column.getPrecisionRadix()-column.getScale();
	}
}
