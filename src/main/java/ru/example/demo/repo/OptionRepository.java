package ru.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.demo.model.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {

    Option findByName(String name);

}
