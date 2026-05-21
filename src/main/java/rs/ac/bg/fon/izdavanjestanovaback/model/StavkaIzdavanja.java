package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Predstavlja domensku klasu koja modeluje pojedinacnu stavku unutar ugovora ili realizacije izdavanja.
 * Klasa je mapirana na tabelu "stavka_izdavanja" u bazi podataka.
 * Koristi slozeni primarni kljuc definisan preko pomocne klase StavkaIzdavanjaPK, 
 * koji se sastoji od rednog broja stavke i identifikatora samog izdavanja.
 * Povezuje konkretnu nekretninu sa ugovorom i definise specificne uslove (kirija, depozit, rokovi).
 * 
 * @author Marko
 */
@Entity
@Data
@Table(name = "stavka_izdavanja")
@IdClass(StavkaIzdavanjaPK.class)
@NamedQueries({
    @NamedQuery(name = "StavkaIzdavanja.findAll", query = "SELECT s FROM StavkaIzdavanja s"),
    @NamedQuery(name = "StavkaIzdavanja.findByRb", query = "SELECT s FROM StavkaIzdavanja s WHERE s.rb = :rb"),
    @NamedQuery(name = "StavkaIzdavanja.findByDatumPocetkaIzdavanja", query = "SELECT s FROM StavkaIzdavanja s WHERE s.datumPocetkaIzdavanja = :datumPocetkaIzdavanja"),
    @NamedQuery(name = "StavkaIzdavanja.findByDatumZavrsetkaIzdavanja", query = "SELECT s FROM StavkaIzdavanja s WHERE s.datumZavrsetkaIzdavanja = :datumZavrsetkaIzdavanja"),
    @NamedQuery(name = "StavkaIzdavanja.findByMesecnaKirija", query = "SELECT s FROM StavkaIzdavanja s WHERE s.mesecnaKirija = :mesecnaKirija"),
    @NamedQuery(name = "StavkaIzdavanja.findByIznosDepozita", query = "SELECT s FROM StavkaIzdavanja s WHERE s.iznosDepozita = :iznosDepozita")})
@NoArgsConstructor
public class StavkaIzdavanja implements Serializable {

    /**
     * Identifikacioni broj za serijalizaciju objekta.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Redni broj stavke unutar jednog izdavanja. 
     * Predstavlja deo slozenog primarnog kljuca i obavezno je polje.
     */
    @Id
    @Basic(optional = false)
    @Column(name = "rb")
    private Long rb;
    
    /**
     * Datum kada zvanicno zapocinje period izdavanja nekretnine definisan ovom stavkom.
     * U bazi podataka se cuva iskljucivo kao cist datum.
     */
    @Column(name = "datumPocetkaIzdavanja")
    @Temporal(TemporalType.DATE)
    private Date datumPocetkaIzdavanja;
    
    /**
     * Datum kada se zavrsava ili istice period izdavanja nekretnine.
     * U bazi podataka se cuva iskljucivo kao cist datum.
     */
    @Column(name = "datumZavrsetkaIzdavanja")
    @Temporal(TemporalType.DATE)
    private Date datumZavrsetkaIzdavanja;
    
    /**
     * Dogovorena cena mesecnog zakupa nekretnine izrazena u novcanoj jedinici.
     */
    @Column(name = "mesecnaKirija")
    private Double mesecnaKirija;
    
    /**
     * Iznos depozita koji je zakupac duzan da polozi kao garanciju pre useljenja.
     */
    @Column(name = "iznosDepozita")
    private Double iznosDepozita;
    
    /**
     * Nadredjeni entitet (Izdavanje) kojem ova stavka pripada.
     * Predstavlja drugi deo slozenog primarnog kljuca (ManyToOne veza).
     */
    @JoinColumn(name = "idIzdavanje", referencedColumnName = "idIzdavanje")
    @ManyToOne
    @Id
    private Izdavanje idIzdavanje;
    
    /**
     * Nekretnina koja se iznajmljuje kroz ovu konkretnu stavku.
     * Predstavlja spoljni kljuc (ManyToOne veza) ka tabeli nekretnina.
     */
    @JoinColumn(name = "idNekretnina", referencedColumnName = "idNekretnina")
    @ManyToOne
    private Nekretnina idNekretnina;   
}
