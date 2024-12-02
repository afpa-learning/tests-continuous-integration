package fr.afpa.orm.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import fr.afpa.orm.entities.Account;

/**
 * Plus d'informations sur la pattern DTO : https://medium.com/@zubeyrdamar/java-spring-boot-handling-infinite-recursion-a95fe5a53c92
 */
public record ClientDto(UUID id, String firstname, String lastname, String email, LocalDate birthdate, List<Account> accounts) {
}
