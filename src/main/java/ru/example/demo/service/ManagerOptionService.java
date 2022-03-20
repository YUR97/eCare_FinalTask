package ru.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demo.model.DTO.ManagerOptionDTO;
import ru.example.demo.model.DTO.converter.ManagerOptionConverterDTO;
import ru.example.demo.model.ManagerApartOption;
import ru.example.demo.model.ManagerTogetherOption;
import ru.example.demo.repo.ManagerApartOptionRepository;
import ru.example.demo.repo.ManagerTogetherOptionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerOptionService {

    private ManagerTogetherOptionRepository managerTogetherOptionRepository;
    private ManagerApartOptionRepository managerApartOptionRepository;
    private ManagerOptionConverterDTO managerOptionConverterDTO;

    @Autowired
    public ManagerOptionService(ManagerTogetherOptionRepository managerTogetherOptionRepository,
                                ManagerApartOptionRepository managerApartOptionRepository,
                                ManagerOptionConverterDTO managerOptionConverterDTO) {
        this.managerTogetherOptionRepository = managerTogetherOptionRepository;
        this.managerApartOptionRepository = managerApartOptionRepository;
        this.managerOptionConverterDTO = managerOptionConverterDTO;
    }

    @Transactional
    public List<String[]> getAllOptionsTogether() {
        List<ManagerOptionDTO> together = managerTogetherOptionRepository.findAll().stream().map(o -> managerOptionConverterDTO.convert(o)).toList();
        List<String[]> togetherList = new ArrayList<>();
        if (together != null) {
            for (ManagerOptionDTO managerOptionDTO : together) {
                togetherList.add(managerOptionDTO.getOptionsTogether().get(0));
            }
        }
        return togetherList;
    }

    @Transactional
    public List<String[]> getAllOptionsApart() {
        List<ManagerOptionDTO> apart = managerApartOptionRepository.findAll().stream().map(o -> managerOptionConverterDTO.convert(o)).toList();
        List<String[]> apartList = new ArrayList<>();
        if (apart != null) {
            for (ManagerOptionDTO managerOptionDTO : apart) {
                apartList.add(managerOptionDTO.getOptionsApart().get(0));
            }
        }
        return apartList;
    }

    @Transactional
    public boolean addTogetherPair(String anotherFirst, String anotherSecond) {

        String[] anotherPair = {anotherFirst, anotherSecond};

        List<String[]> together = getAllOptionsTogether();
        List<String[]> apart = getAllOptionsApart();

        boolean pairTogetherExist = false;
        boolean pairApartExist = false;
        boolean mayAdd = true;

        for (String[] string : together) {
            if (string[0].equals(anotherPair[0]) && string[1].equals(anotherPair[1])) {
                pairTogetherExist = true;
            }
        }

        for (String[] string : apart) {
            if (string[0].equals(anotherPair[0]) && string[1].equals(anotherPair[1])) {
                pairApartExist = true;
            }
        }

        List<String[]> withFirstApart = new ArrayList<>();
        List<String[]> withSecondApart = new ArrayList<>();
        List<String[]> withFirstTogether = new ArrayList<>();
        List<String[]> withSecondTogether = new ArrayList<>();

        for (String[] string : apart) {
            if (string[0].equals(anotherPair[0])) {
                withFirstApart.add(string);
            }
            if (string[0].equals(anotherPair[1])) {
                withSecondApart.add(string);
            }
        }

        for (String[] string : together) {
            if (string[0].equals(anotherPair[0])) {
                withFirstTogether.add(string);
            }
            if (string[0].equals(anotherPair[1])) {
                withSecondTogether.add(string);
            }
        }

        for (String[] stringFirst : withFirstApart) {
            for (String[] stringSecond : withSecondTogether) {
                if (stringFirst[1].equals(stringSecond[1])) {
                    mayAdd = false;
                }
            }
        }

        for (String[] stringSecond : withSecondApart) {
            for (String[] stringFirst : withFirstTogether) {
                if (stringSecond[1].equals(stringFirst[1])) {
                    mayAdd = false;
                }
            }
        }

        List<ManagerTogetherOption> list = new ArrayList<>();
        if (!pairTogetherExist && !pairApartExist && mayAdd) {
            ManagerTogetherOption managerTogetherOption = new ManagerTogetherOption(anotherPair[0], anotherPair[1]);
            ManagerTogetherOption managerTogetherOptionReverse = new ManagerTogetherOption(anotherPair[1], anotherPair[0]);
            list.add(managerTogetherOption);
            list.add(managerTogetherOptionReverse);
            for (String[] string : withFirstTogether) {
                managerTogetherOption = new ManagerTogetherOption(string[1], anotherPair[1]);
                managerTogetherOptionReverse = new ManagerTogetherOption(anotherPair[1], string[1]);
                list.add(managerTogetherOption);
                list.add(managerTogetherOptionReverse);

            }
            for (String[] string : withSecondTogether) {
                managerTogetherOption = new ManagerTogetherOption(string[1], anotherPair[0]);
                managerTogetherOptionReverse = new ManagerTogetherOption(anotherPair[0], string[1]);
                list.add(managerTogetherOption);
                list.add(managerTogetherOptionReverse);
            }

            for (String[] stringFirst : withFirstTogether) {
                for (String[] stringSecond : withSecondTogether) {
                    managerTogetherOption = new ManagerTogetherOption(stringFirst[1], stringSecond[1]);
                    managerTogetherOptionReverse = new ManagerTogetherOption(stringSecond[1], stringFirst[1]);
                    list.add(managerTogetherOption);
                    list.add(managerTogetherOptionReverse);
                }
            }
        }

        if (list.isEmpty()) {
            return false;
        } else {
            managerTogetherOptionRepository.saveAll(list);
            return true;
        }

    }

    @Transactional
    public boolean removeTogetherPair(String option) {

        boolean mayBeDelete = true;

        List<ManagerTogetherOption> result = new ArrayList<>();
        List<ManagerTogetherOption> firsLines = managerTogetherOptionRepository.findByFirstOption(option);
        List<ManagerTogetherOption> secondLines = managerTogetherOptionRepository.findBySecondOption(option);

        if (!firsLines.isEmpty()) {
            result.addAll(firsLines);
        }

        if (!secondLines.isEmpty()) {
            result.addAll(secondLines);
        }

        if (!result.isEmpty()) {
            managerTogetherOptionRepository.deleteAll(result);
        } else {
            mayBeDelete = false;
        }

        return mayBeDelete;

    }

    @Transactional
    public boolean addApartPair(String anotherFirst, String anotherSecond) {

        String[] anotherPair = {anotherFirst, anotherSecond};

        List<String[]> together = getAllOptionsTogether();
        List<String[]> apart = getAllOptionsApart();

        boolean pairTogetherExist = false;
        boolean pairApartExist = false;

        for (String[] string : together) {
            if (string[0].equals(anotherPair[0]) && string[1].equals(anotherPair[1])) {
                pairTogetherExist = true;
            }
        }

        for (String[] string : apart) {
            if (string[0].equals(anotherPair[0]) && string[1].equals(anotherPair[1])) {
                pairApartExist = true;
            }
        }

        if (!pairTogetherExist && !pairApartExist) {
            ManagerApartOption managerApartOption = new ManagerApartOption(anotherPair[0], anotherPair[1]);
            ManagerApartOption managerApartOptionReverse = new ManagerApartOption(anotherPair[1], anotherPair[0]);
            List<ManagerApartOption> list = new ArrayList<>();
            list.add(managerApartOption);
            list.add(managerApartOptionReverse);
            managerApartOptionRepository.saveAll(list);
            return true;
        } else {
            return false;
        }

    }

    @Transactional
    public boolean removeApartPair(String firstOption, String secondOption) {

        boolean mayBeDelete = true;

        ManagerApartOption result = managerApartOptionRepository.findByFirstOptionAndSecondOption(firstOption, secondOption);
        ManagerApartOption resultReverse = managerApartOptionRepository.findByFirstOptionAndSecondOption(secondOption, firstOption);

        if (result != null && resultReverse != null) {
            managerApartOptionRepository.delete(result);
            managerApartOptionRepository.delete(resultReverse);
        } else {
            mayBeDelete = false;
        }

        return mayBeDelete;

    }

}
