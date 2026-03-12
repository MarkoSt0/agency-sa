/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.dto;

/**
 *
 * @author Marko
 */

public class MestoDTO {
    private Long id;
    private String nazivMesta;
    private String opstina;

    public MestoDTO(Long id, String nazivMesta, String opstina) {
        this.id = id;
        this.nazivMesta = nazivMesta;
        this.opstina = opstina;
    }

    public MestoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazivMesta() {
        return nazivMesta;
    }

    public void setNazivMesta(String nazivMesta) {
        this.nazivMesta = nazivMesta;
    }

    public String getOpstina() {
        return opstina;
    }

    public void setOpstina(String opstina) {
        this.opstina = opstina;
    }
    
    
}
