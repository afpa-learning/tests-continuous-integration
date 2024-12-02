package fr.afpa.orm.web.controllers;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.afpa.orm.dto.ClientDto;
import fr.afpa.orm.entities.Client;
import fr.afpa.orm.repositories.ClientRepository;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {
    
    private final ClientRepository userRepository;
    
    public ClientRestController(ClientRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ClientDto toUserDto(Client user) {
        return new ClientDto(user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            user.getBirthdate(),
                            user.getAccounts());
    }

    @GetMapping
    public Iterable<ClientDto> getAll() {
        Iterable<Client> users = userRepository.findAll();
        Stream<Client> usersStream = StreamSupport.stream(users.spliterator(), false);
        
        return usersStream.map(this::toUserDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable UUID id) {
        Optional<Client> user = userRepository.findById(id);
        if (user.isPresent())
            return new ResponseEntity<>(toUserDto(user.get()), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
