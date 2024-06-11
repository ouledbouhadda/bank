package tick.banque.interfaces;
import tick.banque.models.Agent;
import tick.banque.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientInterface extends JpaRepository<Client,Integer> {
}
