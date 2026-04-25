/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.*;
import lombok.Data;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "tip_nekretnine")
@NamedQueries({
    @NamedQuery(name = "TipNekretnine.findAll", query = "SELECT t FROM TipNekretnine t"),
    @NamedQuery(name = "TipNekretnine.findByIdTipaNekretnine", query = "SELECT t FROM TipNekretnine t WHERE t.idTipaNekretnine = :idTipaNekretnine"),
    @NamedQuery(name = "TipNekretnine.findByNazivTipaNekretnine", query = "SELECT t FROM TipNekretnine t WHERE t.nazivTipaNekretnine = :nazivTipaNekretnine")})
@Data
public class TipNekretnine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipaNekretnine")
    private Long idTipaNekretnine;

    @Basic(optional = false)
    @Column(name = "nazivTipaNekretnine")
    private String nazivTipaNekretnine;

    @OneToMany(mappedBy = "tipNekretnine")
    private Collection<Nekretnina> nekretninaCollection;

    public TipNekretnine() {
    }

    public TipNekretnine(Long idTipaNekretnine, String nazivTipaNekretnine) {
        this.idTipaNekretnine = idTipaNekretnine;
        this.nazivTipaNekretnine = nazivTipaNekretnine;
    }
}
