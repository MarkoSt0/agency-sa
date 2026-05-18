package rs.ac.bg.fon.izdavanjestanovaback.dto;

import java.util.Date;
import java.util.List;
import lombok.*;

/**
 *
 * @author Marko
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AgentDTO {
    private Long id;
    private String brojLicence;
    private Date datumIstekaLicence;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String sifra;
    private List<SertifikatDTO> sertifikati;
}

//public record AgentDTO(
//        Long id, 
//        String brojLicence,
//        Date datumIstekaLicence,
//        String ime,
//        String prezime,
//        String korisnickoIme,
//        String sifra,
//        List<SertifikatDTO> sertifikati)
//    {
//        public AgentDTO() {
//            this(null, null, null, null, null, null, null, Collections.emptyList());
//        }
//    }