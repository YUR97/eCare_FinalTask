package ru.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demo.model.DTO.ManagerOptionDTO;
import ru.example.demo.model.DTO.converter.ManagerOptionConverterDTO;
import ru.example.demo.repo.ManagerApartOptionRepository;
import ru.example.demo.repo.ManagerTogetherOptionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerOptionService {

    private ManagerTogetherOptionRepository managerTogetherOptionRepository;
    private ManagerApartOptionRepository managerApartOptionRepository;
    private ManagerOptionConverterDTO managerOptionConverterDTO;

    @Autowired
    public ManagerOptionService(ManagerTogetherOptionRepository managerTogetherOptionRepository, ManagerApartOptionRepository managerApartOptionRepository,
                                ManagerOptionConverterDTO managerOptionConverterDTO) {
        this.managerTogetherOptionRepository = managerTogetherOptionRepository;
        this.managerApartOptionRepository = managerApartOptionRepository;
        this.managerOptionConverterDTO = managerOptionConverterDTO;
    }

    @Transactional
    public List<String[]> getAllOptionsTogether() {
        List<ManagerOptionDTO> together = managerTogetherOptionRepository.findAll().stream().map(o -> managerOptionConverterDTO.convert(o)).toList();
        List<String[]> togetherList = new ArrayList<>();
        for (ManagerOptionDTO managerOptionDTO : together) {
            togetherList.add(managerOptionDTO.getOptionsTogether().get(0));
        }
        return togetherList;
    }

    @Transactional
    public List<String[]> getAllOptionsApart() {
        List<ManagerOptionDTO> apart = managerApartOptionRepository.findAll().stream().map(o -> managerOptionConverterDTO.convert(o)).toList();
        List<String[]> apartList = new ArrayList<>();
        for (ManagerOptionDTO managerOptionDTO : apart) {
            apartList.add(managerOptionDTO.getOptionsApart().get(0));
        }
        return apartList;
    }

}
