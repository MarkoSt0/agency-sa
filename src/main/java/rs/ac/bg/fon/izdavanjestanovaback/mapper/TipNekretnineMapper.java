/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.izdavanjestanovaback.dto.TipNekretnineDTO;
import rs.ac.bg.fon.izdavanjestanovaback.model.TipNekretnine;

/**
 *
 * @author Marko
 */
@Component
public class TipNekretnineMapper {
    
    public TipNekretnineDTO toDTO(TipNekretnine tip) {
        if (tip == null) return null;
        TipNekretnineDTO dto = new TipNekretnineDTO();
        dto.setIdTipaNekretnine(tip.getIdTipaNekretnine());
        dto.setNazivTipaNekretnine(tip.getNazivTipaNekretnine());
        return dto;
    }
    
    public TipNekretnine toEntity(TipNekretnineDTO dto) {
        if (dto == null) return null;
        TipNekretnine tip = new TipNekretnine();
        tip.setIdTipaNekretnine(dto.getIdTipaNekretnine());
        tip.setNazivTipaNekretnine(dto.getNazivTipaNekretnine());
        return tip;
    }
}
