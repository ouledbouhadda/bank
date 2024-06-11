package tick.banque.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import tick.banque.models.Agent;

import java.util.Optional;

public interface AgentInterface extends JpaRepository<Agent,Integer> {
    Optional<Agent> findByUsername(String username);
}
