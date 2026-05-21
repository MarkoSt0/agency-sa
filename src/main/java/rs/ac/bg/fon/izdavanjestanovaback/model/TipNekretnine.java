package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Domenska klasa koja predstavlja tip nekretnine.
 * Klasa je mapirana na tabelu "tip_nekretnine" u bazi podataka.
 * Koristi se za definisanje razlicitih vrsta objekata (npr. Stan, Kuca, Lokal, Garaza) 
 * i omogucava njihovo lakse pretrazivanje i klasifikaciju unutar sistema.
 * 
 * @author Marko
 */
@Entity
@Table(name = "tip_nekretnine")
@NamedQueries({
    @NamedQuery(name = "TipNekretnine.findAll", query = "SELECT t FROM TipNekretnine t"),
    @NamedQuery(name = "TipNekretnine.findByIdTipaNekretnine", query = "SELECT t FROM TipNekretnine t WHERE t.idTipaNekretnine = :idTipaNekretnine"),
    @NamedQuery(name = "TipNekretnine.findByNazivTipaNekretnine", query = "SELECT t FROM TipNekretnine t WHERE t.nazivTipaNekretnine = :nazivTipaNekretnine")})
@Data
@NoArgsConstructor
public class TipNekretnine implements Serializable {

    /**
     * Identifikacioni broj za serijalizaciju objekta.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Jedinstveni identifikator tipa nekretnine u bazi podataka (Primarni kljuc).
     * Vrednost se generise automatski koriscenjem IDENTITY strategije na strani baze.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipaNekretnine")
    private Long idTipaNekretnine;

    /**
     * Naziv tipa nekretnine (npr. Stan, Kuca, Garaza). 
     * Ovo polje je obavezno i ne moze biti null u bazi podataka.
     */
    @Basic(optional = false)
    @Column(name = "nazivTipaNekretnine")
    private String nazivTipaNekretnine;

    /**
     * Kolekcija svih nekretnina koje pripadaju ovom konkretnom tipu.
     * Predstavlja inverznu stranu veze jedan prema vise, mapiranu preko polja tipNekretnine unutar klase Nekretnina.
     */
    @OneToMany(mappedBy = "tipNekretnine")
    private Collection<Nekretnina> nekretninaCollection;
    
}
