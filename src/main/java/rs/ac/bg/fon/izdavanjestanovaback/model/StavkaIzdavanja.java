package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
     * Polje je obavezno i mora biti navedeno.
     * Maksimalna duzina je 20 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @Id
    @NotNull(message = "Redni broj stavke izdavanja je obavezan.")
    @Basic(optional = false)
    @Column(name = "rb")
    private Long rb;
    
    /**
     * Datum kada zvanicno zapocinje period izdavanja nekretnine definisan ovom stavkom.
     * U bazi podataka se cuva iskljucivo kao cist datum.
     * Polje je obavezno i mora biti danasnji datum ili datum u buducnosti.
     * Nije dozvoljeno unositi datume koji su u proslosti.
     */
    @NotNull(message = "Datum pocetka izdavanja je obavezan.")
    @FutureOrPresent(message = "Datum pocetka izdavanja mora biti danasnji datum ili "
            + "datum u buducnosti.")
    @Column(name = "datumPocetkaIzdavanja")
    @Temporal(TemporalType.DATE)
    private Date datumPocetkaIzdavanja;
    
    /**
     * Datum kada se zavrsava ili istice period izdavanja nekretnine.
     * U bazi podataka se cuva iskljucivo kao cist datum.
     * Polje je obavezno i mora biti danasnji datum ili datum u buducnosti.
     * Nije dozvoljeno unositi datume koji su u proslosti.
     */
    @NotNull(message = "Datum zavrsetka izdavanja je obavezan.")
    @FutureOrPresent(message = "Datum zavrsetka izdavanja mora biti danasnji datum ili "
            + "datum u buducnosti.")
    @Column(name = "datumZavrsetkaIzdavanja")
    @Temporal(TemporalType.DATE)
    private Date datumZavrsetkaIzdavanja;
    
    /**
     * Dogovorena cena mesecnog zakupa nekretnine izrazena u novcanoj jedinici.
     * Polje je obavezno i mora biti navedeno.
     * Vrednost mesecne kirije ne sme biti negativan broj
     */
    @NotNull(message = "Mesecna kirija mora biti navedena.")
    @Min(value = 0, message = "Mesecna kirija ne sme biti negativan broj.")
    @Column(name = "mesecnaKirija")
    private Double mesecnaKirija;
    
    /**
     * Iznos depozita koji je zakupac duzan da polozi kao garanciju pre useljenja.
     * Polje je obavezno i ne sme biti negativna vrednost.
     */
    @NotNull(message = "Depozit mora biti naveden.")
    @Min(value = 0, message = "Depozit ne sme biti negativan broj.")
    @Column(name = "iznosDepozita")
    private Double iznosDepozita;
    
    /**
     * Nadredjeni entitet (Izdavanje) kojem ova stavka pripada.
     * Predstavlja drugi deo slozenog primarnog kljuca (ManyToOne veza).
     * Polje je obavezno i mora biti navedeno.
     * Maksimalna duzina je 20 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @NotNull(message = "Izdavanje mora biti navedeno.")
//    @Digits(integer = 20, fraction = 0, message = "Identifikacija izdavanja moze imati do 20 cifara.")    
    @JoinColumn(name = "idIzdavanje", referencedColumnName = "idIzdavanje")
    @ManyToOne
    @Id
    private Izdavanje idIzdavanje;
    
    /**
     * Nekretnina koja se iznajmljuje kroz ovu konkretnu stavku.
     * Predstavlja spoljni kljuc (ManyToOne veza) ka tabeli nekretnina.
     * Polje je obavezno i mora biti navedeno.
     * Maksimalna duzina je 20 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @NotNull(message = "Nekretnina mora biti navedena.")
//    @Digits(integer = 20, fraction = 0, message = "Identifikacija nekretnine moze imati do 20 cifara.")    
    @JoinColumn(name = "idNekretnina", referencedColumnName = "idNekretnina")
    @ManyToOne
    private Nekretnina idNekretnina;   
}
