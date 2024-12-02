package fr.afpa.orm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import fr.afpa.orm.entities.Client;

public record AccountDto(Long id, LocalDateTime creationTime, BigDecimal balance, Client owner) {
}
