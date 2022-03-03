package ru.lexa.number_generator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lexa.number_generator.model.CarNumber;

public interface NumberRepository extends CrudRepository<CarNumber, Long> {
	CarNumber findCarNumberByLettersAndNumbers(String letters, int numbers);
}
