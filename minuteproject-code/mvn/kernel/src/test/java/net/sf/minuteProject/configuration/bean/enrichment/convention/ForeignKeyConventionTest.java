package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.ForeignKey;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.utils.ColumnUtils;
import net.sf.minuteProject.utils.FormatUtils;
import net.sf.minuteProject.utils.TableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static net.sf.minuteProject.configuration.bean.model.mock.ModelMock.FIRST_TABLE;
import static net.sf.minuteProject.configuration.bean.model.mock.ModelMock.SECOND_TABLE;
import static net.sf.minuteProject.configuration.bean.model.mock.ModelMock.SECOND_TABLE_ID;
import static net.sf.minuteProject.configuration.bean.model.mock.ModelMock.getBusinessModel;
import static net.sf.minuteProject.configuration.bean.model.mock.ModelMock.getDatabase;
import static org.assertj.core.api.Assertions.assertThat;

public class ForeignKeyConventionTest {

    static class ApplyOnPatternRelevanceArgumentProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of("_ID", FIRST_TABLE, SECOND_TABLE_ID, Boolean.TRUE),
                    Arguments.of("_ID", FIRST_TABLE, "LAST_TABLE_ID", Boolean.FALSE)
            );
        }
    }

    @Nested
    public class ApplyOnPatternRelevanceMethod {

        @ParameterizedTest
        @ArgumentsSource(ApplyOnPatternRelevanceArgumentProvider.class)
        void isConventionToApplyOnPatternRelevance(String columnEnding, String tableName, String columnName, boolean expectedTargetTableName) {
            ForeignKeyConvention foreignKeyConvention = new ForeignKeyConvention();
            final Table t = TableUtils.getTable(getDatabase(), tableName);
            final Column column = ColumnUtils.getColumn(t, columnName);
            final boolean conventionToApplyOnPatternRelevance = foreignKeyConvention.isConventionToApplyOnPatternRelevance(column);

            assertThat(conventionToApplyOnPatternRelevance);
        }

    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ForeignKeyConventionInnerMethods {
        Stream<Object[]> matchTableInput() {
            return Stream.of(
                    new String[] {
                            FIRST_TABLE,
                            SECOND_TABLE,
                            SECOND_TABLE},
                    new Object[] {
                            FIRST_TABLE,
                            SECOND_TABLE,
                            SECOND_TABLE}
            );
        }

        @ParameterizedTest
        @MethodSource("matchTableInput")
        void matchTable(String tableName, String columnName, String expectedTargetTableName) {
            ForeignKeyConvention foreignKeyConvention = new ForeignKeyConvention();
            final Table t = TableUtils.getTable(getDatabase(), tableName);
            final Column column = ColumnUtils.getColumn(t, columnName);
            final List<Table> entities = getBusinessModel().getBusinessPackage().getEntities();
            final Optional<Table> table = foreignKeyConvention.matchTable(column, entities);

            assertThat(table.isPresent());
            assertThat(table.get().getName()).isEqualTo(expectedTargetTableName);
        }

    }
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ForeignKeyConventionInterface {

        Stream<Object[]> provideFkConventionInput() {
            return Stream.of(
                new Object[] {
                        "",
                        "_ID",
                        "",
                        ForeignKeyConvention.AUTODETECT_FOREIGN_KEY_BASED_ON_SIMILARITY_AND_MAP,
                        SECOND_TABLE_ID,
                        SECOND_TABLE},
                new Object[] {
                        "",
                        "_ID",
                        "",
                        ForeignKeyConvention.AUTODETECT_FOREIGN_KEY_BASED_ON_TARGET_PRIMARY_KEY_NAME,
                        SECOND_TABLE,
                        SECOND_TABLE}
            );
        }

        @ParameterizedTest
        @MethodSource("provideFkConventionInput")
        void testEntityFk(String defaultSuffix, String columnEnding, String columnStarting, String type, String expectedColumn, String expectedTargetTable) {
            ForeignKeyConvention foreignKeyConvention = new ForeignKeyConvention();
            foreignKeyConvention.setDefaultSuffix(defaultSuffix);
            foreignKeyConvention.setColumnEnding(columnEnding);

            foreignKeyConvention.setColumnStarting(columnStarting);
            foreignKeyConvention.setType(type);
            foreignKeyConvention.setFieldPatternType("ends_with");

            final BusinessModel businessModel = getBusinessModel();

            foreignKeyConvention.apply(businessModel);

            final List<Table> entities = businessModel.getBusinessPackage().getEntities();
            final Optional<Table> table = TableUtils.getTableByName(entities, FIRST_TABLE);
            assertThat(table.isPresent());
            final ForeignKey[] foreignKeys = table.get().getForeignKeys();
            assertThat(foreignKeys.length).isEqualTo(1);
            assertThat(foreignKeys[0].getReferences()[0].getForeignTableName()).isEqualTo(expectedTargetTable);
            assertThat(foreignKeys[0].getReferences()[0].getLocalColumnName()).isEqualTo(expectedColumn);
        }

    }

}
