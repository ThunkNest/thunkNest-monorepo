package com.validate.monorepo.reputationservice.controller;

import com.validate.monorepo.reputationservice.service.ReputationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/top")
public class Top5Controller {
	
	private final ReputationService reputationService;
	
	public Top5Controller(ReputationService reputationService) {
		this.reputationService = reputationService;
	}
	
	@GetMapping("/")
	public List<User> getTopFiveOfTheDay() {
		return reputationService.getTop5UsersLast24HoursFromCache();
	}
	
}
