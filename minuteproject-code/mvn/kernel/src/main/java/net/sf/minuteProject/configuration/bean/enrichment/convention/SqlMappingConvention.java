package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.ConvertUtils;

public class SqlMappingConvention extends ModelConvention {

	
	private static final String VARCHAR2_USED_INSTEAD_OF_VARCHAR = "varchar2-used-instead-of-varchar";
	private static final String CHAR2_USED_INSTEAD_OF_CHAR = "char2-used-instead-of-char";

	@Override
	public void apply(BusinessModel model) {
		if (VARCHAR2_USED_INSTEAD_OF_VARCHAR.equals(type)) {
			for (Table table: model.getBusinessPackage().getEntities()) {
				applyChar2Used(table);
			}
		}
		if (CHAR2_USED_INSTEAD_OF_CHAR.equals(type)) {
			for (Table table: model.getBusinessPackage().getEntities()) {
				applyChar2Used(table);
			}
		}
	}

	private void applyChar2Used(Table table) {
		for (Column column : table.getColumns()) {
			if (column.getType().equals(ConvertUtils.DB_STRING_TYPE)) {
//				column.setType(ConvertUtils.DB_STRING2_TYPE);
				getSize(column);
			}
			if (column.getType().equals(ConvertUtils.DB_STRING_CHAR_TYPE)) {
//				column.setType(ConvertUtils.DB_STRING2_CHAR_TYPE);
				getSize(column);
			}

		}
	}

	private void getSize(Column column) {
		int size = column.getSizeAsInt();
		int sizeChar2 = size/4;
		column.setSize(sizeChar2+"");
	}

}
