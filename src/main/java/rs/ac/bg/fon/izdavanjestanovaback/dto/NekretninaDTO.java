/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.dto;

import rs.ac.bg.fon.izdavanjestanovaback.enums.StatusNekretnine;
import rs.ac.bg.fon.izdavanjestanovaback.enums.TipGrejanja;

/**
 *
 * @author Marko
 */
public class NekretninaDTO {
    private Long idNekretnina;
    private String adresa;
    private String povrsina;
    private String sprat;
    private Double brojSoba;
    private Integer godinaIzgradnje;
    private String opis;
    private TipNekretnineDTO tipNekretnine;
    private TipGrejanja tipGrejanja;
    private StatusNekretnine statusNekretnine;

    public NekretninaDTO() {
    }

    public NekretninaDTO(Long idNekretnina, String adresa, String povrsina, String sprat, Double brojSoba,
            Integer godinaIzgradnje, String opis, TipNekretnineDTO tipNekretnine, TipGrejanja tipGrejanja,
            StatusNekretnine statusNekretnine) {
        this.idNekretnina = idNekretnina;
        this.adresa = adresa;
        this.povrsina = povrsina;
        this.sprat = sprat;
        this.brojSoba = brojSoba;
        this.godinaIzgradnje = godinaIzgradnje;
        this.opis = opis;
        this.tipNekretnine = tipNekretnine;
        this.tipGrejanja = tipGrejanja;
        this.statusNekretnine = statusNekretnine;
    }

    public Long getIdNekretnina() {
        return idNekretnina;
    }

    public void setIdNekretnina(Long idNekretnina) {
        this.idNekretnina = idNekretnina;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getPovrsina() {
        return povrsina;
    }

    public void setPovrsina(String povrsina) {
        this.povrsina = povrsina;
    }

    public String getSprat() {
        return sprat;
    }

    public void setSprat(String sprat) {
        this.sprat = sprat;
    }

    public Double getBrojSoba() {
        return brojSoba;
    }

    public void setBrojSoba(Double brojSoba) {
        this.brojSoba = brojSoba;
    }

    public Integer getGodinaIzgradnje() {
        return godinaIzgradnje;
    }

    public void setGodinaIzgradnje(Integer godinaIzgradnje) {
        this.godinaIzgradnje = godinaIzgradnje;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public TipNekretnineDTO getTipNekretnine() {
        return tipNekretnine;
    }

    public void setTipNekretnine(TipNekretnineDTO tipNekretnine) {
        this.tipNekretnine = tipNekretnine;
    }

    public TipGrejanja getTipGrejanja() {
        return tipGrejanja;
    }

    public void setTipGrejanja(TipGrejanja tipGrejanja) {
        this.tipGrejanja = tipGrejanja;
    }

    public StatusNekretnine getStatusNekretnine() {
        return statusNekretnine;
    }

    public void setStatusNekretnine(StatusNekretnine statusNekretnine) {
        this.statusNekretnine = statusNekretnine;
    }

    
    
}
