package tick.banque.models;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Credit")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_cre")
    private int numCre;

    @Column(name = "date_cre", columnDefinition = "DATETIME(6)")
    private LocalDateTime dateCre;

    @Column(name = "mont_cre")
    private BigDecimal montCre;

    @Column(name = "code_cli")
    private int codeCli;

    @Column(name = "num_cpt")
    private int numCpt;

    @Column(name = "files_path")
    private String filesPath;

    @Column(name = "status")
    private String status;

    public Credit() {
    }

    public Credit(int numCre, LocalDateTime dateCre, BigDecimal montCre, int codeCli, int numCpt, String filesPath, String status) {
        this.numCre = numCre;
        this.dateCre = dateCre;
        this.montCre = montCre;
        this.codeCli = codeCli;
        this.numCpt = numCpt;
        this.filesPath = filesPath;
        this.status = status;
    }

    public int getNumCre() {
        return numCre;
    }

    public void setNumCre(int numCre) {
        this.numCre = numCre;
    }

    public LocalDateTime getDateCre() {
        return dateCre;
    }

    public void setDateCre(LocalDateTime dateCre) {
        this.dateCre = dateCre;
    }

    public BigDecimal getMontCre() {
        return montCre;
    }

    public void setMontCre(BigDecimal montCre) {
        this.montCre = montCre;
    }

    public int getCodeCli() {
        return codeCli;
    }

    public void setCodeCli(int codeCli) {
        this.codeCli = codeCli;
    }

    public String getFilesPath() {
        return filesPath;
    }

    public void setFilesPath(String filesPath) {
        this.filesPath = filesPath;
    }

    public int getNumCpt() {
        return numCpt;
    }

    public void setNumCpt(int numCpt) {
        this.numCpt = numCpt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
