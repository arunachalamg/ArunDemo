package com.example.as.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

import com.example.as.TestConstants;
import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class TestService {
	//ResourceBundle rs = ResourceBundle.getBundle("testService");
	int count=0;
	
	@HystrixCommand(fallbackMethod = "getFallbackValue",commandKey = "Test-Service")
	public String getValue(boolean fallback,String val) throws Exception{
		String time = (new SimpleDateFormat("mm:ss")).format(new Date(System.currentTimeMillis()));
		System.out.println((count ++)+" : Original method.."+time);
		Thread.sleep(200);
		if(fallback) {
			System.out.println("processesd..");
			throw new Exception("Falling back based on input");
		}
			
		return "Actual results : "+val;
		
	}
	
	public String getFallbackValue(boolean falback,String keyword) throws Exception{
		String time = (new SimpleDateFormat("mm:ss")).format(new Date(System.currentTimeMillis()));
		System.out.println(count +" : falback method.."+time);

		return "falback :"+keyword;
	}
	
	
}
