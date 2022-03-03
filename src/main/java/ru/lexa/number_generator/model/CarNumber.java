package ru.lexa.number_generator.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class CarNumber {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	private String letters;
	private int numbers;
	private int region = 116;
	private String country = "RUS";

	public CarNumber(String letters, int numbers) {
		this.letters = letters;
		this.numbers = numbers;
	}

	public CarNumber() {
	}

	public int getRegion() {
		return region;
	}

	public void setRegion(int region) {
		this.region = region;
	}

	public String getLetters() {
		return letters;
	}

	public void setLetters(String letters) {
		this.letters = letters;
	}

	public int getNumbers() {
		return numbers;
	}

	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String num = String.format("%03d", numbers);
		return letters.charAt(0) + num + letters.charAt(1) + letters.charAt(2) + " " + region + " " + country;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CarNumber carNumber = (CarNumber) o;
		return region == carNumber.region && numbers == carNumber.numbers && Objects.equals(country, carNumber.country) && Objects.equals(letters, carNumber.letters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(region, country, letters, numbers);
	}
}
