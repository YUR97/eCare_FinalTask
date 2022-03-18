package ru.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demo.model.DTO.LockClientDTO;
import ru.example.demo.model.DTO.converter.LockClientConverterDTO;
import ru.example.demo.model.LockClient;
import ru.example.demo.repo.LockClientRepository;

@Service
public class LockClientService {

    private LockClientRepository lockClientRepository;
    private LockClientConverterDTO lockClientConverterDTO;

    @Autowired
    public LockClientService(LockClientRepository lockClientRepository, LockClientConverterDTO lockClientConverterDTO) {
        this.lockClientRepository = lockClientRepository;
        this.lockClientConverterDTO = lockClientConverterDTO;
    }

    @Transactional
    public LockClientDTO getLockedClient(String email) {
        if (lockClientRepository.findLockClientByEmail(email) != null) {
            return lockClientConverterDTO.convert(lockClientRepository.findLockClientByEmail(email));
        }
        return null;
    }

    @Transactional
    public void unlockClient(String email) {
        LockClient lockClient = lockClientRepository.findLockClientByEmail(email);
        if (lockClient != null) {
            lockClientRepository.delete(lockClient);
        }
    }

    @Transactional
    public void lockClient(String email) {
        lockClientRepository.save(new LockClient(email));
    }

}
