package ru.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.example.demo.model.DTO.ClientDTO;
import ru.example.demo.service.ClientService;

@Controller
public class LoginController {

    private ClientService clientService;

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
            clientService.signUp(clientDTO, role);
            return "redirect:/login";
        }
        catch (Exception e){
            model.addAttribute("error", "Выбранный Email уже используется");
            return "signUp";
        }
    }


}
