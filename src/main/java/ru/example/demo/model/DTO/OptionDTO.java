package ru.example.demo.model.DTO;

import java.util.ArrayList;
import java.util.List;

public class OptionDTO {

    private String name;
    private String payment;
    private String connectionPrice;
    private List<TariffDTO> tariffsDTO;
    private List<ContractDTO> contractsDTO;

    public OptionDTO() {
        tariffsDTO = new ArrayList<>();
        contractsDTO = new ArrayList<>();
    }

    public OptionDTO(String name, String payment, String connectionPrice) {
        this.name = name;
        this.payment = payment;
        this.connectionPrice = connectionPrice;
        tariffsDTO = new ArrayList<>();
        contractsDTO = new ArrayList<>();
    }

    public void addContractDTO(ContractDTO contractDTO) {
        contractsDTO.add(contractDTO);
    }

    public void removeContractDTO(ContractDTO contractDTO) {
        contractsDTO.remove(contractDTO);
    }

    public void addTariffDTO(TariffDTO tariffDTO) {
        tariffsDTO.add(tariffDTO);
    }

    public void removeTariffDTO(TariffDTO tariffDTO) {
        tariffsDTO.remove(tariffDTO);
    }

    public List<TariffDTO> getTariffsDTO() {
        return tariffsDTO;
    }

    public void setTariffsDTO(List<TariffDTO> tariffsDTO) {
        this.tariffsDTO = tariffsDTO;
    }

    public List<ContractDTO> getContractsDTO() {
        return contractsDTO;
    }

    public void setContractsDTO(List<ContractDTO> contractsDTO) {
        this.contractsDTO = contractsDTO;
    }

    public String getName() {
        return name;
    }

    public String getPayment() {
        return payment;
    }

    public String getConnectionPrice() {
        return connectionPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setConnectionPrice(String connectionPrice) {
        this.connectionPrice = connectionPrice;
    }
}
