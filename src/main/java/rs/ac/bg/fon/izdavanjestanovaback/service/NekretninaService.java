/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.service;


import jakarta.transaction.Transactional;
import rs.ac.bg.fon.izdavanjestanovaback.dto.NekretninaDTO;
import rs.ac.bg.fon.izdavanjestanovaback.mapper.NekretninaMapper;
import rs.ac.bg.fon.izdavanjestanovaback.model.Nekretnina;
import rs.ac.bg.fon.izdavanjestanovaback.model.TipNekretnine;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.NekretninaRepo;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.TipNekretnineRepo;

/**
 *
 * @author Marko
 */
@Service
@Transactional
public class NekretninaService {

    private final NekretninaRepo nekretninaRepo;
    private final TipNekretnineRepo tipNekretnineRepo;
    private final NekretninaMapper nekretninaMapper;

    public NekretninaService(NekretninaRepo nekretninaRepo,
                             NekretninaMapper nekretninaMapper,
                             TipNekretnineRepo tipNekretnineRepo) {
        this.nekretninaRepo = nekretninaRepo;
        this.nekretninaMapper = nekretninaMapper;
        this.tipNekretnineRepo = tipNekretnineRepo;
    }

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
            
//            n.setAdresa(dto.getAdresa());
//            n.setPovrsina(dto.getPovrsina());
//            n.setSprat(dto.getSprat());
//            n.setBrojSoba(dto.getBrojSoba());
//            n.setGodinaIzgradnje(dto.getGodinaIzgradnje());
//            n.setOpis(dto.getOpis());
//            n.setTipGrejanja(dto.getTipGrejanja());
//            n.setStatusNekretnine(dto.getStatusNekretnine());

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
                return ServiceResult.failure("Nekretnina sa ID " + id + " nije pronađena");
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
            return ServiceResult.failure("Nekretnina sa ID " + id + " nije pronađena");
        }
        return ServiceResult.success("Nekretnina pronađena", opt.get());
    }
}
