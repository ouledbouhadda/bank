package tick.banque.models;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;


@Entity
@Table(name = "Compte")
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_cpt")
    private int numCpt;

    @Column(name = "montant_global")
    private BigDecimal montantGlobal;

    @Column(name = "montant_init")
    private BigDecimal montantInit;

    @Column(name = "code_cli")
    private int codeCli;
    @OneToMany(mappedBy = "compte")
    private List<Operation> operations;

    public Compte() {
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public Compte(int numCpt, BigDecimal montantGlobal, BigDecimal montantInit, int codeCli) {
        this.numCpt = numCpt;
        this.montantGlobal = montantGlobal;
        this.montantInit = montantInit;
        this.codeCli = codeCli;
    }

    public int getNumCpt() {
        return numCpt;
    }

    public void setNumCpt(int numCpt) {
        this.numCpt = numCpt;
    }

    public BigDecimal getMontantInit() {
        return montantInit;
    }

    public void setMontantInit(BigDecimal montantInit) {
        this.montantInit = montantInit;
    }

    public BigDecimal getMontantGlobal() {
        return montantGlobal;
    }

    public void setMontantGlobal(BigDecimal montantGlobal) {
        this.montantGlobal = montantGlobal;
    }

    public int getCodeCli() {
        return codeCli;
    }

    public void setCodeCli(int codeCli) {
        this.codeCli = codeCli;
    }
}
