/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.dto;

/**
 *
 * @author Marko
 */

public class TipNekretnineDTO {

    private Long idTipaNekretnine;
    private String nazivTipaNekretnine;

    public TipNekretnineDTO() {
    }

    public TipNekretnineDTO(Long idTipaNekretnine, String nazivNekretnine) {
        this.idTipaNekretnine = idTipaNekretnine;
        this.nazivTipaNekretnine = nazivNekretnine;
    }

    public Long getIdTipaNekretnine() {
        return idTipaNekretnine;
    }

    public void setIdTipaNekretnine(Long idTipaNekretnine) {
        this.idTipaNekretnine = idTipaNekretnine;
    }

    public String getNazivTipaNekretnine() {
        return nazivTipaNekretnine;
    }

    public void setNazivTipaNekretnine(String nazivTipaNekretnine) {
        this.nazivTipaNekretnine = nazivTipaNekretnine;
    }
}
