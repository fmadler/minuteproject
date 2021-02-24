package net.sf.minuteproject.fitnesse.fixture;

import java.io.File;

import scriptella.execution.EtlExecutor;
import scriptella.execution.EtlExecutorException;
import fit.ColumnFixture;

public class ScriptellaDbSetupFixture extends ColumnFixture {
	
	public static String DEFAULT_RESET_FILE_RELATIVE_PATH = "etl.xml";
	public String resetfilepath;
	
	public boolean setup () throws EtlExecutorException {
		EtlExecutor etlExecutor = EtlExecutor.newExecutor(new File(getResetfilepath()));
		etlExecutor.execute();
		return true;
	}

	public String getResetfilepath() {
		if (resetfilepath==null)
			resetfilepath = DEFAULT_RESET_FILE_RELATIVE_PATH;
		return resetfilepath;
	}

	public void setResetfilepath(String resetfilepath) {
		this.resetfilepath = resetfilepath;
	}

}
