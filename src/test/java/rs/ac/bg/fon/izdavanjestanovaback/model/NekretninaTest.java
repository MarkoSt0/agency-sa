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

import rs.ac.bg.fon.izdavanjestanovaback.enums.StatusNekretnine;
import rs.ac.bg.fon.izdavanjestanovaback.enums.TipGrejanja;

import java.util.Set;

class NekretninaTest {

    Nekretnina n;

    public static void validirajNekretninu(Nekretnina nekretnina) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Nekretnina>> violations = validator.validate(nekretnina);

        if (!violations.isEmpty()) {
            String porukaGreske = violations.iterator().next().getMessage();
            throw new IllegalArgumentException(porukaGreske);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        n = new Nekretnina();
    }

    @AfterEach
    void tearDown() throws Exception {
        n = null;
    }

    @Test
    void testNoArgsKonstruktorPoljaNull() {
        Nekretnina nekretnina = new Nekretnina();

        assertNull(nekretnina.getIdNekretnina());
        assertNull(nekretnina.getAdresa());
        assertNull(nekretnina.getPovrsina());
        assertNull(nekretnina.getSprat());
        assertNull(nekretnina.getBrojSoba());
        assertNull(nekretnina.getGodinaIzgradnje());
        assertNull(nekretnina.getOpis());
        assertNull(nekretnina.getTipNekretnine());
        assertNull(nekretnina.getTipGrejanja());
        assertNull(nekretnina.getStatusNekretnine());
    }

    @Test
    void testSetAdresa() {
        n.setAdresa("Bulevar Oslobodjenja 15");

        assertEquals("Bulevar Oslobodjenja 15", n.getAdresa());
    }

