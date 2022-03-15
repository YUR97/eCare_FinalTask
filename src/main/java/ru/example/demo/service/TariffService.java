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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public boolean deleteTariff(String tariffNameToDelete) {
        boolean mayBeDelete;
        Tariff tariff = tariffRepository.findTariffByName(tariffNameToDelete);
        if (tariff.getContracts().size() == 0) {
            mayBeDelete = true;
            tariff.setOptions(null);
            tariffRepository.deleteById(tariff.getId());
        } else {
            mayBeDelete = false;
        }
        return mayBeDelete;
    }

    @Transactional
    public boolean createTariff(String tariffName, String payment, List<String> options) {
        boolean mayBeSave;
        Tariff tariff = new Tariff();
        if (!tariffName.contains(Constant.NOTHING)) {
            tariff.setName(tariffName);
        }
        if (!payment.contains(Constant.NOTHING)) {
            tariff.setPayment(payment);
        }
        if (!options.contains(Constant.NOTHING)) {
            Set<Option> optionsToAdd = new HashSet<>();
            for (String option : options) {
                Option optionToAdd = optionRepository.findByName(option);
                optionsToAdd.add(optionToAdd);
            }
            tariff.setOptions(optionsToAdd);
        }
        if (tariff.getName() != null & tariff.getPayment() != null & tariff.getOptions() != null) {
            tariffRepository.save(tariff);
            mayBeSave = true;
        } else {
            mayBeSave = false;
        }
        return mayBeSave;
    }
}
