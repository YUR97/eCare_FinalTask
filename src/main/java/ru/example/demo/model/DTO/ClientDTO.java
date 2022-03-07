package ru.example.demo.model.DTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ClientDTO {
    private int id;
    private String name;
    private String surname;
    private Date birthday;
    private String passport;
    private String address;
    private String email;
    private String password;
    private RoleDTO roleDTO;
    private Set<ContractDTO> contractsDTO;

    public ClientDTO() {
        contractsDTO = new HashSet<>();
    }

    public ClientDTO(String name, String surname, java.sql.Date birthday, String passport, String address, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.passport = passport;
        this.address = address;
        this.email = email;
        this.password = password;
        contractsDTO = new HashSet<>();
    }

    public void addContractDTO(ContractDTO contractDTO) {
        contractsDTO.add(contractDTO);
    }

    public void removeContractDTO(ContractDTO contractDTO) {
        contractsDTO.remove(contractDTO);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getPassport() {
        return passport;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public RoleDTO getRoleDTO() {
        return roleDTO;
    }

    public Set<ContractDTO> getContractsDTO() {
        return contractsDTO;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleDTO(RoleDTO roleDTO) {
        this.roleDTO = roleDTO;
    }

    public void setContractsDTO(Set<ContractDTO> contractsDTO) {
        this.contractsDTO = contractsDTO;
    }
}

