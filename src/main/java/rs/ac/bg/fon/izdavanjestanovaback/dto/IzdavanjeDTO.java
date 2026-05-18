/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.dto;

import java.util.Date;
import java.util.List;
import lombok.*;

/**
 *
 * @author Marko
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
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

}