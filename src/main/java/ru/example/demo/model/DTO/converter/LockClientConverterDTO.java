package ru.example.demo.model.DTO.converter;

import org.springframework.stereotype.Component;
import ru.example.demo.model.DTO.LockClientDTO;
import ru.example.demo.model.LockClient;

@Component
public class LockClientConverterDTO {

    public LockClientDTO convert(LockClient lockClient) {
        return new LockClientDTO(lockClient.getEmail());
    }

}
