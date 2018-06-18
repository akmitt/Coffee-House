package com.coffee.coffeehouse;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.coffee.coffeehouse.Constants;
import com.coffee.coffeehouse.dto.Coffee;
import com.coffee.coffeehouse.dto.CoffeeOrder;
import com.coffee.coffeehouse.dto.Customer;
import com.coffee.coffeehouse.dto.CustomerReciept;

/**This is the integration test to make sure all the endpoints exposed are responding
 * @author Akhil
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CoffeeHouseControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	HttpHeaders headers = new HttpHeaders();

	@Test
	public void addCustomer() {

		Customer customer = new Customer();
		customer.setName("Akhil");
		customer.setPhoneNumber("9880");

		HttpEntity<Customer> entity = new HttpEntity<Customer>(customer, headers);

		ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + port + "/addCustomer",
				HttpMethod.POST, entity, String.class);

		assertTrue(response.getBody().contains(Constants.CUSTOMER_ADDED));

	}

	@Test
	public void addCoffee() {

		Coffee coffee = new Coffee();
		coffee.setName("Black Cofee");

		HttpEntity<Coffee> entity = new HttpEntity<Coffee>(coffee, headers);

		ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + port + "/addCoffee",
				HttpMethod.POST, entity, String.class);

		assertTrue(response.getBody().contains(Constants.COFFE_ADDED));

	}

	@Test
	public void buyCoffee() {

		CoffeeOrder coffee = new CoffeeOrder();
		coffee.setType("Black Coffee");

		HttpEntity<CoffeeOrder> entity = new HttpEntity<CoffeeOrder>(coffee, headers);

		ResponseEntity<CustomerReciept> response = this.restTemplate.exchange("http://localhost:" + port + "/buyCoffee",
				HttpMethod.POST, entity, CustomerReciept.class);

		assertTrue(response.getStatusCode().equals(HttpStatus.OK));

	}

	@Test
	public void getDailyReport() {

		ResponseEntity<ArrayList> response = this.restTemplate.getForEntity("http://localhost:" + port + "/dailyRecord",
				ArrayList.class);

		assertTrue(response.getStatusCode().equals(HttpStatus.OK));

	}
}
