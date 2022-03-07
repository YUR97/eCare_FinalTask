package ru.example.demo.model.DTO.converter;

import org.springframework.stereotype.Component;
import ru.example.demo.model.Client;
import ru.example.demo.model.Contract;
import ru.example.demo.model.DTO.ClientDTO;
import ru.example.demo.model.DTO.ContractDTO;
import ru.example.demo.model.DTO.RoleDTO;

@Component
public class ClientConverterDTO {

    public ClientDTO convert(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        RoleDTO roleDTO = new RoleDTO(client.getRole().getName());
        for (Contract contract : client.getContracts()) {
            ContractDTO contractDTO = new ContractDTO(contract.getContract_number(), contract.getStatus());
            clientDTO.addContractDTO(contractDTO);
        }
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setSurname(client.getSurname());
        clientDTO.setBirthday(client.getBirthday());
        clientDTO.setPassport(client.getPassport());
        clientDTO.setAddress(client.getAddress());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPassword(client.getPassword());
        clientDTO.setRoleDTO(roleDTO);
        return clientDTO;
    }

}
