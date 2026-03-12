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
public class AgentDTO {
    private Long id;
    private String brojLicence;
    private Date datumIstekaLicence;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String sifra;

    public AgentDTO() {
    }

    public AgentDTO(Long id, String brojLicence, Date datumIstekaLicence, String ime, String prezime, String korisnickoIme, String sifra) {
        this.id = id;
        this.brojLicence = brojLicence;
        this.datumIstekaLicence = datumIstekaLicence;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrojLicence() {
        return brojLicence;
    }

    public void setBrojLicence(String brojLicence) {
        this.brojLicence = brojLicence;
    }

    public Date getDatumIstekaLicence() {
        return datumIstekaLicence;
    }

    public void setDatumIstekaLicence(Date datumIstekaLicence) {
        this.datumIstekaLicence = datumIstekaLicence;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    
}
