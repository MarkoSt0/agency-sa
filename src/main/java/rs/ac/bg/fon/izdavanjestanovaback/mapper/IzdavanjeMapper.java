/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.mapper;

import java.util.ArrayList;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.izdavanjestanovaback.dto.IzdavanjeDTO;
import rs.ac.bg.fon.izdavanjestanovaback.model.Izdavanje;

/**
 *
 * @author Marko
 */
@Component
public class IzdavanjeMapper {

    private final AgentMapper agentMapper = new AgentMapper();
    private final KlijentMapper klijentMapper = new KlijentMapper();
    private final StavkaIzdavanjaMapper stavkaMapper = new StavkaIzdavanjaMapper();

    public IzdavanjeDTO toDTO(Izdavanje izdavanje) {
        if (izdavanje == null) return null;

        IzdavanjeDTO dto = new IzdavanjeDTO();
        dto.setIdIzdavanje(izdavanje.getIdIzdavanje());
        dto.setDatumSklapanjaUgovora(izdavanje.getDatumSklapanjaUgovora());
        dto.setStatusUgovora(izdavanje.getStatusUgovora());
        dto.setNacinPlacanja(izdavanje.getNacinPlacanja());
        dto.setUkupanIznos(izdavanje.getUkupanIznos());
        dto.setNapomena(izdavanje.getNapomena());
        dto.setAgent(agentMapper.toDTO(izdavanje.getIdAgent()));
        dto.setKlijent(klijentMapper.toDTO(izdavanje.getIdKlijent()));

        if (izdavanje.getStavkaIzdavanjaCollection() != null) {
            dto.setStavke(stavkaMapper.toDTOList(
                new ArrayList<>(izdavanje.getStavkaIzdavanjaCollection())
            ));
        }

        return dto;
    }

    public Izdavanje toEntity(IzdavanjeDTO dto) {
        if (dto == null) return null;

        Izdavanje izdavanje = new Izdavanje();
        izdavanje.setIdIzdavanje(dto.getIdIzdavanje());
        izdavanje.setDatumSklapanjaUgovora(dto.getDatumSklapanjaUgovora());
        izdavanje.setStatusUgovora(dto.getStatusUgovora());
        izdavanje.setNacinPlacanja(dto.getNacinPlacanja());
        izdavanje.setUkupanIznos(dto.getUkupanIznos());
        izdavanje.setNapomena(dto.getNapomena());
        izdavanje.setIdAgent(agentMapper.toEntity(dto.getAgent()));
        izdavanje.setIdKlijent(klijentMapper.toEntity(dto.getKlijent()));

        if (dto.getStavke() != null) {
            izdavanje.setStavkaIzdavanjaCollection(stavkaMapper.toEntityList(dto.getStavke()));
        }

        return izdavanje;
    }

    public void updateEntityFromDTO(IzdavanjeDTO dto, Izdavanje izdavanje) {
    if (dto == null || izdavanje == null) return;
        izdavanje.setDatumSklapanjaUgovora(dto.getDatumSklapanjaUgovora());
        izdavanje.setStatusUgovora(dto.getStatusUgovora());
        izdavanje.setNacinPlacanja(dto.getNacinPlacanja());
        izdavanje.setUkupanIznos(dto.getUkupanIznos());
        izdavanje.setNapomena(dto.getNapomena());
    }
    
}
