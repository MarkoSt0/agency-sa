/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.izdavanjestanovaback.dto.NekretninaDTO;
import rs.ac.bg.fon.izdavanjestanovaback.model.Nekretnina;

/**
 *
 * @author Marko
 */
@Component
public class NekretninaMapper {

    private static final TipNekretnineMapper tipMapper = new TipNekretnineMapper();

    public NekretninaDTO toDTO(Nekretnina nekretnina) {
        if (nekretnina == null) return null;

        NekretninaDTO dto = new NekretninaDTO();
        dto.setIdNekretnina(nekretnina.getIdNekretnina());
        dto.setAdresa(nekretnina.getAdresa());
        dto.setPovrsina(nekretnina.getPovrsina());
        dto.setSprat(nekretnina.getSprat());
        dto.setBrojSoba(nekretnina.getBrojSoba());
        dto.setGodinaIzgradnje(nekretnina.getGodinaIzgradnje());
        dto.setOpis(nekretnina.getOpis());
        dto.setTipNekretnine(tipMapper.toDTO(nekretnina.getTipNekretnine()));
        dto.setTipGrejanja(nekretnina.getTipGrejanja());
        dto.setStatusNekretnine(nekretnina.getStatusNekretnine());

        return dto;
    }

    public Nekretnina toEntity(NekretninaDTO dto) {
        if (dto == null) return null;

        Nekretnina nekretnina = new Nekretnina();
        nekretnina.setIdNekretnina(dto.getIdNekretnina());
        nekretnina.setAdresa(dto.getAdresa());
        nekretnina.setPovrsina(dto.getPovrsina());
        nekretnina.setSprat(dto.getSprat());
        nekretnina.setBrojSoba(dto.getBrojSoba());
        nekretnina.setGodinaIzgradnje(dto.getGodinaIzgradnje());
        nekretnina.setOpis(dto.getOpis());
        nekretnina.setTipNekretnine(tipMapper.toEntity(dto.getTipNekretnine()));
        nekretnina.setTipGrejanja(dto.getTipGrejanja());
        nekretnina.setStatusNekretnine(dto.getStatusNekretnine());

        return nekretnina;
    }
    
    public void updateEntityFromDTO(NekretninaDTO dto, Nekretnina nekretnina) {
        if (dto == null || nekretnina == null) return;
        nekretnina.setAdresa(dto.getAdresa());
        nekretnina.setPovrsina(dto.getPovrsina());
        nekretnina.setSprat(dto.getSprat());
        nekretnina.setBrojSoba(dto.getBrojSoba());
        nekretnina.setGodinaIzgradnje(dto.getGodinaIzgradnje());
        nekretnina.setOpis(dto.getOpis());
        nekretnina.setTipGrejanja(dto.getTipGrejanja());
        nekretnina.setStatusNekretnine(dto.getStatusNekretnine());
    }
}
