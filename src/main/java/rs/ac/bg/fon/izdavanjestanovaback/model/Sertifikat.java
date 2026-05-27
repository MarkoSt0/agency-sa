package rs.ac.bg.fon.izdavanjestanovaback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import lombok.*;

/**
 * Predstavlja domensku klasu za strucni sertifikat koju agent poseduje.
 * Klasa je mapirana na tabelu "sertifikat" u bazi podataka.
 * Omogucava pracenje kvalifikacija i strucnosti agenata unutar sistema.
 * Podrzava kreiranje objekata preko Lombok Builder sablona.
 * 
 * @author Marko
 */
@Entity
@Table(name = "sertifikat")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Sertifikat implements Serializable{
    
    /**
     * Jedinstveni identifikator sertifikata u bazi podataka (Primarni kljuc).
     * Generise se automatski na strani baze koriscenjem IDENTITY strategije.
     * Maksimalna duzina je 20 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idSertifikat;
    
    /**
     * Zvanicni naziv sertifikata ili polozene obuke (npr. Sertifikat za posrednika u prometu nekretnina).
     * Polje je obavezno i ne sme biti null niti sadrzati samo razmake.
     * Maksimalna duzina je 255 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @Column
    @NotBlank(message = "Naziv sertifikata je obavezan")
    @Size(max = 255, message = "Naziv sertifikata ne moze biti duzi od 255 karaktera")
    private String nazivSertifikata;
    
    /**
     * Pravno lice, institucija ili organizacija koja je izdala sertifikat (npr. Privredna komora Srbije).
     * Polje je obavezno i ne sme biti null niti sadrzati samo razmake.
     * Maksimalna duzina je 255 karaktera sto odgovara ogranicenju kolone u bazi.
     */
    @Column
    @NotBlank(message = "Naziv izdavaoca je obavezan.")
    @Size(max = 255, message = "Naziv izdavaoca ne moze biti duzi od 255 karaktera")
    private String izdavalac;
    
    /**
     * Datum kada je agent zvanicno stekao sertifikat.
     * U bazi se cuva iskljucivo kao cist datum (dan, mesec, godina).
     * Polje je obavezno i mora biti danasnji datum ili datum u proslosti.
     * Nije dozvoljeno unositi datume koji su u buducnosti.
     */
    @Temporal(TemporalType.DATE)
    @Column
    @NotNull(message = "Datum sticanja sertifikata je obavezan.")
    @PastOrPresent(message = "Datum sticanja sertifikata mora biti danasnji datum ili datum u proslosti.")
    private Date datumSticanja;
    
    /**
     * Agent koji poseduje ovaj konkretni sertifikat.
     * Predstavlja spoljni kljuc (ManyTOOne veza) ka tabeli agent.
     * Podaci o agentu se ucitavaju odmah (EAGER) zajedno sa sertifikatom.
     * Agent(vlasnik sertifikata) mora postojati.
     */
    @JoinColumn(name = "idAgent", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "Sertifikat mora biti povezan sa agentom.")
    private Agent agent; 
}
