package ru.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "TOGETHER_OPTION")
public class ManagerTogetherOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "firstOption")
    private String firstOption;
    @Column(name = "secondOption")
    private String secondOption;


    public ManagerTogetherOption() {
    }

    public ManagerTogetherOption(String firstOption, String secondOption) {
        this.firstOption = firstOption;
        this.secondOption = secondOption;
    }

    public int getId() {
        return id;
    }

    public String getFirstOption() {
        return firstOption;
    }

    public void setFirstOption(String firstOption) {
        this.firstOption = firstOption;
    }

    public String getSecondOption() {
        return secondOption;
    }

    public void setSecondOption(String secondOption) {
        this.secondOption = secondOption;
    }
}
