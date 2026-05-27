package rs.ac.bg.fon.izdavanjestanovaback.service;


import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.AgentDTO;
import rs.ac.bg.fon.izdavanjestanovaback.dto.SertifikatDTO;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.AgentRepo;
import rs.ac.bg.fon.izdavanjestanovaback.mapper.AgentMapper;
import rs.ac.bg.fon.izdavanjestanovaback.mapper.SertifikatMapper;
import rs.ac.bg.fon.izdavanjestanovaback.model.Agent;
import rs.ac.bg.fon.izdavanjestanovaback.model.Klijent;
import rs.ac.bg.fon.izdavanjestanovaback.model.Sertifikat;
/**
 *
 * @author Marko
 */
@Service
@Transactional
@AllArgsConstructor
public class AgentService {

    private final AgentRepo agentRepo;
    private final AgentMapper agentMapper;
    private final SertifikatMapper sertifikatMapper;
    private final Validator validator;

    public ServiceResult addAgent(AgentDTO dto) {
        try {
            if (agentRepo.existsByKorisnickoIme(dto.getKorisnickoIme())) {
                return ServiceResult.failure("Korisničko ime je već zauzeto.");
            } else {
                Agent agent = agentMapper.toEntity(dto);
                agentRepo.save(agent);
                return ServiceResult.success("Sistem je sačuvao agenta.");
            }
        } catch (Exception e) {
            return ServiceResult.failure("Sistem ne može da sačuva agenta.");
        }
    }

    public List<AgentDTO> getAllAgenti() {
        return agentRepo.findAll().stream()
                .map(agentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AgentDTO> getAgentiByIme(String ime) {
        List<Agent> agenti = agentRepo.findByImeContaining(ime);
        return agenti.stream()
                .map(agentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AgentDTO getAgentById(Long id) {
        Agent agent = agentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Agent nije pronađen."));
        return agentMapper.toDTO(agent);
    }

    public ServiceResult deleteAgent(Long id) {
        try {
            agentRepo.deleteById(id);
            return ServiceResult.success("Sistem je obrisao agenta.");
        } catch (Exception e) {
            String message = e.getMessage();
            if (message != null && message.contains("foreign key")) {
                return ServiceResult.failure("Sistem ne može da obriše agenta jer je povezan sa postojećim izdavanjem.");
            }
            return ServiceResult.failure("Sistem ne može da obriše agenta: " + e.getMessage());
        }
    }

    public ServiceResult updateAgent(AgentDTO dto) {
        if (dto == null) {
            return ServiceResult.failure("Nije prosledjen agent.");
        }
        if (dto.getId() == null) {
            return ServiceResult.failure("ID agenta je obavezan za izmenu.");
        }
        try {
            Agent agent = agentRepo.findById(dto.getId())
                .orElse(null);
            if (agent == null) {
                return ServiceResult.failure("Agent sa datim ID nije pronađen.");
            }
            agentMapper.updateEntityFromDTO(dto, agent);
            
            if(dto.getSertifikati() != null){
                agent.getSertifikatCollection().clear();
                for (SertifikatDTO sDto : dto.getSertifikati()) {
                    Sertifikat noviSert = sertifikatMapper.toEntity(sDto);
                    noviSert.setAgent(agent);
                    
                    agent.getSertifikatCollection().add(noviSert);
                }
            }
            
            //Nova validacija
                Set<ConstraintViolation<Agent>> violations = validator.validate(agent);
                if (!violations.isEmpty()) {
                    String poruke = violations.stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining(", "));
                    return ServiceResult.failure(poruke);
                }
            
            agentRepo.save(agent);
            return ServiceResult.success("Sistem je izmenio agenta.");
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.failure("Sistem ne može da izmeni agenta.");
        }
    }
    
    public ServiceResult login(String korisnickoIme, String sifra) {
        if (korisnickoIme == null || korisnickoIme.isBlank()) {
            return ServiceResult.failure("Korisničko ime je obavezno.");
        }
        if (sifra == null || sifra.isBlank()) {
            return ServiceResult.failure("Šifra je obavezna.");
        }
        try {
            Agent agent = agentRepo.findByKorisnickoImeAndSifra(korisnickoIme, sifra)
                    .orElse(null);
            if (agent == null) {
                return ServiceResult.failure("Pogrešno korisničko ime ili šifra.");
            }
            return ServiceResult.success("Uspešna prijava.", agentMapper.toDTO(agent));
        } catch (Exception e) {
            return ServiceResult.failure("Došlo je do greške prilikom prijave.");
        }
    }
}
