/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;

import org.springframework.stereotype.Service;

import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.IzdavanjeDTO;
import rs.ac.bg.fon.izdavanjestanovaback.enums.StatusNekretnine;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.AgentRepo;
import rs.ac.bg.fon.izdavanjestanovaback.mapper.IzdavanjeMapper;
import rs.ac.bg.fon.izdavanjestanovaback.model.Izdavanje;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.IzdavanjeRepo;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.KlijentRepo;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.NekretninaRepo;
import rs.ac.bg.fon.izdavanjestanovaback.mapper.KlijentMapper;
import rs.ac.bg.fon.izdavanjestanovaback.mapper.StavkaIzdavanjaMapper;
import rs.ac.bg.fon.izdavanjestanovaback.model.Agent;
import rs.ac.bg.fon.izdavanjestanovaback.model.Klijent;
import rs.ac.bg.fon.izdavanjestanovaback.model.Nekretnina;
import rs.ac.bg.fon.izdavanjestanovaback.model.StavkaIzdavanja;

/**
 *
 * @author Marko
 */

@Service
@Transactional
public class IzdavanjeService {

    private final IzdavanjeRepo izdavanjeRepo;
    private final AgentRepo agentRepo;
    private final KlijentRepo klijentRepo;
    private final NekretninaRepo nekretninaRepo;
    private final IzdavanjeMapper izdavanjeMapper;
    private final KlijentMapper klijentMapper;
    private final StavkaIzdavanjaMapper stavkaMapper;
    

    public IzdavanjeService(
        IzdavanjeRepo izdavanjeRepo,
        NekretninaRepo nekretninaRepo,
        AgentRepo agentRepo,
        KlijentRepo klijentRepo,
        IzdavanjeMapper izdavanjeMapper,
        KlijentMapper klijentMapper,
        @Lazy StavkaIzdavanjaMapper stavkaMapper
    ) {
        this.klijentRepo = klijentRepo;
        this.agentRepo = agentRepo;
        this.nekretninaRepo = nekretninaRepo;
        this.izdavanjeRepo = izdavanjeRepo;
        this.izdavanjeMapper = izdavanjeMapper;
        this.klijentMapper = klijentMapper;
        this.stavkaMapper = stavkaMapper;
    }

