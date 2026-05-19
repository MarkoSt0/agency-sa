/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.izdavanjestanovaback.dto.SertifikatDTO;
import rs.ac.bg.fon.izdavanjestanovaback.model.Sertifikat;

/**
 *
 * @author Marko
 */
@Component
public class SertifikatMapper {
    
    public SertifikatDTO toDTO(Sertifikat s) {
        if (s == null) return null;
        
        Long agentId = (s.getAgent() != null) ? s.getAgent().getId() : null;

        return new SertifikatDTO(
                s.getIdSertifikat(), 
                s.getNazivSertifikata(), 
                s.getIzdavalac(), 
                s.getDatumSticanja(), 
                agentId
        );
    }
    
    public Sertifikat toEntity(SertifikatDTO dto) {
        if (dto == null) return null;

        Sertifikat s = new Sertifikat();
        s.setIdSertifikat(dto.getIdSertifikat());
        s.setNazivSertifikata(dto.getNazivSertifikata());
        s.setIzdavalac(dto.getIzdavaoc());
        s.setDatumSticanja(dto.getDatumSticanja());
        // Agent se postavlja na drugom mestu
        return s;
    }
    
    public void updateEntityFromDTO(SertifikatDTO dto, Sertifikat s) {
        if (dto == null || s == null) return;
        s.setIdSertifikat(dto.getIdSertifikat());
        s.setNazivSertifikata(dto.getNazivSertifikata());
        s.setIzdavalac(dto.getIzdavaoc());
        s.setDatumSticanja(dto.getDatumSticanja());
        // Postavlja se agent na drugom mestu
    }
}
