package net.sf.minuteproject.fitnesse.fixture;

public abstract class DbQueryFixture extends DbCRUDFixture{

	protected void check (int row, int column, String actual) {
		if (actual!=null){
			if (actual.equals(getText(row, column)))
				right(row, column);
			else
				wrong(row, column, actual);
		} else {
			if (getText(row, column).equals(FixtureCode.NOT_PRESENT))
				right(row, column);
			else
				wrong (row, column, FixtureCode.NOT_PRESENT);
		}
	}

	protected void checkCount (String actual) {
		check (OUPUT_GENERAL_ROW_INDEX, ROW_COUNT_COLUMN_INDEX, actual);
	}

	protected void checkResultSet () {
		int rowToCheck = getRowToCheck(ROW_VALUE_INDEX);
		System.out.println("rowToCheck "+rowToCheck);
		for (int i = 1; i <= rowToCheck; i++) {
			checkResultSetRow (i+OUPUT_GENERAL_ROW_INDEX);
		}		
	}
	
	protected void checkResultSetRow (int row) {
		int length = getColumnIndex().size();
		for (int i = 1; i <= length; i++) {
			check (row, i, getResultSetCell(row, i));
		}		
	}

}
