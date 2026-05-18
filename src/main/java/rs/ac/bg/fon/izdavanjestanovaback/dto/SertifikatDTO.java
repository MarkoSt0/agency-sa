/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Marko
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SertifikatDTO {
    private Long idSertifikat;
    private String nazivSertifikata;
    private String izdavaoc;
    private Date datumSticanja;
    private Long agent;
}
