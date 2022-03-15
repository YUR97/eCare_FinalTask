package ru.example.demo.model.DTO.converter;

import org.springframework.stereotype.Component;
import ru.example.demo.model.DTO.OptionDTO;
import ru.example.demo.model.Option;

@Component
public class OptionConverterDTO {

    public OptionDTO convert(Option option){
        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName(option.getName());
        optionDTO.setPayment(option.getPayment());
        optionDTO.setConnectionPrice(option.getConnectionPrice());
        return optionDTO;
    }

}
