package tick.banque.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import tick.banque.models.Virement;

public interface VirementInterface extends JpaRepository<Virement,Integer> {
}
