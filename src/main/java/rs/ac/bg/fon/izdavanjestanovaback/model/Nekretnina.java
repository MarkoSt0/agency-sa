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
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import rs.ac.bg.fon.izdavanjestanovaback.enums.StatusNekretnine;
import rs.ac.bg.fon.izdavanjestanovaback.enums.TipGrejanja;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "nekretnina")
@NamedQueries({
    @NamedQuery(name = "Nekretnina.findAll", query = "SELECT n FROM Nekretnina n"),
    @NamedQuery(name = "Nekretnina.findByIdNekretnina", query = "SELECT n FROM Nekretnina n WHERE n.idNekretnina = :idNekretnina"),
    @NamedQuery(name = "Nekretnina.findByAdresa", query = "SELECT n FROM Nekretnina n WHERE n.adresa = :adresa"),
    @NamedQuery(name = "Nekretnina.findByPovrsina", query = "SELECT n FROM Nekretnina n WHERE n.povrsina = :povrsina"),
    @NamedQuery(name = "Nekretnina.findBySprat", query = "SELECT n FROM Nekretnina n WHERE n.sprat = :sprat"),
    @NamedQuery(name = "Nekretnina.findByBrojSoba", query = "SELECT n FROM Nekretnina n WHERE n.brojSoba = :brojSoba"),
    @NamedQuery(name = "Nekretnina.findByGodinaIzgradnje", query = "SELECT n FROM Nekretnina n WHERE n.godinaIzgradnje = :godinaIzgradnje"),
    @NamedQuery(name = "Nekretnina.findByOpis", query = "SELECT n FROM Nekretnina n WHERE n.opis = :opis")})
public class Nekretnina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idNekretnina")
    private Long idNekretnina;
    @Column(name = "adresa")
    private String adresa;
    @Column(name = "povrsina")
    private String povrsina;
    @Column(name = "sprat")
    private String sprat;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "brojSoba")
    private Double brojSoba;
    @Column(name = "godinaIzgradnje")
    private Integer godinaIzgradnje;
    @Column(name = "opis")
    private String opis;
    @OneToMany(mappedBy = "idNekretnina", fetch = FetchType.EAGER)
    private Collection<StavkaIzdavanja> stavkaIzdavanjaCollection;
    @JoinColumn(name = "idTipaNekretnine", referencedColumnName = "idTipaNekretnine")
    @ManyToOne
    private TipNekretnine tipNekretnine;
    
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "tipGrejanja")
    private TipGrejanja tipGrejanja;
    
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "statusNekretnine")
    private StatusNekretnine statusNekretnine;

    public Nekretnina() {
    }

    public Nekretnina(Long idNekretnina) {
        this.idNekretnina = idNekretnina;
    }

    public Long getIdNekretnina() {
        return idNekretnina;
    }

    public void setIdNekretnina(Long idNekretnina) {
        this.idNekretnina = idNekretnina;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getPovrsina() {
        return povrsina;
    }

    public void setPovrsina(String povrsina) {
        this.povrsina = povrsina;
    }

    public String getSprat() {
        return sprat;
    }

    public void setSprat(String sprat) {
        this.sprat = sprat;
    }

    public Double getBrojSoba() {
        return brojSoba;
    }

    public void setBrojSoba(Double brojSoba) {
        this.brojSoba = brojSoba;
    }

    public Integer getGodinaIzgradnje() {
        return godinaIzgradnje;
    }

    public void setGodinaIzgradnje(Integer godinaIzgradnje) {
        this.godinaIzgradnje = godinaIzgradnje;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Collection<StavkaIzdavanja> getStavkaIzdavanjaCollection() {
        return stavkaIzdavanjaCollection;
    }

    public void setStavkaIzdavanjaCollection(Collection<StavkaIzdavanja> stavkaIzdavanjaCollection) {
        this.stavkaIzdavanjaCollection = stavkaIzdavanjaCollection;
    }

    public TipNekretnine getIdTipaNekretnine() {
        return tipNekretnine;
    }

    public void setIdTipaNekretnine(TipNekretnine tipNekretnine) {
        this.tipNekretnine = tipNekretnine;
    }

    public StatusNekretnine getStatusNekretnine() {
        return statusNekretnine;
    }

    public void setStatusNekretnine(StatusNekretnine statusNekretnine) {
        this.statusNekretnine = statusNekretnine;
    }

    public TipNekretnine getTipNekretnine() {
        return tipNekretnine;
    }

    public void setTipGrejanja(TipGrejanja tipGrejanja) {
        this.tipGrejanja = tipGrejanja;
    }

    public TipGrejanja getTipGrejanja() {
        return tipGrejanja;
    }

    public void setTipNekretnine(TipNekretnine tipNekretnine) {
        this.tipNekretnine = tipNekretnine;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNekretnina != null ? idNekretnina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nekretnina)) {
            return false;
        }
        Nekretnina other = (Nekretnina) object;
        if ((this.idNekretnina == null && other.idNekretnina != null) || (this.idNekretnina != null && !this.idNekretnina.equals(other.idNekretnina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.ac.bg.fon.izdavanjestanovaback.model.Nekretnina[ idNekretnina=" + idNekretnina + " ]";
    }
    
}
