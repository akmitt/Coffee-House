package com.coffee.coffeehouse.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * @author Akhil
 * This is the  POJO class which holds customer order
 */
public class CustomerOrder {

	@NotNull
	
	private Customer customerDetails;
	@NotNull

	private List<CoffeeOrder> coffeeDetails;
	/**
	 * @return the customerDetails
	 */
	public Customer getCustomerDetails() {
		return customerDetails;
	}
	/**
	 * @param customerDetails the customerDetails to set
	 */
	public void setCustomerDetails(Customer customerDetails) {
		this.customerDetails = customerDetails;
	}
	
	/**
	 * @return the coffeeDetails
	 */
	public List<CoffeeOrder> getCoffeeDetails() {
		return coffeeDetails;
	}
	/**
	 * @param coffeeDetails the coffeeDetails to set
	 */
	public void setCoffeeDetails(List<CoffeeOrder> coffeeDetails) {
		this.coffeeDetails = coffeeDetails;
	}
	

}
