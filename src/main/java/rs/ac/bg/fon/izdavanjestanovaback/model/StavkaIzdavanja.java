/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Objects;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "stavka_izdavanja")
@IdClass(StavkaIzdavanjaPK.class)
@NamedQueries({
    @NamedQuery(name = "StavkaIzdavanja.findAll", query = "SELECT s FROM StavkaIzdavanja s"),
    @NamedQuery(name = "StavkaIzdavanja.findByRb", query = "SELECT s FROM StavkaIzdavanja s WHERE s.rb = :rb"),
    @NamedQuery(name = "StavkaIzdavanja.findByDatumPocetkaIzdavanja", query = "SELECT s FROM StavkaIzdavanja s WHERE s.datumPocetkaIzdavanja = :datumPocetkaIzdavanja"),
    @NamedQuery(name = "StavkaIzdavanja.findByDatumZavrsetkaIzdavanja", query = "SELECT s FROM StavkaIzdavanja s WHERE s.datumZavrsetkaIzdavanja = :datumZavrsetkaIzdavanja"),
    @NamedQuery(name = "StavkaIzdavanja.findByMesecnaKirija", query = "SELECT s FROM StavkaIzdavanja s WHERE s.mesecnaKirija = :mesecnaKirija"),
    @NamedQuery(name = "StavkaIzdavanja.findByIznosDepozita", query = "SELECT s FROM StavkaIzdavanja s WHERE s.iznosDepozita = :iznosDepozita")})
public class StavkaIzdavanja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rb")
    private Long rb;
    @Column(name = "datumPocetkaIzdavanja")
    @Temporal(TemporalType.DATE)
    private Date datumPocetkaIzdavanja;
    @Column(name = "datumZavrsetkaIzdavanja")
    @Temporal(TemporalType.DATE)
    private Date datumZavrsetkaIzdavanja;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mesecnaKirija")
    private Double mesecnaKirija;
    @Column(name = "iznosDepozita")
    private Double iznosDepozita;
    @JoinColumn(name = "idIzdavanje", referencedColumnName = "idIzdavanje")
    @ManyToOne
    @Id
    private Izdavanje idIzdavanje;
    @JoinColumn(name = "idNekretnina", referencedColumnName = "idNekretnina")
    @ManyToOne
    private Nekretnina idNekretnina;

    public StavkaIzdavanja() {
    }

    public StavkaIzdavanja(Long rb) {
        this.rb = rb;
    }

    public Long getRb() {
        return rb;
    }

    public void setRb(Long rb) {
        this.rb = rb;
    }

    public Date getDatumPocetkaIzdavanja() {
        return datumPocetkaIzdavanja;
    }

    public void setDatumPocetkaIzdavanja(Date datumPocetkaIzdavanja) {
        this.datumPocetkaIzdavanja = datumPocetkaIzdavanja;
    }

    public Date getDatumZavrsetkaIzdavanja() {
        return datumZavrsetkaIzdavanja;
    }

    public void setDatumZavrsetkaIzdavanja(Date datumZavrsetkaIzdavanja) {
        this.datumZavrsetkaIzdavanja = datumZavrsetkaIzdavanja;
    }

    public Double getMesecnaKirija() {
        return mesecnaKirija;
    }

    public void setMesecnaKirija(Double mesecnaKirija) {
        this.mesecnaKirija = mesecnaKirija;
    }

    public Double getIznosDepozita() {
        return iznosDepozita;
    }

    public void setIznosDepozita(Double iznosDepozita) {
        this.iznosDepozita = iznosDepozita;
    }

    public Izdavanje getIdIzdavanje() {
        return idIzdavanje;
    }

    public void setIdIzdavanje(Izdavanje idIzdavanje) {
        this.idIzdavanje = idIzdavanje;
    }

    public Nekretnina getIdNekretnina() {
        return idNekretnina;
    }

    public void setIdNekretnina(Nekretnina idNekretnina) {
        this.idNekretnina = idNekretnina;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.rb);
        hash = 19 * hash + Objects.hashCode(this.idIzdavanje);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StavkaIzdavanja other = (StavkaIzdavanja) obj;
        if (!Objects.equals(this.rb, other.rb)) {
            return false;
        }
        return Objects.equals(this.idIzdavanje, other.idIzdavanje);
    }

    @Override
    public String toString() {
        return "StavkaIzdavanja{" + "rb=" + rb + ", datumPocetkaIzdavanja=" + datumPocetkaIzdavanja + ", datumZavrsetkaIzdavanja=" + datumZavrsetkaIzdavanja + ", mesecnaKirija=" + mesecnaKirija + ", iznosDepozita=" + iznosDepozita + ", idIzdavanje=" + idIzdavanje + ", idNekretnina=" + idNekretnina + '}';
    }

    
    
}
