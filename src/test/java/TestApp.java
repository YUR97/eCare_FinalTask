import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.demo.constants.Constant;
import ru.example.demo.model.*;
import ru.example.demo.model.DTO.ClientDTO;
import ru.example.demo.model.DTO.RoleDTO;
import ru.example.demo.model.DTO.converter.ClientConverterDTO;
import ru.example.demo.repo.ClientRepository;
import ru.example.demo.repo.OptionRepository;
import ru.example.demo.repo.TariffRepository;
import ru.example.demo.service.ClientService;
import ru.example.demo.service.TariffService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestApp {

    @InjectMocks
    ClientService clientService;

    @InjectMocks
    ClientConverterDTO clientConverterDTO;

    @InjectMocks
    TariffService tariffService;

    @Mock
    TariffRepository tariffRepository;

    @Mock
    OptionRepository optionRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    ClientConverterDTO converterDTOMock;


    @Test
    void testGetClient() {

        Client client = new Client();
        client.setEmail("some@email.ru");
        client.setName("someName");
        client.setSurname("someSurname");
        client.setRole(new Role("USER"));

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setEmail("some@email.ru");
        clientDTO.setName("someName");
        clientDTO.setSurname("someSurname");
        clientDTO.setRoleDTO(new RoleDTO("USER"));


        when(clientRepository.findClientByEmail(anyString())).thenReturn(client);
        when(converterDTOMock.convert(client)).thenReturn(clientDTO);
        Assertions.assertEquals(client.getEmail(), clientService.getByEmail(anyString()).getEmail());
    }

    @Test
    void testConvert() {
        Client client = new Client();
        client.setEmail("some@email.ru");
        client.setName("someName");
        client.setSurname("someSurname");
        client.setRole(new Role("USER"));

        ClientDTO clientDTO = clientConverterDTO.convert(client);

        Assertions.assertEquals(client.getEmail(), clientDTO.getEmail());
        Assertions.assertEquals(client.getName(), clientDTO.getName());
        Assertions.assertEquals(client.getSurname(), clientDTO.getSurname());
        Assertions.assertEquals(client.getRole().getName(), clientDTO.getRoleDTO().getName());
    }

    @Test
    void testUpdateTariff() {
        Tariff existTariffOne = new Tariff("existOne", "somePayment");
        Tariff existTariffTwo = new Tariff("existTwo", "somePayment");

        List<String> optionsDelete = new ArrayList<>();
        List<String> optionsAdd = new ArrayList<>();

        optionsDelete.add(Constant.NOTHING);
        optionsAdd.add(Constant.NOTHING);

        when(tariffRepository.findTariffByName("existOne")).thenReturn(existTariffOne);
        when(tariffRepository.findTariffByName("existTwo")).thenReturn(existTariffTwo);
        when(tariffRepository.findTariffByName("notExist")).thenReturn(null);
        when(tariffRepository.findTariffByName(null)).thenReturn(null);

        Assertions.assertFalse(tariffService.updateTariff("existOne", "notExist", null, optionsDelete, optionsAdd));
        Assertions.assertFalse(tariffService.updateTariff("existOne", null, "somePayment", optionsDelete, optionsAdd));
        Assertions.assertFalse(tariffService.updateTariff("existOne", null, null, optionsDelete, optionsAdd));
        Assertions.assertFalse(tariffService.updateTariff("existOne", "existTwo", "somePayment", optionsDelete, optionsAdd));
        Assertions.assertTrue(tariffService.updateTariff("existOne", "existOne", "somePayment", optionsDelete, optionsAdd));
        Assertions.assertTrue(tariffService.updateTariff("existOne", "notExist", "somePayment", optionsDelete, optionsAdd));
    }

    @Test
    void testDeleteTariff() {
        Tariff tariffWithContract = new Tariff("tariffWithContract", "somePayment");
        Tariff tariffWithoutContract = new Tariff("tariffWithoutContract", "somePayment");

        tariffWithContract.addContract(new Contract("someNumber", "open"));

        when(tariffRepository.findTariffByName("tariffWithContract")).thenReturn(tariffWithContract);
        when(tariffRepository.findTariffByName("tariffWithoutContract")).thenReturn(tariffWithoutContract);

        Assertions.assertFalse(tariffService.deleteTariff(tariffWithContract.getName()));
        Assertions.assertTrue(tariffService.deleteTariff(tariffWithoutContract.getName()));

    }

    @Test
    void testCreateTariff() {
        Tariff tariff = new Tariff("exist", "somePayment");
        List<String> options = new ArrayList<>();
        List<String> optionsNothing = new ArrayList<>();
        options.add("someOption");
        optionsNothing.add(Constant.NOTHING);

        Option option = new Option("someOption", "somePayment", "someConnectionPrice");

        when(optionRepository.findByName("someOption")).thenReturn(option);
        when(tariffRepository.findTariffByName("exist")).thenReturn(tariff);
        when(tariffRepository.findTariffByName("notExist")).thenReturn(null);

        Assertions.assertFalse(tariffService.createTariff("notExist", "somePayment", optionsNothing));
        Assertions.assertFalse(tariffService.createTariff(tariff.getName(), tariff.getPayment(), options));
        Assertions.assertTrue(tariffService.createTariff("notExist", "somePayment", options));
    }
}
