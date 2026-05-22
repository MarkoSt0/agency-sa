/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.controller;

import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponse;
import rs.ac.bg.fon.izdavanjestanovaback.dto.AgentDTO;
import rs.ac.bg.fon.izdavanjestanovaback.dto.IzdavanjeDTO;
import rs.ac.bg.fon.izdavanjestanovaback.dto.KlijentDTO;
import rs.ac.bg.fon.izdavanjestanovaback.dto.StavkaIzdavanjaDTO;
import rs.ac.bg.fon.izdavanjestanovaback.dto.NekretninaDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 * @author Marko
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IzdavanjeControllerTest {
    
    @LocalServerPort
    private int port;
 
    @Autowired
    private TestRestTemplate restTemplate;
 
    private String baseUrl;
 
    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/izdavanje";
    }
    // pomocna metoda za kreiranje validnog IzdavanjeDTO za unos
    private IzdavanjeDTO kreirajValidanDTO() {
        AgentDTO agent = new AgentDTO();
        agent.setId(1L);
 
        KlijentDTO klijent = new KlijentDTO();
        klijent.setId(1L);
 
        NekretninaDTO nekretnina = new NekretninaDTO();
        nekretnina.setIdNekretnina(1L);
 
        StavkaIzdavanjaDTO stavka = new StavkaIzdavanjaDTO();
        stavka.setNekretnina(nekretnina);
        stavka.setDatumPocetkaIzdavanja(new Date());
        stavka.setDatumZavrsetkaIzdavanja(new Date(System.currentTimeMillis() + 86400000L * 30));
        stavka.setMesecnaKirija(30000.0);
        stavka.setIznosDepozita(10000.0);
 
        IzdavanjeDTO dto = new IzdavanjeDTO();
        dto.setDatumSklapanjaUgovora(new Date());
        dto.setStatusUgovora("Aktivan");
        dto.setNacinPlacanja("Kartica");
        dto.setUkupanIznos(30000.0);
        dto.setNapomena("Test napomena");
        dto.setAgent(agent);
        dto.setKlijent(klijent);
        dto.setStavke(List.of(stavka));
 
        return dto;
    }
 
    @Test @Order(1)
    public void getAllIzdavanjaUspesno() {
        ResponseEntity<List<IzdavanjeDTO>> response = restTemplate.exchange(
                baseUrl + "/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IzdavanjeDTO>>() {}
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }
 
    @Test @Order(2)
    public void getIzdavanjeByIdUspesno() {
        ResponseEntity<IzdavanjeDTO> response = restTemplate.exchange(
                baseUrl + "/2",
                HttpMethod.GET,
                null,
                IzdavanjeDTO.class
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2L, response.getBody().getIdIzdavanje());
    }
 
    @Test @Order(3)
    public void getIzdavanjeByIdNeuspesnoNepostojeciId() {
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/99999",
                HttpMethod.GET,
                null,
                String.class
        );
 
        // getIzdavanjeById baca RuntimeException za nepostojeci ID,
        // globalnog exception handler baca internal server error
        assertTrue(response.getStatusCode().is5xxServerError()
                || response.getStatusCode() == HttpStatus.NOT_FOUND);
    }
 
    @Test @Order(4)
    public void getByStatusUspesnoPostojeciStatus() {
        ResponseEntity<List<IzdavanjeDTO>> response = restTemplate.exchange(
                baseUrl + "/status/Zavrsen",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IzdavanjeDTO>>() {}
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        response.getBody().forEach(i ->
                assertTrue(i.getStatusUgovora().contains("Zavrsen"))
        );
    }
 
    @Test @Order(5)
    public void getByStatusPraznaListaNepostojeciStatus() {
        ResponseEntity<List<IzdavanjeDTO>> response = restTemplate.exchange(
                baseUrl + "/status/NepostojeciStatus",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IzdavanjeDTO>>() {}
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
 
    @Test @Order(6)
    public void getByKlijentUspesnoPoImenu() {
        ResponseEntity<List<IzdavanjeDTO>> response = restTemplate.exchange(
                baseUrl + "/klijent?ime=Milan",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IzdavanjeDTO>>() {}
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }
 
    @Test @Order(7)
    public void getByKlijentUspesnoPoPrezimenu() {
        ResponseEntity<List<IzdavanjeDTO>> response = restTemplate.exchange(
                baseUrl + "/klijent?prezime=Nikolic",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IzdavanjeDTO>>() {}
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }
 
    @Test @Order(8)
    public void getByKlijentPraznaListaNepostojeciKlijent() {
        ResponseEntity<List<IzdavanjeDTO>> response = restTemplate.exchange(
                baseUrl + "/klijent?ime=Nepostojeci&prezime=Klijent",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IzdavanjeDTO>>() {}
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
 
    @Test @Order(9)
    public void getByKlijentUspesnoBezParametara() {
        ResponseEntity<List<IzdavanjeDTO>> response = restTemplate.exchange(
                baseUrl + "/klijent",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IzdavanjeDTO>>() {}
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
 
    @Test @Order(10)
    public void addIzdavanjeUspesno() {
        IzdavanjeDTO dto = kreirajValidanDTO();
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/add",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }
 
    @Test @Order(11)
    public void addIzdavanjeNeuspesnoAgentNull() {
        IzdavanjeDTO dto = kreirajValidanDTO();
        dto.setAgent(null);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/add",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
 
    @Test @Order(12)
    public void addIzdavanjeNeuspesnoNepostojeciAgent() {
        IzdavanjeDTO dto = kreirajValidanDTO();
        AgentDTO nepostojeciAgent = new AgentDTO();
        nepostojeciAgent.setId(99999L);
        dto.setAgent(nepostojeciAgent);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/add",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test @Order(13)
    public void addIzdavanjeNeuspesnoKlijentNull() {
        IzdavanjeDTO dto = kreirajValidanDTO();
        dto.setKlijent(null);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/add",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
    @Test @Order(14)
    public void addIzdavanjeNeuspesnoNepostojeciKlijent() {
        IzdavanjeDTO dto = kreirajValidanDTO();
        KlijentDTO nepostojeciKlijent = new KlijentDTO();
        nepostojeciKlijent.setId(99999L);
        dto.setKlijent(nepostojeciKlijent);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/add",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test @Order(15)
    public void addIzdavanjeNeuspesnoNullTelo() {
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/add",
                HttpMethod.POST,
                new HttpEntity<>(null),
                String.class
        );
 
        assertTrue(response.getStatusCode().is4xxClientError());
    }
    
    @Test @Order(21)
    public void updateIzdavanjeUspesno() {
        IzdavanjeDTO dto = kreirajValidanDTO();
        dto.setIdIzdavanje(2L);
        dto.setNapomena("Izmenjena napomena");
        dto.setUkupanIznos(60000.0);
 
        NekretninaDTO nekretnina = new NekretninaDTO();
        nekretnina.setIdNekretnina(3L);
        StavkaIzdavanjaDTO stavka = new StavkaIzdavanjaDTO();
        stavka.setNekretnina(nekretnina);
        stavka.setDatumPocetkaIzdavanja(new Date());
        stavka.setDatumZavrsetkaIzdavanja(new Date(System.currentTimeMillis() + 86400000L * 60));
        stavka.setMesecnaKirija(60000.0);
        stavka.setIznosDepozita(10000.0);
        dto.setStavke(List.of(stavka));
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
    }
 
    @Test @Order(16)
    public void updateIzdavanjeNeuspesnoNepostojeciId() {
        IzdavanjeDTO dto = kreirajValidanDTO();
        dto.setIdIzdavanje(99999L);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test @Order(17)
    public void updateIzdavanjeNeuspesnoAgentNull() {
        IzdavanjeDTO dto = kreirajValidanDTO();
        dto.setIdIzdavanje(2L);
        dto.setAgent(null);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
 
    @Test @Order(18)
    public void updateIzdavanjeNeuspesnoNepostojeciAgent() {
        IzdavanjeDTO dto = kreirajValidanDTO();
        dto.setIdIzdavanje(2L);
        AgentDTO nepostojeciAgent = new AgentDTO();
        nepostojeciAgent.setId(99999L);
        dto.setAgent(nepostojeciAgent);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test @Order(19)
    public void updateIzdavanjeNeuspesnoKlijentNull() {
        IzdavanjeDTO dto = kreirajValidanDTO();
        dto.setIdIzdavanje(2L);
        dto.setKlijent(null);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
 
    @Test @Order(20)
    public void updateIzdavanjeNeuspesnoNepostojeciKlijent() {
        IzdavanjeDTO dto = kreirajValidanDTO();
        dto.setIdIzdavanje(2L);
        KlijentDTO nepostojeciKlijent = new KlijentDTO();
        nepostojeciKlijent.setId(99999L);
        dto.setKlijent(nepostojeciKlijent);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test @Order(22)
    public void deleteIzdavanjeUspesno() {
        // Koristimo izdavanje id=1 iz h2 baze za brisanje
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/delete/1",
                HttpMethod.DELETE,
                null,
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
 
    @Test @Order(23)
    public void deleteIzdavanjeNeuspesnoNepostojeciId() {
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/delete/99999",
                HttpMethod.DELETE,
                null,
                ApiResponse.class
        );
 
        assertNotNull(response.getStatusCode());
    }
}
