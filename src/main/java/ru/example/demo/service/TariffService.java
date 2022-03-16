package ru.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demo.constants.Constant;
import ru.example.demo.model.DTO.TariffDTO;
import ru.example.demo.model.DTO.converter.TariffConverterDTO;
import ru.example.demo.model.Option;
import ru.example.demo.model.Tariff;
import ru.example.demo.repo.OptionRepository;
import ru.example.demo.repo.TariffRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TariffService {

    private TariffRepository tariffRepository;
    private TariffConverterDTO tariffConverterDTO;
    private OptionRepository optionRepository;

    @Autowired
    public TariffService(TariffRepository tariffRepository, TariffConverterDTO tariffConverterDTO, OptionRepository optionRepository) {
        this.tariffRepository = tariffRepository;
        this.tariffConverterDTO = tariffConverterDTO;
        this.optionRepository = optionRepository;
    }

    @Transactional
    public List<TariffDTO> getAll() {
        List<TariffDTO> tariffDTOS = new ArrayList<>();
        for (Tariff tariff : tariffRepository.findAll()) {
            tariffDTOS.add(tariffConverterDTO.convert(tariff));
        }
        return tariffDTOS;
    }

    @Transactional
    public TariffDTO getByName(String tariffName) {
        return tariffConverterDTO.convert(tariffRepository.findTariffByName(tariffName));
    }

    @Transactional
    public boolean updateTariff(String previousName, String newName, String payment, List<String> optionsToDelete, List<String> optionsToSave) {
        boolean mayBeUpdated;
        Tariff tariffToUpdate = tariffRepository.findTariffByName(newName);
        if (tariffToUpdate == null || newName.equals(previousName)) {
            Tariff tariff = tariffRepository.findTariffByName(previousName);
            tariff.setName(newName);
            tariff.setPayment(payment);
            if(!optionsToDelete.contains(Constant.NOTHING)){
                for (String option : optionsToDelete) {
                    Option optionToDelete = optionRepository.findByName(option);
                    tariff.removeOption(optionToDelete);
                    optionToDelete.removeTariff(tariff);
                }
            }
            if(!optionsToSave.contains(Constant.NOTHING)){
                for (String option : optionsToSave) {
                    Option optionToSave = optionRepository.findByName(option);
                    tariff.addOption(optionToSave);
                    optionToSave.addTariff(tariff);
                }
            }
            if (tariff.getName() != null & tariff.getPayment() != null) {
                tariffRepository.save(tariff);
                mayBeUpdated = true;
            } else {
                mayBeUpdated = false;
            }
            return mayBeUpdated;

        } else {
            return false;
        }
    }

    @Transactional
    public boolean deleteTariff(String tariffNameToDelete) {
        boolean mayBeDelete;
        Tariff tariff = tariffRepository.findTariffByName(tariffNameToDelete);
        if (tariff.getContracts().isEmpty()) {
            mayBeDelete = true;
            if (!tariff.getOptions().isEmpty()) {
                for (Option option : tariff.getOptions()) {
                    optionRepository.findByName(option.getName()).removeTariff(tariff);
                }
                tariff.setOptions(null);
            }
            tariffRepository.deleteById(tariff.getId());
        } else {
            mayBeDelete = false;
        }
        return mayBeDelete;
    }

    @Transactional
    public boolean createTariff(String tariffName, String payment, List<String> options) {
        Tariff tariffFromDB = tariffRepository.findTariffByName(tariffName);
        if (tariffFromDB == null) {
            boolean mayBeSave;
            Tariff tariff = new Tariff();
            if (!tariffName.contains(Constant.NOTHING)) {
                tariff.setName(tariffName);
            }
            if (!payment.contains(Constant.NOTHING)) {
                tariff.setPayment(payment);
            }
            if (!options.contains(Constant.NOTHING)) {
                for (String option : options) {
                    Option optionToAdd = optionRepository.findByName(option);
                    tariff.addOption(optionToAdd);
                    optionToAdd.addTariff(tariff);
                }
            }
            if (tariff.getName() != null & tariff.getPayment() != null & !tariff.getOptions().isEmpty()) {
                tariffRepository.save(tariff);
                mayBeSave = true;
            } else {
                mayBeSave = false;
            }
            return mayBeSave;
        } else {
            return false;
        }
    }
}
