package com.coffee.coffeehouse;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.coffee.coffeehouse.Constants;
import com.coffee.coffeehouse.dto.Customer;
import com.coffee.coffeehouse.exception.CoffeeShopException;
import com.coffee.coffeehouse.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
	@InjectMocks
	private CustomerService service;
	
	@Test
	public void addCustomerTest() {
		
		HashSet<Customer> customerList = new HashSet<>();
		// adding null customer
		Customer customer = null;
		try {
			service.addCustomer(customerList, customer);
			fail();
		} catch (CoffeeShopException ex) {
			Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.CUSTOMER_NOT_ADDED);
		}

		customer = new Customer();

		try {
			service.addCustomer(customerList, customer);
			fail();
		} catch (CoffeeShopException ex) {
			Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.CUSTOMER_NOT_ADDED);
		}

		customer.setName("");
		customer.setPhoneNumber("");
		try {
			service.addCustomer(customerList, customer);
			fail();
		} catch (CoffeeShopException ex) {
			Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.CUSTOMER_NOT_ADDED);
		}

		customer.setName("Akhil");
		customer.setPhoneNumber("");
		try {
			service.addCustomer(customerList, customer);
			fail();
		} catch (CoffeeShopException ex) {
			Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.CUSTOMER_NOT_ADDED);
		}

		customer.setName("");
		customer.setPhoneNumber("9880724809");
		try {
			service.addCustomer(customerList, customer);
			fail();
		} catch (CoffeeShopException ex) {
			Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.CUSTOMER_NOT_ADDED);
		}

		customer.setName("Akhil");
		customer.setPhoneNumber("9880724809");
		try {
			assertTrue(service.addCustomer(customerList, customer).equals(Constants.CUSTOMER_ADDED));
		} catch (CoffeeShopException e) {
			// TODO Auto-generated catch block
			fail();
		}

		// adding same customer
		customer.setName("Akhil");
		customer.setPhoneNumber("9880724809");
		try {
			service.addCustomer(customerList, customer);
			fail();
		} catch (CoffeeShopException ex) {
			Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.CUSTOMER_NOT_ADDED);
		}

		// same name different number
		customer.setName("Akhil");
		customer.setPhoneNumber("9880724819");
		try {
			assertTrue(service.addCustomer(customerList, customer).equals(Constants.CUSTOMER_ADDED));
		} catch (CoffeeShopException e) {
			// TODO Auto-generated catch block
		fail();
		}

		// same number different name
		customer.setName("Mittal");
		customer.setPhoneNumber("9880724809");
		try {
			assertTrue(service.addCustomer(customerList, customer).equals(Constants.CUSTOMER_ADDED));
		} catch (CoffeeShopException e) {
			// TODO Auto-generated catch block
			fail();
		}
	}

}
