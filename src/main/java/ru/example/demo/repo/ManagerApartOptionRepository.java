package ru.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.demo.model.ManagerApartOption;

@Repository
public interface ManagerApartOptionRepository extends JpaRepository<ManagerApartOption, Integer> {
}
