/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.controller;


import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponse;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponseBuilder;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.IzdavanjeDTO;
import rs.ac.bg.fon.izdavanjestanovaback.service.IzdavanjeService;

/**
 * REST kontroler koji upravlja zivotnim ciklusom ugovora o izdavanju nekretnina.
 * Predstavlja ulaznu tacku sistema za izvrsavanje sistemskih operacija nad izdavanjima.
 * Komunikacija se vrsi putem HTTP protokola.
 * 
 * @author Marko
 */
@RestController
@RequestMapping("/izdavanje")
@CrossOrigin("http://localhost:9000")
public class IzdavanjeController {

    /**
     * Servisni sloj zaduzen za poslovnu logiku nad podacima o izdavanju.
     */
    private final IzdavanjeService izdavanjeService;

    
    /**
     * Konstruktor koji ubrizgava (inject) zavisnost IzdavanjeService.
     * 
     * @param izdavanjeService servis za rad sa izdavanjem nekretnina
     */
    public IzdavanjeController(IzdavanjeService izdavanjeService) {
        this.izdavanjeService = izdavanjeService;
    }

    /**
     * Izvrsava sistemsku operaciju: Unos izdavanja.
     * Kreira novi ugovor o izdavanju nekretnine na osnovu prosledjenih podataka.
     * 
     * @param dto podaci o novom izdavanju koje treba uneti u sistem
     * @return ResponseEntity sa objektom ApiResponse koji sadrzi poruku o ishodu operacije i HTTP status (CREATED, NOT_FOUND ili BAD_REQUEST)
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addIzdavanje(@RequestBody IzdavanjeDTO dto) {
        ServiceResult result = izdavanjeService.addIzdavanje(dto);

        if (!result.isUspesno()) {
            HttpStatus status = result.getPoruka().toLowerCase().contains("ne postoji")
                    ? HttpStatus.NOT_FOUND
                    : HttpStatus.BAD_REQUEST;

            return ResponseEntity.status(status)
                    .body(ApiResponseBuilder.kreirajOdgovorSaPodacima(
                            result.getPoruka(),
                            Map.of("value", result.getPoruka()),
                            status
                    ));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        result.getPoruka(),
                        HttpStatus.CREATED
                ));
    }
    
    /**
     * Pretraga izdavanja.
     * Vraca listu svih evidentiranih izdavanja nekretnina u sistemu.
     * 
     * @return ResponseEntity sa listom IzdavanjeDTO objekata i HTTP statusom OK
     */
    @GetMapping("/all")
    public ResponseEntity<List<IzdavanjeDTO>> getAllIzdavanja() {
        return ResponseEntity.ok(izdavanjeService.getAllIzdavanja());
    }
    
    /**
     * Sistemska operacija: Pretraga izdavanja (filtriranje po statusu).
     * Pronalazi sva izdavanja koja odgovaraju zadatom statusu ugovora (npr. AKTIVAN, ZAVRSEN).
     * 
     * @param status tekstualni status po kome se vrsi pretraga ugovora
     * @return ResponseEntity sa listom pronadjenih IzdavanjeDTO objekata i HTTP statusom OK
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<IzdavanjeDTO>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(izdavanjeService.searchByStatus(status));
    }
    
    /**
     * Sistemska operacija: Pretraga izdavanja (po primarnom kljucu).
     * Pronalazi specificno izdavanje na osnovu njegovog jedinstvenog identifikatora.
     * 
     * @param id jedinstveni identifikator (ID) ugovora o izdavanju
     * @return ResponseEntity sa IzdavanjeDTO objektom ukoliko postoji (HTTP OK), ili prazan odgovor sa statusom NOT_FOUND
     */
    @GetMapping("/{id}")
    public ResponseEntity<IzdavanjeDTO> getIzdavanjeById(@PathVariable Long id) {
        IzdavanjeDTO dto = izdavanjeService.getIzdavanjeById(id);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(dto);
    }
    
    /**
     * Sistemsks operacija: Izmena izdavanja.
     * Azurira podatke o postojecem izdavanju u sistemu.
     * 
     * @param dto podaci o izdavanju sa izmenjenim vrednostima koje treba sacuvati
     * @return ResponseEntity sa objektom ApiResponse koji sadrzi poruku o ishodu i HTTP status (ACCEPTED, NOT_FOUND ili BAD_REQUEST)
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateIzdavanje(@RequestBody IzdavanjeDTO dto) {
        ServiceResult result = izdavanjeService.updateIzdavanje(dto);

        if (!result.isUspesno()) {
            HttpStatus status = result.getPoruka().toLowerCase().contains("ne postoji")
                    ? HttpStatus.NOT_FOUND
                    : HttpStatus.BAD_REQUEST;

            return ResponseEntity.status(status)
                    .body(ApiResponseBuilder.kreirajOdgovorSaPodacima(
                            result.getPoruka(),
                            Map.of("value", result.getPoruka()),
                            status
                    ));
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        result.getPoruka(),
                        HttpStatus.ACCEPTED
                ));
    }

    /**
     * Izvrsava sistemsku operaciju: Brisanje izdavanja.
     * Uklanja ugovor o izdavanju iz sistema na osnovu prosledjenog identifikatora.
     * 
     * @param id jedinstveni identifikator (ID) ugovora koji se brise
     * @return ResponseEntity sa objektom ApiResponse koji sadrzi rezultat brisanja i HTTP status (OK ili BAD_REQUEST)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteIzdavanje(@PathVariable Long id) {
        ServiceResult result = izdavanjeService.deleteIzdavanje(id);

        HttpStatus status = result.isUspesno() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        result.getPoruka(),
                        status
                ));
    }
    
    /**
     * Sistemska operacija: Pretraga izdavanja (filtriranje po klijentu).
     * Pronalazi ugovore o izdavanju na osnovu imena i/ili prezimena klijenta koji je zakupac.
     * 
     * @param ime ime klijenta (opciono)
     * @param prezime prezime klijenta (opciono)
     * @return ResponseEntity sa listom pronadjenih IzdavanjeDTO objekata i HTTP statusom OK
     */
    @GetMapping("/klijent")
    public ResponseEntity<List<IzdavanjeDTO>> getByKlijent(
            @RequestParam(required = false) String ime,
            @RequestParam(required = false) String prezime) {

        List<IzdavanjeDTO> result = izdavanjeService.searchByKlijent(ime, prezime);
        return ResponseEntity.ok(result);
    }
    
    /**
     * Pomocna sistemska operacija: Provera i azuriranje isticanja ugovora.
     * Pokrece rucnu proveru svih ugovora i automatski im menja status ukoliko je datum isteka prosao.
     * 
     * @return ResponseEntity sa objektom ApiResponse koji potvrdjuje uspesno izvrsenu proveru i HTTP statusom OK
     */
    @PostMapping("/check-expired")
    public ResponseEntity<ApiResponse> checkExpiredContracts() {
        izdavanjeService.checkAndUpdateExpiredContracts();

        HttpStatus status = HttpStatus.OK;

        return ResponseEntity.status(status)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        "Istekli ugovori su provereni i ažurirani",
                        status
                ));
    }
}
