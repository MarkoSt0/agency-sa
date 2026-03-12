/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "izdavanje")
@NamedQueries({
    @NamedQuery(name = "Izdavanje.findAll", query = "SELECT i FROM Izdavanje i"),
    @NamedQuery(name = "Izdavanje.findByIdIzdavanje", query = "SELECT i FROM Izdavanje i WHERE i.idIzdavanje = :idIzdavanje"),
    @NamedQuery(name = "Izdavanje.findByDatumSklapanjaUgovora", query = "SELECT i FROM Izdavanje i WHERE i.datumSklapanjaUgovora = :datumSklapanjaUgovora"),
    @NamedQuery(name = "Izdavanje.findByStatusUgovora", query = "SELECT i FROM Izdavanje i WHERE i.statusUgovora = :statusUgovora"),
    @NamedQuery(name = "Izdavanje.findByNacinPlacanja", query = "SELECT i FROM Izdavanje i WHERE i.nacinPlacanja = :nacinPlacanja"),
    @NamedQuery(name = "Izdavanje.findByUkupanIznos", query = "SELECT i FROM Izdavanje i WHERE i.ukupanIznos = :ukupanIznos"),
    @NamedQuery(name = "Izdavanje.findByNapomena", query = "SELECT i FROM Izdavanje i WHERE i.napomena = :napomena")})
public class Izdavanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idIzdavanje")
    private Long idIzdavanje;
    @Column(name = "datumSklapanjaUgovora")
    @Temporal(TemporalType.DATE)
    private Date datumSklapanjaUgovora;
    @Column(name = "statusUgovora")
    private String statusUgovora;
    @Column(name = "nacinPlacanja")
    private String nacinPlacanja;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ukupanIznos")
    private Double ukupanIznos;
    @Column(name = "napomena")
    private String napomena;
    @JoinColumn(name = "idAgent", referencedColumnName = "id")
    @ManyToOne
    private Agent idAgent;
    @JoinColumn(name = "idKlijent", referencedColumnName = "id")
    @ManyToOne
    private Klijent idKlijent;
    @OneToMany(mappedBy = "idIzdavanje", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<StavkaIzdavanja> stavkaIzdavanjaCollection;

    public Izdavanje() {
    }

    public Izdavanje(Long idIzdavanje) {
        this.idIzdavanje = idIzdavanje;
    }

    public Long getIdIzdavanje() {
        return idIzdavanje;
    }

    public void setIdIzdavanje(Long idIzdavanje) {
        this.idIzdavanje = idIzdavanje;
    }

    public Date getDatumSklapanjaUgovora() {
        return datumSklapanjaUgovora;
    }

    public void setDatumSklapanjaUgovora(Date datumSklapanjaUgovora) {
        this.datumSklapanjaUgovora = datumSklapanjaUgovora;
    }

    public String getStatusUgovora() {
        return statusUgovora;
    }

    public void setStatusUgovora(String statusUgovora) {
        this.statusUgovora = statusUgovora;
    }

    public String getNacinPlacanja() {
        return nacinPlacanja;
    }

    public void setNacinPlacanja(String nacinPlacanja) {
        this.nacinPlacanja = nacinPlacanja;
    }

    public Double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(Double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public Agent getAgent() {
        return idAgent;
    }

    public void setAgent(Agent idAgent) {
        this.idAgent = idAgent;
    }

    public Klijent getKlijent() {
        return idKlijent;
    }

    public void setKlijent(Klijent idKlijent) {
        this.idKlijent = idKlijent;
    }

    public Collection<StavkaIzdavanja> getStavkaIzdavanjaCollection() {
        return stavkaIzdavanjaCollection;
    }

    public void setStavkaIzdavanjaCollection(Collection<StavkaIzdavanja> stavkaIzdavanjaCollection) {
        this.stavkaIzdavanjaCollection = stavkaIzdavanjaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIzdavanje != null ? idIzdavanje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Izdavanje)) {
            return false;
        }
        Izdavanje other = (Izdavanje) object;
        if ((this.idIzdavanje == null && other.idIzdavanje != null) || (this.idIzdavanje != null && !this.idIzdavanje.equals(other.idIzdavanje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.ac.bg.fon.izdavanjestanovaback.model.Izdavanje[ idIzdavanje=" + idIzdavanje + " ]";
    }
    
}
