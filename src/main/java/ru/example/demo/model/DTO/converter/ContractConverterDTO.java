package ru.example.demo.model.DTO.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.demo.model.Contract;
import ru.example.demo.model.DTO.ContractDTO;
import ru.example.demo.model.DTO.OptionDTO;
import ru.example.demo.model.DTO.TariffDTO;
import ru.example.demo.model.Option;

@Component
public class ContractConverterDTO {

    private OptionConverterDTO optionConverterDTO;

    @Autowired
    public ContractConverterDTO(OptionConverterDTO optionConverterDTO) {
        this.optionConverterDTO = optionConverterDTO;
    }

    public ContractDTO convert(Contract contract){
        ContractDTO contractDTO = new ContractDTO();
        TariffDTO tariffDTO = new TariffDTO(contract.getTariff().getName(),contract.getTariff().getPayment());
        for (Option option:contract.getTariff().getOptions()) {
            tariffDTO.addOptionDTO(optionConverterDTO.convert(option));
        }
        contractDTO.setContractNumber(contract.getContractNumber());
        contractDTO.setStatus(contract.getStatus());
        for (Option option:contract.getOptions()) {
            OptionDTO optionDTO = new OptionDTO(option.getName(),option.getPayment(),option.getConnectionPrice());
            contractDTO.addOptionDTO(optionDTO);
        }
        contractDTO.setTariffDTO(tariffDTO);
        return contractDTO;
    }
}
