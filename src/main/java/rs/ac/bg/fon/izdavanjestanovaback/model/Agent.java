package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Agent implements Serializable {

    /**
     * Identifikacioni broj za serijalizaciju objekta.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Jedinstveni identifikator agenta u bazi podataka (Primarni kljuc).
     * Koristi se auto-inkrementna strategija (IDENTITY) za generisanje vrednosti.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    /**
     * Broj profesionalne licence agenta za posredovanje u prometu nekretnina.
     * Ovo polje je obavezno, ne sme biti null niti prazno.
     * Maksimalna duzina je 255 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @NotBlank(message = "Broj licence je obavezan.")
    @Size(max = 255, message = "Broj licence ne sme biti duzi od 255 karaktera.")
    @Column(name = "brojLicence")
    private String brojLicence;
    
    /**
     * Datum kada istice vazenje licence agenta.
     * Mapira se kao cist datum u bazi (bez vremenske komponente).
     * Polje je obavezno i mora biti danasnji datum ili datum u buducnosti.
     * Nije dozvoljeno unositi datume koji su u proslosti.
     */
    @NotNull(message = "Datum isteka licence je obavezan.")
    @FutureOrPresent(message = "Datum isteka licence mora biti danasnji datum ili "
            + "datum u buducnosti.")
    @Column(name = "datumIstekaLicence")
    @Temporal(TemporalType.DATE)
    private Date datumIstekaLicence;
    
    /**
     * Ime agenta. Ovo polje je obavezno i ne moze biti null u bazi.
     * Polje je obavezno i ne sme biti null niti sadrzati samo razmake.
     * Maksimalna duzina je 255 karaktera.
     */
    @EqualsAndHashCode.Include
    @NotBlank(message = "Ime agenta je obavezno.")
    @Size(max = 255, message = "Ime ne sme biti buze od 255 karaktera.")
    @Basic(optional = false)
    @Column(name = "ime")
    private String ime;
    
    /**
     * Prezime agenta.
     * Polje je obavezno i ne sme biti null niti sadrzati samo razmake.
     * Maksimalna duzina je 255 karaktera.
     */
    @NotBlank(message = "Prezime agenta je obavezno.")
    @Size(max = 255, message = "Prezime ne sme biti buze od 255 karaktera.")
    @Column(name = "prezime")
    private String prezime;
    
    /**
     * Jedinstveno korisnicko ime koje agent koristi za login na sistem.
     * Polje je obavezno i ne sme biti null niti sadrzati samo razmake.
     * Mora imati izmedju 3 i 50 karaktera.
     */
    @NotBlank(message = "Korisničko ime je obavezno.")
    @Size(min = 3, max = 50, message = "Korisničko ime mora imati između 3 i 50 karaktera.")
    @Column(name = "korisnickoIme")
    private String korisnickoIme;
    
    /**
     * Lozinka (sifra) agenta za pristup sistemu.
     * Polje je obavezno i ne sme biti null niti sadrzati samo razmake.
     * Mora imati izmedju 6 i 100 karaktera.
     */
    @NotBlank(message = "Šifra je obavezna.")
    @Size(min = 5, max = 100, message = "Šifra mora imati između 5 i 100 karaktera.")
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
     * @param id Jedinstveni identifikator agenta (primarni kljuc).
     */
    public Agent(Long id) {
        this.id = id;
    }
}
