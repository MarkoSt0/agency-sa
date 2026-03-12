/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Marko
 */

public class IzdavanjeDTO {

    private Long idIzdavanje;
    private Date datumSklapanjaUgovora;
    private String statusUgovora;
    private String nacinPlacanja;
    private Double ukupanIznos;
    private String napomena;
    private AgentDTO agent;
    private KlijentDTO klijent;
    private List<StavkaIzdavanjaDTO> stavke;

    public IzdavanjeDTO() {
    }

    public Long getIdIzdavanje() {
        return idIzdavanje;
    }

    public void setIdIzdavanje(Long idIzdavanje) {
        this.idIzdavanje = idIzdavanje;
    }

    public Date getDatumSklapanjaUgovora() {
        return datumSklapanjaUgovora;
    }

    public void setDatumSklapanjaUgovora(Date datumSklapanjaUgovora) {
        this.datumSklapanjaUgovora = datumSklapanjaUgovora;
    }

    public String getStatusUgovora() {
        return statusUgovora;
    }

    public void setStatusUgovora(String statusUgovora) {
        this.statusUgovora = statusUgovora;
    }

    public String getNacinPlacanja() {
        return nacinPlacanja;
    }

    public void setNacinPlacanja(String nacinPlacanja) {
        this.nacinPlacanja = nacinPlacanja;
    }

    public Double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(Double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public AgentDTO getAgent() {
        return agent;
    }

    public void setAgent(AgentDTO agent) {
        this.agent = agent;
    }

    public KlijentDTO getKlijent() {
        return klijent;
    }

    public void setKlijent(KlijentDTO klijent) {
        this.klijent = klijent;
    }

    public List<StavkaIzdavanjaDTO> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaIzdavanjaDTO> stavke) {
        this.stavke = stavke;
    }
}
