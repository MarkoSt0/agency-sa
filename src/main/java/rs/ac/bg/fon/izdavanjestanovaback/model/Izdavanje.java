package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.*;
import lombok.Data;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "izdavanje")
@NamedQueries({
    @NamedQuery(name = "Izdavanje.findAll", query = "SELECT i FROM Izdavanje i"),
    @NamedQuery(name = "Izdavanje.findByIdIzdavanje", query = "SELECT i FROM Izdavanje i WHERE i.idIzdavanje = :idIzdavanje"),
    @NamedQuery(name = "Izdavanje.findByDatumSklapanjaUgovora", query = "SELECT i FROM Izdavanje i WHERE i.datumSklapanjaUgovora = :datumSklapanjaUgovora"),
    @NamedQuery(name = "Izdavanje.findByStatusUgovora", query = "SELECT i FROM Izdavanje i WHERE i.statusUgovora = :statusUgovora"),
    @NamedQuery(name = "Izdavanje.findByNacinPlacanja", query = "SELECT i FROM Izdavanje i WHERE i.nacinPlacanja = :nacinPlacanja"),
    @NamedQuery(name = "Izdavanje.findByUkupanIznos", query = "SELECT i FROM Izdavanje i WHERE i.ukupanIznos = :ukupanIznos"),
    @NamedQuery(name = "Izdavanje.findByNapomena", query = "SELECT i FROM Izdavanje i WHERE i.napomena = :napomena")})
@Data
public class Izdavanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idIzdavanje")
    private Long idIzdavanje;
    @Column(name = "datumSklapanjaUgovora")
    @Temporal(TemporalType.DATE)
    private Date datumSklapanjaUgovora;
    @Column(name = "statusUgovora")
    private String statusUgovora;
    @Column(name = "nacinPlacanja")
    private String nacinPlacanja;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ukupanIznos")
    private Double ukupanIznos;
    @Column(name = "napomena")
    private String napomena;
    @JoinColumn(name = "idAgent", referencedColumnName = "id")
    @ManyToOne
    private Agent idAgent;
    @JoinColumn(name = "idKlijent", referencedColumnName = "id")
    @ManyToOne
    private Klijent idKlijent;
    @OneToMany(mappedBy = "idIzdavanje", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<StavkaIzdavanja> stavkaIzdavanjaCollection;

    public Izdavanje() {
    }

    public Izdavanje(Long idIzdavanje) {
        this.idIzdavanje = idIzdavanje;
    }
}
