package ru.lexa.number_generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lexa.number_generator.model.CarNumber;
import ru.lexa.number_generator.repository.NumberRepository;

import java.util.Random;

@Service
public class NumberService {
	@Autowired
	private NumberRepository numberRepository;

	private CarNumber lastNum = new CarNumber("AAA", 0);

	private String letters = "ABEKMHOPCTYX";

	public NumberService() {
	}

	public void setNumberRepository(NumberRepository numberRepository) {
		this.numberRepository = numberRepository;
	}

	public CarNumber getNextNumber() {
		char[] newLetters = lastNum.getLetters().toCharArray();
		int newNumbers = lastNum.getNumbers() + 1;

		//if all numbers are used
		if (lastNum.equals(new CarNumber("XXX", 999)))
			return null;

		//if num is *999** 116 RUS
		if (newNumbers == 1000) {
			newNumbers = 1;
			for (int i = 2; i >= 0; i--) {
				if (newLetters[i] == 'X' && i != 0) {
					newLetters[i] = 'A';
				}
				else {
					newLetters[i] = letters.charAt(letters.indexOf(newLetters[i] + 1));
					break;
				}
			}
		}

		//update last number
		lastNum = new CarNumber(new String(newLetters), newNumbers);

		//check that new number doesn't exist in DB
		if (numberRepository.findCarNumberByLettersAndNumbers(lastNum.getLetters(), lastNum.getNumbers()) != null)
			lastNum = getNextNumber();

		//add new num to DB
		numberRepository.save(lastNum);
		return lastNum;
	}

	public CarNumber getRandomNumber() {
		CarNumber newNumber;
		StringBuilder numLetters = new StringBuilder();

		for (int i = 0; i < 3; i++) {
			numLetters.append(letters.charAt(new Random().nextInt(letters.length())));
		}
		int nums = (new Random().nextInt(999)) + 1;

		newNumber = new CarNumber(numLetters.toString(), nums);

		if (numberRepository.findCarNumberByLettersAndNumbers(newNumber.getLetters(), newNumber.getNumbers()) != null)
			newNumber = getRandomNumber();

		numberRepository.save(newNumber);
		return newNumber;
	}
}
