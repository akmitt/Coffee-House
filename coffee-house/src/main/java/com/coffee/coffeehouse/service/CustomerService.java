package com.coffee.coffeehouse.service;

import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.coffee.coffeehouse.Constants;
import com.coffee.coffeehouse.dto.Customer;
import com.coffee.coffeehouse.exception.CoffeeShopException;




@Service
public class CustomerService {

	public String addCustomer(HashSet<Customer> customerList, Customer customer) throws CoffeeShopException {
		if (customer != null && customer.getName() != null && !customer.getName().isEmpty()
				&& customer.getPhoneNumber() != null && !customer.getPhoneNumber().isEmpty()
				&& customerList.add(customer)) {
			return Constants.CUSTOMER_ADDED;
		} else {
			 throw new CoffeeShopException(Constants.CUSTOMER_NOT_ADDED);
		}
	}

}
