package ru.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.example.demo.model.Contract;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {

    @Query("from Contract where contractNumber=:contractNumber")
    Contract findContractByContractNumber(@Param("contractNumber") String contractNumber);

    @Query("from Contract order by contractNumber")
    List<Contract> findAll();

}
