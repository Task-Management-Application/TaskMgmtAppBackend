package com.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.backend.service.UserService;
import com.backend.service.UserServiceImpl;

import org.modelmapper.ModelMapper;
import org.slf4j.*;

@SpringBootApplication
public class TaskMgmtAppBackendApplication {
	private static final Logger logger = LoggerFactory.getLogger(TaskMgmtAppBackendApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TaskMgmtAppBackendApplication.class, args);
		logger.info("Shayantan Sarkar");
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

}
