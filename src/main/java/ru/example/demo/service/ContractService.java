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
    public ContractDTO getByContract_number(String contract_number) {
        return contractConverterDTO.convert(contractRepository.findContractByContract_number(contract_number));
    }

    @Transactional
    public List<ContractDTO> getAllByClientEmail(String email) {
        List<ContractDTO> contractDTOS = new ArrayList<>();
        Client client = clientRepository.findClientByEmail(email);
        for (Contract contract : client.getContracts()) {
            ContractDTO contractDTO = contractConverterDTO.convert(contract);
            contractDTOS.add(contractDTO);
        }
        Collections.sort(contractDTOS, Comparator.comparing(ContractDTO::getContract_number));
        return contractDTOS;
    }

    @Transactional
    public void setTariff(String contract_number, String nameTariff) {
        if (!nameTariff.contains(Constant.NOTHING)) {
            contractRepository.findContractByContract_number(contract_number)
                    .setTariff(tariffRepository.findTariffByName(nameTariff));
        }
    }

    @Transactional
    public void setOptions(String contract_number, List<String> options) {
        for (String option : options) {
            if (!option.contains(Constant.NOTHING)) {
                contractRepository.findContractByContract_number(contract_number)
                        .addOption(optionRepository.findByName(option));
            }
        }
    }

    @Transactional
    public void removeOption(String contract_number, List<String> options) {
        for (String option : options) {
            if (!option.contains(Constant.NOTHING)) {
                contractRepository.findContractByContract_number(contract_number)
                        .removeOption(optionRepository.findByName(option));
            }
        }
    }

    @Transactional
    public void setStatus(String contract_number, String status) {
        contractRepository.findContractByContract_number(contract_number).setStatus(status);
    }

    @Transactional
    public void save(String contract_number, String email, String tariffName, String status, List<String> options) {
        if (contractRepository.findContractByContract_number(contract_number) == null) {
            Contract contract = new Contract();
            contract.setContract_number(contract_number);
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
    }

}
