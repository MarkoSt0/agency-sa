/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.controller;


import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponse;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponseBuilder;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.IzdavanjeDTO;
import rs.ac.bg.fon.izdavanjestanovaback.service.IzdavanjeService;

/**
 *
 * @author Marko
 */

@RestController
@RequestMapping("/izdavanje")
@CrossOrigin("http://localhost:9000")
public class IzdavanjeController {

    private final IzdavanjeService izdavanjeService;

    public IzdavanjeController(IzdavanjeService izdavanjeService) {
        this.izdavanjeService = izdavanjeService;
    }

    // Dodavanje novog izdavanja
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addIzdavanje(@RequestBody IzdavanjeDTO dto) {
        ServiceResult result = izdavanjeService.addIzdavanje(dto);

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
    
    @GetMapping("/all")
    public ResponseEntity<List<IzdavanjeDTO>> getAllIzdavanja() {
        return ResponseEntity.ok(izdavanjeService.getAllIzdavanja());
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<IzdavanjeDTO>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(izdavanjeService.searchByStatus(status));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<IzdavanjeDTO> getIzdavanjeById(@PathVariable Long id) {
        IzdavanjeDTO dto = izdavanjeService.getIzdavanjeById(id);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(dto);
    }
    
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateIzdavanje(@RequestBody IzdavanjeDTO dto) {
        ServiceResult result = izdavanjeService.updateIzdavanje(dto);

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteIzdavanje(@PathVariable Long id) {
        ServiceResult result = izdavanjeService.deleteIzdavanje(id);

        HttpStatus status = result.isUspesno() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        result.getPoruka(),
                        status
                ));
    }
    
    @GetMapping("/klijent")
    public ResponseEntity<List<IzdavanjeDTO>> getByKlijent(
            @RequestParam(required = false) String ime,
            @RequestParam(required = false) String prezime) {

        List<IzdavanjeDTO> result = izdavanjeService.searchByKlijent(ime, prezime);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/check-expired")
    public ResponseEntity<ApiResponse> checkExpiredContracts() {
        izdavanjeService.checkAndUpdateExpiredContracts();

        HttpStatus status = HttpStatus.OK;

        return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        "Istekli ugovori su provereni i ažurirani",
                        status
                ));
    }
}
