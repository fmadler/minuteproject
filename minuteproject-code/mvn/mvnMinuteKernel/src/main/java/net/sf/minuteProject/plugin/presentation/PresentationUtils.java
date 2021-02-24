package net.sf.minuteProject.plugin.presentation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.sf.minuteProject.configuration.bean.BusinessPackage;
import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.system.Property;
import net.sf.minuteProject.loader.presentation.node.Block;
import net.sf.minuteProject.loader.presentation.node.Window;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.TableUtils;
import net.sf.minuteProject.utils.property.PropertyUtils;

/**
 * @author adlerfl
 *
 */
public class PresentationUtils {

	private static final String DISPLAY_CHILDREN = "display-children";
	private static final int MAX_COLUMNS_DISPLAY_SIZE = 40;

	public static int getDisplayColumns(Column column) {
		return Math.min(column.getSizeAsInt(),MAX_COLUMNS_DISPLAY_SIZE);
	}

	public static int getDisplayRows(Column column) {
		int sizeAsInt = column.getSizeAsInt();
		int i = sizeAsInt%MAX_COLUMNS_DISPLAY_SIZE;
		int j = sizeAsInt/MAX_COLUMNS_DISPLAY_SIZE;
		return (i % MAX_COLUMNS_DISPLAY_SIZE == 0)? j : j+1;
	}
	
	/**
	 * @param table
	 * @return
	 */
	public static Boolean hasDisplayableChildren (Table table) {
		return (getDisplayableChildren(table).size()>0);
	}
	
	/**
	 * @param table
	 * @return list of associate child references that are not many to many
	 */
	public static List<Reference> getDisplayableChildren (Table table) {
		List<Reference> refs = new ArrayList<Reference>();
		for (Reference reference : table.getChildren()) {
			if (!reference.getForeignTable().isManyToMany())
				refs.add(reference);
		}
		return refs;
	}
	
	public static Boolean isParentDropDownList (Column column) {
		return (ColumnUtils.isForeignKey(column) 
				&& TableUtils.isAdminContentType(ColumnUtils.getForeignTable(column)))? true:false;
	}
	
	public static Boolean isTextArea (Column column) {
		return (column.getSizeAsInt()>100 && !column.isLob())? true:false;
	}
	
	public static Boolean isRichText (Column column) {
		return (column.getSizeAsInt()>100 && column.getType().equals("CLOB"))? true:false;
	}	
	
	public static Boolean isRichTextOnly (Column column) {
		return (column.getType().equals("CLOB"))? true:false;
	}	
	
	public static Boolean isFileUpload (Column column) {
		return (column.getType().equals("BLOB"))? true:false;
	}	
	
	public static Boolean isCheckBox (Column column) {
		return (ColumnUtils.isBoolean(column));
	}
	
	public static Boolean isForm(Template template, GeneratorBean generatorBean) {
		if (generatorBean instanceof Window)
			return isForm((Window)generatorBean);
		if (generatorBean instanceof Block)
			return isForm((Block)generatorBean);
		return false;
	}
	
	public static boolean isForm(Window window) {
		if (window.getIsform()==null)
			return false;
		return window.getIsform();
	}
	
	public static boolean isForm(Block block) {
		if (block.getIsform()==null)
			return false;
		return block.getIsform();
	}

	public static List<Table> getDisplayableEntityFromPackage (net.sf.minuteProject.configuration.bean.Package pack) {
		List<Table> list = new ArrayList<Table>();
		for (Table table : pack.getListOfEntities()) {
			if (isDisplayable(table)) 
				list.add(table);
		}
//		for (Table table : pack.getListOfViews()) {
//			if (!table.isManyToMany() && !table.isLinkEntity()) 
//				list.add(table);
//		}
		return list;
	}
	
	public static List<List<GeneratorBean>> getDisplayableGroups (net.sf.minuteProject.configuration.bean.Package pack) {
		return pack.getGroupsList();
	}
	
