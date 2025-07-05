package com.vaadin.C3Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.vaadin.flow.component.page.AppShellConfigurator;

@SpringBootApplication
public class C3Application extends SpringBootServletInitializer implements AppShellConfigurator  {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SpringApplication.run(C3Application.class, args);
	}

}
