package com.coffee.coffeehouse;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.coffee.coffeehouse.Constants;
import com.coffee.coffeehouse.dto.Coffee;
import com.coffee.coffeehouse.dto.CoffeeOrder;
import com.coffee.coffeehouse.dto.Customer;
import com.coffee.coffeehouse.dto.CustomerOrder;
import com.coffee.coffeehouse.dto.CustomerReciept;
import com.coffee.coffeehouse.dto.DailyReport;
import com.coffee.coffeehouse.exception.CoffeeShopException;
import com.coffee.coffeehouse.service.CoffeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoffeeServiceTest {

	@InjectMocks
	private CoffeeService service;

	@Test
	public void addCoffeeTest() {

		HashSet<Coffee> coffeeList = new HashSet<>();
		// adding null coffee
		Coffee coffee = null;

		try {
			service.addCofee(coffeeList, coffee);
			fail();
		} catch (CoffeeShopException ex) {
			Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.COFFE_NOT_ADDED);
		}
		coffee = new Coffee();
		try {
			service.addCofee(coffeeList, coffee);
			fail();
		} catch (CoffeeShopException ex) {
			Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.COFFE_NOT_ADDED);
		}

		coffee = new Coffee();
		coffee.setName("");
		try {
			service.addCofee(coffeeList, coffee);
			fail();
		} catch (CoffeeShopException ex) {
			Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.COFFE_NOT_ADDED);
		}

		coffee = new Coffee();
		coffee.setName("Black Coffee");
		coffee.setServingPerDay(10);
		try {
			assertTrue(service.addCofee(coffeeList, coffee).equals(Constants.COFFE_ADDED));
		} catch (CoffeeShopException e) {
			// TODO Auto-generated catch block
			fail();
		}

		// Adding same Coffee
		coffee = new Coffee();
		coffee.setName("Black Coffee");

		try {
			service.addCofee(coffeeList, coffee);
			fail();
		} catch (CoffeeShopException ex) {
			// TODO Auto-generated catch block
			Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.COFFE_NOT_ADDED);
		}

	}

	@Test
	public void getReportTest() {
		HashMap<String, HashMap<String, Integer>> dailyOrderMap = new HashMap<>();
		String date = new SimpleDateFormat(Constants.DATE_FORMAT).format(new Date());
		HashMap<String, Integer> c = new HashMap<>();
		c.put("Black Coffee", 5);
		dailyOrderMap.put(date, c);
		HashSet<Coffee> coffeeList = new HashSet<>();
		Coffee coffee = new Coffee();
		coffee.setName("Black Coffee");
		coffee.setServingPerDay(10);
		coffeeList.add(coffee);
		DailyReport report = new DailyReport();
		report.setCoffeeType("Black Coffee");
		report.setTotalServingPerDay(10);
		report.setTotalServingSold(5);
		List<DailyReport> li = new ArrayList<>();
		li.add(report);
		List<DailyReport> listSize = service.getDailyReport(coffeeList, dailyOrderMap);
		assertTrue(listSize.contains(report));
		assertTrue(listSize.size() == 1);
	}

	/**
	 * To test when customer do not exist
	 */
	@Test
	public void buyCoffeeCustomerDoNotExist() {
		// Daily Order
		HashMap<String, HashMap<String, Integer>> dailyOrderMap = new HashMap<>();
		String date = new SimpleDateFormat(Constants.DATE_FORMAT).format(new Date());
		HashMap<String, Integer> c = new HashMap<>();
		c.put("Black Coffee", 5);
		dailyOrderMap.put(date, c);

		// Master Coffee List
		HashSet<Coffee> coffeeList = new HashSet<>();
		Coffee coffee = new Coffee();
		coffee.setName("Black Coffee");
		coffee.setServingPerDay(10);
		coffeeList.add(coffee);
		// Customer List
		HashSet<Customer> customerList = new HashSet<>();
		Customer customer = new Customer();
		customer.setName("Akhil");
		customer.setPhoneNumber("9880724809");
		customerList.add(customer);
		// Customer Order
		CustomerOrder order = new CustomerOrder();
		Customer customer1 = new Customer();
		customer1.setName("AkhilMittal");
		customer1.setPhoneNumber("9880724809");
		try {
			service.buyCoffee(customerList, coffeeList, dailyOrderMap, order);
			fail();
		} catch (CoffeeShopException e) {
			// TODO Auto-generated catch block
			Assertions.assertThat(e.getMessage()).isEqualTo(Constants.CUSTOMER_DO_NOT_EXIST);
		}

	}

	/**
	 * Test to order when Coffee
	 */
	@Test
	public void buyCoffeeTestWhenCustomerValid() {
		// Daily Order
		HashMap<String, HashMap<String, Integer>> dailyOrderMap = new HashMap<>();
		String date = new SimpleDateFormat(Constants.DATE_FORMAT).format(new Date());
		HashMap<String, Integer> c = new HashMap<>();
		c.put("Black Coffee", 5);
		dailyOrderMap.put(date, c);

		// Master Coffee List
		HashSet<Coffee> coffeeList = new HashSet<>();
		Coffee coffee = new Coffee();
		coffee.setName("Black Coffee");
		coffee.setServingPerDay(10);
		coffeeList.add(coffee);

		// Customer List
		HashSet<Customer> customerList = new HashSet<>();
		Customer customer = new Customer();
		customer.setName("Akhil");
		customer.setPhoneNumber("9880724809");
		customerList.add(customer);

		// Customer Order Coffee when stock is there
		CustomerOrder order = new CustomerOrder();
		Customer customer1 = new Customer();
		customer1.setName("Akhil");
		customer1.setPhoneNumber("9880724809");
		order.setCustomerDetails(customer1);
		CoffeeOrder coffeeOrder = new CoffeeOrder();
		coffeeOrder.setQuantity(4);
		coffeeOrder.setType("Black Coffee");
		List<CoffeeOrder> list = new ArrayList<>();
		list.add(coffeeOrder);
		order.setCoffeeDetails(list);

		try {
			CustomerReciept recipet = service.buyCoffee(customerList, coffeeList, dailyOrderMap, order);
			assertTrue(recipet.getOrder().contains(4 + Constants.QUANTITY_ORDERED + "Black Coffee"));

		} catch (CoffeeShopException e) {
			fail();
		}

		// Customer Order Coffee is more than Stock available and limited
		// quantity is ordered

		CoffeeOrder coffeeOrder1 = new CoffeeOrder();
		coffeeOrder1.setQuantity(5);
		coffeeOrder1.setType("Black Coffee");
		List<CoffeeOrder> list1 = new ArrayList<>();
		list1.add(coffeeOrder1);
		order.setCoffeeDetails(list1);

		try {
			CustomerReciept recipet = service.buyCoffee(customerList, coffeeList, dailyOrderMap, order);
			assertTrue(recipet.getOrder().contains("Only " + 1 + Constants.QUANTITY_ORDERED + "Black Coffee"));

		} catch (CoffeeShopException e) {
			fail();
		}

		// Customer Order Coffee is more than Stock available

		CoffeeOrder coffeeOrder2 = new CoffeeOrder();
		coffeeOrder2.setQuantity(10);
		coffeeOrder2.setType("Black Coffee");
		List<CoffeeOrder> list2 = new ArrayList<>();
		list2.add(coffeeOrder2);
		order.setCoffeeDetails(list2);

		try {
			CustomerReciept recipet = service.buyCoffee(customerList, coffeeList, dailyOrderMap, order);
			assertTrue(recipet.getOrder().contains(Constants.STOCK_OVER + "Black Coffee"));

		} catch (CoffeeShopException e) {
			fail();
		}

		// When invalid COffee is ordered

		CoffeeOrder coffeeOrder3 = new CoffeeOrder();
		coffeeOrder3.setQuantity(10);
		coffeeOrder3.setType("White Coffee");
		List<CoffeeOrder> list3 = new ArrayList<>();
		list3.add(coffeeOrder3);
		order.setCoffeeDetails(list3);

		try {
			CustomerReciept recipet = service.buyCoffee(customerList, coffeeList, dailyOrderMap, order);
			assertTrue(recipet.getOrder().contains("White Coffee" + Constants.COFFE_NOT_AVAILABLE));

		} catch (CoffeeShopException e) {
			fail();
		}

	}
}
