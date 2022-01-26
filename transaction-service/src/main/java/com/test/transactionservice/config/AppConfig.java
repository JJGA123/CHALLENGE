package com.test.transactionservice.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public GroupedOpenApi publicApi() {
	      return GroupedOpenApi.builder()
	              .group("springshop-public")
	              .pathsToMatch("/public/**")
	              .build();
	 }

}
