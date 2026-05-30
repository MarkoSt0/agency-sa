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

class StavkaIzdavanjaTest {

    StavkaIzdavanja s;

    public static void validirajStavku(StavkaIzdavanja stavka) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<StavkaIzdavanja>> violations = validator.validate(stavka);

        if (!violations.isEmpty()) {
            String porukaGreske = violations.iterator().next().getMessage();
            throw new IllegalArgumentException(porukaGreske);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        s = new StavkaIzdavanja();
    }

    @AfterEach
    void tearDown() throws Exception {
        s = null;
    }

    @Test
    void testNoArgsKonstruktorPoljaNull() {
        StavkaIzdavanja stavka = new StavkaIzdavanja();

        assertNull(stavka.getRb());
        assertNull(stavka.getDatumPocetkaIzdavanja());
        assertNull(stavka.getDatumZavrsetkaIzdavanja());
        assertNull(stavka.getMesecnaKirija());
        assertNull(stavka.getIznosDepozita());
        assertNull(stavka.getIdIzdavanje());
        assertNull(stavka.getIdNekretnina());
    }

    @Test
    void testSetRb() {
        s.setRb(1L);

        assertEquals(1L, s.getRb());
    }

    @Test
    void testSetRbNull() {
        s.setRb(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajStavku(s);
        });
    }

    @Test
    void testSetRbVelikaVrednost() {
        s.setRb(999L);

        assertEquals(999L, s.getRb());
    }

    @Test
    void testSetDatumPocetkaIzdavanja() {
        Date sutra = new Date(System.currentTimeMillis() + 86400000L);
        s.setDatumPocetkaIzdavanja(sutra);

        assertEquals(sutra, s.getDatumPocetkaIzdavanja());
    }

    @Test
    void testSetDatumPocetkaIzdavanjaDanasnji() {
        Date danas = new Date();
        s.setDatumPocetkaIzdavanja(danas);

        assertEquals(danas, s.getDatumPocetkaIzdavanja());
    }

    @Test
    void testSetDatumPocetkaIzdavanjaNull() {
        s.setDatumPocetkaIzdavanja(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajStavku(s);
        });
    }

    @Test
    void testSetDatumPocetkaIzdavanjaProslost() {
        s.setDatumPocetkaIzdavanja(new Date(System.currentTimeMillis() - 86400000L * 365));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajStavku(s);
        });
    }

    @Test
    void testSetDatumPocetkaIzdavanjaBuducnost() {
        Date buduciDatum = new Date(System.currentTimeMillis() + 86400000L * 365);
        s.setDatumPocetkaIzdavanja(buduciDatum);

        assertEquals(buduciDatum, s.getDatumPocetkaIzdavanja());
    }

    @Test
    void testSetDatumZavrsetkaIzdavanja() {
        Date sutra = new Date(System.currentTimeMillis() + 86400000L);
        s.setDatumZavrsetkaIzdavanja(sutra);

        assertEquals(sutra, s.getDatumZavrsetkaIzdavanja());
    }

    @Test
    void testSetDatumZavrsetkaIzdavanjaDanasnji() {
        Date danas = new Date();
        s.setDatumZavrsetkaIzdavanja(danas);

        assertEquals(danas, s.getDatumZavrsetkaIzdavanja());
    }

    @Test
    void testSetDatumZavrsetkaIzdavanjaNull() {
        s.setDatumZavrsetkaIzdavanja(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajStavku(s);
        });
    }

    @Test
    void testSetDatumZavrsetkaIzdavanjaProslost() {
        s.setDatumZavrsetkaIzdavanja(new Date(System.currentTimeMillis() - 86400000L * 365));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajStavku(s);
        });
    }

    @Test
    void testSetDatumZavrsetkaIzdavanjaBuducnost() {
        Date buduciDatum = new Date(System.currentTimeMillis() + 86400000L * 365);
        s.setDatumZavrsetkaIzdavanja(buduciDatum);

        assertEquals(buduciDatum, s.getDatumZavrsetkaIzdavanja());
    }

    @Test
    void testSetMesecnaKirija() {
        s.setMesecnaKirija(500.00);

        assertEquals(500.00, s.getMesecnaKirija());
    }

    @Test
    void testSetMesecnaKirijaNull() {
        s.setMesecnaKirija(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajStavku(s);
        });
    }

    @Test
    void testSetMesecnaKirijaNula() {
        s.setMesecnaKirija(0.0);

        assertEquals(0.0, s.getMesecnaKirija());
    }

    @Test
    void testSetMesecnaKirijaNegativna() {
        s.setMesecnaKirija(-1.0);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajStavku(s);
        });
    }

    @Test
    void testSetMesecnaKirijaVelikaNegativan() {
        s.setMesecnaKirija(-99999.99);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajStavku(s);
        });
    }

    @Test
    void testSetMesecnaKirijaDecimalna() {
        s.setMesecnaKirija(75000.50);

        assertEquals(75000.50, s.getMesecnaKirija());
    }

    @Test
    void testSetMesecnaKirijaVelikaVrednost() {
        s.setMesecnaKirija(999999.99);

        assertEquals(999999.99, s.getMesecnaKirija());
    }

    @Test
    void testSetIznosDepozita() {
        s.setIznosDepozita(10000.00);

        assertEquals(10000.00, s.getIznosDepozita());
    }

    @Test
    void testSetIznosDepozitaNull() {
        s.setIznosDepozita(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajStavku(s);
        });
    }

    @Test
    void testSetIznosDepozitaNula() {
        s.setIznosDepozita(0.0);

        assertEquals(0.0, s.getIznosDepozita());
    }

    @Test
    void testSetIznosDepozitaNegativna() {
        s.setIznosDepozita(-1.0);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajStavku(s);
        });
    }

    @Test
    void testSetIznosDepozitaVelikaNegativan() {
        s.setIznosDepozita(-99999.99);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajStavku(s);
        });
    }

    @Test
    void testSetIznosDepozitaDecimalan() {
        s.setIznosDepozita(500.50);

        assertEquals(500.50, s.getIznosDepozita());
    }

    @Test
    void testSetIznosDepozitaVelikaVrednost() {
        s.setIznosDepozita(999999.99);

        assertEquals(999999.99, s.getIznosDepozita());
    }

    @Test
    void testSetIdIzdavanje() {
        Izdavanje izdavanje = new Izdavanje();
        s.setIdIzdavanje(izdavanje);

        assertEquals(izdavanje, s.getIdIzdavanje());
    }

    @Test
    void testSetIdIzdavanjeNull() {
        s.setIdIzdavanje(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajStavku(s);
        });
    }

    @Test
    void testSetIdNekretnina() {
        Nekretnina nekretnina = new Nekretnina();
        s.setIdNekretnina(nekretnina);

        assertEquals(nekretnina, s.getIdNekretnina());
    }

    @Test
    void testSetIdNekretninaNull() {
        s.setIdNekretnina(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajStavku(s);
        });
    }

    @Test
    void testToString() {
        s.setRb(1L);
        s.setMesecnaKirija(50000.00);
        s.setIznosDepozita(10000.00);

        String st = s.toString();

        assertTrue(st.contains("1"));
        assertTrue(st.contains("50000.0"));
        assertTrue(st.contains("10000.0"));
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
        "1, 1,   1, 1,   true",
        "1, 1,   2, 1,   false",
        "1, 1,   1, 2,   false",
        "1, 1,   2, 2,   false"
    })
    void testEquals(long rb1, long idIzdavanje1, long rb2, long idIzdavanje2, boolean jednako) {
        s.setRb(rb1);
        Izdavanje iz1 = new Izdavanje(idIzdavanje1);
        s.setIdIzdavanje(iz1);

        StavkaIzdavanja s2 = new StavkaIzdavanja();
        s2.setRb(rb2);
        Izdavanje iz2 = new Izdavanje(idIzdavanje2);
        s2.setIdIzdavanje(iz2);

        assertEquals(jednako, s.equals(s2));
    }
}