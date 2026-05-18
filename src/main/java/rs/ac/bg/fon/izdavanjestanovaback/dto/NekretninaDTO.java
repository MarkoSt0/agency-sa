/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.dto;

import rs.ac.bg.fon.izdavanjestanovaback.enums.StatusNekretnine;
import rs.ac.bg.fon.izdavanjestanovaback.enums.TipGrejanja;
import lombok.*;

/**
 *
 * @author Marko
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
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
}
