package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.*;
import lombok.Data;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "mesto")
@NamedQueries({
    @NamedQuery(name = "Mesto.findAll", query = "SELECT m FROM Mesto m"),
    @NamedQuery(name = "Mesto.findById", query = "SELECT m FROM Mesto m WHERE m.id = :id"),
    @NamedQuery(name = "Mesto.findByNazivMesta", query = "SELECT m FROM Mesto m WHERE m.nazivMesta = :nazivMesta"),
    @NamedQuery(name = "Mesto.findByOpstina", query = "SELECT m FROM Mesto m WHERE m.opstina = :opstina")})
@Data
public class Mesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "nazivMesta")
    private String nazivMesta;
    @Column(name = "opstina")
    private String opstina;
    @OneToMany(mappedBy = "idMesto")
    private Collection<Klijent> klijentCollection;

    public Mesto() {
    }

    public Mesto(Long id) {
        this.id = id;
    }

    public Mesto(Long id, String nazivMesta) {
        this.id = id;
        this.nazivMesta = nazivMesta;
    }

    public Mesto(Long id, String nazivMesta, String opstina) {
        this.id = id;
        this.nazivMesta = nazivMesta;
        this.opstina = opstina;
    }
}
