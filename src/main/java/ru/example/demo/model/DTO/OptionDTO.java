package ru.example.demo.model.DTO;

public class OptionDTO {

    private String name;
    private String payment;
    private String connection_price;

    public OptionDTO() {
    }

    public OptionDTO(String name, String payment, String connection_price) {
        this.name = name;
        this.payment = payment;
        this.connection_price = connection_price;
    }

    public String getName() {
        return name;
    }

    public String getPayment() {
        return payment;
    }

    public String getConnection_price() {
        return connection_price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setConnection_price(String connection_price) {
        this.connection_price = connection_price;
    }
}
