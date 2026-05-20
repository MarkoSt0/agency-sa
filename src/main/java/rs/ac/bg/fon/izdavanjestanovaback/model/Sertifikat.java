package rs.ac.bg.fon.izdavanjestanovaback.model;

import jakarta.persistence.*;
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
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idSertifikat;
    
    /**
     * Zvanicni naziv sertifikata ili polozene obuke (npr. Sertifikat za posrednika u prometu nekretnina).
     */
    @Column
    private String nazivSertifikata;
    
    /**
     * Pravno lice, institucija ili organizacija koja je izdala sertifikat (npr. Privredna komora Srbije).
     */
    @Column
    private String izdavalac;
    
    /**
     * Datum kada je agent zvanicno stekao sertifikat.
     * U bazi se cuva iskljucivo kao cist datum (dan, mesec, godina).
     */
    @Temporal(TemporalType.DATE)
    @Column
    private Date datumSticanja;
    
    /**
     * Agent koji poseduje ovaj konkretni sertifikat.
     * Predstavlja spoljni kljuc (ManyTOOne veza) ka tabeli agent.
     * Podaci o agentu se ucitavaju odmah (EAGER) zajedno sa sertifikatom.
     */
    @JoinColumn(name = "idAgent", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Agent agent; 
}
