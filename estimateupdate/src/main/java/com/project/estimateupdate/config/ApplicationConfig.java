package com.project.estimateupdate.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.AbstractApplicationContext;

@Configuration
@ComponentScan("com.project.estimateupdate.*")
@Import(ConnectionConfig.class)
public class ApplicationConfig {

	public static AbstractApplicationContext context;
}

