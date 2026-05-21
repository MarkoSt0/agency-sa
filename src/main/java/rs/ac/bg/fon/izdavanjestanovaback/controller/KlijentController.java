package rs.ac.bg.fon.izdavanjestanovaback.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponse;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponseBuilder;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.KlijentDTO;
import rs.ac.bg.fon.izdavanjestanovaback.service.KlijentService;


/**
 * REST kontroler koji upravlja podacima o klijentima agencije.
 * Omogucava izvrsavanje operacija kreiranja, izmene, brisanja i pretrazivanja klijenata.
 * Komunikacija se vrsi putem HTTP protokola, a podaci se razmenjuju kroz tela HTTP zahteva i odgovora.
 * 
 * @author Marko
 */
@RestController
@RequestMapping("/klijenti")
@CrossOrigin("http://localhost:9000")
public class KlijentController {

    /**
     * Servisni sloj zaduzen za poslovnu logiku nad podacima o klijentima.
     */
    private final KlijentService klijentService;

    /**
     * Konstruktor koji ubrizgava zavisnost KlijentService.
     * 
     * @param klijentService servis za rad sa klijentima
     */
    public KlijentController(KlijentService klijentService) {
        this.klijentService = klijentService;
    }
    
    /**
     * Izvrsava sistemsku operaciju: Unos novog klijenta.
     * Kreira novi profil klijenta u sistemu uz prethodnu proveru jedinstvenosti email adrese.
     * 
     * @param dto podaci o novom klijentu kojeg treba evidentirati
     * @return ResponseEntity sa objektom ApiResponse koji sadrzi ishod operacije i HTTP status (CREATED, CONFLICT ili BAD_REQUEST)
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addKlijent(@RequestBody KlijentDTO dto) {
        ServiceResult result = klijentService.addKlijent(dto);
        if (!result.isUspesno()) {
            HttpStatus status = result.getPoruka().equals("Email je vec evidentiran.")
                    ? HttpStatus.CONFLICT
                    : HttpStatus.BAD_REQUEST;

            return ResponseEntity.status(status)
                    .body(ApiResponseBuilder.kreirajOdgovorSaPodacima(
                            result.getPoruka(),
                            Map.of("value", result.getPoruka()),
                            status
                    ));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseBuilder.kreirajOdgovorSaPodacima(
                        result.getPoruka(),
                        Map.of("value", result.getPoruka()),
                        HttpStatus.CREATED
                ));
    }
    
    /**
     * Vraca listu svih evidentiranih klijenata u sistemu.
     * 
     * @return ResponseEntity sa listom KlijentDTO objekata i HTTP statusom OK
     */
    @GetMapping("/all")
    public ResponseEntity<List<KlijentDTO>> getAllKlijenti() {
        return ResponseEntity.ok(klijentService.getAllKlijenti());
    }

    /**
     * Pronalazi specificnog klijenta na osnovu njegovog jedinstvenog identifikatora.
     * 
     * @param id jedinstveni identifikator (ID) klijenta
     * @return ResponseEntity sa pronadjenim KlijentDTO objektom i HTTP statusom OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<KlijentDTO> getKlijentById(@PathVariable Long id) {
        KlijentDTO kDTO = klijentService.getKlijentById(id);
        return ResponseEntity.ok(kDTO);
    }
    
    
    /**
     * Sistemsku operacija: Izmena klijenta.
     * Azurira osnovne i kontakt podatke postojeceg klijenta u sistemu.
     * 
     * @param dto podaci o klijentu sa novim vrednostima koje treba sacuvati
     * @return ResponseEntity sa objektom ApiResponse koji sadrzi poruku o ishodu i HTTP status (OK ili BAD_REQUEST)
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateKlijent(@RequestBody KlijentDTO dto) {
        ServiceResult result = klijentService.updateKlijent(dto);
        HttpStatus status = result.isUspesno() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        result.getPoruka(),
                        status
                ));
    }
    
    /**
     * Uklanja klijenta iz sistema na osnovu prosledjenog identifikatora.
     * 
     * @param id jedinstveni identifikator (ID) klijenta kojeg treba obrisati
     * @return ResponseEntity sa objektom ApiResponse i HTTP statusom OK
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteKlijent(@PathVariable Long id) {
        klijentService.deleteKlijent(id);
        return ResponseEntity.ok(
                ApiResponseBuilder.kreirajOdgovor(
                        "Klijent uspešno obrisan.",
                        HttpStatus.OK
                )
        );
    }
    
    /**
     * Sistemska operacija: Pretraga klijenata.
     * Pretrazuje i filtrira klijente na osnovu prosledjene email adrese ili njenog dela.
     * Ako kriterijum nije prosledjen ili je prazan, vraca sve klijente.
     * 
     * @param email kriterijum pretrage (email adresa klijenta ili deo adrese), moze biti null
     * @return ResponseEntity sa listom KlijentDTO objekata koji ispunjavaju uslov pretrage i HTTP statusom OK
     */
    @GetMapping("/pretraga")
    public ResponseEntity<List<KlijentDTO>> pretraziKlijente(@RequestParam(required = false) String email) {
        List<KlijentDTO> klijenti;
        if (email == null || email.isBlank()) {
            klijenti = klijentService.getKlijenti("");
        } else {
            klijenti = klijentService.getKlijenti(email);
        }
        return ResponseEntity.ok(klijenti);
    }
}
