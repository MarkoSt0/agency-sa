/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author Marko
 */


@RestController
@RequestMapping("/klijenti")
@CrossOrigin("http://localhost:9000")
public class KlijentController {

    private final KlijentService klijentService;

    public KlijentController(KlijentService klijentService) {
        this.klijentService = klijentService;
    }
    
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
    
    @GetMapping("/all")
    public ResponseEntity<List<KlijentDTO>> getAllKlijenti() {
        return ResponseEntity.ok(klijentService.getAllKlijenti());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KlijentDTO> getKlijentById(@PathVariable Long id) {
        KlijentDTO kDTO = klijentService.getKlijentById(id);
        return ResponseEntity.ok(kDTO);
    }
    
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
