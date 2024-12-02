package fr.afpa.orm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Classe principale du projet.
 * 
 * Cette annotation est FONDAMENTALE pour activer la recherche automatique des beans dans les différents packages de l'
 * 
 * Documentation -> https://medium.com/@boris.alexandre.rose/springbootapplication-ab08032a7049
 * 
 */
@EntityScan
@SpringBootApplication
public class AccountRestApiApp {
    public static void main(String[] args) {
        SpringApplication.run(AccountRestApiApp.class, args);
    }
}
