package ru.example.demo.model.DTO.converter;

import org.springframework.stereotype.Component;
import ru.example.demo.model.DTO.ManagerOptionDTO;
import ru.example.demo.model.ManagerApartOption;
import ru.example.demo.model.ManagerTogetherOption;

@Component
public class ManagerOptionConverterDTO {

    public ManagerOptionDTO convert(ManagerTogetherOption managerTogetherOption) {
        ManagerOptionDTO managerOptionDTO = new ManagerOptionDTO();
        managerOptionDTO.addTogether(managerTogetherOption.getFirstOption(), managerTogetherOption.getSecondOption());
        return managerOptionDTO;
    }

    public ManagerOptionDTO convert(ManagerApartOption managerApartOption) {
        ManagerOptionDTO managerOptionDTO = new ManagerOptionDTO();
        managerOptionDTO.addApart(managerApartOption.getFirstOption(),managerApartOption.getSecondOption());
        return managerOptionDTO;
    }

}
