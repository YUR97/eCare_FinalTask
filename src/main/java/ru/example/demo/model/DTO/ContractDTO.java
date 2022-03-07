package ru.example.demo.model.DTO;

public class ContractDTO {

    private String contract_number;
    private String status;

    public ContractDTO() {
    }

    public ContractDTO(String contract_number, String status) {
        this.contract_number = contract_number;
        this.status = status;
    }

    public String getContract_number() {
        return contract_number;
    }

    public String getStatus() {
        return status;
    }

    public void setContract_number(String contract_number) {
        this.contract_number = contract_number;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
