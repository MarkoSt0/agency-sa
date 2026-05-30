package rs.ac.bg.fon.izdavanjestanovaback.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * 
 * @author Marko
 */
class AgentTest {

    Agent a;
    
    public static void validirajAgenta(Agent agent) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Agent>> violations = validator.validate(agent);

        if (!violations.isEmpty()) {
            String porukaGreske = violations.iterator().next().getMessage();
            throw new IllegalArgumentException(porukaGreske);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        a = new Agent();
    }

    @AfterEach
    void tearDown() throws Exception {
        a = null;
    }

    @Test
    void testKonstruktorSaId() {
        Agent agent = new Agent(5L);

        assertEquals(5L, agent.getId());
    }

    @Test
    void testKonstruktorSaIdOstalaPoljaNull() {
        Agent agent = new Agent(5L);

        assertNull(agent.getIme());
        assertNull(agent.getPrezime());
        assertNull(agent.getKorisnickoIme());
        assertNull(agent.getSifra());
        assertNull(agent.getBrojLicence());
        assertNull(agent.getDatumIstekaLicence());
    }

    @Test
    void testSetBrojLicence() {
        a.setBrojLicence("LIC-001");

        assertEquals("LIC-001", a.getBrojLicence());
    }

    @Test
    void testSetBrojLicenceNull() {
        a.setBrojLicence(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetBrojLicencePrazan() {
        a.setBrojLicence("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetBrojLicenceBlank() {
        a.setBrojLicence("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetBrojLicencePreviseDug() {
        a.setBrojLicence("N".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetBrojLicenceMaximalnaDuzina() {
        a.setBrojLicence("N".repeat(255));

        assertEquals("N".repeat(255), a.getBrojLicence());
    }

    @Test
    void testSetDatumIstekaLicence() {
        Date datum = new Date(System.currentTimeMillis() + 86400000L * 365);
        a.setDatumIstekaLicence(datum);

        assertEquals(datum, a.getDatumIstekaLicence());
    }

    @Test
    void testSetDatumIstekaLicenceNull() {
        a.setDatumIstekaLicence(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetDatumIstekaLicenceProsliDatum() {
        a.setDatumIstekaLicence(new Date(System.currentTimeMillis() - 86400000L * 365));
                assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetDatumIstekaLicenceDanasnji() {
        Date danas = new Date();
        a.setDatumIstekaLicence(danas);

        assertEquals(danas, a.getDatumIstekaLicence());
    }

    @Test
    void testSetIme() {
        a.setIme("Pera");

        assertEquals("Pera", a.getIme());
    }

    @Test
    void testSetImeNull() {
        a.setIme(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetImePrazan() {
        a.setIme("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetImeBlank() {
        a.setIme("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetImePreviseDugo() {
        a.setIme("P".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetPrezime() {
        a.setPrezime("Peric");

        assertEquals("Peric", a.getPrezime());
    }

    @Test
    void testSetPrezimeNull() {
        a.setPrezime(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetPrezimePrazan() {
        a.setPrezime("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetPrezimeBlank() {
        a.setPrezime("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetPrezimePreviseDugo() {
        a.setPrezime("P".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetKorisnickoIme() {
        a.setKorisnickoIme("pperic_");

        assertEquals("pperic_", a.getKorisnickoIme());
    }

    @Test
    void testSetKorisnickoImeNull() {
        a.setKorisnickoIme(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetKorisnickoImePrazan() {
        a.setKorisnickoIme("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetKorisnickoImeBlank() {
        a.setKorisnickoIme("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetKorisnickoImePrekratko() {
        a.setKorisnickoIme("ab");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetKorisnickoImeMinimalnaDuzina() {
        a.setKorisnickoIme("abc");

        assertEquals("abc", a.getKorisnickoIme());
    }

    @Test
    void testSetKorisnickoImePreviseDugo() {
        a.setKorisnickoIme("p".repeat(51));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetKorisnickoImeMaximalnaDuzina() {
        a.setKorisnickoIme("p".repeat(50));

        assertEquals("p".repeat(50), a.getKorisnickoIme());
    }

    @Test
    void testSetSifra() {
        a.setSifra("sifra123");

        assertEquals("sifra123", a.getSifra());
    }

    @Test
    void testSetSifraNull() {
        a.setSifra(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetSifraPrazan() {
        a.setSifra("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetSifraBlank() {
        a.setSifra("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetSifraPrekratka() {
        a.setSifra("abc");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetSifraMinimalnaDuzina() {
        a.setSifra("abc12");

        assertEquals("abc12", a.getSifra());
    }

    @Test
    void testSetSifraPreviseDuga() {
        a.setSifra("s".repeat(101));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajAgenta(a);
        });
    }

    @Test
    void testSetSifraMaximalnaDuzina() {
        a.setSifra("s".repeat(100));

        assertEquals("s".repeat(100), a.getSifra());
    }

    @Test
    void testToString() {
        a.setIme("Pera");
        a.setPrezime("Peric");
        a.setKorisnickoIme("pperic_");
        a.setSifra("sifra123");
        a.setBrojLicence("LIC-001");
        a.setDatumIstekaLicence(new Date(System.currentTimeMillis() + 86400000L * 365));

        String st = a.toString();

        assertTrue(st.contains("Pera"));
        assertTrue(st.contains("Peric"));
        assertTrue(st.contains("pperic_"));
        assertTrue(st.contains("LIC-001"));
    }
    
    @Test
    void testEqualsIstiObjekat() {
        assertTrue(a.equals(a));
    }

    @Test
    void testEqualsNullObjekat() {
        assertFalse(a.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        assertFalse(a.equals(new String()));
    }

    @ParameterizedTest
    @CsvSource({
        "1, Pera,   1, Pera,   true",
        "1, Pera,   2, Pera,   false",
        "1, Pera,   1, Mika,   false",
        "1, Pera,   2, Mika,   false"
    })
    void testEquals(long id1, String ime1, long id2, String ime2, boolean jednako) {
        a.setId(id1);
        a.setIme(ime1);

        Agent a2 = new Agent();
        a2.setId(id2);
        a2.setIme(ime2);

        assertEquals(jednako, a.equals(a2));
    }
}