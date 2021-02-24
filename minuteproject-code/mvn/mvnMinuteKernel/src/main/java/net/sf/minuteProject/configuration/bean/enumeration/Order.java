package net.sf.minuteProject.configuration.bean.enumeration;

public enum Order {
   ASC, DESC;
   
	public static Order getOrder(String ordering) {
		for (Order order : Order.values()) {
			if (order.name().toLowerCase().equals(ordering)) {
				return order;
			}
		}
		return null;
	}
}
