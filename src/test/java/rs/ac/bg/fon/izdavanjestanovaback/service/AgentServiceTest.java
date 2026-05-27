package rs.ac.bg.fon.izdavanjestanovaback.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.AgentDTO;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.AgentRepo;
import rs.ac.bg.fon.izdavanjestanovaback.model.Agent;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AgentServiceTest {

    @Autowired
    AgentService agentService;

    @Autowired
    AgentRepo agentRepo;

    Long testAgentId;

    @BeforeEach
    public void setUp() {
        Agent a = new Agent();
        a.setBrojLicence("LIC-TEST");
        a.setDatumIstekaLicence(new Date(System.currentTimeMillis() + 86400000L * 365));
        a.setIme("TestIme");
        a.setPrezime("TestPrezime");
        a.setKorisnickoIme("test_agent_update");
        a.setSifra("sifra123");
        testAgentId = agentRepo.save(a).getId();
    }

    @AfterEach
    public void tearDown() {
        if (testAgentId != null && agentRepo.existsById(testAgentId)) {
            agentRepo.deleteById(testAgentId);
        }
    }

    public AgentDTO napraviValidanDTO() {
        AgentDTO dto = new AgentDTO();
        dto.setId(testAgentId);
        dto.setBrojLicence("LIC-TEST");
        dto.setDatumIstekaLicence(new Date(System.currentTimeMillis() + 86400000L * 365));
        dto.setIme("TestIme");
        dto.setPrezime("TestPrezime");
        dto.setKorisnickoIme("test_agent_update");
        dto.setSifra("sifra123");
        return dto;
    }
    
    public void proveriJakartaViolation(AgentDTO dto, String ocekivanaPoruka) {
        TransactionSystemException ex = assertThrows(
            TransactionSystemException.class,
            () -> agentService.updateAgent(dto)
        );

        Throwable cause = ex.getCause().getCause();
        assertInstanceOf(jakarta.validation.ConstraintViolationException.class, cause);

        jakarta.validation.ConstraintViolationException cve =
            (jakarta.validation.ConstraintViolationException) cause;

        String poruke = cve.getConstraintViolations().stream()
            .map(v -> v.getMessage())
            .collect(java.util.stream.Collectors.joining());

        assertTrue(poruke.contains(ocekivanaPoruka));
    }

    @Test
    public void testLoginUspesno() {
        ServiceResult result = agentService.login("pera123", "sifra123");

        assertTrue(result.isUspesno());
        assertEquals("Uspešna prijava.", result.getPoruka());
        assertNotNull(result.getPodaci());
    }

    @Test
    public void testLoginNeuspesnoPogresnaSifra() {
        ServiceResult result = agentService.login("pera123", "pogresna_sifra");

        assertFalse(result.isUspesno());
        assertEquals("Pogrešno korisničko ime ili šifra.", result.getPoruka());
    }

    @Test
    public void testLoginNeuspesnoNepostojeceKorisnickoIme() {
        ServiceResult result = agentService.login("nepostojeci_korisnik_xyz", "sifra123");

        assertFalse(result.isUspesno());
        assertEquals("Pogrešno korisničko ime ili šifra.", result.getPoruka());
    }

    @Test
    public void testLoginNeuspesnoNullKorisnickoIme() {
        ServiceResult result = agentService.login(null, "sifra123");

        assertFalse(result.isUspesno());
        assertEquals("Korisničko ime je obavezno.", result.getPoruka());
    }

    @Test
    public void testLoginNeuspesnoBlankKorisnickoIme() {
        ServiceResult result = agentService.login("   ", "sifra123");

        assertFalse(result.isUspesno());
        assertEquals("Korisničko ime je obavezno.", result.getPoruka());
    }

    @Test
    public void testLoginNeuspesnoNullSifra() {
        ServiceResult result = agentService.login("pera123", null);

        assertFalse(result.isUspesno());
        assertEquals("Šifra je obavezna.", result.getPoruka());
    }

    @Test
    public void testLoginNeuspesnoBlankSifra() {
        ServiceResult result = agentService.login("pera123", "   ");

        assertFalse(result.isUspesno());
        assertEquals("Šifra je obavezna.", result.getPoruka());
    }

    @Test
    public void testUpdateAgentUspesno() {
        AgentDTO dto = napraviValidanDTO();
        dto.setIme("IzmenjenoIme");
        dto.setPrezime("IzmenjenoPrezime");
        dto.setBrojLicence("LIC-NOVO");

        ServiceResult result = agentService.updateAgent(dto);

        assertTrue(result.isUspesno());
        assertEquals("Sistem je izmenio agenta.", result.getPoruka());

        Agent sacuvan = agentRepo.findById(testAgentId).orElseThrow();
        assertEquals("IzmenjenoIme", sacuvan.getIme());
        assertEquals("IzmenjenoPrezime", sacuvan.getPrezime());
        assertEquals("LIC-NOVO", sacuvan.getBrojLicence());
    }

    @Test
    public void testUpdateAgentNeuspesnoNullDTO() {
        ServiceResult result = agentService.updateAgent(null);

        assertFalse(result.isUspesno());
        assertEquals("Nije prosledjen agent.", result.getPoruka());
    }

    @Test
    public void testUpdateAgentNeuspesnoBezId() {
        AgentDTO dto = napraviValidanDTO();
        dto.setId(null);

        ServiceResult result = agentService.updateAgent(dto);

        assertFalse(result.isUspesno());
        assertEquals("ID agenta je obavezan za izmenu.", result.getPoruka());
    }

    @Test
    public void testUpdateAgentNeuspesnoNepostojeciId() {
        AgentDTO dto = napraviValidanDTO();
        dto.setId(99999L);

        ServiceResult result = agentService.updateAgent(dto);

        assertFalse(result.isUspesno());
        assertEquals("Agent sa datim ID nije pronađen.", result.getPoruka());
    }

    @Test
    public void testUpdateAgentNeuspesnoPraznoIme() {
        AgentDTO dto = napraviValidanDTO();
        dto.setIme("");
        
        proveriJakartaViolation(dto, "Ime agenta je obavezno.");

    }

    @Test
    public void testUpdateAgentNeuspesnoPraznoPrezime() {
        AgentDTO dto = napraviValidanDTO();
        dto.setPrezime("");

        proveriJakartaViolation(dto, "Prezime agenta je obavezno.");
    }

    @Test
    public void testUpdateAgentNeuspesnoPrazanBrojLicence() {
        AgentDTO dto = napraviValidanDTO();
        dto.setBrojLicence("");

        proveriJakartaViolation(dto, "Broj licence je obavezan.");
    }

    @Test
    public void testUpdateAgentNeuspesnoDatumUProslosti() {
        AgentDTO dto = napraviValidanDTO();
        dto.setDatumIstekaLicence(new Date(System.currentTimeMillis() - 86400000L));

        proveriJakartaViolation(dto, "Datum isteka licence mora biti danasnji datum ili datum u buducnosti");
    }

    @Test
    public void testUpdateAgentNeuspesnoKratkoKorisnickoIme() {
        AgentDTO dto = napraviValidanDTO();
        dto.setKorisnickoIme("ab");

        proveriJakartaViolation(dto, "Korisničko ime mora imati između 3 i 50 karaktera.");
    }

    @Test
    public void testUpdateAgentNeuspesnoKratkaSifra() {
        AgentDTO dto = napraviValidanDTO();
        dto.setSifra("abc");
        
        proveriJakartaViolation(dto, "Šifra mora imati između 5 i 100 karaktera.");
    }
}