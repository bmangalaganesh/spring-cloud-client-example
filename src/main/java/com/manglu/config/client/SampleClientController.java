package com.manglu.config.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.manglu.config.client.objects.Customer;
import com.manglu.config.client.proxy.ExampleServiceProxy;

@RestController
@RequestMapping("/view-config")
@EnableFeignClients 
//TODO Where should the enable Feign Clients annotation be?
//Should it be done at each caller or should we doing this at the App level?

public class SampleClientController {

	@Value("${some.param.value}")
	private String paramValue;
	
	
	private static final Logger logger = LoggerFactory.getLogger(SampleClientController.class);
	
	@Autowired
	ExampleServiceProxy exampleServiceProxy;

	/**
	 * Reads the config and writes them to the sysout logs
	 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void printConfiguration() {

		logger.info("Configuration info that is read is as follows:");
		logger.info("Paramvalue (some.param.value) is:" + paramValue);

	}
		
	
	//TODO - Need an example where objects are passed across micro-services..
	//E.g CustomerInfo is passed across two different micro-services

	/**
	 * Invokes a down-stream service using the Feign Client (proxy) instead of RestTemplate
	 */
	@RequestMapping(value = "/getcustomer", method = RequestMethod.GET)
	public @ResponseBody Customer giveMeACustomerUsingInputAsName(@RequestParam("name") String customerName){
		
		logger.info("Calling an interface which returns a Customer Object which is different (pacakge wise - to what we use in this client");
		
		Customer aCustomer = exampleServiceProxy.returnACustomer(customerName);
		
		logger.info("Customer returned by the example service is:" + aCustomer.getFirstName());
		logger.info("Customer returned by the example service is:" + aCustomer.getLastName());
		logger.info("Customer returned by the example service is:" + aCustomer.getEmail());
		
		return aCustomer;
		
		
	}
}
