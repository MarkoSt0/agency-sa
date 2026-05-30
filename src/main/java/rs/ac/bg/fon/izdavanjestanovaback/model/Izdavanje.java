package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Predstavlja domensku klasu koja modeluje ugovor i sam proces izdavanja nekretnine.
 * Klasa je mapirana na tabelu "izdavanje" u bazi podataka.
 * Sadrzi informacije o uslovima iznajmljivanja (finansije, status, ugovorne strane),
 * kao i direktne veze ka klijentu, agentu koji vodi proces i stavkama ugovora.
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
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Izdavanje implements Serializable {

    /**
     * Identifikacioni broj za serijalizaciju objekta.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Jedinstveni identifikator izdavanja (Primarni kljuc).
     * Generise se automatski u bazi podataka koriscenjem IDENTITY strategije.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idIzdavanje")
    private Long idIzdavanje;
    
    /**
     * Datum kada je ugovor o izdavanju zvanicno sklopljen i potpisan.
     * U bazi se cuva bez vremenske komponente (samo dan, mesec i godina).
     * Polje je obavezno i mora biti danasnji datum ili datum u proslosti.
     */
    @NotNull(message = "Datum sklapanja ugovora je obavezan.")
    @PastOrPresent(message = "Datum sklapanja ugovora mora biti danasnji datum ili datum u proslosti.")
    @Column(name = "datumSklapanjaUgovora")
    @Temporal(TemporalType.DATE)
    private Date datumSklapanjaUgovora;
    
    /**
     * Trenutni status ugovora o najmu (npr. Aktivan, Istekao, Storniran).
     * Polje je obavezno, ne sme biti null niti sadrzati samo razmake.
     * Maksimalna duzina je 255 karaktera.
     */
    @EqualsAndHashCode.Include
    @NotBlank(message = "Status ugovora je obavezan.")
    @Size(max = 255, message = "Status ugovora ne sme biti duzi od 255 karaktera.")
    @Column(name = "statusUgovora")
    private String statusUgovora;
    
    /**
     * Dogovoreni nacin placanja zakupnine (npr. Mesecno, Kes, Racun, Unapred).
     * Polje je obavezno, ne sme biti null niti sadrzati samo razmake.
     * Maksimalna duzina je 255 karaktera.
     */
    @NotBlank(message = "Nacin placanja je obavezan.")
    @Size(max = 255, message = "Nacin placanja ne sme biti duzi od 255 karaktera.")
    @Column(name = "nacinPlacanja")
    private String nacinPlacanja;
    
    /**
     * Ukupna finansijska vrednost ugovora o izdavanju izrazena kroz decimalni broj.
     * Polje je obavezno i ne sme biti negativan broj.
     */
    @NotNull(message = "Ukupan iznos mora biti naveden.")
    @Min(value = 0, message = "Ukupan iznos ne sme biti negativan broj.")
    @Column(name = "ukupanIznos")
    private Double ukupanIznos;
    
    /**
     * Dodatne napomene ili specificni uslovi vezani za realizaciju izdavanja.
     * Polje je opcionalno. Maksimalna duzina je 255 karaktera.
     */
    @Size(max = 255, message = "Napomena ne sme biti duza od 255 karaktera.")
    @Column(name = "napomena")
    private String napomena;
    
    /**
     * Agent koji je posredovao u realizaciji ovog izdavanja i kreirao ugovor.
     * Predstavlja spoljni kljuc (ManyToOne veza) koji referencira primarni kljuc klase Agent.
     * POlje je obavezno.
     */
    @NotNull(message = "Agent mora biti izabran.")
    @JoinColumn(name = "idAgent", referencedColumnName = "id")
    @ManyToOne
    private Agent idAgent;
    
    /**
     * Klijent koji se pojavljuje u ulozi zakupca (onaj koji iznajmljuje nekretninu).
     * Predstavlja spoljni kljuc (ManyToOne veza) koji referencira primarni kljuc klase Klijent.
     * POlje je obavezno.
     */ 
    @NotNull(message = "Klijent mora biti izabran.")
    @JoinColumn(name = "idKlijent", referencedColumnName = "id")
    @ManyToOne
    private Klijent idKlijent;
    
    /**
     * Kolekcija pojedinacnih stavki koje cine ovo izdavanje.
     * Veza je OneToMany sa kaskadnim operacijama i ukljanjanjem podklasa(orphanRemoval),
     * sto znaci da brisanje celog izdavanja povlaci i brisanje svih pripadajucih stavki.
     */
    @OneToMany(mappedBy = "idIzdavanje", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<StavkaIzdavanja> stavkaIzdavanjaCollection;

    
    /**
     * Konstruktor koji kreira instancu klase Izdavanje sa postavljenim identifikatorom.
     * 
     * @param idIzdavanje Jedinstveni identifikator ugovora o izdavanju.
     */
    public Izdavanje(Long idIzdavanje) {
        this.idIzdavanje = idIzdavanje;
    }
}
