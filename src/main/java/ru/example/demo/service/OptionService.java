package ru.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demo.constants.Constant;
import ru.example.demo.model.DTO.OptionDTO;
import ru.example.demo.model.DTO.converter.OptionConverterDTO;
import ru.example.demo.model.Option;
import ru.example.demo.repo.OptionRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OptionService {

    OptionRepository optionRepository;
    OptionConverterDTO optionConverterDTO;

    public OptionService(OptionRepository optionRepository, OptionConverterDTO optionConverterDTO) {
        this.optionRepository = optionRepository;
        this.optionConverterDTO = optionConverterDTO;
    }

    @Transactional
    public List<OptionDTO> getAll() {
        List<OptionDTO> optionDTOS = new ArrayList<>();
        for (Option option : optionRepository.findAll()) {
            optionDTOS.add(optionConverterDTO.convert(option));
        }
        return optionDTOS;
    }

    @Transactional
    public boolean saveOption(Option option) {
        boolean mayBeSave;
        if (!(option.getName().contains(Constant.NOTHING) | option.getPayment().contains(Constant.NOTHING) | option.getConnectionPrice().contains(Constant.NOTHING))) {
            if (optionRepository.findByName(option.getName()) == null) {
                optionRepository.save(option);
                mayBeSave = true;
            } else {
                mayBeSave = false;
            }
        } else {
            mayBeSave = false;
        }
        return mayBeSave;
    }

    @Transactional
    public void deleteOption(String optionName) {
        optionRepository.deleteById(optionRepository.findByName(optionName).getId());
    }

}
