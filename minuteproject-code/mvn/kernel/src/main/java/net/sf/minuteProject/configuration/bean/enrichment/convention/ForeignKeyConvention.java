package net.sf.minuteProject.configuration.bean.enrichment.convention;

import lombok.Data;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.enrichment.Entity;
import net.sf.minuteProject.configuration.bean.enrichment.Field;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ForeignKeyUtils;
import net.sf.minuteProject.utils.TableUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class ForeignKeyConvention extends ModelConvention {

	public static final String AUTODETECT_FOREIGN_KEY_BASED_ON_TARGET_PRIMARY_KEY_NAME = "autodetect-foreign-key-based-on-target-primary-key-name";
//	public static final String APPLY_DEFAULT_FK_BY_ENTITY_NAME_AND_SUFFIX = "apply-default-foreign-key-by-entity-name-and-suffix";
	public static final String AUTODETECT_FOREIGN_KEY_BASED_ON_SIMILARITY_AND_MAP = "autodetect-foreign-key-based-on-similarity-and-map";
	public static final String AUTODETECT_SELF_REFERENCE_FOREIGN_KEY_BASED_ON_COLUMN_NAME = "autodetect-self-reference-foreign-key-based-on-column-name";

	public String defaultSuffix, columnEnding, columnStarting;
	private String fieldPatternType;

	@Override
	public void apply(BusinessModel model) {
		if (AUTODETECT_FOREIGN_KEY_BASED_ON_SIMILARITY_AND_MAP.equals(type)) {
			if (model.getBusinessPackage() != null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					applyEntitySimilarity(table);
				}				
			}
		}
		else if (AUTODETECT_FOREIGN_KEY_BASED_ON_TARGET_PRIMARY_KEY_NAME.equals(type)) {
			if (model.getBusinessPackage() != null) {
				List<Table> entities = model.getBusinessPackage().getEntities();
				for (Table table : model.getBusinessPackage().getEntities()) {
					applyFieldSimilarity(table, entities);
				}
			}
		}
		else if (AUTODETECT_SELF_REFERENCE_FOREIGN_KEY_BASED_ON_COLUMN_NAME.equals(type)) {
			if (model.getBusinessPackage() != null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					applySelfReferenceForeignKey(table);
				}		
			}
		}
	}

	private void applySelfReferenceForeignKey(Table table) {
		for (Column column : table.getAttributes()) {
			if (matchSelfReferenceCriteria(column)) {
				ForeignKeyUtils.setForeignKey(table, getSelfReferenceField(column));
			}
		}
	}

	private boolean matchSelfReferenceCriteria(Column column) {
		boolean match = true;
		if (columnEnding!=null) 
			match = net.sf.minuteProject.utils.StringUtils.endsWithIgnoreCase(column.getName(), columnEnding);
		if (columnStarting!=null) 
			match = net.sf.minuteProject.utils.StringUtils.startsWithIgnoreCase(column.getName(), columnStarting);
		return match;
	}

	private Field getSelfReferenceField(Column column) {
		Field field = new Field();
		field.setName(column.getName());
		Entity entity = new Entity();
		entity.setName(column.getTable().getName());
		field.setEntity(entity);
		field.setLinkToTargetEntity(column.getTable().getName());
		field.setLinkToTargetField(TableUtils.getPrimaryFirstColumn(column.getTable()).getName());
		return field;
	}

	private void applyEntitySimilarity(Table table) {
		for (Field field : getForeignKeyFieldsNotInSelfReferencedPrimaryKey(table)){
			ForeignKeyUtils.setForeignKey(table, field);
		}
		
	}

	protected void applyFieldSimilarity(Table table, List<Table> entities) {
		for (Column column : table.getColumns()) { //to be replaced by table.getAttributes()
			Optional<Table> target = matchTable(column, entities);
			if (target.isPresent()) {
				Field field = getForeignKeyField(column, table, target.get());
				ForeignKeyUtils.setForeignKey(table, field);
			}
		}
	}

	protected Optional<Table> matchTable(Column column, List<Table> entities) {
		return entities.stream()
				.filter(t -> column.getName().equalsIgnoreCase(t.getName()))
				.findFirst();
	}

	private List<Field> getForeignKeyFieldsNotInSelfReferencedPrimaryKey(Table table) {
		List<Field> list = new ArrayList<>();
		for (Column column : table.getColumns()) {
			if (isConventionToApply(column)) { // 
				Field f = getForeignKeyField(column, table);
				if (f != null) {
					list.add(f);
				}
			}
		}
		return list;
	}

	private boolean isSelfReferencePrimaryKey(Column column) {
		if (column.isPrimaryKey()) {
			Table target = getTarget(column);
			if (target!=null && 
				 target.getName().toLowerCase().equals(column.getTable().getName().toLowerCase()))
				return true;
		}
		return false;
	}

	
	private Table getTarget(Column column) {
		Table table = column.getTable();
		String tablename = getTargetEntityNameLowerCase(column);
		Table target = TableUtils.getEntity(table.getDatabase(), tablename);
		if (target == null) {
			target = TableUtils.getTableFromAlias(table.getDatabase(), tablename);
		}
		return target;
	}

	private Field getForeignKeyField(Column column, Table table) {
		// if (column.isPrimaryKey() && !table.isManyToMany()) return null;
//		String tablename = getTargetEntityName(column);
//		Table target = TableUtils.getTable(table.getDatabase(), tablename);
//		if (target == null)
//			target = TableUtils.getTableFromAlias(table.getDatabase(), tablename);
		Table target = getTarget(column);
		return getForeignKeyField(column, table, target);
	}

	private Field getForeignKeyField(Column column, Table table, Table target) {
		if (target != null) {
			Field f = new Field();
			f.setName(column.getName());
			f.setLinkToTargetEntity(target.getName());
			f.setLinkToTargetField(TableUtils.getPrimaryKey(target));
			Entity entity = new Entity();
			entity.setName(table.getName());
			f.setEntity(entity);
			f.setBidirectional("true");
			return f;
		}
		return null;
	}

	private String getTargetEntityNameLowerCase(Column column) {
		String key = column.getName().toLowerCase();
		if (columnEnding != null && !"".equals(columnEnding))
			key = StringUtils.stripEnd(key, columnEnding.toLowerCase());
		if (columnStarting != null && !"".equals(columnStarting))
			key = StringUtils.stripStart(key, columnStarting.toLowerCase());
		return key;
	}

	private boolean isConventionToApply(Column column) {
		if (isSelfReferencePrimaryKey(column)) return false;
		return isConventionToApplyOnPatternRelevance(column);
	}
	
	protected boolean isConventionToApplyOnPatternRelevance(Column column) {
		if ((columnEnding == null || "".equals(columnEnding)) &&
			(columnStarting == null || "".equals(columnStarting))) return false;
		String key = getTargetEntityNameLowerCase(column);
		if (key.equals(column.getName().toLowerCase())) {
			return false;
		}
		Table target = getTarget(column);
		if (target==null) 
			return false;
		String targetType = TableUtils.getPrimaryKeyType(target);
		if (!targetType.equals(column.getType()))
			return false;
		return true;
	}
}
