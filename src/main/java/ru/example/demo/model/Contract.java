package ru.example.demo.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CONTRACTS")
public class Contract {

    @Id
    private String contractNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tariff")
    private Tariff tariff;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "CONTRACTS_OPTIONS",
            joinColumns = @JoinColumn(name = "contract_number"),
            inverseJoinColumns = @JoinColumn(name = "id_option"))
    private Set<Option> options;
    @Column(name = "status")
    private String status;

    public Contract() {
        options = new HashSet<>();
    }

    public Contract(String contractNumber, String status) {
        this.contractNumber = contractNumber;
        this.status = status;
        options = new HashSet<>();
    }

    public void addOption(Option option) {
        options.add(option);
    }

    public void removeOption(Option option) {
        options.remove(option);
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public Client getClient() {
        return client;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public String getStatus() {
        return status;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
