package ru.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.demo.model.ManagerTogetherOption;

@Repository
public interface ManagerTogetherOptionRepository extends JpaRepository<ManagerTogetherOption, Integer> {
}
