package ru.example.demo.model.DTO.converter;

import org.springframework.stereotype.Component;
import ru.example.demo.model.DTO.RoleDTO;
import ru.example.demo.model.Role;

@Component
public class RoleConverterDTO {

    public RoleDTO convert(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        role.setName(role.getName());
        return roleDTO;
    }

}