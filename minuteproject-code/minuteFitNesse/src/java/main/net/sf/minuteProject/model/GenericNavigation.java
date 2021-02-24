package net.sf.minuteProject.model;

import java.util.List;

import net.sf.minuteProject.configuration.bean.GeneratorBean;

public class GenericNavigation <P, C extends GeneratorBean> {

	public String asNameStringList(P p) {
		StringBuffer sb = new StringBuffer();
		int cpt=0;
//		int size = p.getChildren().size();
//		for (C c : p.getChildren()) {
//			sb.append(c.getName());
//			if (cpt<size) {
//				sb.append(",");
//				cpt++;
//			}
//		}
		return sb.toString();
	}
}
