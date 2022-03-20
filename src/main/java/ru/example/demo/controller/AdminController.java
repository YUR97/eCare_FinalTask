package ru.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.demo.constants.Constant;
import ru.example.demo.model.Client;
import ru.example.demo.model.DTO.ClientDTO;
import ru.example.demo.model.DTO.ContractDTO;
import ru.example.demo.model.DTO.OptionDTO;
import ru.example.demo.model.DTO.TariffDTO;
import ru.example.demo.model.Option;
import ru.example.demo.repo.ContractRepository;
import ru.example.demo.service.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OptionService optionService;
    private final ContractService contractService;
    private final TariffService tariffService;
    private final ClientService clientService;
    private final LockClientService lockClientService;
    private final ContractRepository contractRepository;
    private final ManagerOptionService managerOptionService;

    @Autowired
    public AdminController(OptionService optionService, ContractService contractService, TariffService tariffService,
                           ClientService clientService, LockClientService lockClientService,
                           ContractRepository contractRepository, ManagerOptionService managerOptionService) {
        this.optionService = optionService;
        this.contractService = contractService;
        this.tariffService = tariffService;
        this.clientService = clientService;
        this.lockClientService = lockClientService;
        this.contractRepository = contractRepository;
        this.managerOptionService = managerOptionService;
    }

    @GetMapping("/clients")
    public String showClients(Model model) {
        List<ClientDTO> clientDTOS = clientService.getAll();
        Collections.sort(clientDTOS, (Comparator.comparing(o -> o.getRoleDTO().getName())));
        model.addAttribute("finder", Constant.DEFAULT_FINDER);
        model.addAttribute("clients", clientDTOS);
        return "adminClients";
    }

    @PostMapping("/clients")
    public String showFoundClients(@RequestParam(name = "finder", defaultValue = Constant.NOTHING) String finder, Model model) {
        List<ClientDTO> clientDTOS = clientService.getAll();
        Collections.sort(clientDTOS, (Comparator.comparing(o -> o.getRoleDTO().getName())));
        if (finder.contains("all")) {
            model.addAttribute("finder", finder);
            model.addAttribute("clients", clientDTOS);
        } else {
            List<ClientDTO> toOutClientDTOS = new ArrayList<>();
            for (ClientDTO client : clientDTOS) {
                for (ContractDTO contract : client.getContractsDTO()) {
                    if (contract.getContractNumber().contains(finder)) {
                        toOutClientDTOS.add(client);
                        break;
                    }
                }
            }
            Collections.sort(toOutClientDTOS, (Comparator.comparing(o -> o.getRoleDTO().getName())));
            model.addAttribute("finder", finder);
            model.addAttribute("clients", toOutClientDTOS);
        }
        return "adminClients";
    }

    @GetMapping("/contract/{id}")
    public String showContract(@PathVariable("id") String contractNumber, Model model) {
        model.addAttribute("contract", contractService.getByContractNumber(contractNumber));
        return "showContractToAdmin";
    }

    @PostMapping("contract/changeStatus")
    public String showContractChanged(@RequestParam(name = "contractNumber") String contractNumber,
                                      @RequestParam(name = "status") String status, Model model) {
        contractService.setStatusAdmin(contractNumber, status);
        model.addAttribute("contract", contractService.getByContractNumber(contractNumber));
        return "showContractToAdmin";
    }

    @GetMapping("/changeContract/{contractNumber}")
    public String changeContract(@PathVariable("contractNumber") String contractNumber, Model model) {
        model.addAttribute("contract", contractService.getByContractNumber(contractNumber));
        model.addAttribute("options", optionService.getAll());
        model.addAttribute("tariffs", tariffService.getAll());

        List<String[]> together = managerOptionService.getAllOptionsTogether();
        List<String[]> apart = managerOptionService.getAllOptionsApart();

        model.addAttribute("together", together);
        model.addAttribute("apart", apart);

        return "adminEditContract";
    }

    @PostMapping("/saveEditContract")
    public String saveEditContract(@RequestParam(name = "contractNumber") String contractNumber,
                                   @RequestParam(value = "tariffChoice", defaultValue = Constant.NOTHING) String tariffChoice,
                                   @RequestParam(name = "option", defaultValue = Constant.NOTHING) List<String> options,
                                   @RequestParam(name = "optionsToDelete", defaultValue = Constant.NOTHING) List<String> optionsToDelete) {
        contractService.setOptions(contractNumber, options);
        contractService.setTariff(contractNumber, tariffChoice);
        contractService.removeOption(contractNumber, optionsToDelete);
        return "redirect:/admin/contract/" + contractNumber;
    }

    @GetMapping("/client/{id}")
    public String showClient(@PathVariable("id") int id, Model model) {
        ClientDTO clientDTO = clientService.getById(id);
        model.addAttribute("client", clientDTO);
        model.addAttribute("isLock", lockClientService.getLockedClient(clientDTO.getEmail()) != null);
        return "showClientToAdmin";
    }

    @PostMapping("/client/{id}")
    public String showClientChanged(@RequestParam(name = "isLock") String isLock, @PathVariable("id") int id, Model model) {
        ClientDTO clientDTO = clientService.getById(id);
        if (!isLock.contains(Constant.UNLOCK)) {
            lockClientService.lockClient(clientDTO.getEmail());
        } else {
            lockClientService.unlockClient(clientDTO.getEmail());
        }
        model.addAttribute("client", clientDTO);
        model.addAttribute("isLock", lockClientService.getLockedClient(clientDTO.getEmail()) != null);
        return "showClientToAdmin";
    }

    @GetMapping("/conclusionContract")
    public String conclusionContract(Model model) {
        model.addAttribute("mayBeConclusion", true);
        model.addAttribute("contractExist", false);
        model.addAttribute("tariffs", tariffService.getAll());
        model.addAttribute("options", optionService.getAll());
        model.addAttribute("clients", clientService.getAll());
        return "adminConclusionContract";
    }

    @PostMapping("/conclusionContract")
    public String createContract(@RequestParam(name = "contractNumber", defaultValue = Constant.NOTHING) String contractNumber,
                                 @RequestParam(name = "email", defaultValue = Constant.NOTHING) String email,
                                 @RequestParam(name = "tariffName", defaultValue = Constant.NOTHING) String tariffName,
                                 @RequestParam(name = "status", defaultValue = Constant.NOTHING) String status,
                                 @RequestParam(name = "options", defaultValue = Constant.NOTHING) List<String> options, Model model) {

        Boolean mayBeConclusion = contractService.save(contractNumber, email, tariffName, status, options);
        if (contractRepository.findContractByContractNumber(contractNumber) != null) {
            model.addAttribute("mayBeConclusion", true);
            model.addAttribute("contractExist", true);
            model.addAttribute("tariffs", tariffService.getAll());
            model.addAttribute("options", optionService.getAll());
            model.addAttribute("clients", clientService.getAll());
            return "adminConclusionContract";
        } else {
            model.addAttribute("contractExist", false);
            model.addAttribute("mayBeConclusion", mayBeConclusion);
            if (!mayBeConclusion) {
                model.addAttribute("tariffs", tariffService.getAll());
                model.addAttribute("options", optionService.getAll());
                model.addAttribute("clients", clientService.getAll());
                return "adminConclusionContract";
            } else {
                return "redirect:/admin/clients";
            }
        }
    }

    @GetMapping("/edit")
    public String editAdmin(Authentication auth, Model model) {
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("client", clientService.getByEmail(user.getUsername()));
        return "editAdmin";
    }

    @PostMapping("/edit")
    public String saveEdit(Authentication auth, @RequestParam(name = "name") String name, @RequestParam(name = "surname") String surname,
                           @RequestParam(name = "birthday") String birthday, @RequestParam(name = "address") String address,
                           @RequestParam(name = "passport") String passport, @RequestParam(name = "password") String password) {
        UserDetails user = (UserDetails) auth.getPrincipal();
        java.sql.Date date = java.sql.Date.valueOf(birthday);
        Client client = new Client(name, surname, date, passport, address, user.getUsername(), password);
        clientService.update(client);
        return "redirect:/home";
    }

    @GetMapping("/tariffs")
    public String showTariffs(Model model) {
        List<TariffDTO> tariffsDTO = tariffService.getAll();
        Collections.sort(tariffsDTO, (Comparator.comparing(TariffDTO::getName)));
        model.addAttribute("tariffs", tariffsDTO);
        model.addAttribute("options", optionService.getAll());
        model.addAttribute("mayBeSave", true);
        model.addAttribute("mayBeDelete", true);
        return "adminTariffs";
    }

    @GetMapping("/options")
    public String showOptions(Model model) {
        List<OptionDTO> optionsDTO = optionService.getAll();
        List<String[]> apartList = managerOptionService.getApart();
        List<List<String>> togetherList = managerOptionService.getTogethers();

        Collections.sort(optionsDTO, (Comparator.comparing(OptionDTO::getName)));

        model.addAttribute("mayAddTogether", true);
        model.addAttribute("mayAddApart", true);
        model.addAttribute("mayBeSave", true);

        model.addAttribute("options", optionsDTO);
        model.addAttribute("togetherList", togetherList);
        model.addAttribute("apartList", apartList);

        return "adminOptions";
    }

    @GetMapping("/editOption/{name}")
    public String editOption(@PathVariable(name = "name") String optionName, Model model) {
        model.addAttribute("option", optionService.getByName(optionName));
        model.addAttribute("mayBeUpdated", true);
        return "editOption";
    }

    @PostMapping("/editOption")
    public String saveEditOption(@RequestParam(name = "previousName") String previousName, @RequestParam(name = "newName") String newName,
                                 @RequestParam(name = "payment") String payment, @RequestParam(name = "connectionPrice") String connectionPrice, Model model) {

        Boolean mayBeUpdated = optionService.update(previousName, newName, payment, connectionPrice);
        model.addAttribute("mayBeUpdated", mayBeUpdated);
        if (!mayBeUpdated) {
            model.addAttribute("option", optionService.getByName(previousName));
            return "editOption";
        } else {
            List<OptionDTO> optionsDTO = optionService.getAll();
            Collections.sort(optionsDTO, (Comparator.comparing(OptionDTO::getName)));
            model.addAttribute("mayBeSave", true);
            model.addAttribute("options", optionsDTO);
            return "adminOptions";
        }

    }

    @PostMapping("/createOption")
    public String createOption(@RequestParam(name = "name", defaultValue = Constant.NOTHING) String optionName,
                               @RequestParam(name = "payment", defaultValue = Constant.NOTHING) String payment,
                               @RequestParam(name = "connectionPrice", defaultValue = Constant.NOTHING) String connectionPrice, Model model) {
        model.addAttribute("mayBeSave", optionService.saveOption(new Option(optionName, payment, connectionPrice)));
        model.addAttribute("options", optionService.getAll());
        return "adminOptions";
    }

    @GetMapping("/deleteOption/{optionName}")
    public String deleteOption(@PathVariable(name = "optionName") String optionName) {
        optionService.deleteOption(optionName);
        return "redirect:/admin/options";
    }

    @PostMapping("/addTogether")
    public String addTogether(@RequestParam(name = "firstOptionTogether") String firstOption,
                              @RequestParam(name = "secondOptionTogether") String secondOption, Model model) {

        boolean mayAddTogether;

        if (!firstOption.equals(secondOption)) {
            mayAddTogether = managerOptionService.addTogetherPair(firstOption, secondOption);
        } else {
            mayAddTogether = false;
        }

        List<OptionDTO> optionsDTO = optionService.getAll();
        List<String[]> apartList = managerOptionService.getApart();
        List<List<String>> togetherList = managerOptionService.getTogethers();

        Collections.sort(optionsDTO, (Comparator.comparing(OptionDTO::getName)));

        model.addAttribute("mayAddTogether", mayAddTogether);
        model.addAttribute("mayAddApart", true);
        model.addAttribute("mayBeSave", true);

        model.addAttribute("options", optionsDTO);
        model.addAttribute("togetherList", togetherList);
        model.addAttribute("apartList", apartList);

        return "adminOptions";
    }

    @PostMapping("/addApart")
    public String addApart(@RequestParam(name = "firstOptionApart") String firstOption,
                           @RequestParam(name = "secondOptionApart") String secondOption, Model model) {

        boolean mayAddApart;

        if (!firstOption.equals(secondOption)) {
            mayAddApart = managerOptionService.addApartPair(firstOption, secondOption);
        } else {
            mayAddApart = false;
        }

        List<OptionDTO> optionsDTO = optionService.getAll();
        List<String[]> apartList = managerOptionService.getApart();
        List<List<String>> togetherList = managerOptionService.getTogethers();

        Collections.sort(optionsDTO, (Comparator.comparing(OptionDTO::getName)));

        model.addAttribute("mayAddTogether", true);
        model.addAttribute("mayAddApart", mayAddApart);
        model.addAttribute("mayBeSave", true);

        model.addAttribute("options", optionsDTO);
        model.addAttribute("togetherList", togetherList);
        model.addAttribute("apartList", apartList);

        return "adminOptions";
    }


    @PostMapping("/deleteTariff")
    public String deleteTariff(@RequestParam(name = "nameTariffToDelete") String tariffName, Model model) {
        model.addAttribute("mayBeSave", true);
        model.addAttribute("mayBeDelete", tariffService.deleteTariff(tariffName));
        model.addAttribute("tariffs", tariffService.getAll());
        model.addAttribute("options", optionService.getAll());
        return "adminTariffs";
    }

    @PostMapping("/createTariff")
    public String createTariff(@RequestParam(name = "name", defaultValue = Constant.NOTHING) String tariffName,
                               @RequestParam(name = "payment", defaultValue = Constant.NOTHING) String payment,
                               @RequestParam(name = "options", defaultValue = Constant.NOTHING) List<String> options, Model model) {
        model.addAttribute("mayBeSave", tariffService.createTariff(tariffName, payment, options));
        model.addAttribute("mayBeDelete", true);
        model.addAttribute("tariffs", tariffService.getAll());
        model.addAttribute("options", optionService.getAll());
        return "adminTariffs";
    }

    @GetMapping("/changeTariff/{tariffName}")
    public String changeTariff(@PathVariable("tariffName") String tariffName, Model model) {
        model.addAttribute("mayBeUpdated", true);
        model.addAttribute("options", optionService.getAll());
        model.addAttribute("tariff", tariffService.getByName(tariffName));
        return "adminTariffEdit";
    }

    @PostMapping("/changeTariff")
    public String saveChangeTariff(@RequestParam(name = "previousTariffName", defaultValue = Constant.NOTHING) String previousTariffName,
                                   @RequestParam(name = "newTariffName", defaultValue = Constant.NOTHING) String newTariffName,
                                   @RequestParam(name = "tariffPayment", defaultValue = Constant.NOTHING) String payment,
                                   @RequestParam(name = "optionsToDelete", defaultValue = Constant.NOTHING) List<String> optionsToDelete,
                                   @RequestParam(name = "optionsToSave", defaultValue = Constant.NOTHING) List<String> optionsToSave, Model model) {

        boolean mayBeUpdated = tariffService.updateTariff(previousTariffName, newTariffName, payment, optionsToDelete, optionsToSave);
        model.addAttribute("mayBeUpdated", mayBeUpdated);

        if (mayBeUpdated) {
            model.addAttribute("tariffs", tariffService.getAll());
            model.addAttribute("options", optionService.getAll());
            model.addAttribute("mayBeSave", true);
            model.addAttribute("mayBeDelete", true);
            return "adminTariffs";
        } else {
            model.addAttribute("options", optionService.getAll());
            model.addAttribute("tariff", tariffService.getByName(previousTariffName));
            return "adminTariffEdit";
        }
    }

}
