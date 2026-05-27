package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.izdavanjestanovaback.enums.StatusNekretnine;
import rs.ac.bg.fon.izdavanjestanovaback.enums.TipGrejanja;

/**
 * Predstavlja domensku klasu za nekretninu koja je predmet izdavanja (npr. stan, kuca, garaza).
 * Klasa je mapirana na tabelu "nekretnina" u bazi podataka.
 * Sadrzi fizicke karakteristike objekta, lokaciju, status dostupnosti, 
 * kao i relacije ka tipu nekretnine i stavkama ugovora u kojima se pojavljuje.
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
@NoArgsConstructor
public class Nekretnina implements Serializable {

    /**
     * Identifikacioni broj za serijalizaciju objekta.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Jedinstveni identifikator nekretnine u bazi podataka (Primarni kljuc).
     * Generise se automatski koriscenjem IDENTITY strategije.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idNekretnina")
    private Long idNekretnina;
    
    /**
     * Adresa na kojoj se nekretnina nalazi (ulica, broj, grad).
     * Polje je obavezno i ne sme biti null niti sadrzati samo razmake.
     * Maksimalna duzina je 255 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @NotBlank(message = "Adresa nekretnine je obavezna.")
    @Size(max = 255, message = "Adresa ne sme biti duza od 255 karaktera.")
    @Column(name = "adresa")
    private String adresa;
    
    /**
     * Kvadratura (povrsina) nekretnine izrazena u kvadratnim metrima.
     * Polje je obavezno i ne sme biti null niti sadrzati samo razmake.
     * Maksimalna duzina je 255 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @NotBlank(message = "Povrsina nekretnine je obavezna.")
    @Size(max = 255, message = "Povrsina ne sme biti duza od 255 karaktera.")
    @Column(name = "povrsina")
    private String povrsina;
    
    /**
     * Sprat na kojem se nekretnina nalazi (npr. Prizemlje, 4. sprat, Potkrovlje).
     * Polje je obavezno i ne sme biti null niti sadrzati samo razmake.
     * Maksimalna duzina je 255 karaktera.
     */
    @NotBlank(message = "Sprat na kome se nalazi nekretnina je obavezan.")
    @Size(max = 255, message = "Naziv sprata ne sme biti duzi od 255 karaktera.")
    @Column(name = "sprat")
    private String sprat;
    
    /**
     * Ukupan broj soba u nekretnini (npr. 1.5, 2.0, 3.5).
     * Polje je obavezno i mora biti navedeno.
     * Broj soba ne sme biti negativan broj.
     */
    @NotNull(message = "Broj soba mora biti naveden.")
    @Min(value = 0, message = "Broj soba ne sme biti negativan broj.")
    @Column(name = "brojSoba")
    private Double brojSoba;
    
    /**
     * Godina izgradnje objekta u kojem se nekretnina nalazi.
     * Polje je obavezno i mora biti navedeno.
     * Godina izgradnje ne može biti pre 1800. godine.
     */
    @NotNull(message = "Godina izgradnje je obavezna.")
    @Min(value = 1800, message = "Godina izgradnje ne moze biti pre 1800. godine.")
    @Column(name = "godinaIzgradnje")
    private Integer godinaIzgradnje;
    
    /**
     * Dodatni tekstualni opis nekretnine (stanje, opremljenost, prednosti, nedostaci i dr.).
     * Polje je opcionalno. Maksimalna duzina je 1000 karaktera.
     */
    @Size(max = 1000, message = "Opis nekretnine ne sme biti duži od 1000 karaktera.")
    @Column(name = "opis")
    private String opis;
    
    /**
     * Kolekcija svih stavki izdavanja u kojima se ova nekretnina pojavljuje.
     * Podaci se ucitavaju odmah (EAGER) prilikom ucitavanja same nekretnine.
     */
    @OneToMany(mappedBy = "idNekretnina", fetch = FetchType.EAGER)
    private Collection<StavkaIzdavanja> stavkaIzdavanjaCollection;
    
    /**
     * Kategorija ili tip kojem nekretnina pripada (npr. Stan, Kuca).
     * Predstavlja spoljni kljuc (ManyToOne veza) ka klasi TipNekretnine.
     * Polje je obavezno i mora biti izabrano.
     */
    @NotNull(message = "Tip nekretnine mora biti izabran.")
    @JoinColumn(name = "idTipaNekretnine", referencedColumnName = "idTipaNekretnine")
    @ManyToOne
    private TipNekretnine tipNekretnine;
    
    /**
     * Vrsta grejanja koja je instalirana u nekretnini.
     * Cuva se u bazi podataka kao tekstualna vrednost (String) odgovarajuceg enuma.
     * Polje je obavezno i mora biti izabrano.
     */
    @NotNull(message = "Tip grejanja mora biti izabran.")
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "tipGrejanja")
    private TipGrejanja tipGrejanja;
    
    /**
     * Trenutni status nekretnine na trzistu (npr. Slobodna, Izdata, Rezervisana).
     * Cuva se u bazi podataka kao tekstualna vrednost (String) odgovarajuceg enuma.
     * Polje je obavezno i mora biti izabrano.
     */
    @NotNull(message = "Status nekretnine mora biti izabran.")
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "statusNekretnine")
    private StatusNekretnine statusNekretnine;

}
