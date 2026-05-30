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

import java.util.Set;

/**
 *
 * @author Marko
 */
class KlijentTest {

    Klijent k;

    public static void validirajKlijenta(Klijent klijent) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Klijent>> violations = validator.validate(klijent);

        if (!violations.isEmpty()) {
            String porukaGreske = violations.iterator().next().getMessage();
            throw new IllegalArgumentException(porukaGreske);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        k = new Klijent();
    }

    @AfterEach
    void tearDown() throws Exception {
        k = null;
    }

    @Test
    void testSetId() {
        k.setId(1L);

        assertEquals(1L, k.getId());
    }

    @Test
    void testSetIdNull() {
        k.setId(null);

        assertNull(k.getId());
    }

    @Test
    void testSetIme() {
        k.setIme("Pera");

        assertEquals("Pera", k.getIme());
    }

    @Test
    void testSetImeNull() {
        k.setIme(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetImePrazan() {
        k.setIme("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetImeBlank() {
        k.setIme("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetImePreviseDugo() {
        k.setIme("P".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetImeMaximalnaDuzina() {
        k.setIme("P".repeat(255));

        assertEquals("P".repeat(255), k.getIme());
    }

    @Test
    void testSetImeJedanKarakter() {
        k.setIme("P");

        assertEquals("P", k.getIme());
    }

    @Test
    void testSetPrezime() {
        k.setPrezime("Peric");

        assertEquals("Peric", k.getPrezime());
    }

    @Test
    void testSetPrezimeNull() {
        k.setPrezime(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetPrezimePrazan() {
        k.setPrezime("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetPrezimeBlank() {
        k.setPrezime("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetPrezimePreviseDugo() {
        k.setPrezime("P".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetPrezimeMaximalnaDuzina() {
        k.setPrezime("P".repeat(255));

        assertEquals("P".repeat(255), k.getPrezime());
    }

    @Test
    void testSetPrezimeJedanKarakter() {
        k.setPrezime("P");

        assertEquals("P", k.getPrezime());
    }

    @Test
    void testSetStarost() {
        k.setStarost(25);

        assertEquals(25, k.getStarost());
    }

    @Test
    void testSetStarostNull() {
        k.setStarost(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetStarostNula() {
        k.setStarost(0);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetStarostNegativna() {
        k.setStarost(-1);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetStarostMinimalna() {
        k.setStarost(1);

        assertEquals(1, k.getStarost());
    }

    @Test
    void testSetStarostMaksimalna() {
        k.setStarost(150);

        assertEquals(150, k.getStarost());
    }

    @Test
    void testSetStarostPrekoMaksimalne() {
        k.setStarost(151);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetStarostVelikaPrekoMaksimalne() {
        k.setStarost(999);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetBrojTelefona() {
        k.setBrojTelefona("0641234567");

        assertEquals("0641234567", k.getBrojTelefona());
    }

    @Test
    void testSetBrojTelefonaNull() {
        k.setBrojTelefona(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetBrojTelefonaPrazan() {
        k.setBrojTelefona("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetBrojTelefonaBlank() {
        k.setBrojTelefona("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetBrojTelefonaPreviseDug() {
        k.setBrojTelefona("0".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetBrojTelefonaMaximalnaDuzina() {
        k.setBrojTelefona("0".repeat(255));

        assertEquals("0".repeat(255), k.getBrojTelefona());
    }

    @Test
    void testSetBrojTelefonaJedanKarakter() {
        k.setBrojTelefona("1");

        assertEquals("1", k.getBrojTelefona());
    }

    @Test
    void testSetEmail() {
        k.setEmail("pera.peric@gmail.com");

        assertEquals("pera.peric@gmail.com", k.getEmail());
    }

    @Test
    void testSetEmailNull() {
        k.setEmail(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetEmailPrazan() {
        k.setEmail("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetEmailBlank() {
        k.setEmail("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetEmailNeispravan() {
        k.setEmail("pera.peric");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetEmailNeispravnoBezDomena() {
        k.setEmail("pera@");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetEmailNeispravnoBezAt() {
        k.setEmail("pera.pericgmail.com");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetEmailNeispravnoSamoAt() {
        k.setEmail("@");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetEmailPreviseDug() {
        k.setEmail("p".repeat(250) + "@x.co");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testSetEmailSaSubdomenom() {
        k.setEmail("pera.peric@mail.gmail.com");

        assertEquals("pera.peric@mail.gmail.com", k.getEmail());
    }

    @Test
    void testSetEmailSaTackamaPredAt() {
        k.setEmail("pera.peric.123@gmail.com");

        assertEquals("pera.peric.123@gmail.com", k.getEmail());
    }

    @Test
    void testSetMesto() {
        Mesto mesto = new Mesto();
        k.setIdMesto(mesto);

        assertEquals(mesto, k.getIdMesto());
    }

    @Test
    void testSetMestoNull() {
        k.setIdMesto(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajKlijenta(k);
        });
    }

    @Test
    void testNoArgsKonstruktorPoljaNull() {
        Klijent klijent = new Klijent();

        assertNull(klijent.getId());
        assertNull(klijent.getIme());
        assertNull(klijent.getPrezime());
        assertNull(klijent.getStarost());
        assertNull(klijent.getBrojTelefona());
        assertNull(klijent.getEmail());
        assertNull(klijent.getIdMesto());
    }

    @Test
    void testToString() {
        k.setIme("Pera");
        k.setPrezime("Peric");
        k.setStarost(25);
        k.setBrojTelefona("0641234567");
        k.setEmail("pera.peric@gmail.com");

        String st = k.toString();

        assertTrue(st.contains("Pera"));
        assertTrue(st.contains("Peric"));
        assertTrue(st.contains("25"));
        assertTrue(st.contains("0641234567"));
        assertTrue(st.contains("pera.peric@gmail.com"));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(k.equals(k));
    }

    @Test
    void testEqualsNullObjekat() {
        assertFalse(k.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        assertFalse(k.equals(new String()));
    }

    @ParameterizedTest
    @CsvSource({
        "1, Pera,   1, Pera,   true",
        "1, Pera,   2, Pera,   false",
        "1, Pera,   1, Mika,   false",
        "1, Pera,   2, Mika,   false"
    })
    void testEquals(long id1, String ime1, long id2, String ime2, boolean jednako) {
        k.setId(id1);
        k.setIme(ime1);

        Klijent k2 = new Klijent();
        k2.setId(id2);
        k2.setIme(ime2);

        assertEquals(jednako, k.equals(k2));
    }
}