package net.sf.minuteproject.openxava.view;

import java.util.*;

public class UpdateAction extends org.openxava.actions.SearchByViewKeyAction{

	public void execute() throws Exception {
        Map mapIndexValues = getView().getKeyValuesWithValue();   //1
        getView().setViewName("Update");                          //2
        getView().setValues(mapIndexValues);    		
		super.execute();
	}
}
