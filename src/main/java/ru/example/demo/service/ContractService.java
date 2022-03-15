package ru.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demo.constants.Constant;
import ru.example.demo.model.Client;
import ru.example.demo.model.Contract;
import ru.example.demo.model.DTO.ContractDTO;
import ru.example.demo.model.DTO.converter.ContractConverterDTO;
import ru.example.demo.model.Option;
import ru.example.demo.repo.ClientRepository;
import ru.example.demo.repo.ContractRepository;
import ru.example.demo.repo.OptionRepository;
import ru.example.demo.repo.TariffRepository;

import java.util.*;

@Service
public class ContractService {

    OptionRepository optionRepository;
    ClientRepository clientRepository;
    TariffRepository tariffRepository;
    ContractRepository contractRepository;
    ContractConverterDTO contractConverterDTO;

    @Autowired
    public ContractService(OptionRepository optionRepository, ClientRepository clientRepository, TariffRepository tariffRepository,
                           ContractRepository contractRepository, ContractConverterDTO contractConverterDTO) {
        this.optionRepository = optionRepository;
        this.clientRepository = clientRepository;
        this.tariffRepository = tariffRepository;
        this.contractRepository = contractRepository;
        this.contractConverterDTO = contractConverterDTO;
    }

    @Transactional
    public List<ContractDTO> getAll() {
        List<ContractDTO> contractDTOS = new ArrayList<>();
        for (Contract contract : contractRepository.findAll()) {
            contractDTOS.add(contractConverterDTO.convert(contract));
        }
        return contractDTOS;
    }

    @Transactional
    public ContractDTO getByContractNumber(String contractNumber) {
        return contractConverterDTO.convert(contractRepository.findContractByContractNumber(contractNumber));
    }

    @Transactional
    public List<ContractDTO> getAllByClientEmail(String email) {
        List<ContractDTO> contractDTOS = new ArrayList<>();
        Client client = clientRepository.findClientByEmail(email);
        for (Contract contract : client.getContracts()) {
            ContractDTO contractDTO = contractConverterDTO.convert(contract);
            contractDTOS.add(contractDTO);
        }
        Collections.sort(contractDTOS, Comparator.comparing(ContractDTO::getContractNumber));
        return contractDTOS;
    }

    @Transactional
    public void setTariff(String contractNumber, String nameTariff) {
        if (!nameTariff.contains(Constant.NOTHING)) {
            contractRepository.findContractByContractNumber(contractNumber)
                    .setTariff(tariffRepository.findTariffByName(nameTariff));
        }
    }

    @Transactional
    public void setOptions(String contractNumber, List<String> options) {
        for (String option : options) {
            if (!option.contains(Constant.NOTHING)) {
                contractRepository.findContractByContractNumber(contractNumber)
                        .addOption(optionRepository.findByName(option));
            }
        }
    }

    @Transactional
    public void removeOption(String contractNumber, List<String> options) {
        for (String option : options) {
            if (!option.contains(Constant.NOTHING)) {
                contractRepository.findContractByContractNumber(contractNumber)
                        .removeOption(optionRepository.findByName(option));
            }
        }
    }

    @Transactional
    public void setStatusClient(String contractNumber, String status) {
        Contract contract = contractRepository.findContractByContractNumber(contractNumber);
        if (!contract.getStatus().equals(Constant.LOCK_BY_ADMIN)){
            contract.setStatus(status);
        }
    }

    @Transactional
    public void setStatusAdmin(String contractNumber, String status) {
        contractRepository.findContractByContractNumber(contractNumber).setStatus(status);
    }

    @Transactional
    public boolean save(String contractNumber, String email, String tariffName, String status, List<String> options) {
        boolean mayBeConclusion;
        if (!email.equals(Constant.NOTHING)) {
            if (contractRepository.findContractByContractNumber(contractNumber) == null) {
                Contract contract = new Contract();
                contract.setContractNumber(contractNumber);
                contract.setStatus(status);
                Set<Option> optionsToSave = new HashSet<>();
                for (String option : options) {
                    optionsToSave.add(optionRepository.findByName(option));
                }
                contract.setOptions(optionsToSave);
                contract.setTariff(tariffRepository.findTariffByName(tariffName));
                Client client = clientRepository.findClientByEmail(email);
                client.addContract(contract);
                clientRepository.save(client);
            }
            mayBeConclusion = true;
        } else {
            mayBeConclusion = false;
        }

        return mayBeConclusion;
    }

}
