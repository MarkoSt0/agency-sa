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

/**
 *
 * @author Marko
 */
class IzdavanjeTest {

    Izdavanje i;

    public static void validirajIzdavanje(Izdavanje izdavanje) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Izdavanje>> violations = validator.validate(izdavanje);

        if (!violations.isEmpty()) {
            String porukaGreske = violations.iterator().next().getMessage();
            throw new IllegalArgumentException(porukaGreske);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        i = new Izdavanje();
    }

    @AfterEach
    void tearDown() throws Exception {
        i = null;
    }

    @Test
    void testKonstruktorSaId() {
        Izdavanje izdavanje = new Izdavanje(5L);

        assertEquals(5L, izdavanje.getIdIzdavanje());
    }

    @Test
    void testKonstruktorSaIdOstalaPoljaNull() {
        Izdavanje izdavanje = new Izdavanje(5L);

        assertNull(izdavanje.getDatumSklapanjaUgovora());
        assertNull(izdavanje.getStatusUgovora());
        assertNull(izdavanje.getNacinPlacanja());
        assertNull(izdavanje.getUkupanIznos());
        assertNull(izdavanje.getNapomena());
        assertNull(izdavanje.getIdAgent());
        assertNull(izdavanje.getIdKlijent());
    }

    @Test
    void testNoArgsKonstruktorPoljaNull() {
        Izdavanje izdavanje = new Izdavanje();

        assertNull(izdavanje.getIdIzdavanje());
        assertNull(izdavanje.getDatumSklapanjaUgovora());
        assertNull(izdavanje.getStatusUgovora());
        assertNull(izdavanje.getNacinPlacanja());
        assertNull(izdavanje.getUkupanIznos());
        assertNull(izdavanje.getNapomena());
        assertNull(izdavanje.getIdAgent());
        assertNull(izdavanje.getIdKlijent());
    }

    @Test
    void testSetDatumSklapanjaUgovora() {
        Date danas = new Date();
        i.setDatumSklapanjaUgovora(danas);

        assertEquals(danas, i.getDatumSklapanjaUgovora());
    }

