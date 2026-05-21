package rs.ac.bg.fon.izdavanjestanovaback.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponse;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponseBuilder;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.MestoDTO;
import rs.ac.bg.fon.izdavanjestanovaback.service.MestoService;

/**
 * REST kontroler koji upravlja bazom mesta odakle su klijenti u sistemu.
 * Komunikacija se vrsi putem HTTP protokola, a podaci se razmenjuju kroz tela HTTP zahteva i odgovora.
 * 
 * @author Marko
 */
@RestController
@RequestMapping("/mesto")
@CrossOrigin("http://localhost:9000")
public class MestoController {

    /**
     * Servis za poslovnu logiku nad podacima o mestima.
     */
    private final MestoService mestoService;

    /**
     * Konstruktor koji ubrizgava zavisnost MestoService.
     * 
     * @param mestoService servis za rad sa mestima
     */
    public MestoController(MestoService mestoService) {
        this.mestoService = mestoService;
    }

    /**
     * Dodaje novo mesto u sifrarnik.
     * 
     * @param dto podaci o novom mestu
     * @return ResponseEntity sa objektom ApiResponse i HTTP statusom
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMesto(@RequestBody MestoDTO dto) {
        ServiceResult result = mestoService.addMesto(dto);
        HttpStatus status = result.isUspesno() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        result.getPoruka(),
                        status
                ));
    }

    /**
     * Vraca listu svih mesta iz sifrarnika.
     * 
     * @return ResponseEntity sa listom MestoDTO objekata
     */
    @GetMapping("/all")
    public ResponseEntity<List<MestoDTO>> getAllMesta() {
        return ResponseEntity.ok(mestoService.getAllMesta());
    }

    /**
     * Pronalazi mesto na osnovu njegovog identifikatora.
     * 
     * @param id jedinstveni identifikator mesta
     * @return ResponseEntity sa pronadjenim MestoDTO objektom
     */
    @GetMapping("/{id}")
    public ResponseEntity<MestoDTO> getMestoById(@PathVariable Long id) {
        MestoDTO mestoDTO = mestoService.getMestoById(id);
        return ResponseEntity.ok(mestoDTO);
    }

    /**
     * Azurira podatke o postojecem mestu.
     * 
     * @param dto podaci o mestu sa izmenjenim vrednostima
     * @return ResponseEntity sa objektom ApiResponse i HTTP statusom
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateMesto(@RequestBody MestoDTO dto) {
        ServiceResult result = mestoService.updateMesto(dto);
        HttpStatus status = result.isUspesno() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        result.getPoruka(),
                        status
                ));
    }

    /**
     * Uklanja mesto iz baze.
     * 
     * @param id jedinstveni identifikator mesta koje se brise
     * @return ResponseEntity sa objektom ApiResponse i HTTP statusom
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMesto(@PathVariable Long id) {
        ServiceResult result = mestoService.deleteMesto(id);
        HttpStatus status = result.isUspesno() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        result.getPoruka(),
                        status
                ));
    }
}
