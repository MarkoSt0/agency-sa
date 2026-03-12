/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.izdavanjestanovaback.dto.KlijentDTO;
import rs.ac.bg.fon.izdavanjestanovaback.dto.MestoDTO;
import rs.ac.bg.fon.izdavanjestanovaback.model.Klijent;
import rs.ac.bg.fon.izdavanjestanovaback.model.Mesto;

/**
 *
 * @author Marko
 */
// automatski pravi instancu Spring sa @Component
@Component
public class KlijentMapper {
    public KlijentDTO toDTO(Klijent klijent) {
        if (klijent == null) return null;
        return new KlijentDTO(
            klijent.getId(),
            klijent.getIme(),
            klijent.getPrezime(),
            klijent.getStarost(),
            klijent.getBrojTelefona(),
            klijent.getEmail(),
            klijent.getIdMesto() != null 
                    ? new MestoDTO(klijent.getIdMesto().getId(), 
                            klijent.getIdMesto().getNazivMesta(), 
                            klijent.getIdMesto().getOpstina()) : null
        );
    }
    
    public Klijent toEntity(KlijentDTO dto) {
        if (dto == null) return null;

        Klijent klijent = new Klijent();
        klijent.setId(dto.getId());
        klijent.setIme(dto.getIme());
        klijent.setPrezime(dto.getPrezime());
        klijent.setStarost(dto.getStarost());
        klijent.setBrojTelefona(dto.getBrojTelefona());
        klijent.setEmail(dto.getEmail());
        
        if(dto.getMesto() != null){
            klijent.setIdMesto(new Mesto(dto.getMesto().getId(), dto.getMesto().getNazivMesta(), dto.getMesto().getOpstina()));
        }

        return klijent;
    }
    
    public void updateEntityFromDTO(KlijentDTO dto, Klijent klijent) {
        if (dto == null || klijent == null) return;
        klijent.setIme(dto.getIme());
        klijent.setPrezime(dto.getPrezime());
        klijent.setStarost(dto.getStarost());
        klijent.setBrojTelefona(dto.getBrojTelefona());
        klijent.setEmail(dto.getEmail());
    }
}
