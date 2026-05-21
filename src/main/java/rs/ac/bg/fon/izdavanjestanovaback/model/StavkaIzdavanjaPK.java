package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Predstavlja pomocnu klasu koja modeluje slozeni primarni kljuc za entitet StavkaIzdavanja.
 * Kombinuje redni broj stavke i jedinstveni identifikator izdavanja kako bi se 
 * obezbedio jedinstveni identifikator zapisa u bazi podataka.
 * Polja u ovoj klasi moraju striktno odgovarati nazivima i tipovima polja koja su 
 * oznacena sa @Id unutar maticnog entiteta StavkaIzdavanja.
 * 
 * @author Marko
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StavkaIzdavanjaPK implements Serializable {
    
    /**
     * Redni broj stavke unutar jednog specificnog izdavanja.
     */
    private Long rb;
    
    /**
     * Jedinstveni identifikator krovnog izdavanja (ugovora) kojem stavka pripada.
     */
    private Long idIzdavanje;
}
