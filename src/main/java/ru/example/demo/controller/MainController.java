package ru.example.demo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.example.demo.constants.Constant;
import ru.example.demo.model.DTO.ClientDTO;
import ru.example.demo.service.ClientService;
import ru.example.demo.service.LockClientService;

@Log4j2
@Controller
public class MainController {

    private final ClientService clientService;
    private final LockClientService lockClientService;

    @Autowired
    public MainController(ClientService clientService, LockClientService lockClientService) {
        this.clientService = clientService;
        this.lockClientService = lockClientService;
    }

    @GetMapping("/home")
    public String hello(Authentication authentication, Model model) {
        if (authentication != null) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            ClientDTO clientDTO = clientService.getByEmail(user.getUsername());
            model.addAttribute("client", clientDTO);
            if (clientDTO.getRoleDTO().getName().contains(Constant.ADMIN)) {
                log.info("Вход в аккаунт администратора");
                return "homeAdmin";
            } else {
                model.addAttribute("isLock", lockClientService.getLockedClient(user.getUsername()) != null);
                log.info("Вход в аккаунт клиента");
                return "homeClient";
            }
        } else {
            log.warn("Попытка получения домашней страницы без авторизации");
            return "redirect:/login";
        }

    }
}
