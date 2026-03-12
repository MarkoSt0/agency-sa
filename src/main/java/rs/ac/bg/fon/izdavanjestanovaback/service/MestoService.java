/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.izdavanjestanovaback.com.ServiceResult;
import rs.ac.bg.fon.izdavanjestanovaback.dto.MestoDTO;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.MestoRepo;
import rs.ac.bg.fon.izdavanjestanovaback.mapper.MestoMapper;
import rs.ac.bg.fon.izdavanjestanovaback.model.Mesto;
/**
 *
 * @author Marko
 */
@Service
@Transactional
public class MestoService {

    private final MestoRepo mestoRepo;
    private final MestoMapper mestoMapper;

    public MestoService(MestoRepo mestoRepo, MestoMapper mestoMapper) {
        this.mestoRepo = mestoRepo;
        this.mestoMapper = mestoMapper;
    }

    public ServiceResult addMesto(MestoDTO dto) {
        try {
            Mesto mesto = mestoMapper.toEntity(dto);
            mestoRepo.save(mesto);
            return ServiceResult.success("Sistem je sačuvao mesto.");
        } catch (Exception e) {
            return ServiceResult.failure("Sistem ne može da sačuva mesto.");
        }
    }

    public List<MestoDTO> getAllMesta() {
        return mestoRepo.findAll().stream()
                .map(mestoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MestoDTO getMestoById(Long id) {
        Mesto mesto = mestoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesto nije pronađeno."));
        return mestoMapper.toDTO(mesto);
    }

    public ServiceResult updateMesto(MestoDTO dto) {
        try {
            Mesto mesto = mestoRepo.findById(dto.getId()).orElse(null);
            if (mesto == null) {
                return ServiceResult.failure("Mesto sa datim ID nije pronađeno.");
            }
//            mesto.setNazivMesta(dto.getNazivMesta());
//            mesto.setOpstina(dto.getOpstina());

            mestoMapper.updateEntityFromDTO(dto, mesto);
            mestoRepo.save(mesto);
            return ServiceResult.success("Sistem je izmenio mesto.");
        } catch (Exception e) {
            return ServiceResult.failure("Sistem ne može da izmeni mesto.");
        }
    }

    public ServiceResult deleteMesto(Long id) {
        try {
            mestoRepo.deleteById(id);
            return ServiceResult.success("Sistem je obrisao mesto.");
        } catch (Exception e) {
            return ServiceResult.failure("Sistem ne može da obriše mesto.");
        }
    }
}
