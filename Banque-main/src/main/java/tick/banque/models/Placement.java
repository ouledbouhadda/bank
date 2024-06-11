package tick.banque.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Placement")
public class Placement extends Operation{

    @Column(name = "interet")
    private BigDecimal interet;

    @Column(name = "nouveau_montant")
    private BigDecimal nouveauMontant;

    public Placement() {
        super();
    }

    public Placement(int numOp, LocalDateTime dateOp, int agentId, BigDecimal fraisOp, BigDecimal montant, BigDecimal interet, BigDecimal nouveauMontant) {
        super(numOp, dateOp, agentId, fraisOp, montant);
        this.interet = interet;
        this.nouveauMontant = nouveauMontant;
    }

    public BigDecimal getInteret() {
        return interet;
    }

    public void setInteret(BigDecimal interet) {
        this.interet = interet;
    }

    public BigDecimal getNouveauMontant() {
        return nouveauMontant;
    }

    public void setNouveauMontant(BigDecimal nouveauMontant) {
        this.nouveauMontant = nouveauMontant;
    }

    @Override
    public String getType() {
        return "Placement";
    }
}
