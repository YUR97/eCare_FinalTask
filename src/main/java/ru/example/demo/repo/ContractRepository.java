package ru.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.demo.model.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {
}
