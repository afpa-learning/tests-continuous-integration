package fr.afpa.orm.web.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.afpa.orm.dto.AccountDto;
import fr.afpa.orm.entities.Account;
import fr.afpa.orm.repositories.AccountRepository;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {
    private AccountRepository accountRepository;


    public AccountRestController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto toAccountDto(Account account) {
        return new AccountDto(account.getId(),
                              account.getCreationTime(),
                              account.getBalance(), 
                              account.getOwner());
    }

    @GetMapping
    public List<AccountDto> getAll() {
        Iterable<Account> accounts = accountRepository.findAll();
        Stream<Account> accountsStream = StreamSupport.stream(accounts.spliterator(), false);
        
        return accountsStream.map(this::toAccountDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getOne(@PathVariable long id) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()) {
            return new ResponseEntity<>(toAccountDto(account.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto create(@RequestBody Account account) {
        return toAccountDto(accountRepository.save(account));
    }

    /**
     * TODO implémenter une méthode qui traite les requêtes PUT
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long id, @RequestBody Account account) {
        if (!Objects.equals(id, account.getId())) {
            throw new IllegalStateException("Id parameter does not match account body value");
        }
        accountRepository.save(account);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable long id, HttpServletResponse response) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            accountRepository.delete(account.get());
        }
    }
}