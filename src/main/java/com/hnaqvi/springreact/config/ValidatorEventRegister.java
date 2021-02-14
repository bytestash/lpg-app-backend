package com.hnaqvi.springreact.config;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.validation.Validator;

/**
 * workaround to solve the event discovery bug
 */
@Configuration
public class ValidatorEventRegister implements InitializingBean {

	@Autowired
	ValidatingRepositoryEventListener validatingRepositoryEventListener;

	@Autowired
	private Map<String, Validator> validators;

	@Override
	public void afterPropertiesSet() throws Exception {
		validators.entrySet().stream()
				.filter(entry -> entry.getKey().startsWith("beforeCreate"))
				.forEach(entry -> validatingRepositoryEventListener.addValidator("beforeCreate", entry.getValue()));

	}
}