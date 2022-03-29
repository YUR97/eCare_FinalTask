package ru.example.demo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.example.demo.model.DTO.ClientDTO;
import ru.example.demo.service.ClientService;

@Log4j2
@Controller
public class LoginController {

    private final ClientService clientService;

    @Autowired
    public LoginController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/signup")
    public String signPage(@ModelAttribute("client") ClientDTO clientDTO) {
        return "signUp";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute("client") ClientDTO clientDTO, @RequestParam(name = "role_name") String role, Model model) {
        try {
            log.info("Успешная регистрация клиента");
            clientService.signUp(clientDTO, role);
            return "redirect:/login";
        } catch (Exception e) {
            log.warn("Попытка регистрации под существующий Email");
            model.addAttribute("error", "emailExist");
            return "signUp";
        }
    }

}
