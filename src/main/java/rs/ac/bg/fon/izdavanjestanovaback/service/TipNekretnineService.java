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

import rs.ac.bg.fon.izdavanjestanovaback.dto.TipNekretnineDTO;
import rs.ac.bg.fon.izdavanjestanovaback.jparepository.TipNekretnineRepo;
import rs.ac.bg.fon.izdavanjestanovaback.mapper.TipNekretnineMapper;
import rs.ac.bg.fon.izdavanjestanovaback.model.TipNekretnine;
/**
 *
 * @author Marko
 */
@Service
@Transactional
public class TipNekretnineService {

    private final TipNekretnineRepo tipRepo;
    private final TipNekretnineMapper tipMapper;

    public TipNekretnineService(TipNekretnineRepo tipRepo, TipNekretnineMapper tipMapper) {
        this.tipRepo = tipRepo;
        this.tipMapper = tipMapper;
    }

    public ServiceResult addTip(TipNekretnineDTO dto) {
        try {
            TipNekretnine tip = tipMapper.toEntity(dto);
            tipRepo.save(tip);
            return ServiceResult.success("Tip nekretnine je sačuvan.");
        } catch (Exception e) {
            return ServiceResult.failure("Sistem ne može da sačuva tip nekretnine.");
        }
    }

    public List<TipNekretnineDTO> getAllTipovi() {
        return tipRepo.findAll().stream()
                .map(tipMapper::toDTO)
                .collect(Collectors.toList());
    }
}
