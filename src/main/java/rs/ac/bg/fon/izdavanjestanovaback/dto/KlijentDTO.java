/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.dto;

import java.io.Serializable;

/**
 *
 * @author Marko
 */
public class KlijentDTO implements Serializable {

    private long id;
    private String ime;
    private String prezime;
    private int starost;
    private String brojTelefona;
    private String email;
    private MestoDTO mesto;

    public KlijentDTO() {}

    public KlijentDTO(long id, String ime, String prezime, int starost, String brojTelefona, String email, MestoDTO mesto) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.starost = starost;
        this.brojTelefona = brojTelefona;
        this.email = email;
        this.mesto = mesto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getStarost() {
        return starost;
    }

    public void setStarost(int starost) {
        this.starost = starost;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MestoDTO getMesto() {
        return mesto;
    }

    public void setMesto(MestoDTO mesto) {
        this.mesto = mesto;
    }
    
    
}
