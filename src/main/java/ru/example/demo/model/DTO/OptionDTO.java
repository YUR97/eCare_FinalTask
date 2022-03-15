package ru.example.demo.model.DTO;

public class OptionDTO {

    private String name;
    private String payment;
    private String connectionPrice;

    public OptionDTO() {
    }

    public OptionDTO(String name, String payment, String connectionPrice) {
        this.name = name;
        this.payment = payment;
        this.connectionPrice = connectionPrice;
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
