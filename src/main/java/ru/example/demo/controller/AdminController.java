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
import ru.example.demo.service.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private OptionService optionService;
    private ContractService contractService;
    private TariffService tariffService;
    private ClientService clientService;
    private LockClientService lockClientService;

    @Autowired
    public AdminController(OptionService optionService, ContractService contractService,
                           TariffService tariffService, ClientService clientService, LockClientService lockClientService) {
        this.optionService = optionService;
        this.contractService = contractService;
        this.tariffService = tariffService;
        this.clientService = clientService;
        this.lockClientService = lockClientService;
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
                    if (contract.getContract_number().contains(finder)) {
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
    public String showContract(@PathVariable("id") String contract_number, Model model) {
        model.addAttribute("contract", contractService.getByContract_number(contract_number));
        return "showContractToAdmin";
    }

    @PostMapping("contract/changeStatus")
    public String showContractChanged(@RequestParam(name = "contract_number") String contract_number,
                                      @RequestParam(name = "status") String status, Model model){
        contractService.setStatusAdmin(contract_number,status);
        model.addAttribute("contract", contractService.getByContract_number(contract_number));
        return "showContractToAdmin";
    }

    @GetMapping("/client/{id}")
    public String showClient(@PathVariable("id") int id, Model model) {
        ClientDTO clientDTO = clientService.getById(id);
        model.addAttribute("client", clientDTO);
        if (lockClientService.getLockedClient(clientDTO.getEmail()) != null) {
            model.addAttribute("isLock", true);
        } else {
            model.addAttribute("isLock", false);
        }
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
        if (lockClientService.getLockedClient(clientDTO.getEmail()) != null) {
            model.addAttribute("isLock", true);
        } else {
            model.addAttribute("isLock", false);
        }
        return "showClientToAdmin";
    }

    @GetMapping("/conclusionContract")
    public String conclusionContract(Model model) {
        model.addAttribute("tariffs", tariffService.getAll());
        model.addAttribute("options", optionService.getAll());
        model.addAttribute("clients", clientService.getAll());
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
        model.addAttribute("tariffs", tariffService.getAll());
        return "adminTariffs";
    }

    @GetMapping("/options")
    public String showOptions(Model model) {
        model.addAttribute("options", optionService.getAll());
        return "adminOptions";
    }
}
