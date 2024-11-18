package com.validate.monorepo.authservice.service;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserNameGeneratorService {
	
	private final Faker faker;
	
	@Autowired
	public UserNameGeneratorService(Faker faker) {
		this.faker = faker;
	}
	
	public String generateUsername() {
		String adjective = faker.commerce().color();
		String animal = faker.animal().name();
		int number = faker.number().numberBetween(100, 999);
		
		String username = adjective + "-" + animal + "-" + number;
		
		return username.replace(' ', '-');
	}
	
}
