/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author Marko
 */
@RestController
@RequestMapping("/agent")
@CrossOrigin("http://localhost:9000")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

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

    @GetMapping("/all")
    public ResponseEntity<List<AgentDTO>> getAllAgenti() {
        return ResponseEntity.ok(agentService.getAllAgenti());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentDTO> getAgentById(@PathVariable Long id) {
        AgentDTO agentDTO = agentService.getAgentById(id);
        return ResponseEntity.ok(agentDTO);
    }

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
