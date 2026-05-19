/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import lombok.*;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "sertifikat")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Sertifikat implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idSertifikat;
    
    @Column
    private String nazivSertifikata;
    
    @Column
    private String izdavalac;
    
    @Temporal(TemporalType.DATE)
    @Column
    private Date datumSticanja;
    
    @JoinColumn(name = "idAgent", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Agent agent; 
}
