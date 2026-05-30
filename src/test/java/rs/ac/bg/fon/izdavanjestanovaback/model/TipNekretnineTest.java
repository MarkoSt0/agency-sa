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

class TipNekretnineTest {

    TipNekretnine t;

    public static void validirajTipNekretnine(TipNekretnine tipNekretnine) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<TipNekretnine>> violations = validator.validate(tipNekretnine);

        if (!violations.isEmpty()) {
            String porukaGreske = violations.iterator().next().getMessage();
            throw new IllegalArgumentException(porukaGreske);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        t = new TipNekretnine();
    }

    @AfterEach
    void tearDown() throws Exception {
        t = null;
    }

    @Test
    void testNoArgsKonstruktorPoljaNull() {
        TipNekretnine tipNekretnine = new TipNekretnine();

        assertNull(tipNekretnine.getIdTipaNekretnine());
        assertNull(tipNekretnine.getNazivTipaNekretnine());
    }

    @Test
    void testSetNazivTipaNekretnine() {
        t.setNazivTipaNekretnine("Stan");

        assertEquals("Stan", t.getNazivTipaNekretnine());
    }

    @Test
    void testSetNazivTipaNekretnineNull() {
        t.setNazivTipaNekretnine(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajTipNekretnine(t);
        });
    }

    @Test
    void testSetNazivTipaNekretnineePrazan() {
        t.setNazivTipaNekretnine("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajTipNekretnine(t);
        });
    }

    @Test
    void testSetNazivTipaNekretnineBlank() {
        t.setNazivTipaNekretnine("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajTipNekretnine(t);
        });
    }

    @Test
    void testSetNazivTipaNekretninePreviseDug() {
        t.setNazivTipaNekretnine("N".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajTipNekretnine(t);
        });
    }

    @Test
    void testSetNazivTipaNekretnineMaximalnaDuzina() {
        t.setNazivTipaNekretnine("N".repeat(255));

        assertEquals("N".repeat(255), t.getNazivTipaNekretnine());
    }

    @Test
    void testSetNazivTipaNekretnineJedanKarakter() {
        t.setNazivTipaNekretnine("S");

        assertEquals("S", t.getNazivTipaNekretnine());
    }

    @Test
    void testToString() {
        t.setNazivTipaNekretnine("Stan");

        String st = t.toString();

        assertTrue(st.contains("Stan"));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(t.equals(t));
    }

    @Test
    void testEqualsNullObjekat() {
        assertFalse(t.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        assertFalse(t.equals(new String()));
    }

    @ParameterizedTest
    @CsvSource({
        "1, Stan,   1, Stan,   true",
        "1, Stan,   2, Stan,   false",
        "1, Stan,   1, Kuca,   false",
        "1, Stan,   2, Kuca,   false"
    })
    void testEquals(long id1, String naziv1, long id2, String naziv2, boolean jednako) {
        t.setIdTipaNekretnine(id1);
        t.setNazivTipaNekretnine(naziv1);

        TipNekretnine t2 = new TipNekretnine();
        t2.setIdTipaNekretnine(id2);
        t2.setNazivTipaNekretnine(naziv2);

        assertEquals(jednako, t.equals(t2));
    }
}