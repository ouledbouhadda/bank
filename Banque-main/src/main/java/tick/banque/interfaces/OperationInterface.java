package tick.banque.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import tick.banque.models.Compte;
import tick.banque.models.Operation;

import java.util.List;

public interface OperationInterface extends JpaRepository<Operation, Integer> {
    List<Operation> findByCompte(Compte compte);
}
