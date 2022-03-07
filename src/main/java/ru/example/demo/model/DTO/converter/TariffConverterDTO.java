package ru.example.demo.model.DTO.converter;

import org.springframework.stereotype.Component;
import ru.example.demo.model.DTO.OptionDTO;
import ru.example.demo.model.DTO.TariffDTO;
import ru.example.demo.model.Option;
import ru.example.demo.model.Tariff;

@Component
public class TariffConverterDTO {

    public TariffDTO convert(Tariff tariff){
        TariffDTO tariffDTO = new TariffDTO();

        for (Option option : tariff.getOptions()) {
            OptionDTO optionDTO = new OptionDTO(option.getName(),option.getPayment(),option.getConnection_price());
            tariffDTO.addOptionDTO(optionDTO);
        }
        tariffDTO.setName(tariff.getName());
        tariffDTO.setPayment(tariff.getPayment());
        return tariffDTO;
    }

}
