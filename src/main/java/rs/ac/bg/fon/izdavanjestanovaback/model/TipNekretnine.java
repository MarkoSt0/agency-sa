/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

    public Collection<Nekretnina> getNekretninaCollection() {
        return nekretninaCollection;
    }

    public void setNekretninaCollection(Collection<Nekretnina> nekretninaCollection) {
        this.nekretninaCollection = nekretninaCollection;
    }

    @Override
    public int hashCode() {
        return (idTipaNekretnine != null ? idTipaNekretnine.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TipNekretnine)) return false;
        TipNekretnine other = (TipNekretnine) object;
        return (this.idTipaNekretnine != null || other.idTipaNekretnine == null) &&
               (this.idTipaNekretnine == null || this.idTipaNekretnine.equals(other.idTipaNekretnine));
    }

    @Override
    public String toString() {
        return "TipNekretnine[ idTipaNekretnine=" + idTipaNekretnine + ", nazivTipaNekretnine=" + nazivTipaNekretnine + " ]";
    }
}