    @Test
    void testSetDatumSklapanjaUgovoraNull() {
        i.setDatumSklapanjaUgovora(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetDatumSklapanjaUgovoraDanasnji() {
        Date danas = new Date();
        i.setDatumSklapanjaUgovora(danas);

        assertEquals(danas, i.getDatumSklapanjaUgovora());
    }

    @Test
    void testSetDatumSklapanjaUgovoraProslost() {
        Date prosliDatum = new Date(System.currentTimeMillis() - 86400000L * 365);
        i.setDatumSklapanjaUgovora(prosliDatum);

        assertEquals(prosliDatum, i.getDatumSklapanjaUgovora());
    }

    @Test
    void testSetDatumSklapanjaUgovoraBuducnost() {
        i.setDatumSklapanjaUgovora(new Date(System.currentTimeMillis() + 86400000L * 365));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetStatusUgovora() {
        i.setStatusUgovora("Aktivan");

        assertEquals("Aktivan", i.getStatusUgovora());
    }

    @Test
    void testSetStatusUgovoraNull() {
        i.setStatusUgovora(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetStatusUgovoraPrazan() {
        i.setStatusUgovora("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetStatusUgovoraBlank() {
        i.setStatusUgovora("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetStatusUgovoraPreviseDug() {
        i.setStatusUgovora("S".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetStatusUgovoraMaximalnaDuzina() {
        i.setStatusUgovora("S".repeat(255));

        assertEquals("S".repeat(255), i.getStatusUgovora());
    }

    @Test
    void testSetStatusUgovoraJedanKarakter() {
        i.setStatusUgovora("S");

        assertEquals("S", i.getStatusUgovora());
    }

    @Test
    void testSetNacinPlacanja() {
        i.setNacinPlacanja("Gotovina");

        assertEquals("Gotovina", i.getNacinPlacanja());
    }

    @Test
    void testSetNacinPlacanjaNull() {
        i.setNacinPlacanja(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetNacinPlacanjaePrazan() {
        i.setNacinPlacanja("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetNacinPlacanjaBlank() {
        i.setNacinPlacanja("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetNacinPlacanjaPreviseDug() {
        i.setNacinPlacanja("N".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetNacinPlacanjaMaximalnaDuzina() {
        i.setNacinPlacanja("N".repeat(255));

        assertEquals("N".repeat(255), i.getNacinPlacanja());
    }

    @Test
    void testSetNacinPlacanjaJedanKarakter() {
        i.setNacinPlacanja("G");

        assertEquals("G", i.getNacinPlacanja());
    }

    @Test
    void testSetUkupanIznos() {
        i.setUkupanIznos(45000.00);

        assertEquals(45000.00, i.getUkupanIznos());
    }

    @Test
    void testSetUkupanIznosNull() {
        i.setUkupanIznos(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetUkupanIznosNula() {
        i.setUkupanIznos(0.0);

        assertEquals(0.0, i.getUkupanIznos());
    }

    @Test
    void testSetUkupanIznosNegativan() {
        i.setUkupanIznos(-1.0);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetUkupanIznosVelikiNegativan() {
        i.setUkupanIznos(-99999.99);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetUkupanIznosVelikiPozitivan() {
        i.setUkupanIznos(999999.99);

        assertEquals(999999.99, i.getUkupanIznos());
    }

    @Test
    void testSetUkupanIznosDecimalan() {
        i.setUkupanIznos(750.50);

        assertEquals(750.50, i.getUkupanIznos());
    }

    @Test
    void testSetNapomena() {
        i.setNapomena("Stan je u odlicnom stanju.");

        assertEquals("Stan je u odlicnom stanju.", i.getNapomena());
    }

    @Test
    void testSetNapomenaNull() {
        i.setNapomena(null);

        assertNull(i.getNapomena());
    }

    @Test
    void testSetNapomenaPrazna() {
        i.setNapomena("");

        assertEquals("", i.getNapomena());
    }

    @Test
    void testSetNapomenaPreviseDuga() {
        i.setNapomena("N".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetNapomenaMaximalnaDuzina() {
        i.setNapomena("N".repeat(255));

        assertEquals("N".repeat(255), i.getNapomena());
    }

    @Test
    void testSetNapomenaJedanKarakter() {
        i.setNapomena("N");

        assertEquals("N", i.getNapomena());
    }

    @Test
    void testSetIdAgent() {
        Agent agent = new Agent();
        i.setIdAgent(agent);

        assertEquals(agent, i.getIdAgent());
    }

    @Test
    void testSetIdAgentNull() {
        i.setIdAgent(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testSetIdKlijent() {
        Klijent klijent = new Klijent();
        i.setIdKlijent(klijent);

        assertEquals(klijent, i.getIdKlijent());
    }

    @Test
    void testSetIdKlijentNull() {
        i.setIdKlijent(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajIzdavanje(i);
        });
    }

    @Test
    void testToString() {
        i.setStatusUgovora("Aktivan");
        i.setNacinPlacanja("Mesecno");
        i.setUkupanIznos(1500.00);
        i.setNapomena("Kuca je u odlicnom stanju.");

        String st = i.toString();

        assertTrue(st.contains("Aktivan"));
        assertTrue(st.contains("Mesecno"));
        assertTrue(st.contains("1500.0"));
        assertTrue(st.contains("Kuca je u odlicnom stanju."));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(i.equals(i));
    }

    @Test
    void testEqualsNullObjekat() {
        assertFalse(i.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        assertFalse(i.equals(new String()));
    }

    @ParameterizedTest
    @CsvSource({
        "1, Aktivan,   1, Aktivan,   true",
        "1, Aktivan,   2, Aktivan,   false",
        "1, Aktivan,   1, Istekao,   false",
        "1, Aktivan,   2, Istekao,   false"
    })
    void testEquals(long id1, String status1, long id2, String status2, boolean jednako) {
        i.setIdIzdavanje(id1);
        i.setStatusUgovora(status1);

        Izdavanje i2 = new Izdavanje();
        i2.setIdIzdavanje(id2);
        i2.setStatusUgovora(status2);

        assertEquals(jednako, i.equals(i2));
    }
}