package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Predstavlja domensku klasu za geografsku lokaciju (mesto ili opstinu).
 * Klasa je mapirana na tabelu "mesto" u bazi podataka.
 * Koristi se za cuvanje podataka o prebivalistu klijenata.
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
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Mesto implements Serializable {

    /**
     * Identifikacioni broj za serijalizaciju objekta.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Jedinstveni identifikator mesta u bazi podataka (Primarni kljuc).
     * Generise se automatski koriscenjem IDENTITY strategije.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    /**
     * Naziv mesta ili grada. Ovo polje je obavezno i ne moze biti null.
     * Polje je obavezno i ne sme biti null niti sadrzati samo razmake.
     * Maksimalna duzina je 255 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @EqualsAndHashCode.Include
    @NotBlank(message = "Naziv mesta je obavezan.")
    @Size(max = 255, message = "Naziv mesta ne sme biti duzi od 255 karaktera.")
    @Basic(optional = false)
    @Column(name = "nazivMesta")
    private String nazivMesta;
    
    /**
     * Naziv opstine kojoj mesto pripada.
     * Polje je opcionalno moze biti null.
     * Maksimalna duzina je 255 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @Size(max = 255, message = "Naziv opštine ne sme biti duži od 255 karaktera.")
    @Column(name = "opstina")
    private String opstina;
    
    /**
     * Kolekcija svih klijenata koji su povezani sa ovim mestom.
     * Predstavlja inverznu stranu veze jedan prema vise, mapiranu preko polja idMesto.
     */
    @OneToMany(mappedBy = "idMesto")
    private Collection<Klijent> klijentCollection;

    /**
     * Konstruktor sa parametrima za potpuno inicijalizovanje objekta klase Mesto.
     * 
     * @param id Jedinstveni identifikator mesta (primarni kljuc).
     * @param nazivMesta Naziv mesta ili grada.
     * @param opstina Naziv opstine.
     */
    public Mesto(Long id, String nazivMesta, String opstina) {
        this.id = id;
        this.nazivMesta = nazivMesta;
        this.opstina = opstina;
    }
}
