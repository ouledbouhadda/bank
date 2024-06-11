package tick.banque.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_cli")
    private int codeCli;

    @Column(name = "nom_cli")
    private String nomCli;

    @Column(name = "pre_cli")
    private String preCli;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "num_tel")
    private String numTel;

    @Column(name = "email_cli")
    private String emailCli;

    @Column(name = "adr_cli")
    private String adrCli;

    public Client() {
    }

    public Client(int codeCli, String adrCli, String emailCli, String numTel, LocalDate dateNaissance, String preCli, String nomCli) {
        this.codeCli = codeCli;
        this.adrCli = adrCli;
        this.emailCli = emailCli;
        this.numTel = numTel;
        this.dateNaissance = dateNaissance;
        this.preCli = preCli;
        this.nomCli = nomCli;
    }

    public int getCodeCli() {
        return codeCli;
    }

    public void setCodeCli(int codeCli) {
        this.codeCli = codeCli;
    }

    public String getNomCli() {
        return nomCli;
    }

    public void setNomCli(String nomCli) {
        this.nomCli = nomCli;
    }

    public String getPreCli() {
        return preCli;
    }

    public void setPreCli(String preCli) {
        this.preCli = preCli;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getEmailCli() {
        return emailCli;
    }

    public void setEmailCli(String emailCli) {
        this.emailCli = emailCli;
    }

    public String getAdrCli() {
        return adrCli;
    }

    public void setAdrCli(String adrCli) {
        this.adrCli = adrCli;
    }
}
