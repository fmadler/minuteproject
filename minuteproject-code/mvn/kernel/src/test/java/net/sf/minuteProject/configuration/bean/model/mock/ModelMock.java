package net.sf.minuteProject.configuration.bean.model.mock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.BusinessPackage;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.ColumnDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.DatabaseDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.TableDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.UMLNotation.ColumnUMLNotation;
import net.sf.minuteProject.configuration.bean.model.data.impl.UMLNotation.TableUMLNotation;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelMock {
    
    public static final String FIRST_TABLE = "FIRST_TABLE";
    public static final String SECOND_TABLE = "SECOND_TABLE";
    public static final String FIRST_TABLE_ID = "FIRST_TABLE_ID";
    public static final String SECOND_TABLE_ID = "SECOND_TABLE_ID";

    @Data
    @Builder
    private static class DBEntity {
        private String name;
        private List<DBColumn> columns;
    }
    @Data
    @Builder
    private static class DBColumn {
        private String name;
        private String jdbcTypeName;
        private boolean isPk;
    }

    private static DBColumn getDBColumn (String name, String jdbcTypeName, boolean isPk) {
        return DBColumn.builder().name(name).jdbcTypeName(jdbcTypeName).isPk(isPk).build();
    }

    private static DBColumn getPk() {
        return getDBColumn("ID", "INTEGER", true);
    }
    private static DBColumn getName() {
        return getDBColumn("NAME", "VARCHAR", false);
    }
    private static DBColumn getSecondTableColumn() {
        return getDBColumn(SECOND_TABLE, "INTEGER", false);
    }
    private static DBColumn getSecondTableIdColumn() {
        return getDBColumn(SECOND_TABLE_ID, "INTEGER", false);
    }

    private static DBEntity getFirstTable() {
        return DBEntity.builder().name(FIRST_TABLE).columns(Arrays.asList(getPk(),getName(),getSecondTableColumn(), getSecondTableIdColumn())).build();
    }
    private static DBEntity getSecondTable() {
        return DBEntity.builder().name(SECOND_TABLE).columns(Arrays.asList(getPk(),getName())).build();
    }

    public static Database getDatabase() {
        org.apache.ddlutils.model.Database database = new org.apache.ddlutils.model.Database();
        database.addTable(getDdlTable(getFirstTable()));
        database.addTable(getDdlTable(getSecondTable()));
        Database db = new DatabaseDDLUtils(database);
        db.getDataModel();
        return db;
    }

    public static BusinessModel getBusinessModel() {
        BusinessModel businessModel = new BusinessModel();
        businessModel.setBusinessPackage(getBusinessPackage());
        return businessModel;
    }

    public static BusinessPackage getBusinessPackage() {
        BusinessPackage businessPackage = new BusinessPackage();
        businessPackage.setName("table");
        businessPackage.getEntities().add(getTable(FIRST_TABLE));
        businessPackage.getEntities().add(getTable(SECOND_TABLE));
        return businessPackage;
    }

    public static Table getTable(String name) {
        DBEntity dbEntity = DBEntity.builder().name("TODO").columns(new ArrayList<>()).build();
        if (FIRST_TABLE.equals(name)) {
            dbEntity = getFirstTable();
        } else if (SECOND_TABLE.equals(name)) {
            dbEntity = getSecondTable();
        }
        final org.apache.ddlutils.model.Table ta = getDdlTable(dbEntity);
        final TableDDLUtils tableDdl = new TableDDLUtils(ta);
        Table table = new TableUMLNotation(tableDdl);
        table.setDatabase(getDatabase());
        return table;
    }

    static org.apache.ddlutils.model.Table getDdlTable(DBEntity dbEntity){
        final org.apache.ddlutils.model.Table ta = new org.apache.ddlutils.model.Table();
        ta.setName(dbEntity.getName());
        ta.setType("TABLE");
        dbEntity.getColumns().stream().forEach(
                c -> ta.addColumn(getColumn(c))
        );
        return ta;
    }

    public static org.apache.ddlutils.model.Column getColumn(DBColumn dbColumn) {
        final org.apache.ddlutils.model.Column col = new org.apache.ddlutils.model.Column();
        col.setName(dbColumn.name);
        col.setType(dbColumn.jdbcTypeName);
        col.setPrimaryKey(dbColumn.isPk);
        return col;
    }
}
