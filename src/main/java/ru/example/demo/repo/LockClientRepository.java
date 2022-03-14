package ru.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.demo.model.LockClient;

@Repository
public interface LockClientRepository extends JpaRepository<LockClient, String> {

    LockClient findLockClientByEmail(String email);

}
