package com.coffee.coffeehouse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coffee.coffeehouse.dto.Coffee;
import com.coffee.coffeehouse.dto.Customer;
import com.coffee.coffeehouse.dto.CustomerOrder;
import com.coffee.coffeehouse.dto.CustomerReciept;
import com.coffee.coffeehouse.dto.DailyReport;
import com.coffee.coffeehouse.exception.CoffeeShopException;
import com.coffee.coffeehouse.service.CoffeeService;
import com.coffee.coffeehouse.service.CustomerService;

import io.swagger.annotations.ApiOperation;

/**
 * @author Akhil This is the controller which handles all the incoming requests
 */
@RestController

public class CoffeeHouseController {

	@Autowired
	CustomerService customerService;
	@Autowired
	CoffeeService coffeeService;

	// Unique list of coffee whenever a new coffee is added
	private HashSet<Coffee> coffeeList = new HashSet<>();

	// Unique list of customer whenever a new customer is added
	private HashSet<Customer> customerList = new HashSet<>();

	// This map keeps a track of daily order for coffee. First HashMap key is
	// dd/mm/yyy -- which signifies uniques key for one day
	// Nested HashMap holds the Coffee Type and Number of coffee sold for that
	// day
	private HashMap<String, HashMap<String, Integer>> dailyOrderMap = new HashMap<>();

	/**
	 * This method is used to add a customer with unique combination of id and
	 * phone number
	 * 
	 * @param customer
	 * @return
	 * @throws CoffeeShopException
	 */
	@ApiOperation(value= "To add new customer with unique combination of name and phone number")
	@PostMapping("/addCustomer")
	public String addCustomer(@Valid @RequestBody Customer customer, Errors errors) throws CoffeeShopException {
		if (errors.hasErrors()) {
			throw new CoffeeShopException("Field Validation Error");
		}
		return customerService.addCustomer(customerList, customer);
	}

	/**
	 * This method adds the new Coffee Type with serving per day
	 * 
	 * @param coffee
	 * @return
	 * @throws CoffeeShopException
	 */
	@ApiOperation(value= "To add  new Coffee Type")
	@PostMapping("/addCoffee")
	public String addCoffee(@Valid @RequestBody Coffee coffee, Errors errors) throws CoffeeShopException {
		if (errors.hasErrors()) {
			throw new CoffeeShopException("Field Validation Error");
		}
		return coffeeService.addCofee(coffeeList, coffee);

	}

	/**This method is used to place order
	 * @param order
	 * @param errors
	 * @return
	 * @throws CoffeeShopException
	 */
	@ApiOperation(value= "To add  odrer a new Coffee")
	@PostMapping("/buyCoffee")
	public CustomerReciept buyCoffee(@Valid @RequestBody CustomerOrder order, Errors errors)
			throws CoffeeShopException {
		if (errors.hasErrors()) {
			throw new CoffeeShopException("Field Validation Error Customer details are invalid");
		}
		return coffeeService.buyCoffee(customerList, coffeeList, dailyOrderMap, order);

	}

	/**This methods picks the current date and give report 
	 * @return
	 */
	@ApiOperation(value= "To  get order details based on current day")
	@GetMapping("/dailyRecord")
	public List<DailyReport> getDailyRecord() {
		return coffeeService.getDailyReport(coffeeList, dailyOrderMap);

	}

}
