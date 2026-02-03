package net.sf.minuteProject.utils;

import org.apache.log4j.Logger;

import net.sf.minuteProject.configuration.bean.enrichment.Field;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.ForeignKeyDDLUtils;

public class ForeignKeyUtils {

	private static Logger logger = Logger.getLogger(ForeignKeyUtils.class);

	public static void setForeignKey (Table table, Field field) {
		//field.getEntity().getEnrichment().getBusinessModel().getModel().getDataModel().getDatabase();
		ForeignKey foreignKey = getForeignKey(field, table.getDatabase());
		if (foreignKey!=null) {
			table.setForeignKey (foreignKey);	
			table.convertColumnToRelation(field.getName());
		}
	}
	
	public static ForeignKey getForeignKey (Field field, Database database) {
		String linkToTargetEntity = field.getLinkToTargetEntity();
		if (linkToTargetEntity!=null && field.getLinkToTargetField()!=null) {
			Reference reference = ReferenceUtils.getReference(field, database);
			if (reference!=null) {
				org.apache.ddlutils.model.ForeignKey foreignKeyMp = getForeignKey_(field,database);

				ForeignKey foreignKey = new ForeignKeyDDLUtils (foreignKeyMp, TableUtils.getEntity(database, linkToTargetEntity));
				foreignKey.setForeignTableName(linkToTargetEntity);
				foreignKey.setReference (reference);
				// bidirection
				if (field.getBidirectional()!=null && field.getBidirectional().equals("false"))
					foreignKey.setBidirectional(false);
				return foreignKey;
			}
			logger.info("no correct fk found for "+field.getEntity().getName()+" - "+field.getName()+" - pointing towards "+linkToTargetEntity+" - "+field.getLinkToTargetField());
		}
		return null;
	}
	
	public static org.apache.ddlutils.model.ForeignKey getForeignKey_(String field, Database database) {
		org.apache.ddlutils.model.ForeignKey foreignKeyMp = new org.apache.ddlutils.model.ForeignKey();
		foreignKeyMp.setName(field);
		return foreignKeyMp;
	}
	public static org.apache.ddlutils.model.ForeignKey getForeignKey_(Field field, Database database) {
		org.apache.ddlutils.model.ForeignKey foreignKeyMp = getForeignKey_(field.getName(),database);
		//TODO complete with other field
		return foreignKeyMp;
	}



	public static boolean containsLocalColumn(ForeignKey fk, Column column) {
		for (Reference ref : fk.getReferences()) {
			if (ref.getLocalColumnName().equals(column.getName()))
				return true;
		}
		return false;
	}
}
