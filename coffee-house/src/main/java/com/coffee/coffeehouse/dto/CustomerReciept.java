package com.coffee.coffeehouse.dto;

import java.util.ArrayList;
import java.util.List;

public class CustomerReciept extends Customer {

	

	private String time;

	List<String> order = new ArrayList<>();

	public List<String> getOrder() {
		return order;
	}

	public void setOrder(List<String> order) {
		this.order = order;
	}



	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	private String orderDate;

}
