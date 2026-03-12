/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "agent")
@NamedQueries({
    @NamedQuery(name = "Agent.findAll", query = "SELECT a FROM Agent a"),
    @NamedQuery(name = "Agent.findById", query = "SELECT a FROM Agent a WHERE a.id = :id"),
    @NamedQuery(name = "Agent.findByBrojLicence", query = "SELECT a FROM Agent a WHERE a.brojLicence = :brojLicence"),
    @NamedQuery(name = "Agent.findByDatumIstekaLicence", query = "SELECT a FROM Agent a WHERE a.datumIstekaLicence = :datumIstekaLicence"),
    @NamedQuery(name = "Agent.findByIme", query = "SELECT a FROM Agent a WHERE a.ime = :ime"),
    @NamedQuery(name = "Agent.findByPrezime", query = "SELECT a FROM Agent a WHERE a.prezime = :prezime"),
    @NamedQuery(name = "Agent.findByKorisnickoIme", query = "SELECT a FROM Agent a WHERE a.korisnickoIme = :korisnickoIme"),
    @NamedQuery(name = "Agent.findBySifra", query = "SELECT a FROM Agent a WHERE a.sifra = :sifra")})
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "brojLicence")
    private String brojLicence;
    @Column(name = "datumIstekaLicence")
    @Temporal(TemporalType.DATE)
    private Date datumIstekaLicence;
    @Basic(optional = false)
    @Column(name = "ime")
    private String ime;
    @Column(name = "prezime")
    private String prezime;
    @Column(name = "korisnickoIme")
    private String korisnickoIme;
    @Column(name = "sifra")
    private String sifra;
    @OneToMany(mappedBy = "idAgent")
    private Collection<Izdavanje> izdavanjeCollection;

    public Agent() {
    }

    public Agent(Long id) {
        this.id = id;
    }

    public Agent(Long id, String ime) {
        this.id = id;
        this.ime = ime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrojLicence() {
        return brojLicence;
    }

    public void setBrojLicence(String brojLicence) {
        this.brojLicence = brojLicence;
    }

    public Date getDatumIstekaLicence() {
        return datumIstekaLicence;
    }

    public void setDatumIstekaLicence(Date datumIstekaLicence) {
        this.datumIstekaLicence = datumIstekaLicence;
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

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public Collection<Izdavanje> getIzdavanjeCollection() {
        return izdavanjeCollection;
    }

    public void setIzdavanjeCollection(Collection<Izdavanje> izdavanjeCollection) {
        this.izdavanjeCollection = izdavanjeCollection;
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
        if (!(object instanceof Agent)) {
            return false;
        }
        Agent other = (Agent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.ac.bg.fon.izdavanjestanovaback.model.Agent[ id=" + id + " ]";
    }
    
}
