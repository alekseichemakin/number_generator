package ru.lexa.number_generator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lexa.number_generator.model.CarNumber;

import java.util.List;

public interface NumberRepository extends CrudRepository<CarNumber, Long> {
	List<CarNumber> findAllByLetters(String letters);
}
