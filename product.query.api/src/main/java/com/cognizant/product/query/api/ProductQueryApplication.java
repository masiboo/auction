package com.cognizant.product.query.api;

import com.cognizant.core.configuration.AxonConfig;
import com.cognizant.core.configuration.AppMessageQueueConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ AxonConfig.class,  AppMessageQueueConfig.class })
public class ProductQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductQueryApplication.class, args);
	}

}
