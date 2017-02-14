package com.manglu.config.client.proxy;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manglu.config.client.objects.Customer;

@FeignClient(name = "Example-Service-Proxy", url = "localhost:9090/example")
public interface ExampleServiceProxy {

	// THe methods would have the same definition as the actual service
	// implementation
	@RequestMapping(value = "/simple", method = RequestMethod.GET)
	public String convertToUpperCase(@RequestParam("input") String input);

	@RequestMapping(value = "/simpleObject", method = RequestMethod.GET)
	public @ResponseBody Customer returnACustomer(@RequestParam("input") String input);

}
