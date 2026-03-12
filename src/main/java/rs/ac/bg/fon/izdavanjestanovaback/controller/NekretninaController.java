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
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponse;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponseBuilder;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.NekretninaDTO;
import rs.ac.bg.fon.izdavanjestanovaback.service.NekretninaService;

/**
 *
 * @author Marko
 */
@RestController
@RequestMapping("/nekretnina")
@CrossOrigin("http://localhost:9000")
public class NekretninaController {
    private final NekretninaService nekretninaService;

    public NekretninaController(NekretninaService nekretninaService) {
        this.nekretninaService = nekretninaService;
    }
    
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
    
    @GetMapping("/all")
    public ResponseEntity<List<NekretninaDTO>> getAllNekretnine() {
        return ResponseEntity.ok(nekretninaService.getAllNekretnine());
    }
    
    @GetMapping("/povrsina/{povrsina}")
    public ResponseEntity<List<NekretninaDTO>> getByPovrsina(@PathVariable String povrsina) {
        return ResponseEntity.ok(nekretninaService.searchByPovrsina(povrsina));
    }
    
    @GetMapping("/tip/{id}")
    public ResponseEntity<List<NekretninaDTO>> getByTip(@PathVariable Long id) {
        return ResponseEntity.ok(nekretninaService.searchByTipNekretnine(id));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getNekretninaById(@PathVariable Long id) {
//        return ResponseEntity.ok(nekretninaService.getNekretninaById(id));
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