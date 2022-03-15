package ru.example.demo.model.DTO;

import java.util.ArrayList;
import java.util.List;

public class ContractDTO {

    private String contractNumber;
    private String status;
    private TariffDTO tariffDTO;
    private List<OptionDTO> optionsDTO;

    public ContractDTO() {
        optionsDTO = new ArrayList<>();
    }

    public ContractDTO(String contractNumber, String status, TariffDTO tariffDTO, List<OptionDTO> optionsDTO) {
        this.contractNumber = contractNumber;
        this.status = status;
        this.tariffDTO = tariffDTO;
        this.optionsDTO = optionsDTO;
    }

    public void addOptionDTO(OptionDTO optionDTO) {
        optionsDTO.add(optionDTO);
    }

    public void removeOptionDTO(OptionDTO optionDTO) {
        optionsDTO.remove(optionDTO);
    }

    public TariffDTO getTariffDTO() {
        return tariffDTO;
    }

    public List<OptionDTO> getOptionsDTO() {
        return optionsDTO;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setTariffDTO(TariffDTO tariffDTO) {
        this.tariffDTO = tariffDTO;
    }

    public void setOptionsDTO(List<OptionDTO> optionsDTO) {
        this.optionsDTO = optionsDTO;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
