package ru.example.demo.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TARIFFS")
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "payment")
    private String payment;
    @ManyToMany(mappedBy = "tariffs", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Option> options;
    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contract> contracts;

    public Tariff() {
        options = new HashSet<>();
        contracts = new HashSet<>();
    }

    public Tariff(String name, String payment) {
        this.name = name;
        this.payment = payment;
        options = new HashSet<>();
        contracts = new HashSet<>();
    }

    public void addOption(Option option){ options.add(option); }
    public void removeOption(Option option){
        options.remove(option);
    }

    public void addContract(Contract contract){
        contract.setTariff(this);
        contracts.add(contract);
    }
    public void removeContract(Contract contract){ contracts.remove(contract); }

    public void setContracts(Set<Contract> contracts) { this.contracts = contracts; }
    public void setOptions(Set<Option> options) {
        this.options = options;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Set<Contract> getContracts() { return contracts; }
    public Set<Option> getOptions() {
        return options;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPayment() {
        return payment;
    }

}
