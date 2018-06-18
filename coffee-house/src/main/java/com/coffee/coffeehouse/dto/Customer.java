package com.coffee.coffeehouse.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Customer {
	@NotNull
	@NotEmpty
	private String customerName;
	@NotNull
	@NotEmpty
	private String phoneNumber;

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return customerName;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode() + ((phoneNumber == null) ? 0 : phoneNumber.hashCode()));
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null || !(obj instanceof Customer)) {
			return false;
		}
		final Customer customer = (Customer) obj;
		if (customer.customerName.equalsIgnoreCase(this.getName()) && customer.getPhoneNumber().equalsIgnoreCase(this.getPhoneNumber())) {
			return true;
		} else {
			return false;
		}

	}

}
