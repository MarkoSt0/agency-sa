package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Klijent implements Serializable {
    /**
     * Identifikacioni broj za serijalizaciju objekta.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Jedinstveni identifikator klijenta u bazi podataka (Primarni kljuc).
     * Generise se automatski koriscenjem IDENTITY strategije.
     * Vrednost ne sme biti null, a maksimalna vrednost odgovara BIGINT(20) koloni u bazi.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    /**
     * Ime klijenta. Polje je obavezno i ne moze imati null vrednost u bazi.
     * Polje je obavezno i ne sme biti null niti sadrzati samo razmake.
     * Maksimalna duzina je 255 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @EqualsAndHashCode.Include
    @NotBlank(message = "Ime klijenta je obavezno.")
    @Size(max = 255, message = "Ime ne sme biti duže od 255 karaktera.")
    @Basic(optional = false)
    @Column(name = "ime")
    private String ime;
    
    /**
     * Prezime klijenta.
     * Polje je obavezno i ne sme biti null niti sadrzati samo razmake.
     * Maksimalna duzina je 255 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @NotBlank(message = "Prezime klijenta je obavezno.")
    @Size(max = 255, message = "Prezime ne sme biti duže od 255 karaktera.")
    @Column(name = "prezime")
    private String prezime;
    
    /**
     * Starost (broj godina) klijenta.
     * Polje je obavezno i ne sme biti null.
     * Vrednost mora biti pozitivan broj koji odgovara INT(11) koloni u bazi.
     * Opseg za broj godina je od 18 do 150 godina.
     */
    @NotNull(message = "Starost klijenta je obavezna.")
    @Min(value = 1, message = "Starost mora biti veća od 0.")
    @Max(value = 150, message = "Starost ne sme biti veća od 150.")
    @Column(name = "starost")
    private Integer starost;
    
    /**
     * Kontakt telefon klijenta. Moze biti u bilo kom obliku.
     * Polje je obavezno i ne sme biti null niti sadrzati samo razmake.
     * Maksimalna duzina je 255 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @NotBlank(message = "Broj telefona je obavezan.")
    @Size(max = 255, message = "Broj telefona ne sme biti duži od 255 karaktera.")
    @Column(name = "brojTelefona")
    private String brojTelefona;
    
    /**
     * Elektronska posta (email) klijenta za slanje obavestenja i ugovora.
     * Polje je obavezno, ne sme biti null niti sadrzati samo razmake,
     * i mora biti u ispravnom formatu email adrese.
     * Maksimalna duzina je 255 karaktera sto odgovara ogranicenju kolone u bazi.
     * Email mora biti jedinstven u sistemu, duplikati nisu dozvoljeni.
     */
    @Column(name = "email")
    @NotBlank(message = "Email je obavezan.")
    @Email(message = "Email mora biti u ispravnom formatu.")
    @Size(max = 255, message = "Email ne sme biti duzi od 255 karaktera.")
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
    @NotNull(message = "Mesto mora biti izabrano.")
    private Mesto idMesto;

}
