package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Predstavlja domensku klasu za klijenta koji se pojavljuje u ulozi zakupca nekretnine.
 * Klasa je mapirana na tabelu "klijent" u bazi podataka.
 * Sadrzi osnovne licne i kontakt podatke klijenta, kao i relacije prema
 * mestu iz kojeg klijent dolazi i svim izdavanjima koja su vezana za njega.
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
    /**
     * Identifikacioni broj za serijalizaciju objekta.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Jedinstveni identifikator klijenta u bazi podataka (Primarni kljuc).
     * Generise se automatski koriscenjem IDENTITY strategije.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    /**
     * Ime klijenta. Polje je obavezno i ne moze imati null vrednost u bazi.
     */
    @Basic(optional = false)
    @Column(name = "ime")
    private String ime;
    
    /**
     * Prezime klijenta.
     */
    @Column(name = "prezime")
    private String prezime;
    
    /**
     * Starost (broj godina) klijenta.
     */
    @Column(name = "starost")
    private Integer starost;
    
    /**
     * Kontakt telefon klijenta. Moze biti u bilo kom obliku.
     */
    @Column(name = "brojTelefona")
    private String brojTelefona;
    
    /**
     * Elektronska posta (email) klijenta za slanje obavestenja i ugovora.
     */
    @Column(name = "email")
    private String email;
    
    /**
     * Kolekcija svih izdavanja u kojima ovaj klijent ucestvuje kao zakupac.
     * Predstavlja inverznu stranu veze jedan prema vise koja je mapirana poljem idKlijent.
     */
    @OneToMany(mappedBy = "idKlijent")
    private Collection<Izdavanje> izdavanjeCollection;
    
    /**
     * Mesto (grad/opstina) iz kojeg klijent dolazi ili u kojem ima prebivaliste.
     * Predstavlja spoljni kljuc (ManyToOne veza) koji referencira primarni kljuc klase Mesto.
     */
    @JoinColumn(name = "idMesto", referencedColumnName = "id")
    @ManyToOne
    private Mesto idMesto;

}
