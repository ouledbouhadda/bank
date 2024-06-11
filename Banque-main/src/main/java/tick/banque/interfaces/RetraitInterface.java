package tick.banque.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import tick.banque.models.Retrait;

public interface RetraitInterface extends JpaRepository<Retrait, Integer> {
}
