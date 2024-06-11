package tick.banque.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import tick.banque.models.Compte;

import java.util.Optional;

public interface CompteInterface extends JpaRepository<Compte,Integer> {
    Optional<Compte> findByCodeCli(int codeCli);
}
