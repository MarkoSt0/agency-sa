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
import rs.ac.bg.fon.izdavanjestanovaback.dto.NekretninaDTO;
import rs.ac.bg.fon.izdavanjestanovaback.dto.TipNekretnineDTO;
import rs.ac.bg.fon.izdavanjestanovaback.enums.StatusNekretnine;
import rs.ac.bg.fon.izdavanjestanovaback.enums.TipGrejanja;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.NekretninaRepo;
import rs.ac.bg.fon.izdavanjestanovaback.model.Nekretnina;


import static org.junit.jupiter.api.Assertions.*;

/**
 * * @author Marko
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NekretninaServiceTest {

    @Autowired
    NekretninaService nekretninaService;

    @Autowired
    NekretninaRepo nekretninaRepo;

    Long testNekretninaId;

    @BeforeEach
    public void setUp() {
        TipNekretnineDTO tipDTO = new TipNekretnineDTO();
        tipDTO.setIdTipaNekretnine(1L);

        NekretninaDTO dto = new NekretninaDTO();
        dto.setAdresa("TestAdresa123");
        dto.setPovrsina("54m2");
        dto.setSprat("3. sprat");
        dto.setBrojSoba(2.0);
        dto.setGodinaIzgradnje(1995);
        dto.setOpis("Nema dodatnih napomena");
        dto.setTipGrejanja(TipGrejanja.CENTRALNO);
        dto.setStatusNekretnine(StatusNekretnine.DOSTUPNA);
        dto.setTipNekretnine(tipDTO);

        ServiceResult result = nekretninaService.addNekretnina(dto);
        assertTrue(result.isUspesno(), "setUp nije uspeo: " + result.getPoruka());

        testNekretninaId = nekretninaRepo.findAll()
                .stream()
                .mapToLong(Nekretnina::getIdNekretnina)
                .max()
                .getAsLong();
    }

    @AfterEach
    public void tearDown() {
        if (testNekretninaId != null && nekretninaRepo.existsById(testNekretninaId)) {
            nekretninaRepo.deleteById(testNekretninaId);
        }
    }

    public NekretninaDTO napraviValidanDTO() {
        TipNekretnineDTO tipDTO = new TipNekretnineDTO();
        tipDTO.setIdTipaNekretnine(1L);

        NekretninaDTO dto = new NekretninaDTO();
        dto.setIdNekretnina(testNekretninaId);
        dto.setAdresa("TestAdresa123");
        dto.setPovrsina("54m2");
        dto.setSprat("3. sprat");
        dto.setBrojSoba(2.0);
        dto.setGodinaIzgradnje(1995);
        dto.setOpis("Nema dodatnih napomena");
        dto.setTipGrejanja(TipGrejanja.CENTRALNO);
        dto.setStatusNekretnine(StatusNekretnine.DOSTUPNA);
        dto.setTipNekretnine(tipDTO);
        return dto;
    }

    public void proveriJakartaViolation(NekretninaDTO dto, String ocekivanaPoruka) {
        TransactionSystemException ex = assertThrows(
                TransactionSystemException.class,
                () -> nekretninaService.updateNekretnina(dto)
        );

        Throwable cause = ex.getCause().getCause();
        assertInstanceOf(jakarta.validation.ConstraintViolationException.class, cause);

        jakarta.validation.ConstraintViolationException cve =
                (jakarta.validation.ConstraintViolationException) cause;

        String poruke = cve.getConstraintViolations().stream()
                .map(v -> v.getMessage())
                .collect(java.util.stream.Collectors.joining(", "));

        assertTrue(poruke.contains(ocekivanaPoruka));
    }

    @Test
    public void testGetNekretninaByIdUspesno() {
        ServiceResult result = nekretninaService.getNekretninaById(testNekretninaId);

        assertTrue(result.isUspesno());
        assertNotNull(result.getPodaci());
        NekretninaDTO dto = (NekretninaDTO) result.getPodaci();
        assertEquals(testNekretninaId, dto.getIdNekretnina());
        assertEquals("TestAdresa123", dto.getAdresa());
    }

    @Test
    public void testGetNekretninaByIdNeuspesnoNepostojeciId() {
        ServiceResult result = nekretninaService.getNekretninaById(99999L);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Nekretnina sa ID 99999 nije pronadjena"));
    }

    @Test
    public void testAddNekretninaUspesno() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        dto.setAdresa("NovaAdresa");

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertTrue(result.isUspesno());
        assertTrue(result.getPoruka().contains("Sistem je sačuvao nekretninu"));

        Long noviId = nekretninaRepo.findAll()
                .stream()
                .mapToLong(Nekretnina::getIdNekretnina)
                .max()
                .getAsLong();

        Nekretnina sacuvana = nekretninaRepo.findById(noviId).orElseThrow();
        assertEquals("NovaAdresa", sacuvana.getAdresa());

        nekretninaRepo.deleteById(noviId);
    }

    @Test
    public void testAddNekretninaNeuspesnoNullTipNekretnine() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        dto.setTipNekretnine(null);

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertFalse(result.isUspesno());
    }

    @Test
    public void testAddNekretninaNeuspesnoNullIdTipaNekretnine() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        dto.getTipNekretnine().setIdTipaNekretnine(null);

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("ID tipa nekretnine mora biti naveden"));
    }

    @Test
    public void testAddNekretninaNeuspesnoNepostojeciTipNekretnine() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        dto.getTipNekretnine().setIdTipaNekretnine(99999L);

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Tip nekretnine sa ID 99999 nije pronađen"));
    }

    @Test
    public void testAddNekretninaNeuspesnoPraznaAdresa() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        //NotBlank
        dto.setAdresa("");

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Adresa nekretnine je obavezna."));
    }

    @Test
    public void testAddNekretninaNeuspesnoPraznaPovrsina() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        //NotBlank
        dto.setPovrsina("   ");

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Povrsina nekretnine je obavezna."));
    }

    @Test
    public void testAddNekretninaNeuspesnoPrazanSprat() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        //NotBlank
        dto.setSprat("");

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Sprat na kome se nalazi nekretnina je obavezan."));
    }

    @Test
    public void testAddNekretninaNeuspesnoNullBrojSoba() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        //NotBlank
        dto.setBrojSoba(null);

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Broj soba mora biti naveden."));
    }

    @Test
    public void testAddNekretninaNeuspesnoNegativanBrojSoba() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        //Min 0
        dto.setBrojSoba(-1.5);

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Broj soba ne sme biti negativan broj."));
    }

    @Test
    public void testAddNekretninaNeuspesnoNullGodinaIzgradnje() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        // NotNUll
        dto.setGodinaIzgradnje(null);

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Godina izgradnje je obavezna."));
    }

    @Test
    public void testAddNekretninaNeuspesnoStaroPre1800() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        // Min 1800 godina
        dto.setGodinaIzgradnje(1799);

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Godina izgradnje ne moze biti pre 1800. godine."));
    }

    @Test
    public void testAddNekretninaNeuspesnoPredugacakOpis() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        
        StringBuilder predugackiOpis = new StringBuilder();
        for (int i = 0; i < 1005; i++) {
            predugackiOpis.append("A");
        }
        //Max opis 1000 karaktera
        dto.setOpis(predugackiOpis.toString());

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Opis nekretnine ne sme biti duži od 1000 karaktera."));
    }

    @Test
    public void testAddNekretninaNeuspesnoNullTipGrejanja() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        // NotNull
        dto.setTipGrejanja(null);

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Tip grejanja mora biti izabran."));
    }

    @Test
    public void testAddNekretninaNeuspesnoNullStatusNekretnine() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(null);
        //NotNull
        dto.setStatusNekretnine(null);

        ServiceResult result = nekretninaService.addNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Status nekretnine mora biti izabran."));
    }

    @Test
    public void testUpdateNekretninaUspesno() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setAdresa("IzmenjenaAdresa");
        dto.setSprat("5. sprat");
        dto.setStatusNekretnine(StatusNekretnine.IZNAJMLJENA);

        ServiceResult result = nekretninaService.updateNekretnina(dto);

        assertTrue(result.isUspesno());
        assertTrue(result.getPoruka().contains("Nekretnina je uspešno izmenjena"));

        Nekretnina izmenjena = nekretninaRepo.findById(testNekretninaId).orElseThrow();
        assertEquals("IzmenjenaAdresa", izmenjena.getAdresa());
        assertEquals("5. sprat", izmenjena.getSprat());
        assertEquals(StatusNekretnine.IZNAJMLJENA, izmenjena.getStatusNekretnine());
    }

    @Test
    public void testUpdateNekretninaNeuspesnoNepostojeciId() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setIdNekretnina(99999L);

        ServiceResult result = nekretninaService.updateNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Nekretnina nije pronađena"));
    }

    @Test
    public void testUpdateNekretninaNeuspesnoNullIdTipaNekretnine() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.getTipNekretnine().setIdTipaNekretnine(null);

        ServiceResult result = nekretninaService.updateNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("ID tipa nekretnine mora biti naveden"));
    }

    @Test
    public void testUpdateNekretninaNeuspesnoNepostojeciTipNekretnine() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.getTipNekretnine().setIdTipaNekretnine(99999L);

        ServiceResult result = nekretninaService.updateNekretnina(dto);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Tip nekretnine sa ID 99999 nije pronađen"));
    }

    @Test
    public void testUpdateNekretninaNeuspesnoPraznaAdresa() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setAdresa("");

        proveriJakartaViolation(dto, "Adresa nekretnine je obavezna.");
    }

    @Test
    public void testUpdateNekretninaNeuspesnoPraznaPovrsina() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setPovrsina("");

        proveriJakartaViolation(dto, "Povrsina nekretnine je obavezna.");
    }

    @Test
    public void testUpdateNekretninaNeuspesnoPrazanSprat() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setSprat("   ");

        proveriJakartaViolation(dto, "Sprat na kome se nalazi nekretnina je obavezan.");
    }

    @Test
    public void testUpdateNekretninaNeuspesnoNullBrojSoba() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setBrojSoba(null);

        proveriJakartaViolation(dto, "Broj soba mora biti naveden.");
    }

    @Test
    public void testUpdateNekretninaNeuspesnoNegativanBrojSoba() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setBrojSoba(-2.0);

        proveriJakartaViolation(dto, "Broj soba ne sme biti negativan broj.");
    }

    @Test
    public void testUpdateNekretninaNeuspesnoNullGodinaIzgradnje() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setGodinaIzgradnje(null);

        proveriJakartaViolation(dto, "Godina izgradnje je obavezna.");
    }

    @Test
    public void testUpdateNekretninaNeuspesnoStaroPre1800() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setGodinaIzgradnje(1500);

        proveriJakartaViolation(dto, "Godina izgradnje ne moze biti pre 1800. godine.");
    }

    @Test
    public void testUpdateNekretninaNeuspesnoNullTipGrejanja() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setTipGrejanja(null);

        proveriJakartaViolation(dto, "Tip grejanja mora biti izabran.");
    }

    @Test
    public void testUpdateNekretninaNeuspesnoNullStatusNekretnine() {
        NekretninaDTO dto = napraviValidanDTO();
        dto.setStatusNekretnine(null);

        proveriJakartaViolation(dto, "Status nekretnine mora biti izabran.");
    }

    @Test
    public void testDeleteNekretninaUspesno() {
        TipNekretnineDTO tipDTO = new TipNekretnineDTO();
        tipDTO.setIdTipaNekretnine(1L);

        NekretninaDTO dto = new NekretninaDTO();
        dto.setAdresa("Adresa123");
        dto.setPovrsina("40m2");
        dto.setSprat("Prizemlje");
        dto.setBrojSoba(1.0);
        dto.setGodinaIzgradnje(2010);
        dto.setTipGrejanja(TipGrejanja.CENTRALNO);
        dto.setStatusNekretnine(StatusNekretnine.DOSTUPNA);
        dto.setTipNekretnine(tipDTO);

        nekretninaService.addNekretnina(dto);

        Long idZaBrisanje = nekretninaRepo.findAll()
                .stream()
                .mapToLong(Nekretnina::getIdNekretnina)
                .max()
                .getAsLong();

        ServiceResult result = nekretninaService.deleteNekretnina(idZaBrisanje);

        assertTrue(result.isUspesno());
        assertTrue(result.getPoruka().contains("Nekretnina je uspešno obrisana"));
        assertFalse(nekretninaRepo.existsById(idZaBrisanje));
    }

    @Test
    public void testDeleteNekretninaNeuspesnoNepostojeciId() {
        ServiceResult result = nekretninaService.deleteNekretnina(99999L);

        assertFalse(result.isUspesno());
        assertTrue(result.getPoruka().contains("Nekretnina sa ID 99999 nije pronadjena"));
    }
}