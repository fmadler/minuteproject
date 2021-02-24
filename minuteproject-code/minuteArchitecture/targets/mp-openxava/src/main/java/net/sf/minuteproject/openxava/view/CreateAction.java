package net.sf.minuteproject.openxava.view;

public class CreateAction extends org.openxava.actions.NewAction{

	public void execute() throws Exception {
		getView().setViewName("Create");
		super.execute();
	}
}
