/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package rs.ac.bg.fon.izdavanjestanovaback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author Marko
 */

@SpringBootApplication
@EntityScan("rs.ac.bg.fon.izdavanjestanovaback.model")
@EnableJpaRepositories("rs.ac.bg.fon.izdavanjestanovaback.jparepository")
public class IzdavanjeStanovaBack {

    public static void main(String[] args) {
        SpringApplication.run(IzdavanjeStanovaBack.class, args);
    }
}
