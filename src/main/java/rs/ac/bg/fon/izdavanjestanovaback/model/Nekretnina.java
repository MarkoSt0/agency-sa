package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.*;
import lombok.Data;
import rs.ac.bg.fon.izdavanjestanovaback.enums.StatusNekretnine;
import rs.ac.bg.fon.izdavanjestanovaback.enums.TipGrejanja;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "nekretnina")
@NamedQueries({
    @NamedQuery(name = "Nekretnina.findAll", query = "SELECT n FROM Nekretnina n"),
    @NamedQuery(name = "Nekretnina.findByIdNekretnina", query = "SELECT n FROM Nekretnina n WHERE n.idNekretnina = :idNekretnina"),
    @NamedQuery(name = "Nekretnina.findByAdresa", query = "SELECT n FROM Nekretnina n WHERE n.adresa = :adresa"),
    @NamedQuery(name = "Nekretnina.findByPovrsina", query = "SELECT n FROM Nekretnina n WHERE n.povrsina = :povrsina"),
    @NamedQuery(name = "Nekretnina.findBySprat", query = "SELECT n FROM Nekretnina n WHERE n.sprat = :sprat"),
    @NamedQuery(name = "Nekretnina.findByBrojSoba", query = "SELECT n FROM Nekretnina n WHERE n.brojSoba = :brojSoba"),
    @NamedQuery(name = "Nekretnina.findByGodinaIzgradnje", query = "SELECT n FROM Nekretnina n WHERE n.godinaIzgradnje = :godinaIzgradnje"),
    @NamedQuery(name = "Nekretnina.findByOpis", query = "SELECT n FROM Nekretnina n WHERE n.opis = :opis")})
@Data
public class Nekretnina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idNekretnina")
    private Long idNekretnina;
    @Column(name = "adresa")
    private String adresa;
    @Column(name = "povrsina")
    private String povrsina;
    @Column(name = "sprat")
    private String sprat;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "brojSoba")
    private Double brojSoba;
    @Column(name = "godinaIzgradnje")
    private Integer godinaIzgradnje;
    @Column(name = "opis")
    private String opis;
    @OneToMany(mappedBy = "idNekretnina", fetch = FetchType.EAGER)
    private Collection<StavkaIzdavanja> stavkaIzdavanjaCollection;
    @JoinColumn(name = "idTipaNekretnine", referencedColumnName = "idTipaNekretnine")
    @ManyToOne
    private TipNekretnine tipNekretnine;
    
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "tipGrejanja")
    private TipGrejanja tipGrejanja;
    
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "statusNekretnine")
    private StatusNekretnine statusNekretnine;

    public Nekretnina() {
    }

    public Nekretnina(Long idNekretnina) {
        this.idNekretnina = idNekretnina;
    }
}
