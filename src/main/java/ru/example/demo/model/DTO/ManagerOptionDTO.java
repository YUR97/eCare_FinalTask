package ru.example.demo.model.DTO;

import java.util.ArrayList;
import java.util.List;

public class ManagerOptionDTO {

    private List<String[]> optionsTogether;
    private List<String[]> optionsApart;

    public ManagerOptionDTO() {
        optionsTogether = new ArrayList<>();
        optionsApart = new ArrayList<>();
    }

    public ManagerOptionDTO(List<String[]> optionsTogether, List<String[]> optionsApart) {
        this.optionsTogether = optionsTogether;
        this.optionsApart = optionsApart;
    }

    public void addTogether(String firstOption, String secondOption) {
        String[] arrTogether = {firstOption, secondOption};
        optionsTogether.add(arrTogether);
    }

    public void addApart(String firstOption, String secondOption) {
        String[] arrApart = {firstOption, secondOption};
        optionsApart.add(arrApart);
    }

    public void removeTogether(String firstOption, String secondOption){
        String[] arrTogether = {firstOption, secondOption};
        optionsTogether.remove(arrTogether);
    }

    public void removeApart(String firstOption, String secondOption) {
        String[] arrApart = {firstOption, secondOption};
        optionsApart.remove(arrApart);
    }

    public List<String[]> getOptionsTogether() {
        return optionsTogether;
    }

    public void setOptionsTogether(List<String[]> optionsTogether) {
        this.optionsTogether = optionsTogether;
    }

    public List<String[]> getOptionsApart() {
        return optionsApart;
    }

    public void setOptionsApart(List<String[]> optionsApart) {
        this.optionsApart = optionsApart;
    }
}
