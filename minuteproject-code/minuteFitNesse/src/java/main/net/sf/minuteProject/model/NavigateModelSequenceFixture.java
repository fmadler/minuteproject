package net.sf.minuteProject.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import fitlibrary.SequenceFixture;
import net.sf.minuteProject.application.ModelViewGenerator;
import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.FunctionModel;
import net.sf.minuteProject.configuration.bean.Model;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Function;
import net.sf.minuteProject.configuration.bean.model.data.FunctionColumn;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.DatabaseUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ModelUtils;
import net.sf.minuteProject.utils.MpObjectBuilder;
import net.sf.minuteProject.utils.ReferenceUtils;
import net.sf.minuteProject.utils.TableUtils;
import net.sf.minuteProject.utils.enrichment.EnrichmentUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class NavigateModelSequenceFixture extends SequenceFixture{

	private enum ObjectAttribute {NAME, ALIAS};
	private enum ColumnType {PK, FK, SIMPLE};
	private enum ReferenceAttribute {LOCAL_COLUMN, LINKED_COLUMN, LOCAL_TABLE, LINKED_TABLE}
	private static ModelViewGenerator modelViewGenerator;
	private static Configuration configuration;
	private static Model model;
	private static Database database;
	
	public static String load (String config) {
		try {
			modelViewGenerator = new ModelViewGenerator(config);
			configuration = (Configuration) modelViewGenerator.load();
			model = modelViewGenerator.getEnrichedModel(configuration);
			modelViewGenerator.load();
			database = model.getDataModel().getDatabase();
		} catch (MinuteProjectException e) {
			// TODO Auto-generated catch block
			return e.getError();
		}
		return "SUCCESSFUL";
	}

	public int numberOfEntity (String name) {
		if (model!=null) {
			return model.getBusinessModel().getBusinessPackage().getEntities().size();
		}
		return -1;
	}
	
	public static Reference getParentReference(String child, String parent) {
		for (Reference ref : parentsReferenceForEntity(child)) {
			if (ref.getForeignTable().getName().toLowerCase().equals(parent.toLowerCase()))
				return ref;
		}
		return null;
	}
	public static Reference getChildReference(String child, String parent) {
		for (Reference ref : childrenReferenceForEntity(parent)) {
			if (ref.getForeignTable().getName().toLowerCase().equals(child.toLowerCase()))
				return ref;
		}
		return null;
	}
	public static Table getEntity (String name) {
		Database db = model.getDataModel().getDatabase();
		return TableUtils.getEntity(db, name);
	}
	public static List<Table> getEntities () {
		return model.getBusinessModel().getBusinessPackage().getEntities();
	}
	public static List<Reference> parentsReferenceForEntity (String name) {
		Database db = model.getDataModel().getDatabase();
		return Arrays.asList(TableUtils.getEntity(db, name).getParents());
	}
	public static List<Reference> childrenReferenceForEntity (String name) {
		Database db = model.getDataModel().getDatabase();
		return Arrays.asList(TableUtils.getEntity(db, name).getChildren());
	}
	
	public int numberOfParentsForEntity (String name) {
		if (model!=null) {
			return parentsReferenceForEntity(name).size();
		}
		return -1;
	}
	
//	number of column for entity
	public int numberOfColumnsForEntity (String name) {
		if (model!=null) {
			Table entity = TableUtils.getEntity(database, name);
			return (entity!=null)?entity.getColumnCount():-1;
		}
		return -1;
	}
//	column names of table name
	public String columnNamesOfTableName (String name, String values) {
		if (model!=null) {
			Table entity = TableUtils.getEntity(database, name);
			String columns = ColumnUtils.asNameStringList(Arrays.asList(entity.getColumns()));
			return compareList(ParserUtils.getList(columns), ParserUtils.getList(values));
		}
		return "NA";
	}
	
//	public List<String> getNamesOfParentsEntitiesForEntity (String name) {
//		List<String> list = new ArrayList<String>();
//		List<Reference> l =  parentsReferenceForEntity(name);
//		for (Reference ref : l) {
//			list.add(ref.getTable()Name());
//		}
//		return list;
//	}
	
	public String namesOfParentsEntityForEntity (String name, String values) {
		return compareAttributesOfParentsEntitiesForEntity(name, values, ObjectAttribute.NAME, ReferenceAttribute.LINKED_TABLE);
	}
	
	public String aliasesOfParentsEntityForEntity (String name, String values) {
		return compareAttributesOfParentsEntitiesForEntity(name, values, ObjectAttribute.ALIAS, ReferenceAttribute.LINKED_TABLE);
	}
	
	public String namesOfLocalFkColumnsForEntity (String name, String values) {
		return compareAttributesOfParentsEntitiesForEntity(name, values, ObjectAttribute.NAME, ReferenceAttribute.LOCAL_COLUMN);
	}
	
	public String aliasesOfLocalFkColumnsForEntity (String name, String values) {
		return compareAttributesOfParentsEntitiesForEntity(name, values, ObjectAttribute.ALIAS, ReferenceAttribute.LOCAL_COLUMN);
	}
	
	
	public String compareAttributesOfParentsEntitiesForEntity (String name, 
			 String values,
			 ObjectAttribute attribute,
			 ReferenceAttribute refAttribute) {
		List<String> l1 = getAttributesOfParentsEntitiesForEntity (name, attribute, refAttribute);
		List<String> l2 = ParserUtils.getList(values);
		return compareList (l1, l2);
	}
	
	public static String compareList (List<String> l1, List<String> l2) {
		if (isIdentical(l1, l2, false)) {
			return "true";
		}
		return l1.toString();
	}
	public static String compareListWithOrder (List<String> l1, List<String> l2) {
		if (isIdentical(l1, l2, true)) {
			return "true";
		}
		return l1.toString();
	}
	
	
	public List<String> getAttributesOfParentsEntitiesForEntity 
			(String name,
			 ObjectAttribute attribute,
			 ReferenceAttribute refAttribute) {
		List<String> list = new ArrayList<String>();
		List<Reference> l =  parentsReferenceForEntity(name);
		for (Reference ref : l) {
			String s = "";
			switch (refAttribute) {
				case LOCAL_COLUMN: 
					s=(ObjectAttribute.NAME.equals(attribute))?ref.getLocalColumn().getName():ref.getLocalColumn().getAlias();
					break;
				case LINKED_COLUMN: 
					s=(ObjectAttribute.NAME.equals(attribute))?ref.getForeignColumn().getName():ref.getForeignColumn().getAlias();
					break;
				case LOCAL_TABLE: 
					s=(ObjectAttribute.NAME.equals(attribute))?ref.getLocalTable().getName():ref.getLocalTable().getAlias();
					break;
				case LINKED_TABLE: 
					s=(ObjectAttribute.NAME.equals(attribute))?ref.getForeignTable().getName():ref.getForeignTable().getAlias();
					break;
			}
			list.add(s);
		}
		return list;
	}
	
	public static boolean isIdentical (List<String> l1, List<String> l2, boolean checkOrder) {
		if (l1==null) {
			if (l2 == null) return false;
			else return true;
		}
		if (l1.size()!=l2.size())
			return false;
		if (checkOrder) {
			for (int i = 0; i < l1.size(); i++) {
				if (!l1.get(i).equals(l2.get(i)))
					return false;
			}
		} else {
			for (String s1 : l1) {
				boolean exist = false;
				for (String s2 : l2) {
					if (s2.equals(s1)) {
						exist = true;
						break;
					}					
				}
				if (!exist) return false;
			}
		}
		return true;
//		
//      Collection<String> compare = new HashSet<String>(l1);
//      return (compare.retainAll(l2)&&compare.removeAll(l2));
	}
	
	public String aliasJavaVariableOfManyToManyBetweenTablesViaTable (String parent, String child, String via) {
		return FormatUtils.getJavaNameVariable(getReferenceManyToManyBetweenTablesViaTable(parent, child, via));
	}
	
	public String getReferenceManyToManyBetweenTablesViaTable (String parent, String child, String via) {
		System.out.println(">> parent: "+parent);
		Table table = TableUtils.getEntity(database, parent);
		if (table!=null) {
			for (Reference ref : EnrichmentUtils.getLinkedTargetReferenceByMany2Many(table)) {
				if (ref.getForeignTable().getName().equals(child) && ref.getLocalTableName().toLowerCase().equals(via.toLowerCase()))
					return ref.getAlias();
			}
		}
		return "table do not exist";
	}
	
	public String aliasJavaVariableOfFirstRelationshipBetween (String child, String parent) {
		System.out.println("aliasJavaVariableOfFirstRelationshipBetween child = "+child+" - "+parent);
		return CommonUtils.getColumnAliasVariable(getEntity(child), 
				getParentReference(child, parent));
	}
	
	public String nameJavaVariableOfFirstRelationshipBetween (String child, String parent) {
		return CommonUtils.getColumnNameVariable(getEntity(child), 
				getParentReference(child, parent));
	}
	
	public String columnClassNameOfFirstRelationshipBetween (String child, String parent) {
		Reference reference = getParentReference(child, parent);
		Database db = model.getDataModel().getDatabase();
		return ReferenceUtils.getColumnClassNameForLinkTable(db, reference.getForeignColumn());
	}

	public String aliasOfColumnOfTable (String columnName, String tableName) {
		Column column = ColumnUtils.getColumn(TableUtils.getEntity(database, tableName), columnName);
		return column.getAlias();
	}
	
	public Column getColumnOfTableByType (String columnName, String tableName, ColumnType type) throws MinuteProjectException {
		for (Table table : model.getBusinessModel().getBusinessPackage().getTables()) {
			if (table.getName().equals(tableName)) {
				if (type.equals(ColumnType.PK))
					for (Column column:table.getPrimaryKeyColumns()) {
						System.out.println("column "+column.getName());
						if (column.getName().equals(columnName)) {
							return column;
						}
					}
				if (type.equals(ColumnType.SIMPLE))
					for (Column column:table.getColumns()) {
						System.out.println("column "+column.getName());
						if (column.getName().equals(columnName)) {
							return column;
						}
					}
				throw new MinuteProjectException("column not found","error");
			}
		}
		throw new MinuteProjectException("table not found","error");
	}
	
	public String aliasOfColumnOfTableNew (String columnName, String tableName) {
		try {
			return getColumnOfTableByType(columnName, tableName, ColumnType.PK).getAlias();
		} catch (MinuteProjectException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}

	public String columnNameOfTableNameIsNotForeignKey (String columnName, String tableName) {
		String s = columnNameOfTableNameIsForeignKey(columnName, tableName);
		if ("true".equals(s)) return "false";
		if ("false".equals(s)) return "true";
		return s;
	}
	public String columnNameOfTableNameIsForeignKey (String columnName, String tableName) {
		try {
			Column column = getColumnOfTableByType(columnName, tableName, ColumnType.SIMPLE);
			return (ColumnUtils.isForeignKey(column))?"true":"false";
		} catch (MinuteProjectException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}
	
	public String aliasJavaVariableOfPrimaryKeyFromPrimaryKeySet (String tableName) {
		Column column = getFirstPrimaryKeyFromPrimaryKeySet(tableName);
		if (column!=null)
			return ColumnUtils.getJavaVariableColumnAlias(column);
		return "NOT FOUND";	
	}
	public String aliasJavaVariableOfPrimaryKeyFromColumnSet (String tableName) {
		Column column = getFirstPrimaryKeyFromColumnSet(tableName);
		if (column!=null)
			return ColumnUtils.getJavaVariableColumnAlias(column);
		return "NOT FOUND";	
	}
	public String aliasJavaVariableOfPrimaryKeyFromForeignKeySet (String tableName) {
		Column column = getFirstPrimaryKeyFromForeignKeySet(tableName);
		if (column!=null)
			return ColumnUtils.getJavaVariableColumnAlias(column);
		return "NOT FOUND";	
	}
	
	private Column getFirstPrimaryKeyFromPrimaryKeySet(String tableName){
		return getPrimaryKeyFromPrimaryKeySet(tableName, null);
	}
	private Column getPrimaryKeyFromPrimaryKeySet(String tableName, String columnName) {
		Table table = TableUtils.getEntity(database, tableName);
		if (table!=null)
			for (Column column:table.getPrimaryKeyColumns()) {
				if (columnName==null) return column;
				else if (columnName.equals(column.getName()))
					return column;
			}
//			return TableUtils.getPrimaryKey(table)FirstColumn(table);
		return null;
	}
	
	private Column getFirstPrimaryKeyFromColumnSet(String tableName) {
		Column column = getFirstPrimaryKeyFromPrimaryKeySet(tableName);
		if (column!=null) {
			return ColumnUtils.getColumn(TableUtils.getEntity(database, tableName), column.getName());
		}
		return null;
	}
	private Column getFirstPrimaryKeyFromForeignKeySet(String tableName) {
		Column column = getFirstPrimaryKeyFromPrimaryKeySet(tableName);
		if (column!=null) {
			Reference reference = ReferenceUtils.getReference(column);
			return reference.getLocalColumn();
		}
		return null;
	}
	private Column getKeyFromForeignKeySet(String tableName, String columnName) {
		Column column = getPrimaryKeyFromPrimaryKeySet(tableName, columnName);
		if (column!=null) {
			Reference reference = ReferenceUtils.getReference(column);
			return reference.getLocalColumn();
		}
		return null;
	}
	
	public String aliasJavaVariableOfPrimaryKeyFromPrimaryKeySet (String tableName, String columnName) {
		Column column = getKeyFromForeignKeySet(tableName, columnName);
		if (column!=null)
			return ColumnUtils.getJavaVariableColumnAlias(column);
		return "NOT FOUND";	
	}
	
	public boolean storeProcedureExists (String sp) {
		return (getProcedureByName(sp)!=null)?true:false;
	}
	
	private Function getProcedureByName (String sp) {
		Function[] fs = model.getDataModel().getDatabase().getFunctions();
		for (Function f : fs) {
			if (sp.equals(f.getName()))
				return f;
		}
		return null;
	}
	
	public String storeProcedureInputColumnsAre (String sp, String columns) {
		return storeProcedureInputColumnsAre(sp, columns,Direction.IN);
	}
	
	public String storeProcedureOutputColumnsAre (String sp, String columns) {
		return storeProcedureInputColumnsAre(sp, columns,Direction.OUT);
	}
	
	public boolean storeProcedureHasReturn (String sp) {
		Function f = getProcedureByName(sp);
		return f.hasReturn();
	}
	
	private String storeProcedureInputColumnsAre (String sp, String columns, Direction direction) {
		Function f = getProcedureByName(sp);
		if (f!=null) {
			List<String> l1 = getColumnNames(f, direction);
			List<String> l2 = ParserUtils.getList(columns);
			return compareList (l1, l2);
		}
		return "false";
	}

	private List<String> getColumnNames(Function f, Direction direction) {
		List<String> r = new ArrayList<String>();
//		Table t = f.getEntity(direction);
//		for (Column c : t.getColumns()) {
//			r.add(c.getName());
//		}		
//		return r;
		if (direction.equals(Direction.IN))
			for (FunctionColumn c : f.getInputColumns()) {
				r.add(c.getName());
			}
		else if (direction.equals(Direction.OUT))
			for (FunctionColumn c : f.getOutputColumns()) {
				r.add(c.getName());
			}
		return r;
	}

	public static Model getModel() {
		return model;
	}

	public static Database getDatabase() {
		return database;
	}

}
