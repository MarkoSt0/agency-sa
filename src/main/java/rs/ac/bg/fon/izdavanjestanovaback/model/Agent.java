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
@Table(name = "agent")
@NamedQueries({
    @NamedQuery(name = "Agent.findAll", query = "SELECT a FROM Agent a"),
    @NamedQuery(name = "Agent.findById", query = "SELECT a FROM Agent a WHERE a.id = :id"),
    @NamedQuery(name = "Agent.findByBrojLicence", query = "SELECT a FROM Agent a WHERE a.brojLicence = :brojLicence"),
    @NamedQuery(name = "Agent.findByDatumIstekaLicence", query = "SELECT a FROM Agent a WHERE a.datumIstekaLicence = :datumIstekaLicence"),
    @NamedQuery(name = "Agent.findByIme", query = "SELECT a FROM Agent a WHERE a.ime = :ime"),
    @NamedQuery(name = "Agent.findByPrezime", query = "SELECT a FROM Agent a WHERE a.prezime = :prezime"),
    @NamedQuery(name = "Agent.findByKorisnickoIme", query = "SELECT a FROM Agent a WHERE a.korisnickoIme = :korisnickoIme"),
    @NamedQuery(name = "Agent.findBySifra", query = "SELECT a FROM Agent a WHERE a.sifra = :sifra")})
@Data
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "brojLicence")
    private String brojLicence;
    @Column(name = "datumIstekaLicence")
    @Temporal(TemporalType.DATE)
    private Date datumIstekaLicence;
    @Basic(optional = false)
    @Column(name = "ime")
    private String ime;
    @Column(name = "prezime")
    private String prezime;
    @Column(name = "korisnickoIme")
    private String korisnickoIme;
    @Column(name = "sifra")
    private String sifra;
    @OneToMany(mappedBy = "idAgent")
    private Collection<Izdavanje> izdavanjeCollection;
    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Sertifikat> sertifikatCollection;

    public Agent() {
    }

    public Agent(Long id) {
        this.id = id;
    }

    public Agent(Long id, String ime) {
        this.id = id;
        this.ime = ime;
    }
}
