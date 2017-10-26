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
public class TestService1 {
	//ResourceBundle rs = ResourceBundle.getBundle("testService");
	
	
	@HystrixCommand(fallbackMethod = "getFallbackValue_1",commandKey = "Test-Service1")
	public String getValue_1(boolean fallback,boolean close) throws Exception{
		System.out.println("getValue_1:Original method.."+(new SimpleDateFormat("mm:ss")).format(new Date(System.currentTimeMillis())));
		if(fallback) {
			Thread.sleep(300);
			throw new Exception("getValue_1 : Falling back based on input");
		}
		return "getValue_1 Actual results";
		
	}
	
	public String getFallbackValue_1(boolean falback,boolean close) throws Exception{
		System.out.println("getValue_1:falback method.."+(new SimpleDateFormat("mm:ss")).format(new Date(System.currentTimeMillis())));

		return "getValue_1 fallback results";
	}
	
	
}
