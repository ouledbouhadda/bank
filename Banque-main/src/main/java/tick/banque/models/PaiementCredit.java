package tick.banque.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "PaiementCredit")
public class PaiementCredit extends Operation {

    public PaiementCredit() {
        super();
    }

    public PaiementCredit(int numOp, LocalDateTime dateOp, int agentId, BigDecimal fraisOp, BigDecimal montant) {
        super(numOp, dateOp, agentId, fraisOp, montant);
    }
    @Override
    public String getType() {
        return "Paiement credit";
    }
}
