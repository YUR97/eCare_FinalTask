package ru.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.example.demo.constants.Constant;
import ru.example.demo.model.DTO.ClientDTO;
import ru.example.demo.model.DTO.ContractDTO;
import ru.example.demo.service.ClientService;
import ru.example.demo.service.ContractService;
import ru.example.demo.service.OptionService;
import ru.example.demo.service.TariffService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private OptionService optionService;
    private ContractService contractService;
    private TariffService tariffService;
    private ClientService clientService;

    @Autowired
    public AdminController(OptionService optionService, ContractService contractService, TariffService tariffService, ClientService clientService) {
        this.optionService = optionService;
        this.contractService = contractService;
        this.tariffService = tariffService;
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public String showClients(Model model) {
        List<ClientDTO> clientDTOS = clientService.getAll();
        model.addAttribute("clients", clientDTOS);
        return "adminClients";
    }

    @GetMapping("/conclusionContract")
    public String conclusionContract(Model model) {
        model.addAttribute("tariffs", tariffService.getAll());
        model.addAttribute("options", optionService.getAll());
        return "adminConclusionContract";
    }

    @PostMapping("/conclusionContract")
    public String createContract(@RequestParam(name = "contract", defaultValue = Constant.NOTHING) String contract_number,
                                 @RequestParam(name = "email", defaultValue = Constant.NOTHING) String email,
                                 @RequestParam(name = "tariffName", defaultValue = Constant.NOTHING) String tariffName,
                                 @RequestParam(name = "status", defaultValue = Constant.NOTHING) String status,
                                 @RequestParam(name = "options", defaultValue = Constant.NOTHING) List<String> options) {
        contractService.save(contract_number, email, tariffName, status, options);
        return "redirect:/admin/clients";
    }
}
