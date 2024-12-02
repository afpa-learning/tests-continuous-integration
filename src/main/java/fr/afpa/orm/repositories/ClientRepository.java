package fr.afpa.orm.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.afpa.orm.entities.Client;

@Repository("clientRepository")
public interface ClientRepository extends CrudRepository<Client, UUID>  {   
}
