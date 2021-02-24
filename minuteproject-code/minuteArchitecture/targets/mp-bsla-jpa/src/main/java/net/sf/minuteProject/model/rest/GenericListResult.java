package net.sf.minuteProject.model.rest;

import java.io.Serializable;
import java.util.List;

public class GenericListResult<T> extends GenericResult implements Serializable {

	private static final long serialVersionUID = -7913858705337233587L;

	private Integer total;
	private List<T> data;

	public int getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}