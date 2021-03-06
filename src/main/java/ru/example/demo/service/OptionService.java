package ru.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demo.constants.Constant;
import ru.example.demo.model.DTO.OptionDTO;
import ru.example.demo.model.DTO.converter.OptionConverterDTO;
import ru.example.demo.model.ManagerApartOption;
import ru.example.demo.model.ManagerTogetherOption;
import ru.example.demo.model.Option;
import ru.example.demo.repo.ManagerApartOptionRepository;
import ru.example.demo.repo.ManagerTogetherOptionRepository;
import ru.example.demo.repo.OptionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OptionService {

    private OptionRepository optionRepository;
    private OptionConverterDTO optionConverterDTO;
    private ManagerTogetherOptionRepository managerTogetherOptionRepository;
    private ManagerApartOptionRepository managerApartOptionRepository;

    @Autowired
    public OptionService(OptionRepository optionRepository, OptionConverterDTO optionConverterDTO, ManagerTogetherOptionRepository managerTogetherOptionRepository,
                         ManagerApartOptionRepository managerApartOptionRepository) {
        this.optionRepository = optionRepository;
        this.optionConverterDTO = optionConverterDTO;
        this.managerTogetherOptionRepository = managerTogetherOptionRepository;
        this.managerApartOptionRepository = managerApartOptionRepository;
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
    public OptionDTO getByName(String optionName) {
        return optionConverterDTO.convert(optionRepository.findByName(optionName));
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
    public boolean update(String previousName, String newName, String payment, String connectionPrice) {
        boolean mayBeUpdated;
        Option optionToUpdate = optionRepository.findByName(newName);
        List<ManagerTogetherOption> firstTogether = managerTogetherOptionRepository.findByFirstOption(previousName);
        List<ManagerTogetherOption> secondTogether = managerTogetherOptionRepository.findBySecondOption(previousName);
        List<ManagerApartOption> firstApart = managerApartOptionRepository.findByFirstOption(previousName);
        List<ManagerApartOption> secondApart = managerApartOptionRepository.findBySecondOption(previousName);
        if (optionToUpdate == null || newName.equals(previousName)) {
            Option option = optionRepository.findByName(previousName);
            option.setName(newName);

            for (ManagerTogetherOption managerTogetherOption : firstTogether) {
                managerTogetherOption.setFirstOption(newName);
            }
            managerTogetherOptionRepository.saveAll(firstTogether);

            for (ManagerTogetherOption managerTogetherOption : secondTogether) {
                managerTogetherOption.setSecondOption(newName);
            }
            managerTogetherOptionRepository.saveAll(secondTogether);

            for (ManagerApartOption managerApartOption : firstApart) {
                managerApartOption.setFirstOption(newName);
            }
            managerApartOptionRepository.saveAll(firstApart);

            for (ManagerApartOption managerApartOption : secondApart) {
                managerApartOption.setSecondOption(newName);
            }
            managerApartOptionRepository.saveAll(secondApart);

            option.setPayment(payment);
            option.setConnectionPrice(connectionPrice);
            optionRepository.save(option);
            mayBeUpdated = true;
        } else {
            mayBeUpdated = false;
        }
        return mayBeUpdated;
    }

    @Transactional
    public void deleteOption(String optionName) {
        optionRepository.deleteById(optionRepository.findByName(optionName).getId());
    }

}
