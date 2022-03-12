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
import ru.example.demo.model.DTO.ContractDTO;
import ru.example.demo.model.DTO.OptionDTO;
import ru.example.demo.model.DTO.TariffDTO;
import ru.example.demo.service.ClientService;
import ru.example.demo.service.ContractService;
import ru.example.demo.service.OptionService;
import ru.example.demo.service.TariffService;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private OptionService optionService;
    private ContractService contractService;
    private ClientService clientService;
    private TariffService tariffService;

    @Autowired
    public ClientController(OptionService optionService, ContractService contractService, ClientService clientService, TariffService tariffService) {
        this.optionService = optionService;
        this.contractService = contractService;
        this.clientService = clientService;
        this.tariffService = tariffService;
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
    public String edit(@RequestParam(name = "contract_number") String contract_number, Model model) {
        ContractDTO contractDTO = contractService.getByContract_number(contract_number);
        List<TariffDTO> tariffsDTO = tariffService.getAll();
        List<OptionDTO> optionDTOS = optionService.getAll();
        model.addAttribute("options", optionDTOS);
        model.addAttribute("tariffs", tariffsDTO);
        model.addAttribute("contract", contractDTO);
        return "editContract";
    }

    @PostMapping("/saveEdit")
    public String saveEdit(@RequestParam("contract_number") String contract_number, @RequestParam(value = "tariffChoice", defaultValue = Constant.NOTHING) String tariffChoice, @RequestParam(name = "option", defaultValue = Constant.NOTHING) List<String> options, @RequestParam(name = "optionsToDelete", defaultValue = Constant.NOTHING) List<String> optionsToDelete) {
        contractService.setOptions(contract_number, options);
        contractService.setTariff(contract_number, tariffChoice);
        contractService.removeOption(contract_number, optionsToDelete);
        return Constant.REDIRECT;
    }

    @PostMapping("/changeStatus")
    public String changeStatus(@RequestParam("contract_number") String contract_number, @RequestParam("status") String status) {
        contractService.setStatus(contract_number, status);
        return Constant.REDIRECT;
    }

    @GetMapping("/editClient")
    public String editClient(Authentication auth, Model model) {
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("client", clientService.getByEmail(user.getUsername()));
        return "editClient";
    }

    @PostMapping("/editClient")
    public String saveEdit(@RequestParam(name = "name") String name, @RequestParam(name = "surname") String surname, @RequestParam(name = "birthday") String birthday, @RequestParam(name = "address") String address, @RequestParam(name = "passport") String passport, @RequestParam(name = "password") String password) {
        System.out.println(name);
        System.out.println(surname);
        System.out.println(birthday);
        System.out.println(address);
        System.out.println(passport);
        System.out.println(password);
        return "redirect:/home";
    }

}
