/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Marko
 */
public class StavkaIzdavanjaPK implements Serializable {
    private Long rb;
    private Long idIzdavanje;

    public StavkaIzdavanjaPK() {}

    public StavkaIzdavanjaPK(Long rb, Long idIzdavanje) {
        this.rb = rb;
        this.idIzdavanje = idIzdavanje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StavkaIzdavanjaPK)) return false;
        StavkaIzdavanjaPK that = (StavkaIzdavanjaPK) o;
        return Objects.equals(rb, that.rb) && Objects.equals(idIzdavanje, that.idIzdavanje);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rb, idIzdavanje);
    }

    public Long getIdIzdavanje() {
        return idIzdavanje;
    }

    public Long getRb() {
        return rb;
    }

    public void setRb(Long rb) {
        this.rb = rb;
    }

    public void setIdIzdavanje(Long idIzdavanje) {
        this.idIzdavanje = idIzdavanje;
    }

    
}
