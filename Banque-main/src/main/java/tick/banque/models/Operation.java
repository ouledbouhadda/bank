package tick.banque.models;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Operation")
public abstract class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_op")
    private int numOp;

    @Column(name = "date_op")
    private LocalDateTime dateOp;

    @Column(name = "agent_id")
    private int agentId;

    @Column(name = "frais_op")
    private BigDecimal fraisOp;

    @Column(name = "montant")
    private BigDecimal montant;

    @ManyToOne
    @JoinColumn(name = "num_cpt")
    private Compte compte;

    public Operation() {
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Operation(int numOp, LocalDateTime dateOp, int agentId, BigDecimal fraisOp, BigDecimal montant) {
        this.numOp = numOp;
        this.dateOp = dateOp;
        this.agentId = agentId;
        this.fraisOp = fraisOp;
        this.montant = montant;
    }

    public abstract String getType();

    public int getNumOp() {
        return numOp;
    }

    public void setNumOp(int numOp) {
        this.numOp = numOp;
    }

    public LocalDateTime getDateOp() {
        return dateOp;
    }

    public void setDateOp(LocalDateTime dateOp) {
        this.dateOp = dateOp;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public BigDecimal getFraisOp() {
        return fraisOp;
    }

    public void setFraisOp(BigDecimal fraisOp) {
        this.fraisOp = fraisOp;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
}
