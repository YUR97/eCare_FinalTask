package ru.example.demo.model.DTO.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.demo.model.Contract;
import ru.example.demo.model.DTO.ContractDTO;
import ru.example.demo.model.DTO.OptionDTO;
import ru.example.demo.model.Option;
import ru.example.demo.model.Tariff;

@Component
public class OptionConverterDTO {

    private TariffConverterDTO tariffConverterDTO;

    @Autowired
    public OptionConverterDTO(TariffConverterDTO tariffConverterDTO) {
        this.tariffConverterDTO = tariffConverterDTO;
    }

    public OptionDTO convert(Option option) {
        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName(option.getName());
        optionDTO.setPayment(option.getPayment());
        optionDTO.setConnectionPrice(option.getConnectionPrice());
        for (Tariff tariff : option.getTariffs()) {
            optionDTO.addTariffDTO(tariffConverterDTO.convert(tariff));
        }
        for (Contract contract : option.getContracts()) {
            ContractDTO contractDTO = new ContractDTO();
            contractDTO.setContractNumber(contract.getContractNumber());
            optionDTO.addContractDTO(contractDTO);
        }
        return optionDTO;
    }

}
