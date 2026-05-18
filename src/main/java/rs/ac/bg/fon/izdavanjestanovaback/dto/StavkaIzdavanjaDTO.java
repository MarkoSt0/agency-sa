/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.dto;

import java.util.Date;
import lombok.*;

/**
 *
 * @author Marko
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StavkaIzdavanjaDTO {
    private Long rb;
    private Date datumPocetkaIzdavanja;
    private Date datumZavrsetkaIzdavanja;
    private Double mesecnaKirija;
    private Double iznosDepozita;
    private Long izdavanje;
    private NekretninaDTO nekretnina;
}