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
@Table(name = "mesto")
@NamedQueries({
    @NamedQuery(name = "Mesto.findAll", query = "SELECT m FROM Mesto m"),
    @NamedQuery(name = "Mesto.findById", query = "SELECT m FROM Mesto m WHERE m.id = :id"),
    @NamedQuery(name = "Mesto.findByNazivMesta", query = "SELECT m FROM Mesto m WHERE m.nazivMesta = :nazivMesta"),
    @NamedQuery(name = "Mesto.findByOpstina", query = "SELECT m FROM Mesto m WHERE m.opstina = :opstina")})
public class Mesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "nazivMesta")
    private String nazivMesta;
    @Column(name = "opstina")
    private String opstina;
    @OneToMany(mappedBy = "idMesto")
    private Collection<Klijent> klijentCollection;

    public Mesto() {
    }

    public Mesto(Long id) {
        this.id = id;
    }

    public Mesto(Long id, String nazivMesta) {
        this.id = id;
        this.nazivMesta = nazivMesta;
    }

    public Mesto(Long id, String nazivMesta, String opstina) {
        this.id = id;
        this.nazivMesta = nazivMesta;
        this.opstina = opstina;
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

    public Collection<Klijent> getKlijentCollection() {
        return klijentCollection;
    }

    public void setKlijentCollection(Collection<Klijent> klijentCollection) {
        this.klijentCollection = klijentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesto)) {
            return false;
        }
        Mesto other = (Mesto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.ac.bg.fon.izdavanjestanovaback.model.Mesto[ id=" + id + " ]";
    }
    
}
