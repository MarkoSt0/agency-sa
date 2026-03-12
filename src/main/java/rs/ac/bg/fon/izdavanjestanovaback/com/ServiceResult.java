/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.com;

/**
 *
 * @author Marko
 * @param <T>
 */
public class ServiceResult<T> {
    private boolean uspesno;
    private String poruka;
    private T podaci;
    
    public ServiceResult(boolean uspesno, String poruka, T podaci) {
        this.uspesno = uspesno;
        this.poruka = poruka;
        this.podaci = podaci;
    }

    public static <T> ServiceResult<T> success(String message) {
        return new ServiceResult<>(true, message, null);
    }

    public static <T> ServiceResult<T> success(String message, T data) {
        return new ServiceResult<>(true, message, data);
    }

    public static <T> ServiceResult<T> failure(String message) {
        return new ServiceResult<>(false, message, null);
    }

    public boolean isUspesno() {
        return uspesno;
    }

    public void setUspesno(boolean uspesno) {
        this.uspesno = uspesno;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public T getPodaci() {
        return podaci;
    }

    public void setPodaci(T podaci) {
        this.podaci = podaci;
    }

    
    
    
}

