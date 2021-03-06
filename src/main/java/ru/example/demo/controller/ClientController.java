package ru.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.example.demo.constants.Constant;
import ru.example.demo.model.Client;
import ru.example.demo.model.DTO.ContractDTO;
import ru.example.demo.model.DTO.OptionDTO;
import ru.example.demo.model.DTO.TariffDTO;
import ru.example.demo.service.*;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private OptionService optionService;
    private ContractService contractService;
    private ClientService clientService;
    private TariffService tariffService;
    private ManagerOptionService managerOptionService;

    @Autowired
    public ClientController(OptionService optionService, ContractService contractService, ClientService clientService,
                            TariffService tariffService, ManagerOptionService managerOptionService) {
        this.optionService = optionService;
        this.contractService = contractService;
        this.clientService = clientService;
        this.tariffService = tariffService;
        this.managerOptionService = managerOptionService;
    }

    @PostMapping("/me")
    public String showMe(Authentication authentication, Model model) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        model.addAttribute("client", clientService.getByEmail(user.getUsername()));
        return "redirect:/home";
    }

    @GetMapping("/tariffs")
    public String showTariffs(Model model) {
        List<TariffDTO> tariffsDTO = tariffService.getAll();
        List<OptionDTO> optionsDTO = optionService.getAll();
        model.addAttribute("options", optionsDTO);
        model.addAttribute("tariffs", tariffsDTO);
        return "tariffsClient";
    }

    @GetMapping("/contracts")
    public String showContracts(Authentication authentication, Model model) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        List<ContractDTO> contractDTOS = contractService.getAllByClientEmail(user.getUsername());
        model.addAttribute("contracts", contractDTOS);
        return "contractsClient";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam(name = "contractNumber") String contractNumber, Model model) {
        ContractDTO contractDTO = contractService.getByContractNumber(contractNumber);
        List<TariffDTO> tariffsDTO = tariffService.getAll();
        List<OptionDTO> optionDTOS = optionService.getAll();
        model.addAttribute("options", optionDTOS);
        model.addAttribute("tariffs", tariffsDTO);
        model.addAttribute("contract", contractDTO);

        List<String[]> together = managerOptionService.getAllOptionsTogether();
        List<String[]> apart = managerOptionService.getAllOptionsApart();

        model.addAttribute("together", together);
        model.addAttribute("apart", apart);

        return "editContract";
    }

    @PostMapping("/saveEdit")
    public String saveEdit(@RequestParam(name = "contractNumber") String contractNumber,
                           @RequestParam(value = "tariffChoice", defaultValue = Constant.NOTHING) String tariffChoice,
                           @RequestParam(name = "option", defaultValue = Constant.NOTHING) List<String> options,
                           @RequestParam(name = "optionsToDelete", defaultValue = Constant.NOTHING) List<String> optionsToDelete) {
        contractService.setOptions(contractNumber, options);
        contractService.setTariff(contractNumber, tariffChoice);
        contractService.removeOption(contractNumber, optionsToDelete);
        return Constant.REDIRECT;
    }

    @PostMapping("/changeStatus")
    public String changeStatus(@RequestParam("contractNumber") String contractNumber, @RequestParam("status") String status) {
        contractService.setStatusClient(contractNumber, status);
        return Constant.REDIRECT;
    }

    @PostMapping("/editClient")
    public String saveEdit(Authentication auth, @RequestParam(name = "name") String name, @RequestParam(name = "surname") String surname,
                           @RequestParam(name = "birthday") String birthday, @RequestParam(name = "address") String address,
                           @RequestParam(name = "passport") String passport) {
        UserDetails user = (UserDetails) auth.getPrincipal();
        java.sql.Date date = java.sql.Date.valueOf(birthday);
        Client client = new Client();
        client.setName(name);
        client.setSurname(surname);
        client.setBirthday(date);
        client.setPassport(passport);
        client.setAddress(address);
        client.setEmail(user.getUsername());
        clientService.update(client);
        return "redirect:/home";
    }

    @PostMapping("/editClientPassword")
    public String saveEditPassword(Authentication auth, @RequestParam(name = "password") String password) {
        UserDetails user = (UserDetails) auth.getPrincipal();
        Client client = new Client();
        client.setPassword(password);
        client.setEmail(user.getUsername());
        clientService.updatePassword(client);
        return "redirect:/home";
    }

}
