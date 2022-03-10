package ru.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.demo.model.Tariff;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Integer> {

    Tariff findTariffById(int id);

    Tariff findTariffByName(String name);

}
