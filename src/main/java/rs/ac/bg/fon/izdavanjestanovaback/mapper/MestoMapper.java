/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.izdavanjestanovaback.dto.MestoDTO;
import rs.ac.bg.fon.izdavanjestanovaback.model.Mesto;

/**
 *
 * @author Marko
 */
@Component
public class MestoMapper {

    public MestoDTO toDTO(Mesto mesto) {
        if (mesto == null) return null;

        return new MestoDTO(
            mesto.getId(),
            mesto.getNazivMesta(),
            mesto.getOpstina()
        );
    }
    
    public Mesto toEntity(MestoDTO dto) {
        if (dto == null) return null;

        Mesto mesto = new Mesto();
        mesto.setId(dto.getId());
        mesto.setNazivMesta(dto.getNazivMesta());
        mesto.setOpstina(dto.getOpstina());
        return mesto;
    }
    
    public void updateEntityFromDTO(MestoDTO dto, Mesto mesto) {
        if (dto == null || mesto == null) return;
        mesto.setNazivMesta(dto.getNazivMesta());
        mesto.setOpstina(dto.getOpstina());
    }
}
