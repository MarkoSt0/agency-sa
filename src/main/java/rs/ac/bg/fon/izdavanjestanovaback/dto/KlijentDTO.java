/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.dto;

import java.io.Serializable;
import lombok.*;

/**
 *
 * @author Marko
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KlijentDTO implements Serializable {

    private long id;
    private String ime;
    private String prezime;
    private int starost;
    private String brojTelefona;
    private String email;
    private MestoDTO mesto;
}
