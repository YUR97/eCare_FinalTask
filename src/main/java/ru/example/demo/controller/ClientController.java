package ru.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.example.demo.model.DTO.TariffDTO;
import ru.example.demo.service.ClientService;
import ru.example.demo.service.TariffService;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;
    private TariffService tariffService;

    @Autowired
    public ClientController(ClientService clientService, TariffService tariffService) {
        this.clientService = clientService;
        this.tariffService = tariffService;
    }

    @PostMapping("/me")
    public String showMe(Authentication authentication, Model model) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        model.addAttribute("client", clientService.getByEmail(user.getUsername()));
        return "viewOneClient";
    }

    @GetMapping("/tariffs")
    public String showTariffs(Authentication authentication, Model model) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        List<TariffDTO> tariffsDTO = tariffService.getAll();
        model.addAttribute("tariffs", tariffsDTO);
        model.addAttribute("client", clientService.getByEmail(user.getUsername()));
        return "homeClient";
    }

}
