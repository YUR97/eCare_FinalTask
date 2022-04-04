package ru.example.demo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.demo.model.DTO.OptionDTO;
import ru.example.demo.model.DTO.TariffDTO;
import ru.example.demo.service.OptionService;
import ru.example.demo.service.TariffService;

import java.util.List;

@Log4j2
@RestController
public class ResponseController {

    private TariffService tariffService;
    private OptionService optionService;

    @Autowired
    public ResponseController(TariffService tariffService, OptionService optionService) {
        this.tariffService = tariffService;
        this.optionService = optionService;
    }

    @CrossOrigin
    @GetMapping("/tariffs")
    public List<TariffDTO> sendTariffs() {
        log.info("Отправка данных о тарифах клиент-приложению");
        return tariffService.getAll();
    }

    @CrossOrigin
    @GetMapping("/options")
    public List<OptionDTO> sendOptions() {
        log.info("Отправка данных об опциях клиент-приложению");
        return optionService.getAll();
    }

}
