/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.com;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Marko
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private String poruka;
    private Map<String, Object> podaci;
    private HttpStatus status; //greska, bez ovoga!

    public ApiResponse(String poruka, HttpStatus status) {
        this.poruka = poruka;
        this.status = status;
    }

    public ApiResponse(String poruka, Map<String, Object> podaci, HttpStatus status) {
        this.poruka = poruka;
        this.podaci = podaci;
        this.status = status;
    }

    public String getPoruka() { return poruka; }
    public Map<String, Object> getPodaci() { return podaci; }
    public HttpStatus getStatus() { return status; }
}
