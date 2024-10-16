package net.sf.minuteProject.plugin.openxava;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.enrichment.Action;
import net.sf.minuteProject.configuration.bean.enrichment.SemanticReference;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.plugin.format.I18nUtils;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.ConvertUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.ReferenceUtils;
import net.sf.minuteProject.utils.TableUtils;
import net.sf.minuteProject.utils.code.RestrictedCodeUtils;
import net.sf.minuteProject.utils.enrichment.SemanticReferenceUtils;
import net.sf.minuteProject.utils.java.JavaUtils;

public class OpenXavaUtils {

	public static String getHelpDirectory () {
		return "xava/help/";
	}
	
	public static String getHelpFileExtension () {
		return "jsp";
	}
	
	public static List<String> getTabAll (Table table) {
		List<String> list = new ArrayList<String>();
		list.addAll(getTabDefaultProperties(table));
		list.addAll(getParentSemanticReference(table));
		return list;
	}
	public static List<String> getTabSimple (Table table) {
		List<String> list = new ArrayList<String>();
		list.addAll(getTabDefaultProperties(table));
		return list;
	}
	public static List<String> getTabWithRef (Table table) {
		return getTabAll(table);
	}
	
	public static boolean hasTabAll (Table table) {
		return (getTabAll(table).isEmpty())?false:true;
	}
	public static boolean hasTabSimple (Table table) {
		return (getTabSimple(table).isEmpty())?false:true;
	}
	public static boolean hasTabWithRef (Table table) {
		return (getTabWithRef(table).isEmpty())?false:true;
	}
	
	private static List<String> getTabDefaultProperties (Table table) {
		List<String> list = new ArrayList<String>();
		for (Column column : table.getPrimaryKeyColumns()) {
			if (ColumnUtils.isNaturalPk(column)) {
				list.add(JavaUtils.getJavaVariableNaming(FormatUtils.getJavaNameVariable(column.getAlias())));
			}
		}
		for (Column column : table.getAttributes()) {
			if (column.isSearchable() && !column.isLob() && !column.isTransient() && !(column.getTriggers().size()>0))
				list.add(JavaUtils.getJavaVariableNaming(FormatUtils.getJavaNameVariable(column.getAlias())));
		}
		return list;
	}
		
	public static List<String> getParentSemanticReference (Table table) {
		return getParentSemanticReference(table, null, true);
	}
	
	public static List<String> getParentSemanticReferenceEntityVariable (Table table) {
		return getParentSemanticReference(table, null, false);
	}
	
	public static List<String> getParentSemanticReference (Table table, Reference removeReference, boolean addChunk) {
		List<String> list = new ArrayList<String>();
		for (Reference reference : table.getParents()) {
			if (!ReferenceUtils.isSimilarReference(reference, removeReference)) {
				Table foreignTable = reference.getForeignTable();
				Table parent = foreignTable;
				if (TableUtils.hasSemanticReference(parent)) {
					SemanticReference sr = foreignTable.getSemanticReference();
					for (String chunk : sr.getSemanticReferenceBeanPath()) {
						Column column = ColumnUtils.getColumn(foreignTable, chunk);
						//only one level
						if (column!=null) {
							if (!ColumnUtils.isForeignKey(column)) {
								String c = FormatUtils.getJavaNameVariable(reference.getLocalColumn().getAlias());
								if (addChunk) c = c+"."+convertChunkToAlias(chunk, parent);
								list.add(c);
							}
						}
						//break; // only the first is added
					}
				}
			}
		}
		return list;
	}
	
	public String getI18nSemanticReference(String key) {
		//only 2 token parent table and column
		String ss [] = StringUtils.split(key,".");
		if (ss.length!=2) return key;
		StringBuffer sb = new StringBuffer();
		for (int i=0 ; i < ss.length; i++) {
			sb.append(FormatUtils.firstUpperCase(ss[i]));
			if (i!=ss.length-1)
				sb.append(" ");
		}
		return sb.toString();
	}
	
	private static String convertChunkToAlias(String chunk, Table table) {
		Column column = ColumnUtils.getColumn(table, chunk);
		return FormatUtils.getJavaNameVariable(column.getAlias());
	}

	public static boolean hasDescriptionList (Table table) {
		if (TableUtils.hasSemanticReference(table) && 
			(TableUtils.isReferenceDataContentType(table) 
			|| TableUtils.isMasterDataContentType(table)
//			|| TableUtils.isPseudoStaticDataContentType(table)
			)
			)
			return true;
		return false;
	}
	
