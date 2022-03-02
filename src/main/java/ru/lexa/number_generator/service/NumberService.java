package ru.lexa.number_generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lexa.number_generator.model.CarNumber;
import ru.lexa.number_generator.repository.NumberRepository;

@Service
public class NumberService {
	@Autowired
	private NumberRepository numberRepository;

	private CarNumber lastNum = new CarNumber("AAA", 1);

	private String letters = "ABEKMHOPCTYX";

	public NumberService() {
	}

	public void setNumberRepository(NumberRepository numberRepository) {
		this.numberRepository = numberRepository;
	}

	public CarNumber getNextNumber() {
		System.out.println(lastNum.toString());
		char[] newLetters = lastNum.getLetters().toCharArray();
		int newNumbers = lastNum.getNumbers() == 999 ? 1 : lastNum.getNumbers() + 1;

		if (lastNum.equals(new CarNumber("XXX", 999)))
			return null;

		if (newNumbers == 1) {
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

		return new CarNumber(new String(newLetters), newNumbers);
	}
}
