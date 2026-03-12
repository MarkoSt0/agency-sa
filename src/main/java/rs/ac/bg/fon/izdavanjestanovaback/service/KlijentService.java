/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.KlijentDTO;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.KlijentRepo;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.MestoRepo;
import rs.ac.bg.fon.izdavanjestanovaback.mapper.KlijentMapper;
import rs.ac.bg.fon.izdavanjestanovaback.model.Klijent;
import rs.ac.bg.fon.izdavanjestanovaback.model.Mesto;

/**
 *
 * @author Marko
 */

@Service
@Transactional
public class KlijentService {
    private final KlijentRepo klijentRepo;
    private final MestoRepo mestoRepo;
    private final KlijentMapper klijentMapper;

    public KlijentService(KlijentRepo klijentRepo, MestoRepo mestoRepo, KlijentMapper klijentMapper) {
        this.klijentRepo = klijentRepo;
        this.mestoRepo = mestoRepo;
        this.klijentMapper = klijentMapper;
    }
    
    public ServiceResult addKlijent(KlijentDTO dto) {
        try {
            if(klijentRepo.existsByEmail(dto.getEmail())){
                return ServiceResult.failure("Email je vec evidentiran.");
            }else{
                Klijent k = klijentMapper.toEntity(dto);
                mestoRepo.findById(dto.getMesto().getId()).ifPresent(k::setIdMesto);
                klijentRepo.save(k);
                return ServiceResult.success("Sistem je sacuvao korisnika");
            }
        } catch (Exception e) {
            return ServiceResult.failure("Sistem ne moze da sacuva korisnika");
        }
    }
    
    public List<KlijentDTO> getAllKlijenti() {
        return klijentRepo.findAll().stream()
                .map(klijentMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<KlijentDTO> getKlijenti(String email) {
        List<Klijent> klijenti = klijentRepo.findByEmailStartsWith(email);
        return klijenti.stream()
                .map(klijentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public KlijentDTO getKlijentById(Long id) {
        Klijent klijent = klijentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Klijent nije pronađen"));
        return klijentMapper.toDTO(klijent);
    }


    public ServiceResult deleteKlijent(Long id) {
        try {
            klijentRepo.deleteById(id);
            return ServiceResult.success("Sistem je obrisao klijenta");
        } catch (DataIntegrityViolationException d) {
            return ServiceResult.failure("Sistem ne može da obriše klijenta jer je povezan sa postojećim izdavanjem.");
        } catch (Exception e) {
            return ServiceResult.failure("Sistem ne može da obriše klijenta: " + e.getMessage());
        }
    }
    
    // ne koristi se u slucajevima koriscenja
    public ServiceResult updateKlijent(KlijentDTO dto) {
        try {
            Optional<Klijent> result = klijentRepo.findById(dto.getId());
            if (result.isEmpty()) {
                return ServiceResult.failure("Klijent nije pronađen");
            }
            Klijent klijent = result.get();
            if (dto.getMesto().getNazivMesta()== null || dto.getMesto().getNazivMesta().isBlank()) {
                return ServiceResult.failure("Naziv mesta mora biti naveden");
            }

            Optional<Mesto> mesto = mestoRepo.findByNazivMesta(dto.getMesto().getNazivMesta());
            if (mesto.isEmpty()) {
                return ServiceResult.failure("Mesto sa nazivom '" + dto.getMesto().getNazivMesta() + "' nije pronađeno");
            }
//            klijent.setIme(dto.getIme());
//            klijent.setPrezime(dto.getPrezime());
//            klijent.setStarost(dto.getStarost());
//            klijent.setBrojTelefona(dto.getBrojTelefona());
//            klijent.setEmail(dto.getEmail());
//            klijent.setIdMesto(mesto.get());

            klijentMapper.updateEntityFromDTO(dto, klijent);
            klijent.setIdMesto(mesto.get());


            klijentRepo.save(klijent);
            return ServiceResult.success("Klijent je uspešno izmenjen");
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.failure("Sistem ne može da izmeni klijenta: " + e.getMessage());
        }
    }
}
