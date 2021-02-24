package net.sf.minuteProject.architecture.holder;

import java.util.ArrayList;
import java.util.List;

public class AbstractHolder <E> {
	
	private List<E> messages;
	
	public void add(E e) {
		if (messages!=null)
			getMessages().add(e);
	}

	public List<E> getMessages() {
		if (messages==null)
			messages = new ArrayList<E>();
		return messages;
	}

	public boolean isEmpty() {
		return getMessages().isEmpty();
	}
}
