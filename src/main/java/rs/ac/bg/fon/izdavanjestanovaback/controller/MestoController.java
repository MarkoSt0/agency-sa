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
import rs.ac.bg.fon.izdavanjestanovaback.dto.MestoDTO;
import rs.ac.bg.fon.izdavanjestanovaback.service.MestoService;
/**
 *
 * @author Marko
 */
@RestController
@RequestMapping("/mesto")
@CrossOrigin("http://localhost:9000")
public class MestoController {

    private final MestoService mestoService;

    public MestoController(MestoService mestoService) {
        this.mestoService = mestoService;
    }

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

    @GetMapping("/all")
    public ResponseEntity<List<MestoDTO>> getAllMesta() {
        return ResponseEntity.ok(mestoService.getAllMesta());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MestoDTO> getMestoById(@PathVariable Long id) {
        MestoDTO mestoDTO = mestoService.getMestoById(id);
        return ResponseEntity.ok(mestoDTO);
    }

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
