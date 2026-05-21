package rs.ac.bg.fon.izdavanjestanovaback.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponse;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponseBuilder;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.AgentDTO;
import rs.ac.bg.fon.izdavanjestanovaback.service.AgentService;

/**
 * REST kontroler koji upravlja podacima o agentima i autorizacijom na sistemu.
 * Predstavlja ulaznu tacku za izvrsavanje operacija autentifikacije i upravljanja nalozima.
 * Komunikacija se vrsi putem HTTP protokola, a podaci se razmenjuju kroz tela HTTP zahteva i odgovora.
 * 
 * @author Marko
 */
@RestController
@RequestMapping("/agent")
@CrossOrigin("http://localhost:9000")
public class AgentController {

    /**
     * Servisni sloj zaduzen za poslovnu logiku nad podacima o agentima.
     */
    private final AgentService agentService;

    /**
     * Konstruktor koji ubrizgava zavisnost AgentService.
     * 
     * @param agentService servis za rad sa agentima nekretnina
     */
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    
    /**
     * Registruje novog agenta u sistemu.
     * Vrsi proveru zauzetosti korisnickog imena pre samog unosa.
     * 
     * @param dto podaci o novom agentu kojeg treba kreirati
     * @return ResponseEntity sa objektom ApiResponse koji sadrzi ishod operacije i HTTP status (CREATED, CONFLICT ili BAD_REQUEST)
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAgent(@RequestBody AgentDTO dto) {
        ServiceResult result = agentService.addAgent(dto);
        if (!result.isUspesno()) {
            HttpStatus status = result.getPoruka().equals("Korisničko ime je već zauzeto.")
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
     * Vraca listu svih registrovonih agenata u sistemu.
     * 
     * @return ResponseEntity sa listom AgentDTO objekata i HTTP statusom OK
     */
    @GetMapping("/all")
    public ResponseEntity<List<AgentDTO>> getAllAgenti() {
        return ResponseEntity.ok(agentService.getAllAgenti());
    }

    /**
     * Pronalazi specificnog agenta na osnovu njegovog jedinstvenog identifikatora.
     * 
     * @param id jedinstveni identifikator (ID) agenta
     * @return ResponseEntity sa pronadjenim AgentDTO objektom i HTTP statusom OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<AgentDTO> getAgentById(@PathVariable Long id) {
        AgentDTO agentDTO = agentService.getAgentById(id);
        return ResponseEntity.ok(agentDTO);
    }

    /**
     * Izvrsava sistemsku operaciju: Izmena agenta.
     * Azurira podatke postojeceg agenta u sistemu.
     * 
     * @param dto podaci o agentu sa novim vrednostima koje treba sacuvati
     * @return ResponseEntity sa objektom ApiResponse koji sadrzi poruku o ishodu i HTTP status (OK ili BAD_REQUEST)
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateAgent(@RequestBody AgentDTO dto) {
        ServiceResult result = agentService.updateAgent(dto);
        HttpStatus status = result.isUspesno() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        result.getPoruka(),
                        status
                ));
    }

    /**
     * Uklanja agenta iz sistema na osnovu prosledjenog identifikatora.
     * 
     * @param id jedinstveni identifikator (ID) agenta kojeg treba obrisati
     * @return ResponseEntity sa objektom ApiResponse i HTTP statusom (OK ili BAD_REQUEST)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAgent(@PathVariable Long id) {
        ServiceResult result = agentService.deleteAgent(id);
        HttpStatus status = result.isUspesno() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        result.getPoruka(),
                        status
                ));
    }
    
    /**
     * Izvrsava sistemsku operaciju: Prijava agenta(Login).
     * Vrsi autentifikaciju korisnika na osnovu korisnickog imena i lozinke.
     * Ukoliko je prijava uspesna, vraca podatke o ulogovanom agentu.
     * 
     * @param loginData mapa koja sadrzi kljuceve "korisnickoIme" i "sifra" sa njihovim vrednostima
     * @return ResponseEntity sa objektom ApiResponse koji sadrzi podatke o agentu (ako je uspesno) i HTTP statusom (OK ili UNAUTHORIZED)
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody Map<String, String> loginData) {
        String korisnickoIme = loginData.get("korisnickoIme");
        String sifra = loginData.get("sifra");

        ServiceResult result = agentService.login(korisnickoIme, sifra);
        HttpStatus status = result.isUspesno() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        Map<String, Object> data = new HashMap<>();
        data.put("agent", result.getPodaci());
        
        return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovorSaPodacima(
                        result.getPoruka(),
                        data,
                        status
                ));
    }
}
