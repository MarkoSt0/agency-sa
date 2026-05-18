/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.izdavanjestanovaback.model.Sertifikat;

/**
 *
 * @author Marko
 */
@Repository
public interface SertifikatRepo extends JpaRepository<Sertifikat, Long>{
    
}