	public static String getPackageName(Package pack) {
		return I18nUtils.getPackageName(pack);
	}
	public enum ListPropertyDirection { REFERENCE, TAB }
	
	public static List<String> getListProperties (Table table, ListPropertyDirection direction) {
		List<String> list = new ArrayList<String>();
		SemanticReference sr = table.getSemanticReference();
		if (sr!=null) {
			for (String chunk : sr.getSemanticReferenceBeanPath()) {
				//if is composite ex: A_ID.B_ID.C_ID.D_ID ...
				// check A_ID is composite, the B_ID
				// construct the path
				//
				Column column = ColumnUtils.getColumn(table, chunk);
				if (column!=null) {
					String colPar = FormatUtils.getJavaNameVariable(column.getAlias());
					if (ColumnUtils.isForeignKey(column)) {
						Reference reference = ReferenceUtils.getReference(table, column.getName());
						Table parent = reference.getForeignTable();
						SemanticReference srParent = parent.getSemanticReference();
						if (srParent!=null) {
							for (String chunkParent : srParent.getSemanticReferenceBeanPath()) {
								Column columnParent = ColumnUtils.getColumn(parent, chunkParent); 
								if (columnParent!=null) {
									if (!ColumnUtils.isForeignKey(columnParent)) {
										String parCol = FormatUtils.getJavaNameVariable(columnParent.getAlias());
										if (ListPropertyDirection.TAB == direction) {
											list.add(colPar+"."+parCol);
										} else {
											list.add(colPar);
										}
									}
								}
							}
						}
					} else {
						list.add(colPar);
					}
				}
			}
		}
		return list.stream().distinct().collect(Collectors.toList());
	}
	
	public static List<String> getListProperties (Table table) {
		return getListProperties(table, ListPropertyDirection.TAB);
	}
	
	public static List<String> getReference (Table table) {
		return getListProperties(table, ListPropertyDirection.REFERENCE);
	}
	
	public static List<String> getListProperties (Reference reference) {
		boolean hasSemRef = false;
		List<String> list = new ArrayList<String>();
		Table linkTable  = reference.getForeignTable();
		Table childTable = reference.getLocalTable();
		List<String> relativeChildSR = getParentChildRelativeSemanticReference(reference, linkTable);
		if (SemanticReferenceUtils.hasSemanticReference(childTable)) {
			list.addAll(getSemanticRefProperties(childTable));
			hasSemRef = true;
		}
		if (!relativeChildSR.isEmpty()) {
			if (!hasSemRef)
				list.addAll(getTabDefaultProperties(linkTable));//todo change by reference instead of default
			list.addAll(relativeChildSR);
		}
		return list;
	}

	private static List<String> getSemanticRefProperties(Table childTable) {
		List<String> list = new ArrayList<String>();
		SemanticReference sr = childTable.getSemanticReference();
		for (String chunk : sr.getSemanticReferenceBeanPath()) {
			
			list.add(convertChunkToAlias(chunk, childTable));
		}
		return null;
	}

	private static List<String> getParentChildRelativeSemanticReference(
			Reference reference, Table linkTable) {
		return getParentSemanticReference (linkTable, reference, true);
	}

	public static String getColumnDescription(Column column) {
		return I18nUtils.getColumnDescription(column); 
	}
	
	public static String getActionClassName (Action action) {
		if (action!=null)
			return RestrictedCodeUtils.convertToValidJava(action.getName());
		return "action_must_be_set";
	}
	
	public static String getControllerName (Table table) {
		return FormatUtils.getJavaName(table.getAlias())+"Controller";
	}
	
	public static String getModuleUrlRelativeViaParameter (String application, String module) {
		return "home.jsp?application="+application+"&module="+module;
	}
	
	public static String getModuleUrlAbsoluteViaSlash (String application, String module) {
		return "/"+application+"/MenuModules/"+module;
	}	
	
	public static String getModuleUrl (String application, String module) {
		return getModuleUrlRelativeViaParameter(application, module);
//		return getModuleUrlAbsoluteViaSlash(application, module);
	}	
	
