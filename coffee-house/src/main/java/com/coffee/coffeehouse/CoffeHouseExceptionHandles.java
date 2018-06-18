package com.coffee.coffeehouse;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.coffee.coffeehouse.dto.ErrorDetails;
import com.coffee.coffeehouse.exception.CoffeeShopException;



/**This is the class to handle all custom exceptions
 * @author Akhil
 *
 */
@ControllerAdvice
public class CoffeHouseExceptionHandles extends ResponseEntityExceptionHandler {
public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date d= new Date();
	    String strDate = sdfDate.format(d);
	    return strDate;
	}
   //other exception handlers
  
   @ExceptionHandler(CoffeeShopException.class)
   protected ResponseEntity<Object> handleCustomerNotFound(
		   CoffeeShopException ex) {
	   ErrorDetails error = new ErrorDetails();
       error.setMessage(ex.getMessage());
       error.setTimestamp(getCurrentTimeStamp());
       return new ResponseEntity < > (error, HttpStatus.OK);
   }
   

}