    // Pretraga svih izdanja (ucitava i stavke)
    public List<IzdavanjeDTO> getAllIzdavanja() {
        return izdavanjeRepo.findAll().stream()
                .map(izdavanjeMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Dohvatanje po ID
    public IzdavanjeDTO getIzdavanjeById(Long id) {
        Optional<Izdavanje> i = izdavanjeRepo.findById(id);
        if (i.isEmpty()) {
            throw new RuntimeException("Izdavanje nije pronađeno");
        }
        return izdavanjeMapper.toDTO(i.get());
    }

    // Ubacivanje novog Izdavanja zajedno sa stavkama
    public ServiceResult addIzdavanje(IzdavanjeDTO dto) {
        try {
            Izdavanje izdavanje = izdavanjeMapper.toEntity(dto);
            
            if (dto.getKlijent() == null || dto.getKlijent().getId() == 0) {
            return ServiceResult.failure("Klijent mora biti naveden");
            }
            Optional<Klijent> klijentOpt = klijentRepo.findById(dto.getKlijent().getId());
            if (klijentOpt.isEmpty()) {
                return ServiceResult.failure("Klijent sa ID " + dto.getKlijent().getId() + " ne postoji");
            }
            izdavanje.setIdKlijent(klijentOpt.get());

            // Provera i povezivanje postojećeg agenta
            if (dto.getAgent() == null || dto.getAgent().getId() == null) {
                return ServiceResult.failure("Agent mora biti naveden");
            }
            Optional<Agent> agentOpt = agentRepo.findById(dto.getAgent().getId());
            if (agentOpt.isEmpty()) {
                return ServiceResult.failure("Agent sa ID " + dto.getAgent().getId() + " ne postoji");
            }
            izdavanje.setIdAgent(agentOpt.get());
            
            if (izdavanje.getStavkaIzdavanjaCollection() != null) {
                long rbCounter = 1;
                for (StavkaIzdavanja stavka : izdavanje.getStavkaIzdavanjaCollection()) {
                                stavka.setIdIzdavanje(izdavanje);
                                stavka.setRb(rbCounter++);
                }       
            }
            
            izdavanjeRepo.save(izdavanje);
            return ServiceResult.success("Izdavanje je uspešno sačuvano");
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.failure("Sistem ne može da sačuva izdavanje: " + e.getMessage());
        }
    }

    public ServiceResult updateIzdavanje(IzdavanjeDTO dto) {
        try {
            Optional<Izdavanje> result = izdavanjeRepo.findById(dto.getIdIzdavanje());
            if (result.isEmpty()) {
                return ServiceResult.failure("Izdavanje ne postoji");
            }

            Izdavanje izdavanje = result.get();

            if (dto.getAgent() == null || dto.getAgent().getId() == null) {
            return ServiceResult.failure("Agent mora biti naveden");
            }
            Optional<Agent> agentOpt = agentRepo.findById(dto.getAgent().getId());
            if (agentOpt.isEmpty()) {
                return ServiceResult.failure("Agent sa ID " + dto.getAgent().getId() + " ne postoji");
            }
            izdavanje.setIdAgent(agentOpt.get());

            if (dto.getKlijent() == null || dto.getKlijent().getId() == 0) {
                return ServiceResult.failure("Klijent mora biti naveden");
            }
            Optional<Klijent> klijentOpt = klijentRepo.findById(dto.getKlijent().getId());
            if (klijentOpt.isEmpty()) {
                return ServiceResult.failure("Klijent sa ID " + dto.getKlijent().getId() + " ne postoji");
            }
            izdavanje.setIdKlijent(klijentOpt.get());
            
            izdavanjeMapper.updateEntityFromDTO(dto, izdavanje);
            
            if (dto.getStavke() != null) {
                List<StavkaIzdavanja> noveStavke = stavkaMapper.toEntityList(dto.getStavke());
                long rbCounter = 1;
                for (StavkaIzdavanja stavka : noveStavke) {
                    stavka.setIdIzdavanje(izdavanje);
                    stavka.setRb(rbCounter++);
                }
                if (izdavanje.getStavkaIzdavanjaCollection() == null) {
                    izdavanje.setStavkaIzdavanjaCollection(new ArrayList<>());
                } else {
                    izdavanje.getStavkaIzdavanjaCollection().clear();
                }
                izdavanje.getStavkaIzdavanjaCollection().addAll(noveStavke);
                
            }
            izdavanjeRepo.save(izdavanje);
            return ServiceResult.success("Izdavanje je uspešno izmenjeno");

        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.failure("Sistem ne može da izmeni izdavanje: " + e.getMessage());
        }
    }

    public ServiceResult deleteIzdavanje(Long id) {
        try {
            izdavanjeRepo.deleteById(id);
            return ServiceResult.success("Izdavanje je obrisano");
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.failure("Sistem ne može da obriše izdavanje: " + e.getMessage());
        }
    }

    public List<IzdavanjeDTO> searchByStatus(String status) {
        return izdavanjeRepo.findByStatusUgovoraContaining(status).stream()
                .map(izdavanjeMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<IzdavanjeDTO> searchByKlijent(String ime, String prezime) {
        return izdavanjeRepo.findByIdKlijent_ImeContainingAndIdKlijent_PrezimeContaining(
                        ime != null ? ime : "",
                        prezime != null ? prezime : ""
                ).stream()
                .map(izdavanjeMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    
    // ovo je mtd za status, ako istekne ugovor da promeni i ucini nekretnine Dostupnim
    @Transactional
    public void checkAndUpdateExpiredContracts() {
        Date danas = new Date();
        List<Izdavanje> aktivnaIzdavanja = izdavanjeRepo.findByStatusUgovora("Aktivan");

        for (Izdavanje izdavanje : aktivnaIzdavanja) {
            
            Optional<Date> maxDatum = izdavanje.getStavkaIzdavanjaCollection()
                .stream()
                .map(StavkaIzdavanja::getDatumZavrsetkaIzdavanja)
                .max((d1, d2) -> d1.compareTo(d2));

            if (maxDatum.isPresent() && maxDatum.get().before(danas)) {
                izdavanje.setStatusUgovora("Zavrsen");

                for (StavkaIzdavanja stavka : izdavanje.getStavkaIzdavanjaCollection()) {
                    Nekretnina nekretnina = stavka.getIdNekretnina();
                    nekretnina.setStatusNekretnine(StatusNekretnine.DOSTUPNA);
                    nekretninaRepo.save(nekretnina);
                }

                izdavanjeRepo.save(izdavanje);
                System.out.println("Izdavanje " + izdavanje.getIdIzdavanje() + " je automatski završeno");
            }
        }
    }
}