	public static String getStereotype (Column column) {
		if (ColumnUtils.hasStereotype(column))
			return getStereotype (column.getStereotype().getStereotype());
		return null;
	}

//   MONEY, DINERO
//   PHOTO, IMAGEN, FOTO, IMAGE
//   MEMO, TEXT_AREA, TEXTO_GRANDE
//   LABEL, ETIQUETA
//   BOLD_LABEL, ETIQUETA_NEGRITA
//   TIME, HORA
//   DATETIME, FECHAHORA
//   IMAGES_GALLERY, GALERIA_IMAGENES (setup instructions)
//   ZEROS_FILLED, RELLENADO_CON_CEROS
//   HTML_TEXT, TEXTO_HTML (text with editable format)
//   IMAGE_LABEL, ETIQUETA_IMAGEN (image depending on property content)
//   EMAIL
//   TELEPHONE, TELEFONO
//   WEBURL
//   IP
//   ISBN
//   CREDIT_CARD, TARJETA_CREDITO
//   EMAIL_LIST, LISTA_EMAIL
//   DOCUMENT_LIBRARY, LIBRERIA_DOCUMENTOS (new in v4m6, it only works inside Liferay)
//   PASSWORD, CONTRASENA (new in v4.1)
	private static String getStereotype(String stereotype) {
		String s = stereotype.toLowerCase();
		if (s.equals("url"))
			return "WEBURL";
		if (s.equals("currency"))
			return "MONEY";
		if (s.equals("datetime"))
			return "DATETIME";
		if (s.equals("text-area"))
			return "TEXT_AREA";
		if (s.equals("text-html"))
			return "HTML_TEXT";
		if (s.equals("email"))
			return "EMAIL";
		if (s.equals("phone"))
			return "TELEPHONE";
		if (s.equals("password"))
			return "PASSWORD";
		if ("picture".equals(s) || "image".equals(s))
			return "IMAGE";
		return stereotype.toUpperCase();
	}

	
	
	/**
	 * @param table
	 * @return
	 */
	public static String getMainView (Table table) {
		// get main view
		// link semantic reference (content type) in short version
		// self semantic reference
		// matrix
		// mandatory param should appear
		// overflow in tab
		// text-html in tab
		return "";
	}
	
	public static String getViewValue(Column column, boolean useTemporal) {
		String var = CommonUtils.getColumnVariableName(column);
		return ConvertUtils.getJavaTypeCastExpression(column, "getView().getValue(\""+var+"\")", useTemporal);
	}

	public static boolean isRelationshipEditable (Reference reference, Database database) {
		List<Table> tables = database.getDataModel().getModel().getBusinessModel().getBusinessPackage().getEntities();
		Table table = TableUtils.getEntityByAlias(tables, reference.getLocalTable().getAlias());
		Column column = ColumnUtils.getColumn(table, reference.getLocalColumnName());
		if (column != null)
			return column.isEditable();
		return true;
	}
	
	public static boolean hasDefaultValueCalculator(Column column) {
		return StringUtils.isNotBlank(getDefaultValueCalculator(column));
	}
	
	public static String getDefaultValueCalculator(Column column) {
		String defValue = column.getDefaultValue();
		if (defValue!=null && StringUtils.isNotBlank(defValue)) {
			StringBuffer sb = new StringBuffer("@org.openxava.annotations.DefaultValueCalculator");
			if (ColumnUtils.isTimeStampColumn(column) && "CURRENT_DATE".equalsIgnoreCase(defValue)) {
				sb.append("(value = org.openxava.calculators.CurrentDateCalculator.class)");
				return sb.toString();
			}
			else if (ColumnUtils.isString(column)) {
				sb.append("(\r\n" + 
						"        value=org.openxava.calculators.StringCalculator.class,\r\n" + 
						"        properties={ @org.openxava.annotations.PropertyValue(name=\"string\", value=\""+defValue+"\") }\r\n" + 
						"    )");
				return sb.toString();
			} else if (ColumnUtils.isBoolean(column)) {
				if ("true".equalsIgnoreCase(defValue)) {
					sb.append("(value = org.openxava.calculators.TrueCalculator.class)");
					return sb.toString();
				} else if ("false".equalsIgnoreCase(defValue)) {
					sb.append("(value = org.openxava.calculators.FalseCalculator.class)");
					return sb.toString();
				} 
			} else if (ColumnUtils.isInteger(column) ) {
				if (StringUtils.isNumeric(defValue)) {
					sb.append("(\r\n" + 
							"        value=org.openxava.calculators.IntegerCalculator.class,\r\n" + 
							"        properties={ @org.openxava.annotations.PropertyValue(name=\"value\", value=\""+defValue+"\") }\r\n" + 
							"    )");
					return sb.toString();
				} else if ("CURRENT_YEAR".equalsIgnoreCase(defValue)) {
					sb.append("(value = org.openxava.calculators.CurrentYearCalculator.class)");
					return sb.toString();
				}
				
			} 
			
		}
		return null;
	}
}
