package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
import lombok.Data;

/**
 *
 * @author Marko
 */
@Entity
@Data
@Table(name = "stavka_izdavanja")
@IdClass(StavkaIzdavanjaPK.class)
@NamedQueries({
    @NamedQuery(name = "StavkaIzdavanja.findAll", query = "SELECT s FROM StavkaIzdavanja s"),
    @NamedQuery(name = "StavkaIzdavanja.findByRb", query = "SELECT s FROM StavkaIzdavanja s WHERE s.rb = :rb"),
    @NamedQuery(name = "StavkaIzdavanja.findByDatumPocetkaIzdavanja", query = "SELECT s FROM StavkaIzdavanja s WHERE s.datumPocetkaIzdavanja = :datumPocetkaIzdavanja"),
    @NamedQuery(name = "StavkaIzdavanja.findByDatumZavrsetkaIzdavanja", query = "SELECT s FROM StavkaIzdavanja s WHERE s.datumZavrsetkaIzdavanja = :datumZavrsetkaIzdavanja"),
    @NamedQuery(name = "StavkaIzdavanja.findByMesecnaKirija", query = "SELECT s FROM StavkaIzdavanja s WHERE s.mesecnaKirija = :mesecnaKirija"),
    @NamedQuery(name = "StavkaIzdavanja.findByIznosDepozita", query = "SELECT s FROM StavkaIzdavanja s WHERE s.iznosDepozita = :iznosDepozita")})
public class StavkaIzdavanja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rb")
    private Long rb;
    @Column(name = "datumPocetkaIzdavanja")
    @Temporal(TemporalType.DATE)
    private Date datumPocetkaIzdavanja;
    @Column(name = "datumZavrsetkaIzdavanja")
    @Temporal(TemporalType.DATE)
    private Date datumZavrsetkaIzdavanja;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mesecnaKirija")
    private Double mesecnaKirija;
    @Column(name = "iznosDepozita")
    private Double iznosDepozita;
    @JoinColumn(name = "idIzdavanje", referencedColumnName = "idIzdavanje")
    @ManyToOne
    @Id
    private Izdavanje idIzdavanje;
    @JoinColumn(name = "idNekretnina", referencedColumnName = "idNekretnina")
    @ManyToOne
    private Nekretnina idNekretnina;

    public StavkaIzdavanja() {
    }

    public StavkaIzdavanja(Long rb) {
        this.rb = rb;
    }    
}
