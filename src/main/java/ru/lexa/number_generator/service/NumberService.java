package ru.lexa.number_generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexa.number_generator.model.CarNumber;
import ru.lexa.number_generator.repository.NumberRepository;

import java.util.Random;

@Service
public class NumberService {
	@Autowired
	private NumberRepository numberRepository;

	private final static String LETTERS = "ABEKMHOPCTYX"; //all possible LETTERS
	private final static long MAX_NUM_COMBINATIONS = 10 * 10 * 10 * LETTERS.length() * LETTERS.length() * LETTERS.length(); //combination of numbers (0-9) and numbers of letters

	public CarNumber getNextNumber() {
		if (getTableSize() == MAX_NUM_COMBINATIONS) //if all numbers are used
			return null;

		CarNumber lastNum = findFirstByOrderByIdDesc(); //get last number from db

		if (lastNum == null) //if in table no data
			lastNum = new CarNumber("AAA", -1);

		lastNum = generateNext(lastNum.getLetters(), lastNum.getNumbers()); //update last number

		save(lastNum); //add new num to DB
		return lastNum;
	}

	public CarNumber getRandomNumber() {
		if (getTableSize() == MAX_NUM_COMBINATIONS) //if all numbers are used
			return null;

		CarNumber newNumber;
		StringBuilder numLetters = new StringBuilder();

		for (int i = 0; i < 3; i++) //create random letters
			numLetters.append(LETTERS.charAt(new Random().nextInt(LETTERS.length())));

		int nums = (new Random().nextInt(1000)); //create random nums

		newNumber = new CarNumber(numLetters.toString(), nums);


		if (findCarNumberByLettersAndNumbers(newNumber.getLetters(), newNumber.getNumbers()) != null) //check that new number doesn't exist in DB
			newNumber = getRandomNumber();


		save(newNumber); //add new num to DB
		return newNumber;
	}

	private CarNumber generateNext(String strLetters, int newNumbers) {
		char[] newLetters = strLetters.toCharArray();
		CarNumber nextCarNumber;

//		do {
			newNumbers++;
			if (newNumbers == 1000 && new String(newLetters).equals("XXX")) { //if num is X99XX 116 RUS
				newNumbers = 0;
				newLetters = "AAA".toCharArray();
			}
			if (newNumbers == 1000) { //if num is *999** 116 RUS
				newNumbers = 0;
				for (int i = 2; i >= 0; i--) {
					if (newLetters[i] == 'X' && i != 0) {
						newLetters[i] = 'A';
					} else {
						newLetters[i] = LETTERS.charAt(LETTERS.indexOf(newLetters[i]) + 1);
						break;
					}
				}
			}
//		} while (findCarNumberByLettersAndNumbers(new String(newLetters), newNumbers) != null); //check that new number doesn't exist in DB

		nextCarNumber = new CarNumber(new String(newLetters), newNumbers);

		if (findCarNumberByLettersAndNumbers(new String(newLetters), newNumbers) != null)
			nextCarNumber = generateNext(new String(newLetters), newNumbers);

		return nextCarNumber;
	}

	@Transactional
	public void save(CarNumber carNumber) {
		numberRepository.save(carNumber);
	}

	@Transactional
	public CarNumber findCarNumberByLettersAndNumbers(String letters, int numbers) {
		return numberRepository.findCarNumberByLettersAndNumbers(letters, numbers);
	}

	@Transactional
	public CarNumber findFirstByOrderByIdDesc() {
		return numberRepository.findFirstByOrderByIdDesc();
	}

	@Transactional
	public Long getTableSize() {
		return numberRepository.getTableSize();
	}
}
