package com.example.as.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.as.service.TestService;
import com.example.as.service.TestService1;

@RestController
@RequestMapping("/test")
public class TestingController {

	@Autowired
	private TestService testService;
	@Autowired
	private TestService1 testService1;
	
	@RequestMapping(value = "/greetings", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String Greeting() throws Exception {
		return "Welcome to Spring boot. Arun!!";
		
	}
	
	@RequestMapping(value = "/circuitbreaker", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String testing(@RequestParam(value = "fallback", defaultValue = "false") boolean fallback,
			@RequestParam(value = "fallback1", defaultValue = "false") boolean fallback1,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) throws Exception {
		
		/*System.setProperty("hystrix.command.Test-Service.fallback.isolation.semaphore.maxConcurrentRequests", "1000");
		System.setProperty("hystrix.command.Test-Service.execution.isolation.semaphore.maxConcurrentRequests", "1000");
		System.out.println("ID :"+env.getProperty("hystrix.command.Test-Service.fallback.isolation.semaphore.maxConcurrentRequests"));
	*/	//System.out.println("name :"+tokenConfig.getName());
			String str =testService.getValue(fallback,keyword);
		System.out.println(str);
		testService1.getValue_1(fallback1,false);
		return str;
		
	}
	
	
	
}
