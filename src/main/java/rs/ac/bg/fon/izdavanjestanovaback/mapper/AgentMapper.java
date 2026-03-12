/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.izdavanjestanovaback.dto.AgentDTO;
import rs.ac.bg.fon.izdavanjestanovaback.model.Agent;

/**
 *
 * @author Marko
 */
@Component
public class AgentMapper {

    public AgentDTO toDTO(Agent agent) {
        if (agent == null) return null;

        return new AgentDTO(
            agent.getId(),
            agent.getBrojLicence(),
            agent.getDatumIstekaLicence(),
            agent.getIme(),
            agent.getPrezime(),
            agent.getKorisnickoIme(),
            agent.getSifra()
        );
    }
    
    public Agent toEntity(AgentDTO dto) {
        if (dto == null) return null;

        Agent agent = new Agent();
        agent.setId(dto.getId());
        agent.setBrojLicence(dto.getBrojLicence());
        agent.setDatumIstekaLicence(dto.getDatumIstekaLicence());
        agent.setIme(dto.getIme());
        agent.setPrezime(dto.getPrezime());
        agent.setKorisnickoIme(dto.getKorisnickoIme());
        agent.setSifra(dto.getSifra());
        return agent;
    }
    
    public void updateEntityFromDTO(AgentDTO dto, Agent agent) {
        if (dto == null || agent == null) return;
        agent.setIme(dto.getIme());
        agent.setPrezime(dto.getPrezime());
        agent.setBrojLicence(dto.getBrojLicence());
        agent.setDatumIstekaLicence(dto.getDatumIstekaLicence());
        agent.setKorisnickoIme(dto.getKorisnickoIme());
        agent.setSifra(dto.getSifra());
    }
}
