/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.dto;

import java.util.Date;

/**
 *
 * @author Marko
 */

public class StavkaIzdavanjaDTO {

    private Long rb;
    private Date datumPocetkaIzdavanja;
    private Date datumZavrsetkaIzdavanja;
    private Double mesecnaKirija;
    private Double iznosDepozita;
    private Long izdavanje;
    private NekretninaDTO nekretnina;

    public StavkaIzdavanjaDTO() {
    }

    public Long getRb() {
        return rb;
    }

    public void setRb(Long rb) {
        this.rb = rb;
    }

    public Date getDatumPocetkaIzdavanja() {
        return datumPocetkaIzdavanja;
    }

    public void setDatumPocetkaIzdavanja(Date datumPocetkaIzdavanja) {
        this.datumPocetkaIzdavanja = datumPocetkaIzdavanja;
    }

    public Date getDatumZavrsetkaIzdavanja() {
        return datumZavrsetkaIzdavanja;
    }

    public void setDatumZavrsetkaIzdavanja(Date datumZavrsetkaIzdavanja) {
        this.datumZavrsetkaIzdavanja = datumZavrsetkaIzdavanja;
    }

    public Double getMesecnaKirija() {
        return mesecnaKirija;
    }

    public void setMesecnaKirija(Double mesecnaKirija) {
        this.mesecnaKirija = mesecnaKirija;
    }

    public Double getIznosDepozita() {
        return iznosDepozita;
    }

    public void setIznosDepozita(Double iznosDepozita) {
        this.iznosDepozita = iznosDepozita;
    }

    public Long getIzdavanje() {
        return izdavanje;
    }

    public void setIzdavanje(Long izdavanje) {
        this.izdavanje = izdavanje;
    }

    public NekretninaDTO getNekretnina() {
        return nekretnina;
    }

    public void setNekretnina(NekretninaDTO nekretnina) {
        this.nekretnina = nekretnina;
    }
}