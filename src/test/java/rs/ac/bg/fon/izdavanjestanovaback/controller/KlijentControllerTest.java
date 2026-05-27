//package rs.ac.bg.fon.izdavanjestanovaback.controller;
//
//
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponse;
//import rs.ac.bg.fon.izdavanjestanovaback.dto.KlijentDTO;
//import rs.ac.bg.fon.izdavanjestanovaback.dto.MestoDTO;
//
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.TestMethodOrder;
//
///**
// *
// * @author Marko
// */
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class KlijentControllerTest {
//    
//     @LocalServerPort
//    private int port;
// 
//    @Autowired
//    private TestRestTemplate restTemplate;
// 
//    private String baseUrl;
// 
//    @BeforeEach
//    public void setUp() {
//        baseUrl = "http://localhost:" + port + "/klijenti";
//    }
// 
//    private KlijentDTO kreirajValidanDTO() {
//        MestoDTO mesto = new MestoDTO();
//        mesto.setId(1L);
//        mesto.setNazivMesta("Beograd");
// 
//        KlijentDTO dto = new KlijentDTO();
//        dto.setIme("Zika");
//        dto.setPrezime("Zivkovic");
//        dto.setStarost(151);
//        dto.setBrojTelefona("0641234567");
//        dto.setEmail("zika@gmail.com");
//        dto.setMesto(mesto);
//        return dto;
//    }
// 
//    @Test
//    @Order(1)
//    public void getAllKlijenti_Uspesno() {
//        ResponseEntity<List<KlijentDTO>> response = restTemplate.exchange(
//                baseUrl + "/all",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<KlijentDTO>>() {}
//        );
// 
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertFalse(response.getBody().isEmpty());
//    }
// 
//    @Test
//    @Order(2)
//    public void getKlijentById_Uspesno() {
//        ResponseEntity<KlijentDTO> response = restTemplate.exchange(
//                baseUrl + "/1",
//                HttpMethod.GET,
//                null,
//                KlijentDTO.class
//        );
// 
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(1L, response.getBody().getId());
//    }
// 
//    @Test
//    @Order(3)
//    public void getKlijentById_Neuspesno_NepostojeciId() {
//        ResponseEntity<String> response = restTemplate.exchange(
//                baseUrl + "/99999",
//                HttpMethod.GET,
//                null,
//                String.class
//        );
// 
//        // RuntimeException se hendluje kroz GlobalExceptionHandler -> 404
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
// 
//    @Test
//    @Order(4)
//    public void pretragaKlijenata_Uspesno_PostojeciEmail() {
//        ResponseEntity<List<KlijentDTO>> response = restTemplate.exchange(
//                baseUrl + "/pretraga?email=milan",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<KlijentDTO>>() {}
//        );
// 
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertFalse(response.getBody().isEmpty());
//    }
// 
//    @Test
//    @Order(5)
//    public void pretragaKlijenata_PraznaLista_NepostojeciEmail() {
//        ResponseEntity<List<KlijentDTO>> response = restTemplate.exchange(
//                baseUrl + "/pretraga?email=nepostojeci_korisnik_xyz",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<KlijentDTO>>() {}
//        );
// 
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertTrue(response.getBody().isEmpty());
//    }
// 
//    @Test
//    @Order(6)
//    public void pretragaKlijenata_Uspesno_BezParametra() {
//        ResponseEntity<List<KlijentDTO>> response = restTemplate.exchange(
//                baseUrl + "/pretraga",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<KlijentDTO>>() {}
//        );
// 
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }
// 
//    @Test
//    @Order(7)
//    public void pretragaKlijenata_Uspesno_PrazanEmail() {
//        // 1. Prvo prihvatamo odgovor kao čist String da izbegnemo Jackson krah
//        ResponseEntity<String> response = restTemplate.getForEntity(
//                baseUrl + "/pretraga?email=",
//                String.class
//        );
//
//        // 2. Štampamo u konzolu šta nam je bek STVARNO poslao
//        System.out.println("STVARNI ODGOVOR SA BEKA: " + response.getBody());
//
//        // 3. Privremena asercija da vidimo status
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
// 
//    // ==================== UNOS ====================
// 
//    @Test
//    @Order(10)
//    public void addKlijent_Uspesno() {
//        KlijentDTO dto = kreirajValidanDTO();
// 
//        ResponseEntity<ApiResponse> response = restTemplate.exchange(
//                baseUrl + "/add",
//                HttpMethod.POST,
//                new HttpEntity<>(dto),
//                ApiResponse.class
//        );
//        
//        String vracenaPoruka = response.getBody().getPoruka();
//        
//        System.out.println(vracenaPoruka);
// 
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }
// 
//    @Test
//    @Order(11)
//    public void addKlijent_Neuspesno_EmailVecPostoji() {
//        KlijentDTO dto = kreirajValidanDTO();
//        // Email "milan@gmail.com" vec postoji u data.sql
//        dto.setEmail("milan@gmail.com");
// 
//        ResponseEntity<ApiResponse> response = restTemplate.exchange(
//                baseUrl + "/add",
//                HttpMethod.POST,
//                new HttpEntity<>(dto),
//                ApiResponse.class
//        );
// 
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//    }
// 
//    @Test
//    @Order(12)
//    public void addKlijent_Neuspesno_DuplikatEmailIzPrethodногTesta() {
//        // Pokusavamo ponovo ubaciti klijenta sa emailom koji je dodat u Order(10)
//        KlijentDTO dto = kreirajValidanDTO();
// 
//        ResponseEntity<ApiResponse> response = restTemplate.exchange(
//                baseUrl + "/add",
//                HttpMethod.POST,
//                new HttpEntity<>(dto),
//                ApiResponse.class
//        );
// 
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//    }
// 
//    @Test
//    @Order(13)
//    public void addKlijent_Neuspesno_NullTelo() {
//        ResponseEntity<String> response = restTemplate.exchange(
//                baseUrl + "/add",
//                HttpMethod.POST,
//                new HttpEntity<>(null),
//                String.class
//        );
// 
//        assertTrue(response.getStatusCode().is4xxClientError());
//    }
// 
//    // ==================== BRISANJE ====================
// 
//    @Test
//    @Order(20)
//    public void deleteKlijent_Uspesno() {
//        // Klijent id=3 (Stefan Petrovic) nije vezan ni za jedno izdavanje u data.sql
//        ResponseEntity<ApiResponse> response = restTemplate.exchange(
//                baseUrl + "/delete/10",
//                HttpMethod.DELETE,
//                null,
//                ApiResponse.class
//        );
// 
//        // Kontroler uvek vraca 200 bez obzira na rezultat servisa
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }
// 
//    @Test
//    @Order(21)
//    public void deleteKlijent_Neuspesno_PovezanSaIzdavanjem() {
//        // Klijent id=1 (Milan Jovanovic) je vezan za izdavanje id=1 u data.sql
//        ResponseEntity<ApiResponse> response = restTemplate.exchange(
//                baseUrl + "/delete/1",
//                HttpMethod.DELETE,
//                null,
//                ApiResponse.class
//        );
// 
//        // Kontroler uvek vraca 200 - ovo je svesno ponasanje trenutne implementacije
//        // Ako se kontroler ispravi da vraca BAD_REQUEST, ovaj test treba azurirati
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//    }
// 
//    @Test
//    @Order(22)
//    public void deleteKlijent_NepostojeciId() {
//        ResponseEntity<ApiResponse> response = restTemplate.exchange(
//                baseUrl + "/delete/99999",
//                HttpMethod.DELETE,
//                null,
//                ApiResponse.class
//        );
// 
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//    
//}
