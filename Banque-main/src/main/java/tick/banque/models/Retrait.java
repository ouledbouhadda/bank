package tick.banque.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Retrait")
public class Retrait extends Operation{
    public Retrait() {
        super();
    }

    public Retrait(int numOp, LocalDateTime dateOp, int agentId, BigDecimal fraisOp, BigDecimal montant) {
        super(numOp, dateOp, agentId, fraisOp, montant);
    }

    @Override
    public String getType() {
        return "Retrait";
    }
}
