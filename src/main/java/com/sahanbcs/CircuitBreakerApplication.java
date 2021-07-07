package com.sahanbcs;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@EnableCircuitBreaker
public class CircuitBreakerApplication {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/")
	public String getApiResponce(){
		return restTemplate.getForObject("https://reqres.in/api/users?page=2",String.class);
	}

	@GetMapping("/api")
	@HystrixCommand(fallbackMethod = "apiFallBack")
	public String getApiNoResponce(){
		return restTemplate.getForObject("https://www.nourifound.com/",String.class);
//		return restTemplate.getForObject("https://reqres.in/api/users?page=2",String.class);
	}

	public String apiFallBack(){
		return "Api Is Not Working Try Again Later";
	}

	public static void main(String[] args) {
		SpringApplication.run(CircuitBreakerApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
