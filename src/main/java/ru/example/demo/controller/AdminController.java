package ru.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.demo.constants.Constant;
import ru.example.demo.model.DTO.ClientDTO;
import ru.example.demo.model.DTO.ContractDTO;
import ru.example.demo.model.DTO.OptionDTO;
import ru.example.demo.model.DTO.TariffDTO;
import ru.example.demo.service.ClientService;
import ru.example.demo.service.ContractService;
import ru.example.demo.service.OptionService;
import ru.example.demo.service.TariffService;

import java.util.ArrayList;
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
        model.addAttribute("finder", Constant.DEFAULT_FINDER);
        model.addAttribute("clients", clientDTOS);
        return "adminClients";
    }

    @PostMapping("/clients")
    public String showFoundClients(@RequestParam(name = "finder", defaultValue = Constant.NOTHING) String finder, Model model) {
        List<ClientDTO> clientDTOS = clientService.getAll();
        if (finder.contains("all")) {
            model.addAttribute("finder", finder);
            model.addAttribute("clients", clientDTOS);
        } else {
            List<ClientDTO> toOutClientDTOS = new ArrayList<>();
            for (ClientDTO client : clientDTOS) {
                for (ContractDTO contract : client.getContractsDTO()) {
                    if(contract.getContract_number().contains(finder)){
                        toOutClientDTOS.add(client);
                        break;
                    }
                }
            }
            model.addAttribute("finder", finder);
            model.addAttribute("clients", toOutClientDTOS);
        }
        return "adminClients";
    }

    @GetMapping("/contract/{id}")
    public String showContract(@PathVariable("id") String contract_number, Model model){
        model.addAttribute("contract", contractService.getByContract_number(contract_number));
        return "showContractToAdmin";
    }

    @GetMapping("/client/{id}")
    public String showClient(@PathVariable("id") int id, Model model){
        model.addAttribute("client", clientService.getById(id));
        return "showClientToAdmin";
    }

    @GetMapping("/conclusionContract")
    public String conclusionContract(Model model) {
        model.addAttribute("tariffs", tariffService.getAll());
        model.addAttribute("options", optionService.getAll());
        return "adminConclusionContract";
    }

    @PostMapping("/conclusionContract")
    public String createContract(@RequestParam(name = "contract", defaultValue = Constant.NOTHING) String contract_number, @RequestParam(name = "email", defaultValue = Constant.NOTHING) String email, @RequestParam(name = "tariffName", defaultValue = Constant.NOTHING) String tariffName, @RequestParam(name = "status", defaultValue = Constant.NOTHING) String status, @RequestParam(name = "options", defaultValue = Constant.NOTHING) List<String> options) {
        contractService.save(contract_number, email, tariffName, status, options);
        return "redirect:/admin/clients";
    }

    @GetMapping("/edit")
    public String editAdmin(Authentication auth, Model model) {
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("client", clientService.getByEmail(user.getUsername()));
        return "editAdmin";
    }

    @PostMapping("/edit")
    public String saveEdit(@RequestParam(name = "name") String name, @RequestParam(name = "surname") String surname, @RequestParam(name = "birthday") String birthday, @RequestParam(name = "address") String address, @RequestParam(name = "passport") String passport, @RequestParam(name = "password") String password) {
        System.out.println(name);
        System.out.println(surname);
        System.out.println(birthday);
        System.out.println(address);
        System.out.println(passport);
        System.out.println(password);
        return "redirect:/home";
    }

    @GetMapping("/tariffs")
    public String showTariffs(Model model){
        model.addAttribute("tariffs", tariffService.getAll());
        return "adminTariffs";
    }

    @GetMapping("/options")
    public String showOptions(Model model){
        model.addAttribute("options", optionService.getAll());
        return "adminOptions";
    }
}