    @Test
    void testSetAdresaNull() {
        n.setAdresa(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetAdresaPrazna() {
        n.setAdresa("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetAdresaBlank() {
        n.setAdresa("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetAdresaPreviseDuga() {
        n.setAdresa("A".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetAdresaMaximalnaDuzina() {
        n.setAdresa("A".repeat(255));

        assertEquals("A".repeat(255), n.getAdresa());
    }

    @Test
    void testSetAdresaJedanKarakter() {
        n.setAdresa("A");

        assertEquals("A", n.getAdresa());
    }

    @Test
    void testSetPovrsina() {
        n.setPovrsina("65");

        assertEquals("65", n.getPovrsina());
    }

    @Test
    void testSetPovrsinaNull() {
        n.setPovrsina(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetPovrsinaPrazna() {
        n.setPovrsina("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetPovrsinaBlank() {
        n.setPovrsina("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetPovrsinaPreviseDuga() {
        n.setPovrsina("P".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetPovrsinaMaximalnaDuzina() {
        n.setPovrsina("P".repeat(255));

        assertEquals("P".repeat(255), n.getPovrsina());
    }

    @Test
    void testSetPovrsinaJedanKarakter() {
        n.setPovrsina("5");

        assertEquals("5", n.getPovrsina());
    }

    @Test
    void testSetSprat() {
        n.setSprat("4. sprat");

        assertEquals("4. sprat", n.getSprat());
    }

    @Test
    void testSetSpratNull() {
        n.setSprat(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetSpratPrazan() {
        n.setSprat("");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetSpratBlank() {
        n.setSprat("   ");
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetSpratPreviseDug() {
        n.setSprat("S".repeat(256));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetSpratMaximalnaDuzina() {
        n.setSprat("S".repeat(255));

        assertEquals("S".repeat(255), n.getSprat());
    }

    @Test
    void testSetSpratJedanKarakter() {
        n.setSprat("P");

        assertEquals("P", n.getSprat());
    }

    @Test
    void testSetBrojSoba() {
        n.setBrojSoba(2.5);

        assertEquals(2.5, n.getBrojSoba());
    }

    @Test
    void testSetBrojSobaNull() {
        n.setBrojSoba(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetBrojSobaNula() {
        n.setBrojSoba(0.0);

        assertEquals(0.0, n.getBrojSoba());
    }

    @Test
    void testSetBrojSobaNegativna() {
        n.setBrojSoba(-1.0);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetBrojSobaVelikaNegativan() {
        n.setBrojSoba(-99.0);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetBrojSobaDecimalan() {
        n.setBrojSoba(3.5);

        assertEquals(3.5, n.getBrojSoba());
    }

    @Test
    void testSetBrojSobaVelikiBroj() {
        n.setBrojSoba(20.0);

        assertEquals(20.0, n.getBrojSoba());
    }

    @Test
    void testSetGodinaIzgradnje() {
        n.setGodinaIzgradnje(2005);

        assertEquals(2005, n.getGodinaIzgradnje());
    }

    @Test
    void testSetGodinaIzgradnjeNull() {
        n.setGodinaIzgradnje(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetGodinaIzgradnjeMinimalna() {
        n.setGodinaIzgradnje(1800);

        assertEquals(1800, n.getGodinaIzgradnje());
    }

    @Test
    void testSetGodinaIzgradnjePre1800() {
        n.setGodinaIzgradnje(1799);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetGodinaIzgradnjeNula() {
        n.setGodinaIzgradnje(0);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetGodinaIzgradnjeNegativna() {
        n.setGodinaIzgradnje(-100);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetGodinaIzgradnjeTekucaGodina() {
        n.setGodinaIzgradnje(2025);

        assertEquals(2025, n.getGodinaIzgradnje());
    }

    @Test
    void testSetOpis() {
        n.setOpis("Opis nekretnine");

        assertEquals("Opis nekretnine", n.getOpis());
    }

    @Test
    void testSetOpisNull() {
        n.setOpis(null);

        assertNull(n.getOpis());
    }

    @Test
    void testSetOpisPrazan() {
        n.setOpis("");

        assertEquals("", n.getOpis());
    }

    @Test
    void testSetOpisPreviseDug() {
        n.setOpis("O".repeat(1001));
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetOpisMaximalnaDuzina() {
        n.setOpis("O".repeat(1000));

        assertEquals("O".repeat(1000), n.getOpis());
    }

    @Test
    void testSetOpisJedanKarakter() {
        n.setOpis("O");

        assertEquals("O", n.getOpis());
    }

    @Test
    void testSetTipNekretnine() {
        TipNekretnine tip = new TipNekretnine();
        n.setTipNekretnine(tip);

        assertEquals(tip, n.getTipNekretnine());
    }

    @Test
    void testSetTipNekretnineNull() {
        n.setTipNekretnine(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetTipGrejanja() {
        n.setTipGrejanja(TipGrejanja.CENTRALNO);

        assertEquals(TipGrejanja.CENTRALNO, n.getTipGrejanja());
    }

    @Test
    void testSetTipGrejanjaNull() {
        n.setTipGrejanja(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testSetStatusNekretnine() {
        n.setStatusNekretnine(StatusNekretnine.DOSTUPNA);

        assertEquals(StatusNekretnine.DOSTUPNA, n.getStatusNekretnine());
    }

    @Test
    void testSetStatusNekretnineNull() {
        n.setStatusNekretnine(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validirajNekretninu(n);
        });
    }

    @Test
    void testToString() {
        n.setAdresa("Bulevar Oslobodjenja 15");
        n.setPovrsina("65");
        n.setSprat("4. sprat");
        n.setBrojSoba(2.5);
        n.setGodinaIzgradnje(2005);
        n.setOpis("Lep stan u centru grada.");

        String st = n.toString();

        assertTrue(st.contains("Bulevar Oslobodjenja 15"));
        assertTrue(st.contains("65"));
        assertTrue(st.contains("4. sprat"));
        assertTrue(st.contains("2.5"));
        assertTrue(st.contains("2005"));
        assertTrue(st.contains("Lep stan u centru grada."));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(n.equals(n));
    }

    @Test
    void testEqualsNullObjekat() {
        assertFalse(n.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        assertFalse(n.equals(new String()));
    }

    @ParameterizedTest
    @CsvSource({
        "1, Bulevar Oslobodjenja 15,   1, Bulevar Oslobodjenja 15,   true",
        "1, Bulevar Oslobodjenja 15,   2, Bulevar Oslobodjenja 15,   false",
        "1, Bulevar Oslobodjenja 15,   1, Cara Lazara 5,             false",
        "1, Bulevar Oslobodjenja 15,   2, Cara Lazara 5,             false"
    })
    void testEquals(long id1, String adresa1, long id2, String adresa2, boolean jednako) {
        n.setIdNekretnina(id1);
        n.setAdresa(adresa1);

        Nekretnina n2 = new Nekretnina();
        n2.setIdNekretnina(id2);
        n2.setAdresa(adresa2);

        assertEquals(jednako, n.equals(n2));
    }
}