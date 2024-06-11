package tick.banque.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Virement")
public class Virement extends Operation{
    @Column(name = "dest")
    private int destinataire;

    @Column(name = "src")
    private int source;

    public Virement() {
        super();
    }

    public Virement(int numOp, LocalDateTime dateOp, int agentId, BigDecimal fraisOp, BigDecimal montant, int destinataire, int source) {
        super(numOp, dateOp, agentId, fraisOp, montant);
        this.destinataire = destinataire;
        this.source = source;
    }

    public int getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(int destinataire) {
        this.destinataire = destinataire;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    @Override
    public String getType() {
        return "Virement";
    }
}
