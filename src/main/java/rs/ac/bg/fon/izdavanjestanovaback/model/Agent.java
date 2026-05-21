package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Predstavlja domensku klasu za agenta koji posreduje u izdavanju stanova.
 * Klasa je mapirana na tabelu "agent" u bazi podataka i podrzava ORM (JPA).
 * Sadrzi osnovne podatke o agentu, njegove autentifikacione podatke, 
 * licencu, kao i veze sa izdavanjima i sertifikatima koje poseduje.
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
@NoArgsConstructor
public class Agent implements Serializable {

    /**
     * Identifikacioni broj za serijalizaciju objekta.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Jedinstveni identifikator agenta u bazi podataka (Primarni kljuc).
     * Koristi se auto-inkrementna strategija (IDENTITY) za generisanje vrednosti.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    /**
     * Broj profesionalne licence agenta za posredovanje u prometu nekretnina.
     */
    @Column(name = "brojLicence")
    private String brojLicence;
    
    /**
     * Datum kada istice vazenje licence agenta.
     * Mapira se kao cist datum u bazi (bez vremenske komponente).
     */
    @Column(name = "datumIstekaLicence")
    @Temporal(TemporalType.DATE)
    private Date datumIstekaLicence;
    
    /**
     * Ime agenta. Ovo polje je obavezno i ne moze biti null u bazi.
     */
    @Basic(optional = false)
    @Column(name = "ime")
    private String ime;
    
    /**
     * Prezime agenta.
     */
    @Column(name = "prezime")
    private String prezime;
    
    /**
     * Jedinstveno korisnicko ime koje agent koristi za login na sistem.
     */
    @Column(name = "korisnickoIme")
    private String korisnickoIme;
    
    /**
     * Lozinka (sifra) agenta za pristup sistemu.
     */
    @Column(name = "sifra")
    private String sifra;
    
    /**
     * Kolekcija svih izdavanja nekretnina u kojima je ovaj agent posredovao.
     * Predstavlja inverznu stranu veze "OneToMany" mapiranu preko polja idAgent.
     */
    @OneToMany(mappedBy = "idAgent")
    private Collection<Izdavanje> izdavanjeCollection;
    
    /**
     * Kolekcija strucnih sertifikata koje agent poseduje.
     * Veza je tipa jedan prema vise sa kaskadnim operacijama, sto znaci da se
     * brisanjem agenta automatski brisu i svi njegovi sertifikati iz baze (orphanRemoval).
     */
    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Sertifikat> sertifikatCollection;

    /**
     * Konstruktor koji inicijalizuje objekat klase Agent sa prosledjenim identifikatorom.
     * Koristan za kreiranje instanci kada je potrebno raditi pretragu ili povezivanje preko ID-ja.
     * 
     * @param id Jedinstveni identifikator agenta (primarni kljuc).
     */
    public Agent(Long id) {
        this.id = id;
    }
}
