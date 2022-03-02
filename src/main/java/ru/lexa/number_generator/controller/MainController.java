package ru.lexa.number_generator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.number_generator.service.NumberService;

@RestController
@RequestMapping("/number")
public class MainController {
	@Autowired
	private static NumberService numberService;

	@GetMapping("/next")
	public String nextNumber() {
		return numberService.getNextNumber().toString();
	}

	@GetMapping("/random")
	public String randomNumber() {
		return "";
	}
}
