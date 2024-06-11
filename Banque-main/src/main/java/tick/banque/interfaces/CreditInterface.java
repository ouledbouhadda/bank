package tick.banque.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import tick.banque.models.Credit;

public interface CreditInterface extends JpaRepository<Credit,Integer> {
}
