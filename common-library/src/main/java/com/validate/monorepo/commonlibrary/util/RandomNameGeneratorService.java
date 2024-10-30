package com.validate.monorepo.commonlibrary.util;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

@Service
public class RandomNameGeneratorService {
	
	private final Faker faker;
	
	public RandomNameGeneratorService(Faker faker) {
		this.faker = faker;
	}
	
	public String generateUsername() {
		String adjective = faker.commerce().color();
		String animal = faker.animal().name();
		int number = faker.number().numberBetween(100, 999);
		
		return adjective + "-" + animal + "-" + number;
	}
	
}
