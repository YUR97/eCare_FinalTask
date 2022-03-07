package ru.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demo.model.Client;
import ru.example.demo.repo.ClientRepository;

@Service
public class ClientDetailService implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientDetailService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Client client = clientRepository.findClientByEmail(userName);
        if (client == null) {
            throw new UsernameNotFoundException("Unknown user: " + userName);
        }
        return User.builder()
                .username(client.getEmail())
                .password(client.getPassword())
                .roles(client.getRole().getName())
                .build();
    }

}
