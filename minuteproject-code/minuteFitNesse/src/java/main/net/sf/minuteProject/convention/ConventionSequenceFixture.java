package net.sf.minuteProject.convention;

import java.util.List;

import net.sf.minuteProject.configuration.bean.enrichment.SemanticReference;
import net.sf.minuteProject.configuration.bean.enrichment.convention.SemanticReferenceConvention;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.model.NavigateModelSequenceFixture;
import net.sf.minuteProject.plugin.openxava.OpenXavaUtils;
import net.sf.minuteProject.utils.enrichment.SemanticReferenceUtils;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class ConventionSequenceFixture extends NavigateModelSequenceFixture {


//	semantic reference columns and order of table name
//	reset semantic reference for all tables
	public static String semanticReferenceColumnsAndOrderOfTableName(String table, String values) {
		Table t = getEntity(table);
		String semRefs = SemanticReferenceUtils.getSemanticReferenceListAsString(t);
		return compareListWithOrder(ParserUtils.getList(semRefs), ParserUtils.getList(values));
	}
//	add semref conv with field pattern type and field pattern and max fields
	public static boolean getResetSemanticReferenceForAllTables() {
		for (Table table : getEntities ()) {
			table.setSemanticReference(new SemanticReference());
		}
		return true;
	}
//	add semref conv with field pattern type and field pattern and max fields
	public static void addSemrefConvWithFieldPatternTypeAndFieldPatternAndMaxFields(String fieldPatternType, String fieldPattern, int maxNumberOfFields)  {
		SemanticReferenceConvention src = new SemanticReferenceConvention();
		src.setFieldPatternType(fieldPatternType);
		src.setFieldPattern(fieldPattern);
		src.setMaxNumberOfFields(maxNumberOfFields);
		src.apply(getModel().getBusinessModel());
	}
//	semantic reference columns and order of table name
//	semantic reference columns and order as child table of table name
	public static String semanticReferenceColumnsAndOrderAsChildTableOfTableName(String child, String parent, String values) {
		List<String> l1 = OpenXavaUtils.getListProperties(getChildReference(child, parent));
		return compareListWithOrder(l1, ParserUtils.getList(values));
	}
	
}
