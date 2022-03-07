package ru.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.demo.model.DTO.RoleDTO;
import ru.example.demo.model.DTO.converter.RoleConverterDTO;
import ru.example.demo.model.Role;
import ru.example.demo.repo.RoleRepository;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    private RoleConverterDTO roleConverterDTO;

    @Autowired
    public RoleService(RoleRepository roleRepository, RoleConverterDTO roleConverterDTO) {
        this.roleRepository = roleRepository;
        this.roleConverterDTO = roleConverterDTO;
    }

    public RoleDTO getRoleDTOByName(String name) {
        return roleConverterDTO.convert(roleRepository.findRoleByName(name));
    }

    public Role getRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
