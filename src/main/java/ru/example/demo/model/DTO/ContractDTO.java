package ru.example.demo.model.DTO;

import java.util.ArrayList;
import java.util.List;

public class ContractDTO {

    private String contract_number;
    private String status;
    private TariffDTO tariffDTO;
    private List<OptionDTO> optionsDTO;

    public ContractDTO() {
        optionsDTO = new ArrayList<>();
    }

    public ContractDTO(String contract_number, String status, TariffDTO tariffDTO, List<OptionDTO> optionsDTO) {
        this.contract_number = contract_number;
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

    public String getContract_number() {
        return contract_number;
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

    public void setContract_number(String contract_number) {
        this.contract_number = contract_number;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
