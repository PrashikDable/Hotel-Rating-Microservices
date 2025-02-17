package com.micro.userService.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {

	@Bean
	@LoadBalanced //Used When Host and Port are Removed from URL. 
	public RestTemplate restemplate() {
		return new RestTemplate();
	}
}
