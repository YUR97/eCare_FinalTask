package ru.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demo.model.Client;
import ru.example.demo.model.DTO.ClientDTO;
import ru.example.demo.model.DTO.ContractDTO;
import ru.example.demo.model.DTO.converter.ClientConverterDTO;
import ru.example.demo.repo.ClientRepository;
import ru.example.demo.repo.ContractRepository;
import ru.example.demo.repo.RoleRepository;
import ru.example.demo.repo.TariffRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ClientService {

    private ContractRepository contractRepository;
    private TariffRepository tariffRepository;
    private ClientRepository clientRepository;
    private RoleRepository roleRepository;
    private ClientConverterDTO clientConverterDTO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ContractRepository contractRepository, TariffRepository tariffRepository, ClientRepository clientRepository,
                         RoleRepository roleRepository, ClientConverterDTO clientConverterDTO, PasswordEncoder passwordEncoder) {
        this.contractRepository = contractRepository;
        this.tariffRepository = tariffRepository;
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
    public void signUp(ClientDTO clientDTO, String role) {
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

    @Transactional
    public void update(Client client) {
        Client clientToUpdate = clientRepository.findClientByEmail(client.getEmail());
        clientToUpdate.setName(client.getName());
        clientToUpdate.setSurname(client.getSurname());
        clientToUpdate.setBirthday(client.getBirthday());
        clientToUpdate.setAddress(client.getAddress());
        clientToUpdate.setPassport(client.getPassport());
        clientToUpdate.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(clientToUpdate);
    }

    @Transactional
    public void setTariff(UserDetails user, String nameTariff) {
        Client client = clientRepository.findClientByEmail(user.getUsername());
        ClientDTO clientDTO = clientConverterDTO.convert(client);
        Set<ContractDTO> contractDTO = clientDTO.getContractsDTO();
        for (ContractDTO contract : contractDTO) {
            contractRepository.findContractByContract_number(contract.getContract_number())
                    .setTariff(tariffRepository.findTariffByName(nameTariff));
        }
    }

    public void delete(int id) {
        clientRepository.delete(clientRepository.findClientById(id));
    }
}
