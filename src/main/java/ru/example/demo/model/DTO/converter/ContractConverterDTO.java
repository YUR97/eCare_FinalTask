package ru.example.demo.model.DTO.converter;

import org.springframework.stereotype.Component;
import ru.example.demo.model.Contract;
import ru.example.demo.model.DTO.ContractDTO;

@Component
public class ContractConverterDTO {

    public ContractDTO convert(Contract contract){
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContract_number(contract.getContract_number());
        contractDTO.setStatus(contract.getStatus());
        return contractDTO;
    }
}
