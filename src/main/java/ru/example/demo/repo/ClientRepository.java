package ru.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.demo.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findClientById(int id);

    Client findClientByEmail(String email);

}
