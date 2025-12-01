package net.sf.minuteProject.configuration.bean.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum Order {

	ASC("ascending"), DESC("descending");

	private final String value;
   
	public static Order getOrder(String ordering) {
		for (Order order : Order.values()) {
			if (order.name().toLowerCase().equals(ordering)) {
				return order;
			}
		}
		return null;
	}

}
