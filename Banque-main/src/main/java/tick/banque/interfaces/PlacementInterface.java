package tick.banque.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import tick.banque.models.Placement;

public interface PlacementInterface extends JpaRepository<Placement,Integer> {
}
