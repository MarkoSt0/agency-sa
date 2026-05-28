package rs.ac.bg.fon.izdavanjestanovaback.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.KlijentDTO;
import rs.ac.bg.fon.izdavanjestanovaback.dto.MestoDTO;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.KlijentRepo;
import rs.ac.bg.fon.izdavanjestanovaback.model.Klijent;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * 
 * @author Marko
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KlijentServiceTest {

    @Autowired
    KlijentService klijentService;

    @Autowired
    KlijentRepo klijentRepo;

    Long testKlijentId;

    @BeforeEach
    public void setUp() {
        MestoDTO mestoDTO = new MestoDTO(1L, "Beograd", "Vozdovac");

        KlijentDTO dto = new KlijentDTO();
        dto.setIme("TestIme");
        dto.setPrezime("TestPrezime");
        dto.setStarost(30);
        dto.setBrojTelefona("0611111111");
        dto.setEmail("test.klijent@gmail.com");
        dto.setMesto(mestoDTO);

        ServiceResult result = klijentService.addKlijent(dto);
        assertTrue(result.isUspesno(), "setUp nije uspeo: " + result.getPoruka());

        testKlijentId = klijentRepo.findAll()
                .stream()
                .mapToLong(Klijent::getId)
                .max()
                .getAsLong();
    }

    @AfterEach
    public void tearDown() {
        if (testKlijentId != null && klijentRepo.existsById(testKlijentId)) {
            klijentRepo.deleteById(testKlijentId);
        }
    }

    public KlijentDTO napraviValidanDTO() {
        MestoDTO mestoDTO = new MestoDTO(1L, "Beograd", "Vozdovac");

        KlijentDTO dto = new KlijentDTO();
        dto.setId(testKlijentId);
        dto.setIme("TestIme");
        dto.setPrezime("TestPrezime");
        dto.setStarost(30);
        dto.setBrojTelefona("0611111111");
        dto.setEmail("test.klijent@gmail.com");
        dto.setMesto(mestoDTO);
        return dto;
    }

    public void proveriJakartaViolation(KlijentDTO dto, String ocekivanaPoruka) {
        ServiceResult result = klijentService.addKlijent(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains(ocekivanaPoruka));
    }

    @Test
    public void testGetKlijentByIdUspesno() {
        KlijentDTO result = klijentService.getKlijentById(testKlijentId);

        assertNotNull(result);
        assertEquals(testKlijentId, result.getId());
        assertEquals("TestIme", result.getIme());
        assertEquals("TestPrezime", result.getPrezime());
        assertEquals("test.klijent@gmail.com", result.getEmail());
    }

    @Test
    public void testGetKlijentByIdNeuspesnoNepostojeciId() {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> klijentService.getKlijentById(99999L)
        );

        assertNotNull(ex);
        assertEquals("Klijent nije pronađen", ex.getMessage());
    }

    @Test
    public void testAddKlijentUspesno() {
        MestoDTO mestoDTO = new MestoDTO(2L, "Novi Sad", "Novi Sad");

        KlijentDTO dto = new KlijentDTO();
        dto.setIme("NoviIme");
        dto.setPrezime("NoviPrezime");
        dto.setStarost(25);
        dto.setBrojTelefona("0622222222");
        dto.setEmail("novi.klijent@gmail.com");
        dto.setMesto(mestoDTO);

        ServiceResult result = klijentService.addKlijent(dto);

        assertTrue(result.isUspesno());
        assertEquals("Sistem je sacuvao korisnika", result.getPoruka());

        assertTrue(klijentRepo.existsByEmail("novi.klijent@gmail.com"));

        klijentRepo.findAll().stream()
                .filter(k -> "novi.klijent@gmail.com".equals(k.getEmail()))
                .findFirst()
                .ifPresent(k -> klijentRepo.deleteById(k.getId()));
    }

    @Test
    public void testAddKlijentNeuspesnoDuplikatEmail() {
        KlijentDTO dto = napraviValidanDTO();
        dto.setId(0);

        ServiceResult result = klijentService.addKlijent(dto);

        assertFalse(result.isUspesno());
        assertEquals("Email je vec evidentiran.", result.getPoruka());
    }

    @Test
    public void testAddKlijentNeuspesnoPraznoIme() {
        KlijentDTO dto = napraviValidanDTO();
        dto.setId(0);
        dto.setEmail("prazno.ime@gmail.com");
        dto.setIme("");

        proveriJakartaViolation(dto, "Ime klijenta je obavezno.");
    }

    @Test
    public void testAddKlijentNeuspesnoPraznoPrezime() {
        KlijentDTO dto = napraviValidanDTO();
        dto.setId(0);
        dto.setEmail("gmail@gmail.com");
        dto.setPrezime("");

        proveriJakartaViolation(dto, "Prezime klijenta je obavezno.");
    }

    @Test
    public void testAddKlijentNeuspesnoPogrešanEmail() {
        KlijentDTO dto = napraviValidanDTO();
        dto.setId(0);
        dto.setEmail("neispravan-email-format");

        proveriJakartaViolation(dto, "Email mora biti u ispravnom formatu.");
    }

    @Test
    public void testAddKlijentNeuspesnoPrazanBrojTelefona() {
        KlijentDTO dto = napraviValidanDTO();
        dto.setId(0);
        dto.setEmail("gmail@gmail.com");
        dto.setBrojTelefona("");

        proveriJakartaViolation(dto, "Broj telefona je obavezan.");
    }

    @Test
    public void testAddKlijentNeuspesnoPrevelikaStarost() {
        KlijentDTO dto = napraviValidanDTO();
        dto.setId(0);
        dto.setEmail("gmail@gmail.com");
        dto.setStarost(200);

        proveriJakartaViolation(dto, "Starost ne sme biti veća od 150.");
    }

    @Test
    public void testAddKlijentNeuspesnoNegativnaStarost() {
        KlijentDTO dto = napraviValidanDTO();
        dto.setId(0);
        dto.setEmail("gmail@gmail.com");
        dto.setStarost(-1);

        proveriJakartaViolation(dto, "Starost mora biti veća od 0.");
    }

    @Test
    public void testDeleteKlijentUspesno() {
        MestoDTO mestoDTO = new MestoDTO(1L, "Beograd", "Vozdovac");

        KlijentDTO dto = new KlijentDTO();
        dto.setIme("TestIme");
        dto.setPrezime("TEstPRezime");
        dto.setStarost(22);
        dto.setBrojTelefona("0633333333");
        dto.setEmail("gmail@gmail.com");
        dto.setMesto(mestoDTO);

        klijentService.addKlijent(dto);

        Long idZaBrisanje = klijentRepo.findAll()
                .stream()
                .mapToLong(Klijent::getId)
                .max()
                .getAsLong();

        ServiceResult result = klijentService.deleteKlijent(idZaBrisanje);

        assertTrue(result.isUspesno());
        assertEquals("Sistem je obrisao klijenta", result.getPoruka());
        assertFalse(klijentRepo.existsById(idZaBrisanje));
    }

    @Test
    public void testDeleteKlijentNeuspesnoVezanZaIzdavanje() {
        assertThrows(DataIntegrityViolationException.class, 
                () -> {
                    klijentService.deleteKlijent(1L);
                });
    }

    @Test
    public void testDeleteKlijentUspesnoNepostojeciId() {
        ServiceResult result = klijentService.deleteKlijent(99999L);

        assertTrue(result.isUspesno());
    }
}