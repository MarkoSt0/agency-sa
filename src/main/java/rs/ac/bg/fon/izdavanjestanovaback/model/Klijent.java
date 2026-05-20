package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "klijent")
@NamedQueries({
    @NamedQuery(name = "Klijent.findAll", query = "SELECT k FROM Klijent k"),
    @NamedQuery(name = "Klijent.findById", query = "SELECT k FROM Klijent k WHERE k.id = :id"),
    @NamedQuery(name = "Klijent.findByIme", query = "SELECT k FROM Klijent k WHERE k.ime = :ime"),
    @NamedQuery(name = "Klijent.findByPrezime", query = "SELECT k FROM Klijent k WHERE k.prezime = :prezime"),
    @NamedQuery(name = "Klijent.findByStarost", query = "SELECT k FROM Klijent k WHERE k.starost = :starost"),
    @NamedQuery(name = "Klijent.findByBrojTelefona", query = "SELECT k FROM Klijent k WHERE k.brojTelefona = :brojTelefona"),
    @NamedQuery(name = "Klijent.findByEmail", query = "SELECT k FROM Klijent k WHERE k.email = :email")})
@Data
@NoArgsConstructor
public class Klijent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "ime")
    private String ime;
    @Column(name = "prezime")
    private String prezime;
    @Column(name = "starost")
    private Integer starost;
    @Column(name = "brojTelefona")
    private String brojTelefona;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "idKlijent")
    private Collection<Izdavanje> izdavanjeCollection;
    @JoinColumn(name = "idMesto", referencedColumnName = "id")
    @ManyToOne
    private Mesto idMesto;

}
