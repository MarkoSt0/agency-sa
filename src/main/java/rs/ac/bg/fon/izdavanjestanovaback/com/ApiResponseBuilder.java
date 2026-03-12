/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.com;

import java.util.Map;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Marko
 */
public class ApiResponseBuilder {

    public static ApiResponse kreirajOdgovor(String poruka, HttpStatus status) {
        return new ApiResponse(poruka, status);
    }

    public static ApiResponse kreirajOdgovorSaPodacima(String poruka, Map<String, Object> podaci, HttpStatus status) {
        return new ApiResponse(poruka, podaci, status);
    }
}
