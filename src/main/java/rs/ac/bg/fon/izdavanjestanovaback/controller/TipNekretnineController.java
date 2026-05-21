/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponse;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponseBuilder;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.TipNekretnineDTO;
import rs.ac.bg.fon.izdavanjestanovaback.service.TipNekretnineService;

/**
 * REST kontroler za upravljanje tipova nekretnina.
 * Podaci se razmenjuju kroz tela HTTP zahteva i odgovora.
 * 
 * @author Marko
 */
@RestController
@RequestMapping("/tip-nekretnine")
@CrossOrigin("http://localhost:9000")
public class TipNekretnineController {

    /**
     * Servis za poslovnu logiku nad tipovima nekretnina.
     */
    private final TipNekretnineService tipService;

    
    /**
     * Konstruktor koji ubrizgava zavisnost TipNekretnineService.
     * 
     * @param tipService servis za rad sa tipovima nekretnina
     */
    public TipNekretnineController(TipNekretnineService tipService) {
        this.tipService = tipService;
    }

    /**
     * Dodaje novi tip nekretnine u sifrarnik sistema.
     * 
     * @param dto podaci o novom tipu nekretnine
     * @return ResponseEntity sa objektom ApiResponse i HTTP statusom
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTip(@RequestBody TipNekretnineDTO dto) {
        ServiceResult result = tipService.addTip(dto);
        HttpStatus status = result.isUspesno() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        result.getPoruka(),
                        status
                ));
    }

    /**
     * Vraca listu svih tipova nekretnina iz sifrarnika.
     * 
     * @return ResponseEntity sa listom TipNekretnineDTO objekata
     */
    @GetMapping("/all")
    public ResponseEntity<List<TipNekretnineDTO>> getAllTipovi() {
        return ResponseEntity.ok(tipService.getAllTipovi());
    }
}
