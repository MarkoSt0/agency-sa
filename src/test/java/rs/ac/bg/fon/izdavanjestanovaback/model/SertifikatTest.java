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

import java.util.Date;
import java.util.Set;

class SertifikatTest {

    Sertifikat s;

    public static void validirajSertifikat(Sertifikat sertifikat) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Sertifikat>> violations = validator.validate(sertifikat);

        if (!violations.isEmpty()) {
            String porukaGreske = violations.iterator().next().getMessage();
            throw new IllegalArgumentException(porukaGreske);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        s = new Sertifikat();
    }

    @AfterEach
    void tearDown() throws Exception {
        s = null;
    }

    @Test
    void testNoArgsKonstruktorPoljaNull() {
        Sertifikat sertifikat = new Sertifikat();

        assertNull(sertifikat.getIdSertifikat());
        assertNull(sertifikat.getNazivSertifikata());
        assertNull(sertifikat.getIzdavalac());
        assertNull(sertifikat.getDatumSticanja());
        assertNull(sertifikat.getAgent());
    }

    @Test
    void testAllArgsKonstruktor() {
        Agent agent = new Agent();
        Date danas = new Date();
        Sertifikat sertifikat = new Sertifikat(1L, "Sertifikat za posrednika", "Privredna komora Srbije", danas, agent);

        assertEquals(1L, sertifikat.getIdSertifikat());
        assertEquals("Sertifikat za posrednika", sertifikat.getNazivSertifikata());
        assertEquals("Privredna komora Srbije", sertifikat.getIzdavalac());
        assertEquals(danas, sertifikat.getDatumSticanja());
        assertEquals(agent, sertifikat.getAgent());
    }

    @Test
    void testBuilderKonstruktor() {
        Agent agent = new Agent();
        Date danas = new Date();
        Sertifikat sertifikat = Sertifikat.builder()
                .idSertifikat(1L)
                .nazivSertifikata("Sertifikat za posrednika")
                .izdavalac("Privredna komora Srbije")
                .datumSticanja(danas)
                .agent(agent)
                .build();

        assertEquals(1L, sertifikat.getIdSertifikat());
        assertEquals("Sertifikat za posrednika", sertifikat.getNazivSertifikata());
        assertEquals("Privredna komora Srbije", sertifikat.getIzdavalac());
        assertEquals(danas, sertifikat.getDatumSticanja());
        assertEquals(agent, sertifikat.getAgent());
    }

    @Test
    void testSetNazivSertifikata() {
        s.setNazivSertifikata("Sertifikat za posrednika");

        assertEquals("Sertifikat za posrednika", s.getNazivSertifikata());
    }

    @Test
    void testSetNazivSertifikataNull() {
        s.setNazivSertifikata(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajSertifikat(s);
        });
    }

    @Test
    void testSetNazivSertifikataPrazan() {
        s.setNazivSertifikata("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajSertifikat(s);
        });
    }

    @Test
    void testSetNazivSertifikataBlank() {
        s.setNazivSertifikata("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajSertifikat(s);
        });
    }

    @Test
    void testSetNazivSertifikataPreviseDug() {
        s.setNazivSertifikata("N".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajSertifikat(s);
        });
    }

    @Test
    void testSetNazivSertifikataMaximalnaDuzina() {
        s.setNazivSertifikata("N".repeat(255));

        assertEquals("N".repeat(255), s.getNazivSertifikata());
    }

    @Test
    void testSetNazivSertifikataJedanKarakter() {
        s.setNazivSertifikata("N");

        assertEquals("N", s.getNazivSertifikata());
    }

    @Test
    void testSetIzdavalac() {
        s.setIzdavalac("Privredna komora Srbije");

        assertEquals("Privredna komora Srbije", s.getIzdavalac());
    }

    @Test
    void testSetIzdavalacNull() {
        s.setIzdavalac(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajSertifikat(s);
        });
    }

    @Test
    void testSetIzdavalacPrazan() {
        s.setIzdavalac("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajSertifikat(s);
        });
    }

    @Test
    void testSetIzdavalacBlank() {
        s.setIzdavalac("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajSertifikat(s);
        });
    }

    @Test
    void testSetIzdavalacPreviseDug() {
        s.setIzdavalac("I".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajSertifikat(s);
        });
    }

    @Test
    void testSetIzdavalacMaximalnaDuzina() {
        s.setIzdavalac("I".repeat(255));

        assertEquals("I".repeat(255), s.getIzdavalac());
    }

    @Test
    void testSetIzdavalacJedanKarakter() {
        s.setIzdavalac("I");

        assertEquals("I", s.getIzdavalac());
    }

    @Test
    void testSetDatumSticanja() {
        Date danas = new Date();
        s.setDatumSticanja(danas);

        assertEquals(danas, s.getDatumSticanja());
    }

    @Test
    void testSetDatumSticanjaNull() {
        s.setDatumSticanja(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajSertifikat(s);
        });
    }

    @Test
    void testSetDatumSticanjaDanasnji() {
        Date danas = new Date();
        s.setDatumSticanja(danas);

        assertEquals(danas, s.getDatumSticanja());
    }

    @Test
    void testSetDatumSticanjaProslost() {
        Date prosliDatum = new Date(System.currentTimeMillis() - 86400000L * 365);
        s.setDatumSticanja(prosliDatum);

        assertEquals(prosliDatum, s.getDatumSticanja());
    }

    @Test
    void testSetDatumSticanjaBuducnost() {
        s.setDatumSticanja(new Date(System.currentTimeMillis() + 86400000L * 365));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajSertifikat(s);
        });
    }

    @Test
    void testSetAgent() {
        Agent agent = new Agent();
        s.setAgent(agent);

        assertEquals(agent, s.getAgent());
    }

    @Test
    void testSetAgentNull() {
        s.setAgent(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajSertifikat(s);
        });
    }

    @Test
    void testToString() {
        s.setNazivSertifikata("Sertifikat za posrednika");
        s.setIzdavalac("Privredna komora Srbije");

        String st = s.toString();

        assertTrue(st.contains("Sertifikat za posrednika"));
        assertTrue(st.contains("Privredna komora Srbije"));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(s.equals(s));
    }

    @Test
    void testEqualsNullObjekat() {
        assertFalse(s.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        assertFalse(s.equals(new String()));
    }

    @ParameterizedTest
    @CsvSource({
        "1, Sertifikat za posrednika,   1, Sertifikat za posrednika,   true",
        "1, Sertifikat za posrednika,   2, Sertifikat za posrednika,   false",
        "1, Sertifikat za posrednika,   1, Sertifikat za procenitelja, false",
        "1, Sertifikat za posrednika,   2, Sertifikat za procenitelja, false"
    })
    void testEquals(long id1, String naziv1, long id2, String naziv2, boolean jednako) {
        s.setIdSertifikat(id1);
        s.setNazivSertifikata(naziv1);

        Sertifikat s2 = new Sertifikat();
        s2.setIdSertifikat(id2);
        s2.setNazivSertifikata(naziv2);

        assertEquals(jednako, s.equals(s2));
    }
}