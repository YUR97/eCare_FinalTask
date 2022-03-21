package ru.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.demo.model.ManagerApartOption;

import java.util.List;

@Repository
public interface ManagerApartOptionRepository extends JpaRepository<ManagerApartOption, Integer> {

    ManagerApartOption findByFirstOptionAndSecondOption(String firstOption, String secondOption);

    List<ManagerApartOption> findByFirstOption(String firstOption);

    List<ManagerApartOption> findBySecondOption(String secondOption);

}
