package ru.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demo.model.Client;
import ru.example.demo.model.DTO.ClientDTO;
import ru.example.demo.model.DTO.converter.ClientConverterDTO;
import ru.example.demo.repo.ClientRepository;
import ru.example.demo.repo.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    private RoleRepository roleRepository;
    private ClientConverterDTO clientConverterDTO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, RoleRepository roleRepository, ClientConverterDTO clientConverterDTO, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.clientConverterDTO = clientConverterDTO;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ClientDTO getById(int id) {
        Client client = clientRepository.findClientById(id);
        return clientConverterDTO.convert(client);
    }

    @Transactional
    public ClientDTO getByEmail(String email) {
        Client client = clientRepository.findClientByEmail(email);
        return clientConverterDTO.convert(client);
    }

    @Transactional
    public List<ClientDTO> getAll() {
        List<ClientDTO> clientDTOS = new ArrayList<>();
        for (Client client : clientRepository.findAll()) {
            clientDTOS.add(clientConverterDTO.convert(client));
        }
        return clientDTOS;
    }

    @Transactional
    public void signUp(ClientDTO clientDTO, String role){
        Client clientToSave = new Client();
        clientToSave.setEmail(clientDTO.getEmail());
        clientToSave.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
        clientToSave.setRole(roleRepository.findRoleByName(role));
        clientToSave.setName(clientDTO.getName());
        clientToSave.setSurname(clientDTO.getSurname());
        clientRepository.save(clientToSave);
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    public void update(int id, Client client) {
        Client clientToUpdate = clientRepository.findClientById(id);
        clientToUpdate.setName(client.getName());
        clientToUpdate.setSurname(client.getSurname());
        clientRepository.save(clientToUpdate);
    }

    public void delete(int id) {
        clientRepository.delete(clientRepository.findClientById(id));
    }

}
