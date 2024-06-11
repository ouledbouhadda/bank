package tick.banque.interfaces;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import tick.banque.models.PaiementCredit;

public interface PaiementCreditInterface extends JpaRepository<PaiementCredit, Integer> {
}
