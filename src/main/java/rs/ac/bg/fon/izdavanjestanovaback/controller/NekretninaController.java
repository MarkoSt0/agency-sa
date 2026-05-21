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
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponse;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponseBuilder;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.NekretninaDTO;
import rs.ac.bg.fon.izdavanjestanovaback.service.NekretninaService;

/**
 * REST kontroler koji upravlja podacima o nekretninama koje su predmet izdavanja.
 * Pruza interfejs za kreiranje, izmenu, brisanje i filtriranje stambenih ili poslovnih objekata.
 * Komunikacija se vrsi putem HTTP protokola, a podaci se razmenjuju kroz tela HTTP zahteva i odgovora.
 * 
 * @author Marko
 */
@RestController
@RequestMapping("/nekretnina")
@CrossOrigin("http://localhost:9000")
public class NekretninaController {
    
    /**
     * Servisni sloj zaduzen za poslovnu logiku nad podacima o nekretninama.
     */
    private final NekretninaService nekretninaService;

    
    /**
     * Konstruktor koji ubrizgava zavisnost NekretninaService.
     * 
     * @param nekretninaService servis za rad sa nekretninama
     */
    public NekretninaController(NekretninaService nekretninaService) {
        this.nekretninaService = nekretninaService;
    }
    
    /**
     * Izvrsava sistemsku operaciju: Unos nove nekretnine.
     * Registruje novi objekat u sistemu i povezuje ga sa odgovarajucim vlasnikom i lokacijom.
     * 
     * @param dto podaci o nekretnini koju je potrebno evidentirati
     * @return ResponseEntity sa objektom ApiResponse i odgovarajucim HTTP statusom (CREATED, NOT_FOUND ili BAD_REQUEST)
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addNekretnina(@RequestBody NekretninaDTO dto) {
        ServiceResult result = nekretninaService.addNekretnina(dto);

        if (!result.isUspesno()) {
        HttpStatus status = result.getPoruka().toLowerCase().contains("nije pronađen")
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovorSaPodacima(
                        result.getPoruka(),
                        Map.of("value", result.getPoruka()),
                        status
                ));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        result.getPoruka(),
                        HttpStatus.CREATED
                ));
    }
    
    /**
     * Vraca listu svih nekretnina koje su zabelezene u sistemu.
     * 
     * @return ResponseEntity sa listom svih NekretninaDTO objekata i HTTP statusom OK
     */
    @GetMapping("/all")
    public ResponseEntity<List<NekretninaDTO>> getAllNekretnine() {
        return ResponseEntity.ok(nekretninaService.getAllNekretnine());
    }
    
    /**
     * Sistemska operacija: Pretraga nekretnina.
     * Filtrira nekretnine na osnovu zadate vrednosti povrsine.
     * 
     * @param povrsina tekstualni kriterijum za pretragu ili filtriranje po kvadraturi
     * @return ResponseEntity sa listom NekretninaDTO objekata koji ispunjavaju uslov i HTTP statusom OK
     */
    @GetMapping("/povrsina/{povrsina}")
    public ResponseEntity<List<NekretninaDTO>> getByPovrsina(@PathVariable String povrsina) {
        return ResponseEntity.ok(nekretninaService.searchByPovrsina(povrsina));
    }
    
    /**
     * Sistemska operacija: Pretraga nekretnina.
     * Filtrira nekretnine na osnovu jedinstvenog identifikatora tipa nekretnine.
     * 
     * @param id jedinstveni identifikator (ID) tipa nekretnine (npr. stan, kuca)
     * @return ResponseEntity sa listom NekretninaDTO objekata koji pripadaju tom tipu i HTTP statusom OK
     */
    @GetMapping("/tip/{id}")
    public ResponseEntity<List<NekretninaDTO>> getByTip(@PathVariable Long id) {
        return ResponseEntity.ok(nekretninaService.searchByTipNekretnine(id));
    }
    
    /**
     * Pronalazi detaljne podatke o specificnoj nekretnini na osnovu njenog identifikatora.
     * 
     * @param id jedinstveni identifikator (ID) trazene nekretnine
     * @return ResponseEntity sa objektom ApiResponse koji nosi podatke o nekretnini (ako je uspesno) i HTTP statusom (OK ili NOT_FOUND)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getNekretninaById(@PathVariable Long id) {
        ServiceResult result = nekretninaService.getNekretninaById(id);
        if (!result.isUspesno()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseBuilder.kreirajOdgovor(
                            result.getPoruka(),
                            HttpStatus.NOT_FOUND
                    ));
        }
        return ResponseEntity.ok(
                ApiResponseBuilder.kreirajOdgovorSaPodacima(
                        result.getPoruka(),
                        Map.of("nekretnina", result.getPodaci()),
                        HttpStatus.OK
                )
        );
    }
    
    /**
     * Sistemska operacija: Izmena nekretnine.
     * Azurira tehnicke karakteristike, adresu ili status postojece nekretnine u sistemu.
     * 
     * @param dto podaci o nekretnini sa azuriranim vrednostima koje treba snimiti
     * @return ResponseEntity sa objektom ApiResponse i HTTP statusom (ACCEPTED, NOT_FOUND ili BAD_REQUEST)
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateNekretnina(@RequestBody NekretninaDTO dto) {
        ServiceResult result = nekretninaService.updateNekretnina(dto);
        if (!result.isUspesno()) {
            HttpStatus status = result.getPoruka().toLowerCase().contains("nije pronađen")
            ? HttpStatus.NOT_FOUND
            : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovorSaPodacima(
                        result.getPoruka(),
                        Map.of("value", result.getPoruka()),
                        status
                ));
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        result.getPoruka(),
                        HttpStatus.ACCEPTED
                ));
    }
    
    /**
     * Uklanja nekretninu iz sistema na osnovu prosledjenog identifikatora.
     * 
     * @param id jedinstveni identifikator (ID) nekretnine koju treba obrisati
     * @return ResponseEntity sa objektom ApiResponse i HTTP statusom OK
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteNekretnina(@PathVariable Long id) {
        nekretninaService.deleteNekretnina(id);
        return ResponseEntity.ok(
                ApiResponseBuilder.kreirajOdgovor(
                        "Nekretnina uspešno obrisana.",
                        HttpStatus.OK
                )
        );
    }
    
}