	public static List<List<Table>> getDisplayableEntityGroups (net.sf.minuteProject.configuration.bean.Package pack) {
		List<List<GeneratorBean>> groups = getDisplayableGroups(pack);
		List<List<Table>> entityGroup = new ArrayList<List<Table>>();
		for (List<GeneratorBean> group: groups) {
			List<Table> entities = new ArrayList<Table>();
			for (GeneratorBean generatorBean : group) {
				if (generatorBean instanceof Table) {
					Table t = (Table)generatorBean;
					if (isDisplayable(t))
						entities.add(t);
				}
			}
			if (!entities.isEmpty()) {
				entityGroup.add(entities);
			}
		}
		return entityGroup;
	}

	private static boolean isDisplayable(Table table) {
		if (!table.isManyToMany() && !table.isLinkEntity()) 
			return true;
		return false;
	}
	
	public static List<List<List<Column>>> getDisplayableColumnsForSearchView(Table table) {
		List<Column> row = new ArrayList<Column>();
		List<List<Column>> block = new ArrayList<List<Column>>();
		List<List<List<Column>>> displayable = new ArrayList<List<List<Column>>>();
		
		// 1 if has semantic reference
		
		// 2 else if has fieldgroup
		
		// 3
		for (Column column : getPotentialDisplayableColumns(table)) {
			//sort and group based on fieldgroup
			row.add(column);
			block.add(row);
		}
		displayable.add(block);
		return displayable;
	}

	public static List<Column> getPotentialDisplayableColumnsWithExclusion(Table table, String columnName) {
		List<Column> columns = getPotentialDisplayableColumns(table);
		for (Iterator<Column> iterator = columns.iterator(); iterator.hasNext();) {
			Column column = iterator.next();
			if (column.getName().equals(columnName)) {
				iterator.remove();
				break;
			}
		}
		return columns;
	}
	
	public static List<Column> getPotentialDisplayableColumnsWithOnePkInFirstPosition(Table table) {
		List<Column> columns = new ArrayList<Column> ();
		columns.add(TableUtils.getPrimaryFirstColumn(table));
		columns.addAll(getPotentialDisplayableColumns(table));
		int cpt=0;
		for (Iterator<Column> iterator = columns.iterator(); iterator.hasNext();) {
			Column column = iterator.next();
			if (column.isPrimaryKey() && cpt>0) {
				iterator.remove();
				break;
			}
			cpt++;
		}
		return columns;
	}
	
	public static List<Column> getPotentialDisplayableColumns(Table table) {
		if (TableUtils.hasSemanticReference(table)) {
			return TableUtils.getSemanticReferenceColumns(table);
		}
		return getNotTechnicalColumns(table);
	}
	public static List<Column> getNotTechnicalColumns(Table table) {
		return TableUtils.getNotTechnicalColumns(table);
	}
	
	public static List<Reference> getDisplayDisplayableChildReference(Table table, Template template) {
		List<Reference> ref = new ArrayList<Reference>();
		Property displayChildren = template.getPropertyByName(DISPLAY_CHILDREN);
		if (displayChildren==null)
			return ref;
		List<Property> props = displayChildren.getProperties();
		Reference[] children = table.getChildren();
		if (props.isEmpty())
			return Arrays.asList(children);
		for (Property prop : props) {
			for (Reference reference : children) {
				if (PropertyUtils.isPropertyCondition(prop, reference.getForeignTable().getName())){
					ref.add(reference);
					break;
				}
			}
		}
		return ref;
	}
	
	//used by OX
	public static List<Column> getDisplayableAttributes(Table table) {
		List<Column> columns = new ArrayList<Column>();
		for (Column column : table.getColumns()) {
			if (column.isPrimaryKey() && ColumnUtils.isPkUserProvided(column))
				columns.add(column);
			else if (!column.isLob()) {
				columns.add(column);
			}
		}
		return columns;
	}
}