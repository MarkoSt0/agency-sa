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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "klijent")
@NamedQueries({
    @NamedQuery(name = "Klijent.findAll", query = "SELECT k FROM Klijent k"),
    @NamedQuery(name = "Klijent.findById", query = "SELECT k FROM Klijent k WHERE k.id = :id"),
    @NamedQuery(name = "Klijent.findByIme", query = "SELECT k FROM Klijent k WHERE k.ime = :ime"),
    @NamedQuery(name = "Klijent.findByPrezime", query = "SELECT k FROM Klijent k WHERE k.prezime = :prezime"),
    @NamedQuery(name = "Klijent.findByStarost", query = "SELECT k FROM Klijent k WHERE k.starost = :starost"),
    @NamedQuery(name = "Klijent.findByBrojTelefona", query = "SELECT k FROM Klijent k WHERE k.brojTelefona = :brojTelefona"),
    @NamedQuery(name = "Klijent.findByEmail", query = "SELECT k FROM Klijent k WHERE k.email = :email")})
public class Klijent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "ime")
    private String ime;
    @Column(name = "prezime")
    private String prezime;
    @Column(name = "starost")
    private Integer starost;
    @Column(name = "brojTelefona")
    private String brojTelefona;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "idKlijent")
    private Collection<Izdavanje> izdavanjeCollection;
    @JoinColumn(name = "idMesto", referencedColumnName = "id")
    @ManyToOne
    private Mesto idMesto;

    public Klijent() {
    }

    public Klijent(Long id) {
        this.id = id;
    }

    public Klijent(Long id, String ime) {
        this.id = id;
        this.ime = ime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Integer getStarost() {
        return starost;
    }

    public void setStarost(Integer starost) {
        this.starost = starost;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Izdavanje> getIzdavanjeCollection() {
        return izdavanjeCollection;
    }

    public void setIzdavanjeCollection(Collection<Izdavanje> izdavanjeCollection) {
        this.izdavanjeCollection = izdavanjeCollection;
    }

    public Mesto getIdMesto() {
        return idMesto;
    }

    public void setIdMesto(Mesto idMesto) {
        this.idMesto = idMesto;
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
        if (!(object instanceof Klijent)) {
            return false;
        }
        Klijent other = (Klijent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.ac.bg.fon.izdavanjestanovaback.model.Klijent[ id=" + id + " ]";
    }
    
}
