package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.*;
import lombok.Data;
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
public class Izdavanje implements Serializable {

    /**
     * Identifikacioni broj za serijalizaciju objekta.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Jedinstveni identifikator izdavanja (Primarni kljuc).
     * Generise se automatski u bazi podataka koriscenjem IDENTITY strategije.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idIzdavanje")
    private Long idIzdavanje;
    
    /**
     * Datum kada je ugovor o izdavanju zvanicno sklopljen i potpisan.
     * U bazi se cuva bez vremenske komponente (samo dan, mesec i godina).
     */
    @Column(name = "datumSklapanjaUgovora")
    @Temporal(TemporalType.DATE)
    private Date datumSklapanjaUgovora;
    
    /**
     * Trenutni status ugovora o najmu (npr. Aktivan, Istekao, Storniran).
     */
    @Column(name = "statusUgovora")
    private String statusUgovora;
    
    /**
     * Dogovoreni nacin placanja zakupnine (npr. Mesecno, Kes, Racun, Unapred).
     */
    @Column(name = "nacinPlacanja")
    private String nacinPlacanja;
    
    /**
     * Ukupna finansijska vrednost ugovora o izdavanju izrazena kroz decimalni broj.
     */
    @Column(name = "ukupanIznos")
    private Double ukupanIznos;
    
    /**
     * Dodatne napomene ili specificni uslovi vezani za realizaciju izdavanja.
     */
    @Column(name = "napomena")
    private String napomena;
    
    /**
     * Agent koji je posredovao u realizaciji ovog izdavanja i kreirao ugovor.
     * Predstavlja spoljni kljuc (ManyToOne veza) koji referencira primarni kljuc klase Agent.
     */
    @JoinColumn(name = "idAgent", referencedColumnName = "id")
    @ManyToOne
    private Agent idAgent;
    
    /**
     * Klijent koji se pojavljuje u ulozi zakupca (onaj koji iznajmljuje nekretninu).
     * Predstavlja spoljni kljuc (ManyToOne veza) koji referencira primarni kljuc klase Klijent.
     */
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
