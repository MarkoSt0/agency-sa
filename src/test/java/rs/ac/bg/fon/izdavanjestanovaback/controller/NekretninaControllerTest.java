package rs.ac.bg.fon.izdavanjestanovaback.controller;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponse;
import rs.ac.bg.fon.izdavanjestanovaback.dto.NekretninaDTO;
import rs.ac.bg.fon.izdavanjestanovaback.dto.TipNekretnineDTO;
import rs.ac.bg.fon.izdavanjestanovaback.enums.StatusNekretnine;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import rs.ac.bg.fon.izdavanjestanovaback.enums.TipGrejanja;

/**
 *
 * @author Marko
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class NekretninaControllerTest {
    @LocalServerPort
    private int port;
 
    @Autowired
    private TestRestTemplate restTemplate;
 
    private String baseUrl;
 
    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/nekretnina";
    }
 
    private NekretninaDTO kreirajValidanDTO() {
        TipNekretnineDTO tip = new TipNekretnineDTO();
        tip.setIdTipaNekretnine(1L);
 
        NekretninaDTO dto = new NekretninaDTO();
        dto.setAdresa("Nova adresa 99");
        dto.setPovrsina("60m2");
        dto.setSprat("2");
        dto.setBrojSoba(2.0);
        dto.setGodinaIzgradnje(2010);
        dto.setStatusNekretnine(StatusNekretnine.DOSTUPNA);
        dto.setTipGrejanja(TipGrejanja.GAS);
        dto.setOpis("Test nekretnina");
        dto.setTipNekretnine(tip);
        return dto;
    }
 
    @Test
    @Order(1)
    public void getAllNekretnineUspesno() {
        ResponseEntity<List<NekretninaDTO>> response = restTemplate.exchange(
                baseUrl + "/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NekretninaDTO>>() {}
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }
 
    @Test
    @Order(2)
    public void getNekretninaByIdUspesno() {
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/1",
                HttpMethod.GET,
                null,
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
 
    @Test
    @Order(3)
    public void getNekretninaByIdNeuspesno_NepostojeciId() {
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/99999",
                HttpMethod.GET,
                null,
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test
    @Order(4)
    public void getByPovrsinaUspesnoPostojecaPovrsina() {
        ResponseEntity<List<NekretninaDTO>> response = restTemplate.exchange(
                baseUrl + "/povrsina/75m2",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NekretninaDTO>>() {}
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }
 
    @Test
    @Order(5)
    public void getByPovrsinaPraznaListaNepostojecaPovrsina() {
        ResponseEntity<List<NekretninaDTO>> response = restTemplate.exchange(
                baseUrl + "/povrsina/999m2",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NekretninaDTO>>() {}
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
 
    @Test
    @Order(6)
    public void getByTipUspesnoPostojeciTip() {
        ResponseEntity<List<NekretninaDTO>> response = restTemplate.exchange(
                baseUrl + "/tip/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NekretninaDTO>>() {}
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }
 
    @Test
    @Order(7)
    public void getByTipPraznaListaNepostojeciTip() {
        ResponseEntity<List<NekretninaDTO>> response = restTemplate.exchange(
                baseUrl + "/tip/99999",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NekretninaDTO>>() {}
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
 
    @Test
    @Order(10)
    public void addNekretninaUspesno() {
        NekretninaDTO dto = kreirajValidanDTO();
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/add",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }
 
    @Test
    @Order(11)
    public void addNekretninaUspesnoBezTipa() {
        // TipNekretnine je opcionalan u servisu - null je dozvoljen!!!
        NekretninaDTO dto = kreirajValidanDTO();
        dto.setTipNekretnine(null);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/add",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
 
    @Test
    @Order(12)
    public void addNekretninaNeuspesnoTipNullId() {
        NekretninaDTO dto = kreirajValidanDTO();
        TipNekretnineDTO tipBezId = new TipNekretnineDTO();
        tipBezId.setIdTipaNekretnine(null);
        dto.setTipNekretnine(tipBezId);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/add",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
 
    @Test
    @Order(13)
    public void addNekretninaNeuspesnoNepostojeciTip() {
        NekretninaDTO dto = kreirajValidanDTO();
        TipNekretnineDTO nepostojeciTip = new TipNekretnineDTO();
        nepostojeciTip.setIdTipaNekretnine(99999L);
        dto.setTipNekretnine(nepostojeciTip);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/add",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test
    @Order(14)
    public void addNekretninaNeuspesnoNullTelo() {
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/add",
                HttpMethod.POST,
                new HttpEntity<>(null),
                String.class
        );
 
        assertTrue(response.getStatusCode().is4xxClientError());
    }
 
    @Test
    @Order(16)
    public void updateNekretninaNeuspesnoNepostojeciId() {
        NekretninaDTO dto = kreirajValidanDTO();
        dto.setIdNekretnina(99999L);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test
    @Order(17)
    public void updateNekretninaNeuspesnoTipNullId() {
        NekretninaDTO dto = kreirajValidanDTO();
        dto.setIdNekretnina(1L);
        TipNekretnineDTO tipBezId = new TipNekretnineDTO();
        tipBezId.setIdTipaNekretnine(null);
        dto.setTipNekretnine(tipBezId);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
 
    @Test
    @Order(18)
    public void updateNekretninaNeuspesnoNepostojeciTip() {
        NekretninaDTO dto = kreirajValidanDTO();
        dto.setIdNekretnina(1L);
        TipNekretnineDTO nepostojeciTip = new TipNekretnineDTO();
        nepostojeciTip.setIdTipaNekretnine(99999L);
        dto.setTipNekretnine(nepostojeciTip);
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test
    @Order(19)
    public void updateNekretninaUspesno() {
        NekretninaDTO dto = kreirajValidanDTO();
        dto.setIdNekretnina(1L);
        dto.setAdresa("Izmenjena adresa 1");
        dto.setOpis("Izmenjen opis");
 
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
    }
 
    @Test
    @Order(20)
    public void deleteNekretninaNeuspesnoPovezanaSaIzdavanjem() {
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/delete/3",
                HttpMethod.DELETE,
                null,
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
 
    @Test
    @Order(21)
    public void deleteNekretninaNeuspesnoNepostojeciId() {
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/delete/99999",
                HttpMethod.DELETE,
                null,
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
 
    @Test
    @Order(22)
    public void deleteNekretninaUspesno() {
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl + "/delete/1",
                HttpMethod.DELETE,
                null,
                ApiResponse.class
        );
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
