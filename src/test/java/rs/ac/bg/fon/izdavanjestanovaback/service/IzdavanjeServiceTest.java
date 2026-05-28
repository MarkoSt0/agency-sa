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
import rs.ac.bg.fon.izdavanjestanovaback.dto.IzdavanjeDTO;
import rs.ac.bg.fon.izdavanjestanovaback.dto.KlijentDTO;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.IzdavanjeRepo;
import rs.ac.bg.fon.izdavanjestanovaback.model.Izdavanje;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 * @author Marko
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IzdavanjeServiceTest {

    @Autowired
    IzdavanjeService izdavanjeService;

    @Autowired
    IzdavanjeRepo izdavanjeRepo;

    Long testIzdavanjeId;

    @BeforeEach
    public void setUp() {
        try {
            java.nio.file.Files.deleteIfExists(java.nio.file.Path.of("izdavanja-log.json"));
        } catch (Exception e) {}
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setId(1L);

        KlijentDTO klijentDTO = new KlijentDTO();
        klijentDTO.setId(1L);

        IzdavanjeDTO dto = new IzdavanjeDTO();
        dto.setDatumSklapanjaUgovora(new Date());
        dto.setStatusUgovora("Aktivan");
        dto.setNacinPlacanja("Gotovina");
        dto.setUkupanIznos(5000.0);
        dto.setNapomena("Test napomena");
        dto.setAgent(agentDTO);
        dto.setKlijent(klijentDTO);

        ServiceResult result = izdavanjeService.addIzdavanje(dto);
        assertTrue(result.isUspesno(), "setUp nije uspeo: " + result.getPoruka());

        testIzdavanjeId = izdavanjeRepo.findAll()
                .stream()
                .mapToLong(Izdavanje::getIdIzdavanje)
                .max()
                .getAsLong();
    }

    @AfterEach
    public void tearDown() {
        if (testIzdavanjeId != null && izdavanjeRepo.existsById(testIzdavanjeId)) {
            izdavanjeRepo.deleteById(testIzdavanjeId);
        }
        try {
            java.nio.file.Files.deleteIfExists(java.nio.file.Path.of("izdavanja-log.json"));
        } catch (Exception e) {}
    }

    public IzdavanjeDTO napraviValidanDTO() {
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setId(1L);

        KlijentDTO klijentDTO = new KlijentDTO();
        klijentDTO.setId(1L);

        IzdavanjeDTO dto = new IzdavanjeDTO();
        dto.setIdIzdavanje(testIzdavanjeId);
        dto.setDatumSklapanjaUgovora(new Date());
        dto.setStatusUgovora("Aktivan");
        dto.setNacinPlacanja("Gotovina");
        dto.setUkupanIznos(5000.0);
        dto.setNapomena("Test napomena");
        dto.setAgent(agentDTO);
        dto.setKlijent(klijentDTO);
        return dto;
    }

    @Test
    public void testGetIzdavanjeByIdUspesno() {
        IzdavanjeDTO result = izdavanjeService.getIzdavanjeById(testIzdavanjeId);

        assertNotNull(result);
        assertEquals(testIzdavanjeId, result.getIdIzdavanje());
        assertEquals("Aktivan", result.getStatusUgovora());
        assertEquals("Gotovina", result.getNacinPlacanja());
        assertEquals(5000.0, result.getUkupanIznos());
    }

    @Test
    public void testGetIzdavanjeByIdNeuspesnoNepostojeciId() {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> izdavanjeService.getIzdavanjeById(99999L)
        );
        assertNotNull(ex);
        assertEquals("Izdavanje nije pronađeno", ex.getMessage());
    }

    @Test
    public void testAddIzdavanjeUspesno() {
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setId(2L);

        KlijentDTO klijentDTO = new KlijentDTO();
        klijentDTO.setId(2L);

        IzdavanjeDTO dto = new IzdavanjeDTO();
        dto.setDatumSklapanjaUgovora(new Date());
        dto.setStatusUgovora("Aktivan");
        dto.setNacinPlacanja("Kartica");
        dto.setUkupanIznos(8000.0);
        dto.setNapomena("Novo izdavanje");
        dto.setAgent(agentDTO);
        dto.setKlijent(klijentDTO);

        ServiceResult result = izdavanjeService.addIzdavanje(dto);

        assertTrue(result.isUspesno());
        assertEquals("Izdavanje je uspešno sačuvano", result.getPoruka());

        Long noviId = izdavanjeRepo.findAll()
                .stream()
                .mapToLong(Izdavanje::getIdIzdavanje)
                .max()
                .getAsLong();

        Izdavanje sacuvano = izdavanjeRepo.findById(noviId).orElseThrow();
        assertEquals("Aktivan", sacuvano.getStatusUgovora());
        assertEquals("Kartica", sacuvano.getNacinPlacanja());
        assertEquals(8000.0, sacuvano.getUkupanIznos());

        izdavanjeRepo.deleteById(noviId);
    }

    @Test
    public void testAddIzdavanjeNeuspesnoNullKlijent() {
        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setIdIzdavanje(null);
        dto.setKlijent(null);

        ServiceResult result = izdavanjeService.addIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertEquals("Klijent mora biti naveden", result.getPoruka());
    }

    @Test
    public void testAddIzdavanjeNeuspesnoNepostojeciKlijent() {
        KlijentDTO nepostojeciKlijent = new KlijentDTO();
        nepostojeciKlijent.setId(99999L);

        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setIdIzdavanje(null);
        dto.setKlijent(nepostojeciKlijent);

        ServiceResult result = izdavanjeService.addIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertEquals("Klijent sa ID 99999 ne postoji", result.getPoruka());
    }

    @Test
    public void testAddIzdavanjeNeuspesnoNullAgent() {
        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setIdIzdavanje(null);
        dto.setAgent(null);

        ServiceResult result = izdavanjeService.addIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertEquals("Agent mora biti naveden", result.getPoruka());
    }

    @Test
    public void testAddIzdavanjeNeuspesnoNepostojeciAgent() {
        AgentDTO nepostojeciAgent = new AgentDTO();
        nepostojeciAgent.setId(99999L);

        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setIdIzdavanje(null);
        dto.setAgent(nepostojeciAgent);

        ServiceResult result = izdavanjeService.addIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertEquals("Agent sa ID 99999 ne postoji", result.getPoruka());
    }

    @Test
    public void testAddIzdavanjeNeuspesnoPrazanStatusUgovora() {
        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setIdIzdavanje(null);
        //NotBlank
        dto.setStatusUgovora("");

        ServiceResult result = izdavanjeService.addIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Status ugovora je obavezan."));
    }

    @Test
    public void testAddIzdavanjeNeuspesnoPrazanNacinPlacanja() {
        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setIdIzdavanje(null);
        //NotBlank
        dto.setNacinPlacanja("");

        ServiceResult result = izdavanjeService.addIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Nacin placanja je obavezan."));
    }

    @Test
    public void testAddIzdavanjeNeuspesnoNullUkupanIznos() {
        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setIdIzdavanje(null);
        //NotNull
        dto.setUkupanIznos(null);

        ServiceResult result = izdavanjeService.addIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Ukupan iznos mora biti naveden."));
    }

    @Test
    public void testAddIzdavanjeNeuspesnoNegativanUkupanIznos() {
        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setIdIzdavanje(null);
        //Min 0
        dto.setUkupanIznos(-100.0);

        ServiceResult result = izdavanjeService.addIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Ukupan iznos ne sme biti negativan broj."));
    }

    @Test
    public void testAddIzdavanjeNeuspesnoDatumUBuducnosti() {
        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setIdIzdavanje(null);
        // Proslost ili trenutno
        dto.setDatumSklapanjaUgovora(new Date(System.currentTimeMillis() + 86400000L));

        ServiceResult result = izdavanjeService.addIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Datum sklapanja ugovora mora biti danasnji datum ili datum u proslosti."));
    }

    @Test
    public void testUpdateIzdavanjeUspesno() {
        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setStatusUgovora("Zavrsen");
        dto.setNacinPlacanja("Racun");
        dto.setUkupanIznos(9999.0);

        ServiceResult result = izdavanjeService.updateIzdavanje(dto);

        assertTrue(result.isUspesno());
        assertEquals("Izdavanje je uspešno izmenjeno", result.getPoruka());

        Izdavanje sacuvano = izdavanjeRepo.findById(testIzdavanjeId).orElseThrow();
        assertEquals("Zavrsen", sacuvano.getStatusUgovora());
        assertEquals("Racun", sacuvano.getNacinPlacanja());
        assertEquals(9999.0, sacuvano.getUkupanIznos());
    }

    @Test
    public void testUpdateIzdavanjeNeuspesnoNepostojeciId() {
        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setIdIzdavanje(99999L);

        ServiceResult result = izdavanjeService.updateIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertEquals("Izdavanje ne postoji", result.getPoruka());
    }

    @Test
    public void testUpdateIzdavanjeNeuspesnoNullAgent() {
        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setAgent(null);

        ServiceResult result = izdavanjeService.updateIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertEquals("Agent mora biti naveden", result.getPoruka());
    }

    @Test
    public void testUpdateIzdavanjeNeuspesnoNepostojeciAgent() {
        AgentDTO nepostojeciAgent = new AgentDTO();
        nepostojeciAgent.setId(99999L);

        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setAgent(nepostojeciAgent);

        ServiceResult result = izdavanjeService.updateIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertEquals("Agent sa ID 99999 ne postoji", result.getPoruka());
    }

    @Test
    public void testUpdateIzdavanjeNeuspesnoNullKlijent() {
        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setKlijent(null);

        ServiceResult result = izdavanjeService.updateIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertEquals("Klijent mora biti naveden", result.getPoruka());
    }

    @Test
    public void testUpdateIzdavanjeNeuspesnoNepostojeciKlijent() {
        KlijentDTO nepostojeciKlijent = new KlijentDTO();
        nepostojeciKlijent.setId(99999L);

        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setKlijent(nepostojeciKlijent);

        ServiceResult result = izdavanjeService.updateIzdavanje(dto);

        assertFalse(result.isUspesno());
        assertEquals("Klijent sa ID 99999 ne postoji", result.getPoruka());
    }

    public void proveriJakartaViolation(IzdavanjeDTO dto, String ocekivanaPoruka) {
        TransactionSystemException ex = assertThrows(
                TransactionSystemException.class,
                () -> izdavanjeService.updateIzdavanje(dto)
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
    public void testUpdateIzdavanjeNeuspesnoPrazanStatusUgovora() {
        IzdavanjeDTO dto = napraviValidanDTO();
        //NotBlank
        dto.setStatusUgovora("");

        proveriJakartaViolation(dto, "Status ugovora je obavezan.");
    }

    @Test
    public void testUpdateIzdavanjeNeuspesnoPrazanNacinPlacanja() {
        IzdavanjeDTO dto = napraviValidanDTO();
        //NotBlank
        dto.setNacinPlacanja("");

        proveriJakartaViolation(dto, "Nacin placanja je obavezan.");
    }

    @Test
    public void testUpdateIzdavanjeNeuspesnoNullUkupanIznos() {
        IzdavanjeDTO dto = napraviValidanDTO();
        //NotNUll
        dto.setUkupanIznos(null);

        proveriJakartaViolation(dto, "Ukupan iznos mora biti naveden.");
    }

    @Test
    public void testUpdateIzdavanjeNeuspesnoNegativanUkupanIznos() {
        IzdavanjeDTO dto = napraviValidanDTO();
        //Min
        dto.setUkupanIznos(-100.0);

        proveriJakartaViolation(dto, "Ukupan iznos ne sme biti negativan broj.");
    }

    @Test
    public void testUpdateIzdavanjeNeuspesnoDatumUBuducnosti() {
        IzdavanjeDTO dto = napraviValidanDTO();
        dto.setDatumSklapanjaUgovora(new Date(System.currentTimeMillis() + 86400000L));

        proveriJakartaViolation(dto, "Datum sklapanja ugovora mora biti danasnji datum ili datum u proslosti.");
    }

    @Test
    public void testDeleteIzdavanjeUspesno() {
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setId(1L);

        KlijentDTO klijentDTO = new KlijentDTO();
        klijentDTO.setId(3L);

        IzdavanjeDTO dto = new IzdavanjeDTO();
        dto.setDatumSklapanjaUgovora(new Date());
        dto.setStatusUgovora("Zavrsen");
        dto.setNacinPlacanja("Gotovina");
        dto.setUkupanIznos(1000.0);
        dto.setAgent(agentDTO);
        dto.setKlijent(klijentDTO);

        izdavanjeService.addIzdavanje(dto);

        Long idZaBrisanje = izdavanjeRepo.findAll()
                .stream()
                .mapToLong(Izdavanje::getIdIzdavanje)
                .max()
                .getAsLong();

        ServiceResult result = izdavanjeService.deleteIzdavanje(idZaBrisanje);

        assertTrue(result.isUspesno());
        assertEquals("Izdavanje je obrisano", result.getPoruka());
        assertFalse(izdavanjeRepo.existsById(idZaBrisanje));
    }
}