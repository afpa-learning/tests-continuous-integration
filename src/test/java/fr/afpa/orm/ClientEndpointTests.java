package fr.afpa.orm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.afpa.orm.entities.Client;
import fr.afpa.orm.repositories.ClientRepository;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest
public class ClientEndpointTests {
    @Autowired
    private WebApplicationContext applicationContext;

	/**
	 * Objet d'une classe permettant de simuler les requêtes HTTP
	 */
    private MockMvc mockMvc;

    /**
     * On "mock" le repository de User afin de le simuler
     * --> CECI N'INCLUT PAS D'INTERACTION AVEC LA BASE DE DONNEES
     */
    @MockitoBean
    private ClientRepository clientRepository;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    /**
     * Vérifie qu'une erreur 400 est bien reçu en cas de mauvais paramètre d'entrée
     * 
     * @throws Exception
     */
    @Test
	public void get_client_with_bad_uuid_should_return_400_error() throws Exception {

        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isBadRequest()); // retour du serveur = 400 bad request

        mockMvc.perform(get("/api/clients/hacking"))
                .andExpect(status().isBadRequest()); // retour du serveur = 400 bad request

        mockMvc.perform(get("/api/clients/0-0-0"))
                .andExpect(status().isBadRequest()); // retour du serveur = 400 bad request
    }

    /**
     * Le test suivant simule deux choses :
     * - une requête HTTP
     * - la récupération d'un client à partir d'une base de données
     * 
     * Pour simuler un client un "mock" est utilisé.
     * 
     * Ce test mène également la vérification de la structure JSon obtenue.
     * 
     * Pour se faire la fonction utilise un validateur de schéma json.path/
     * Plus d'information ici : https://www.jvt.me/posts/2021/12/20/validate-json-schema-spring-response/
     * 
     * Il vous est possible de tester les schéma JSon avec des outils tels que : https://www.jsonschemavalidator.net/
     * 
     * Ne pas oublier la dépendance suivante :
     * <dependency>
			<groupId>io.rest-assured</groupId>
			<artifactId>json-schema-validator</artifactId>
			<version>5.5.6</version>
		</dependency>
     * 
     * @throws Exception
     */
    @Test
	public void get_client_should_return_ok_json() throws Exception {

        // déclaration des variables permettant d'instancier un client
        UUID uuid = UUID.randomUUID();
        LocalDate date = LocalDate.of(1985, 1, 8);

        Client mockedClient = new Client(uuid, "Bobby", "Bub", "bobby@mail.com", date);
        
        // on configure le mock de "clientRepository" pour qu'on retourne un Optional contenant le client "mocké"
        when(clientRepository.findById(uuid)).thenReturn(Optional.of(mockedClient));

        // on simule la requête HTTP
        // et on vérifie grâce au schéma de validation la bonne structure JSon
        mockMvc.perform(get("/api/clients/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().string(matchesJsonSchemaInClasspath("client_schema.json"))); // retour du serveur = 400 bad request
                
    }
}
