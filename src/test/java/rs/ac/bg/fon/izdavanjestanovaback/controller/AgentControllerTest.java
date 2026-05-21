package rs.ac.bg.fon.izdavanjestanovaback.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponse;
import rs.ac.bg.fon.izdavanjestanovaback.dto.AgentDTO;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Marko
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AgentControllerTest {
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    private String baseUrl;
    
    public AgentControllerTest() {   
    }
    
    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/agent";
    }
    
    @Test
    public void testLoginUspesno() {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("korisnickoIme", "pera123");
        loginData.put("sifra", "sifra123");

        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/login",
                HttpMethod.POST,
                new HttpEntity<>(loginData),
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testLoginNeuspesnoPogresnaSifra() {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("korisnickoIme", "pera123");
        loginData.put("sifra", "pogresnaSifra");

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/login",
                HttpMethod.POST,
                new HttpEntity<>(loginData),
                String.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
    
    @Test
    public void testLoginNeuspesnoNepostojeceKorisnickoIme() {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("korisnickoIme", "nepostojeciKorisnik");
        loginData.put("sifra", "sifra");

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/login",
                HttpMethod.POST,
                new HttpEntity<>(loginData),
                String.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
    
    @Test
    public void testLoginNeuspesnoPraznoKorisnickoIme() {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("korisnickoIme", "");
        loginData.put("sifra", "sifra123");

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/login",
                HttpMethod.POST,
                new HttpEntity<>(loginData),
                String.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
    
    @Test
    public void testLoginNeuspesnoPraznaSifra() {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("korisnickoIme", "pera123");
        loginData.put("sifra", "");

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/login",
                HttpMethod.POST,
                new HttpEntity<>(loginData),
                String.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
    
    @Test
    public void testLoginNeuspesnoNullKorisnickoIme() {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("korisnickoIme", null);
        loginData.put("sifra", "sifra123");

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/login",
                HttpMethod.POST,
                new HttpEntity<>(loginData),
                String.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
    
    @Test
    public void testLoginNeuspesnoNullSifra() {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("korisnickoIme", "pera123");
        loginData.put("sifra", null);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/login",
                HttpMethod.POST,
                new HttpEntity<>(loginData),
                String.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
    
    @Test
    public void testLoginUspesnoVracaPodatkeOAgentu() {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("korisnickoIme", "pera123");
        loginData.put("sifra", "sifra123");

        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/login",
                HttpMethod.POST,
                new HttpEntity<>(loginData),
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getPodaci());
    }

    @Test
    public void testUpdateAgentUspesno() {
        AgentDTO updateDto = new AgentDTO();
        updateDto.setId(1L);
        updateDto.setIme("Petar");
        updateDto.setPrezime("Peric");
        updateDto.setKorisnickoIme("pera123");
        updateDto.setSifra("sifra123");

        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updateDto),
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    
    @Test
    public void testUpdateAgentNeuspesnoNepostojeciId() {
        AgentDTO updateDto = new AgentDTO();
        
        updateDto.setId(99999L);
        
        updateDto.setIme("Petar");
        updateDto.setPrezime("Peric");
        updateDto.setKorisnickoIme("pera123");
        updateDto.setSifra("sifra123");

        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updateDto),
                ApiResponse.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateAgentNeuspesnoNullId() {
        AgentDTO updateDto = new AgentDTO();
        
        updateDto.setId(null);
        
        updateDto.setIme("Petar");
        updateDto.setPrezime("Peric");
        updateDto.setKorisnickoIme("pera123");
        updateDto.setSifra("sifra123");

        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updateDto),
                ApiResponse.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateAgentNeuspesnoPraznoIme() {
        AgentDTO updateDto = new AgentDTO();
        updateDto.setId(1L);
        
        updateDto.setIme("");
        
        updateDto.setPrezime("Peric");
        updateDto.setKorisnickoIme("pera123");
        updateDto.setSifra("sifra123");

        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updateDto),
                ApiResponse.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateAgentNeuspesnoPraznoPrezime() {
        AgentDTO updateDto = new AgentDTO();
        updateDto.setId(1L);
        updateDto.setIme("Petar");
        
        updateDto.setPrezime("");
        
        updateDto.setKorisnickoIme("pera123");
        updateDto.setSifra("sifra123");

        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updateDto),
                ApiResponse.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateAgentNeuspesnoPraznoKorisnickoIme() {
        AgentDTO updateDto = new AgentDTO();
        updateDto.setId(1L);
        updateDto.setIme("Petar");
        updateDto.setPrezime("Peric");
        
        updateDto.setKorisnickoIme("");
        
        updateDto.setSifra("sifra123");

        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updateDto),
                ApiResponse.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
