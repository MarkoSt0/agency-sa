/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.mapper;


import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

import rs.ac.bg.fon.izdavanjestanovaback.dto.StavkaIzdavanjaDTO;
import rs.ac.bg.fon.izdavanjestanovaback.model.StavkaIzdavanja;

/**
 *
 * @author Marko
 */
@Component
public class StavkaIzdavanjaMapper {

    private final NekretninaMapper nekretninaMapper = new NekretninaMapper();

    public StavkaIzdavanjaDTO toDTO(StavkaIzdavanja stavka) {
        if (stavka == null) return null;

        StavkaIzdavanjaDTO dto = new StavkaIzdavanjaDTO();
        dto.setRb(stavka.getRb());
        dto.setDatumPocetkaIzdavanja(stavka.getDatumPocetkaIzdavanja());
        dto.setDatumZavrsetkaIzdavanja(stavka.getDatumZavrsetkaIzdavanja());
        dto.setMesecnaKirija(stavka.getMesecnaKirija());
        dto.setIznosDepozita(stavka.getIznosDepozita());
        dto.setIzdavanje(stavka.getIdIzdavanje() != null ? stavka.getIdIzdavanje().getIdIzdavanje() : null);        
        dto.setNekretnina(nekretninaMapper.toDTO(stavka.getIdNekretnina()));

        return dto;
    }

    public StavkaIzdavanja toEntity(StavkaIzdavanjaDTO dto) {
        if (dto == null) return null;

        StavkaIzdavanja stavka = new StavkaIzdavanja();
        stavka.setRb(dto.getRb());
        stavka.setDatumPocetkaIzdavanja(dto.getDatumPocetkaIzdavanja());
        stavka.setDatumZavrsetkaIzdavanja(dto.getDatumZavrsetkaIzdavanja());
        stavka.setMesecnaKirija(dto.getMesecnaKirija());
        stavka.setIznosDepozita(dto.getIznosDepozita());
//        stavka.setIdIzdavanje(stavka.getIdIzdavanje());
        stavka.setIdNekretnina(nekretninaMapper.toEntity(dto.getNekretnina()));

        return stavka;
    }

    // ove dve metode koristim u IzadavanjeMapper da bi lakse preveo listu u drugu i obrnuto - VAZNE!
    public List<StavkaIzdavanjaDTO> toDTOList(List<StavkaIzdavanja> stavke) {
        return stavke.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<StavkaIzdavanja> toEntityList(List<StavkaIzdavanjaDTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
