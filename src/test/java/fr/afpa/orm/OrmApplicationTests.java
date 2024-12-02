package fr.afpa.orm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Déclaration d'une classe qui contiendra toutes les méthodes de test portant sur des tests d'intégration
 * permettant de simuler des requête HTTP vers des contrôleurs.
 * 
 * Ces tests permettent de tester les interactions entre les couches suivantes :
 * - la couche "contrôleur" ;
 * - la couche "repository" ;
 * - la couche de base de données.
 */
@SpringBootTest
class OrmApplicationTests {
	
	@Autowired
    private WebApplicationContext applicationContext;

	/**
	 * Objet d'une classe permettant de simuler les requêtes HTTP
	 */
    private MockMvc mockMvc;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

	/**
	 * Méthode de test qui vérifie qu'une requête vers le endpoint "/api/accounts/{id}" renvoie les informations d'un compte bancaire correctement structurées
	 * @throws Exception
	 */
	@Test
	public void get_account_ok_response_and_ok_jsonstructure() throws Exception {
		mockMvc.perform(get("/api/accounts/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()) // vérification du status de la réponse du serveur à la requête HTTP
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)) // vérification du type de la réponse serveur
			.andExpect(jsonPath("$.id").exists()) // vérification  de l'existance de la clé "id" dans la réponse
			.andExpect(jsonPath("$.creationTime").exists()) // vérification  de l'existance de la clé "creationTime" dans la réponse
			// .andExpect(jsonPath("$.balance").exists()) // vérification  de l'existance de la clé "creationTime" dans la réponse
			.andExpect(jsonPath("$.owner").exists()); // vérification  de l'existance de la clé "creationTime" dans la réponse
	}

	/**
	 * Méthode de test qui vérifie qu'une requête vers le endpoint "/api/accounts" renvoie le nombre correct d'informations de comptes bancaires
	 * @throws Exception
	 */
	@Test
	public void get_accounts_ok_response() throws Exception {

		// ici, en plus d'effectuer la vérification sur le status de la requête, on récupère le résultat dans la variable "result"
		MvcResult result = mockMvc.perform(get("/api/accounts").contentType(MediaType.APPLICATION_JSON))
								.andExpect(status().isOk())
								.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
								.andReturn(); // la méthode "andReturn" permet de récupéer le contenu de la réponse

		// on récupère le contenu de la réponse du serveur à partir du résultat pour pouvoir en déduire le nombre de Json définissant les comptes bancaires récupérés
		String stringResult = result.getResponse().getContentAsString();

		// instanciation d'un "ObjectMapper" Jackson pour transformation de la chaîne de caractères en JsonNode (pour pouvoir compter le nombre de résultats)
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode arrayNode = (ArrayNode) mapper.readTree(stringResult);
		
		// vérification du nombre de Json trouvés
		assertEquals(200, arrayNode.size());
	}
}
