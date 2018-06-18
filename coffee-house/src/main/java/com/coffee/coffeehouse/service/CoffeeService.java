package com.coffee.coffeehouse.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coffee.coffeehouse.Constants;
import com.coffee.coffeehouse.dto.Coffee;
import com.coffee.coffeehouse.dto.Customer;
import com.coffee.coffeehouse.dto.CustomerOrder;
import com.coffee.coffeehouse.dto.CustomerReciept;
import com.coffee.coffeehouse.dto.DailyReport;
import com.coffee.coffeehouse.exception.CoffeeShopException;

@Service
public class CoffeeService {
	public String addCofee(HashSet<Coffee> coffeeList, Coffee coffee) throws CoffeeShopException {

		if (coffee != null && coffee.getName() != null && !coffee.getName().isEmpty() && coffeeList.add(coffee)) {
			return Constants.COFFE_ADDED;
		} else {

			throw new CoffeeShopException(Constants.COFFE_NOT_ADDED);
		}
	}

	/**This method return the list of transaction done for current date
	 * @param coffeeList
	 * @param dailyOrderMap
	 * @return
	 */
	public List<DailyReport> getDailyReport(HashSet<Coffee> coffeeList,
			HashMap<String, HashMap<String, Integer>> dailyOrderMap) {
		String date = new SimpleDateFormat(Constants.DATE_FORMAT).format(new Date());

		List<DailyReport> list = new ArrayList<>();
		DailyReport report = null;
		for (Coffee coffee : coffeeList) {
			report = new DailyReport();
			report.setCoffeeType(coffee.getName());
			report.setTotalServingPerDay(coffee.getServingPerDay());
			report.setTotalServingSold(
					dailyOrderMap.containsKey(date) ? (dailyOrderMap.get(date).containsKey(coffee.getName())
							? dailyOrderMap.get(date).get(coffee.getName()) : 0) : 0);

			list.add(report);
		}
		return list;

	}
	
	public CustomerReciept buyCoffee(HashSet<Customer> customerList, HashSet<Coffee> coffeeList,
			HashMap<String, HashMap<String, Integer>> dailyOrderMap, CustomerOrder order) throws CoffeeShopException {
		// TODO Auto-generated method stub
		CustomerReciept reciept = new CustomerReciept();

		String date = new SimpleDateFormat(Constants.DATE_FORMAT).format(new Date());

		List<String> values = new ArrayList<>();
		if (!customerList.contains(order.getCustomerDetails())) {
			throw new CoffeeShopException(Constants.CUSTOMER_DO_NOT_EXIST);
		} else {
			reciept.setName(order.getCustomerDetails().getName());
			reciept.setPhoneNumber(order.getCustomerDetails().getPhoneNumber());
			
			reciept.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

			order.getCoffeeDetails().forEach(orderedCoffee -> {

				Optional<Coffee> c = coffeeList.stream()
						.filter(d -> d.getName().equalsIgnoreCase(orderedCoffee.getType())).findAny();
				if (!c.isPresent()) {
					values.add(orderedCoffee.getType() + Constants.COFFE_NOT_AVAILABLE);
				} else {
					// if no entry means its the first order of the day
					if (!dailyOrderMap.containsKey(date)) {

						HashMap<String, Integer> coffee = new HashMap<>();
						// if ordered quantity is more than serving per day
						if (orderedCoffee.getQuantity() > c.get().getServingPerDay()) {
							coffee.put(orderedCoffee.getType(), c.get().getServingPerDay());
							values.add("Only " + c.get().getServingPerDay() + Constants.QUANTITY_ORDERED
									+ orderedCoffee.getType());
						} else {
							coffee.put(orderedCoffee.getType(), orderedCoffee.getQuantity());
							values.add(
									orderedCoffee.getQuantity() + Constants.QUANTITY_ORDERED + orderedCoffee.getType());
						}
						dailyOrderMap.put(date, coffee);

					} else {
						// scenario where coffee is already ordered for same day
						HashMap<String, Integer> coffeeType = dailyOrderMap.get(date);
						// check if same coffe is ordered before
						if (coffeeType.containsKey(orderedCoffee.getType())) {
							// find existing orders placed for the day
							int existingOrder = coffeeType.get(orderedCoffee.getType());
							if (existingOrder == c.get().getServingPerDay()) {
								values.add(Constants.STOCK_OVER + orderedCoffee.getType());
							} else if (existingOrder + orderedCoffee.getQuantity() > c.get().getServingPerDay()) {

								coffeeType.put(orderedCoffee.getType(), c.get().getServingPerDay());
								values.add("Only " + (c.get().getServingPerDay() - existingOrder)
										+ Constants.QUANTITY_ORDERED + orderedCoffee.getType());
							} else {
								coffeeType.put(orderedCoffee.getType(), orderedCoffee.getQuantity() + existingOrder);
								values.add(orderedCoffee.getQuantity() + Constants.QUANTITY_ORDERED
										+ orderedCoffee.getType());
							}

						} else {
							coffeeType.put(orderedCoffee.getType(), orderedCoffee.getQuantity());
							values.add(
									orderedCoffee.getQuantity() + Constants.QUANTITY_ORDERED + orderedCoffee.getType());
						}

					}

				}

			});
			reciept.setOrder(values);
		}
		return reciept;
	}

}
