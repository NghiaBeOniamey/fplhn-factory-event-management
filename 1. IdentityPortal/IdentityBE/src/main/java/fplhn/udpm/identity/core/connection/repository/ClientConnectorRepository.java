package fplhn.udpm.identity.core.connection.repository;

import fplhn.udpm.identity.entity.Client;
import fplhn.udpm.identity.repository.ClientRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientConnectorRepository extends ClientRepository {

    @Query("SELECT c FROM Client c WHERE c.clientId = :clientId AND c.clientSecret = :clientSecret")
    Optional<Client> findByClientIdAndClientSecret(String clientId, String clientSecret);

}
