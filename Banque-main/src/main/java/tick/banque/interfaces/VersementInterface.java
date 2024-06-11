package tick.banque.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import tick.banque.models.Versement;

public interface VersementInterface extends JpaRepository<Versement,Integer> {
}
