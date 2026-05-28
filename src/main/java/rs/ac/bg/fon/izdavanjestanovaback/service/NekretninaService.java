/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.service;


import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import rs.ac.bg.fon.izdavanjestanovaback.dto.NekretninaDTO;
import rs.ac.bg.fon.izdavanjestanovaback.mapper.NekretninaMapper;
import rs.ac.bg.fon.izdavanjestanovaback.model.Nekretnina;
import rs.ac.bg.fon.izdavanjestanovaback.model.TipNekretnine;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.NekretninaRepo;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.TipNekretnineRepo;
import rs.ac.bg.fon.izdavanjestanovaback.model.Agent;

/**
 *
 * @author Marko
 */
@Service
@Transactional
@AllArgsConstructor
public class NekretninaService {

    private final NekretninaRepo nekretninaRepo;
    private final TipNekretnineRepo tipNekretnineRepo;
    private final NekretninaMapper nekretninaMapper;
    private final Validator validator;

    public ServiceResult addNekretnina(NekretninaDTO dto) {
        try {
            Nekretnina nekretnina = nekretninaMapper.toEntity(dto);

            if (dto.getTipNekretnine() != null) {
                if (dto.getTipNekretnine().getIdTipaNekretnine() == null) {
                    return ServiceResult.failure("ID tipa nekretnine mora biti naveden");
                }
                Optional<TipNekretnine> tip = tipNekretnineRepo.findById(dto.getTipNekretnine().getIdTipaNekretnine());
                if (tip.isEmpty()) {
                    return ServiceResult.failure("Tip nekretnine sa ID " + dto.getTipNekretnine().getIdTipaNekretnine() + " nije pronađen");
                }
                nekretnina.setTipNekretnine(tip.get());
            }
            //Nova validacija
            Set<ConstraintViolation<Nekretnina>> violations = validator.validate(nekretnina);
            if (!violations.isEmpty()) {
                String poruke = violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
                return ServiceResult.failure(poruke);
            }
            nekretninaRepo.save(nekretnina);
            return ServiceResult.success("Sistem je sačuvao nekretninu.");

        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.failure("Sistem ne može da sačuva nekretninu: " + e.getMessage());
        }
    }

    public ServiceResult updateNekretnina(NekretninaDTO dto) {
        try {
            Optional<Nekretnina> result = nekretninaRepo.findById(dto.getIdNekretnina());
            if (result.isEmpty()) {
                return ServiceResult.failure("Nekretnina nije pronađena");
            }
            Nekretnina n = result.get();

            if (dto.getTipNekretnine() != null) {
                if (dto.getTipNekretnine().getIdTipaNekretnine() == null) {
                    return ServiceResult.failure("ID tipa nekretnine mora biti naveden");
                }
                Optional<TipNekretnine> tip = tipNekretnineRepo.findById(dto.getTipNekretnine().getIdTipaNekretnine());
                if (tip.isEmpty()) {
                    return ServiceResult.failure("Tip nekretnine sa ID " + dto.getTipNekretnine().getIdTipaNekretnine() + " nije pronađen");
                }
                n.setTipNekretnine(tip.get());
            }
            
            nekretninaMapper.updateEntityFromDTO(dto, n);

            //Nova validacija
            Set<ConstraintViolation<Nekretnina>> violations = validator.validate(n);
            if (!violations.isEmpty()) {
                String poruke = violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
                return ServiceResult.failure(poruke);
            }
            
            nekretninaRepo.save(n);
            return ServiceResult.success("Nekretnina je uspešno izmenjena");
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.failure("Sistem ne može da izmeni nekretninu: " + e.getMessage());
        }
    }

    public ServiceResult deleteNekretnina(Long id) {
        try {
            Optional<Nekretnina> opt = nekretninaRepo.findById(id);
            if (opt.isEmpty()) {
                return ServiceResult.failure("Nekretnina sa ID " + id + " nije pronadjena");
            }
            nekretninaRepo.delete(opt.get());
            return ServiceResult.success("Nekretnina je uspešno obrisana");
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.failure("Sistem ne može da obriše nekretninu: " + e.getMessage());
        }
    }

    public List<NekretninaDTO> getAllNekretnine() {
        return nekretninaRepo.findAll().stream()
                .map(nekretninaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<NekretninaDTO> searchByPovrsina(String povrsina) {
        return nekretninaRepo.findByPovrsinaContaining(povrsina).stream()
                .map(nekretninaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<NekretninaDTO> searchByTipNekretnine(Long tipId) {
        return nekretninaRepo.findByTipNekretnine_IdTipaNekretnine(tipId).stream()
                .map(nekretninaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ServiceResult getNekretninaById(Long id) {
        Optional<Nekretnina> opt = nekretninaRepo.findById(id);
        if (opt.isEmpty()) {
            return ServiceResult.failure("Nekretnina sa ID " + id + " nije pronadjena");
        }
        return ServiceResult.success("Nekretnina pronađena", nekretninaMapper.toDTO(opt.get()));
    }
}
