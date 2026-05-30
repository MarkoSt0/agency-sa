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

class MestoTest {

    Mesto m;

    public static void validirajMesto(Mesto mesto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Mesto>> violations = validator.validate(mesto);

        if (!violations.isEmpty()) {
            String porukaGreske = violations.iterator().next().getMessage();
            throw new IllegalArgumentException(porukaGreske);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        m = new Mesto();
    }

    @AfterEach
    void tearDown() throws Exception {
        m = null;
    }

    @Test
    void testKonstruktorSaParametrima() {
        Mesto mesto = new Mesto(1L, "Novi Sad", "Novi Sad");

        assertEquals(1L, mesto.getId());
        assertEquals("Novi Sad", mesto.getNazivMesta());
        assertEquals("Novi Sad", mesto.getOpstina());
    }

    @Test
    void testKonstruktorSaParametrimaOpstinaNull() {
        Mesto mesto = new Mesto(1L, "Novi Sad", null);

        assertEquals(1L, mesto.getId());
        assertEquals("Novi Sad", mesto.getNazivMesta());
        assertNull(mesto.getOpstina());
    }

    @Test
    void testNoArgsKonstruktorPoljaNull() {
        Mesto mesto = new Mesto();

        assertNull(mesto.getId());
        assertNull(mesto.getNazivMesta());
        assertNull(mesto.getOpstina());
    }

    @Test
    void testSetNazivMesta() {
        m.setNazivMesta("Novi Sad");

        assertEquals("Novi Sad", m.getNazivMesta());
    }

    @Test
    void testSetNazivMestaNull() {
        m.setNazivMesta(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajMesto(m);
        });
    }

    @Test
    void testSetNazivMestaPrazan() {
        m.setNazivMesta("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajMesto(m);
        });
    }

    @Test
    void testSetNazivMestaBlank() {
        m.setNazivMesta("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajMesto(m);
        });
    }

    @Test
    void testSetNazivMestaPreviseDugo() {
        m.setNazivMesta("N".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajMesto(m);
        });
    }

    @Test
    void testSetNazivMestaMaximalnaDuzina() {
        m.setNazivMesta("N".repeat(255));

        assertEquals("N".repeat(255), m.getNazivMesta());
    }

    @Test
    void testSetNazivMestaJedanKarakter() {
        m.setNazivMesta("N");

        assertEquals("N", m.getNazivMesta());
    }

    @Test
    void testSetOpstina() {
        m.setOpstina("Novi Sad");

        assertEquals("Novi Sad", m.getOpstina());
    }

    @Test
    void testSetOpstinaNull() {
        m.setOpstina(null);

        assertNull(m.getOpstina());
    }

    @Test
    void testSetOpstinaPrazna() {
        m.setOpstina("");

        assertEquals("", m.getOpstina());
    }

    @Test
    void testSetOpstinaPreviseDuga() {
        m.setOpstina("O".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajMesto(m);
        });
    }

    @Test
    void testSetOpstinaMaximalnaDuzina() {
        m.setOpstina("O".repeat(255));

        assertEquals("O".repeat(255), m.getOpstina());
    }

    @Test
    void testSetOpstinaJedanKarakter() {
        m.setOpstina("O");

        assertEquals("O", m.getOpstina());
    }

    @Test
    void testToString() {
        m.setNazivMesta("Novi Sad");
        m.setOpstina("Novi Sad O");

        String st = m.toString();

        assertTrue(st.contains("Novi Sad"));
        assertTrue(st.contains("Novi Sad O"));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(m.equals(m));
    }

    @Test
    void testEqualsNullObjekat() {
        assertFalse(m.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        assertFalse(m.equals(new String()));
    }

    @ParameterizedTest
    @CsvSource({
        "1, Novi Sad,   1, Novi Sad,   true",
        "1, Novi Sad,   2, Novi Sad,   false",
        "1, Novi Sad,   1, Beograd,    false",
        "1, Novi Sad,   2, Beograd,    false"
    })
    void testEquals(long id1, String naziv1, long id2, String naziv2, boolean jednako) {
        m.setId(id1);
        m.setNazivMesta(naziv1);

        Mesto m2 = new Mesto();
        m2.setId(id2);
        m2.setNazivMesta(naziv2);

        assertEquals(jednako, m.equals(m2));
    }
}