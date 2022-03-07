package ru.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demo.model.DTO.TariffDTO;
import ru.example.demo.model.DTO.converter.TariffConverterDTO;
import ru.example.demo.model.Tariff;
import ru.example.demo.repo.TariffRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TariffService {

    private TariffRepository tariffRepository;
    private TariffConverterDTO tariffConverterDTO;

    @Autowired
    public TariffService(TariffRepository tariffRepository, TariffConverterDTO tariffConverterDTO) {
        this.tariffRepository = tariffRepository;
        this.tariffConverterDTO = tariffConverterDTO;
    }

    @Transactional
    public List<TariffDTO> getAll(){
        List<TariffDTO> tariffDTOS = new ArrayList<>();
        for (Tariff tariff: tariffRepository.findAll()) {
            tariffDTOS.add(tariffConverterDTO.convert(tariff));
        }
        return tariffDTOS;
    }

}
