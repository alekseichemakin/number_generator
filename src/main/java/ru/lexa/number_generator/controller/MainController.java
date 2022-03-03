package ru.lexa.number_generator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.number_generator.model.CarNumber;
import ru.lexa.number_generator.service.NumberService;

@RestController
@RequestMapping("/number")
public class MainController {

	private NumberService numberService;

	@Autowired
	public MainController(NumberService numberService) {
		this.numberService = numberService;
	}

	@GetMapping("/next")
	public String nextNumber() {
		CarNumber carNumber = numberService.getNextNumber();
		if (carNumber == null)
			return "The numbers are over";
		return carNumber.toString();
	}

	@GetMapping("/random")
	public String randomNumber() {
		CarNumber carNumber = numberService.getRandomNumber();
		if (carNumber == null)
			return "The numbers are over";
		return carNumber.toString();
	}
}
