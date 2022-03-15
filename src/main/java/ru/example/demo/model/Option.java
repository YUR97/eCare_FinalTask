package ru.example.demo.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OPTIONS")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "payment")
    private String payment;
    @Column(name = "connection_price")
    private String connectionPrice;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "TARIFFS_OPTIONS",
            joinColumns = @JoinColumn(name = "id_option"),
            inverseJoinColumns = @JoinColumn(name = "id_tariff"))
    private Set<Tariff> tariffs;
    @ManyToMany(mappedBy = "options", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Contract> contracts;

    public Option() {
        tariffs = new HashSet<>();
        contracts = new HashSet<>();
    }

    public Option(String name, String payment, String connectionPrice) {
        this.name = name;
        this.payment = payment;
        this.connectionPrice = connectionPrice;
        tariffs = new HashSet<>();
        contracts = new HashSet<>();
    }
    
    public void addTariff(Tariff tariff) {
        tariffs.add(tariff);
    }

    public void removeTariff(Tariff tariff) {
        tariffs.remove(tariff);
    }

    public void addContract(Contract contract) {
        contracts.add(contract);
    }

    public void removeContract(Contract contract) {
        contracts.remove(contract);
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public void setTariffs(Set<Tariff> tariffs) {
        this.tariffs = tariffs;
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

    public Set<Contract> getContracts() {
        return contracts;
    }

    public Set<Tariff> getTariffs() {
        return tariffs;
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

    public String getConnectionPrice() {
        return connectionPrice;
    }

}
