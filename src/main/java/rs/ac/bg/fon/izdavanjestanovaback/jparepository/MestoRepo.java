/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.jparepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.izdavanjestanovaback.model.Mesto;

/**
 *
 * @author Marko
 */
@Repository
public interface MestoRepo extends JpaRepository<Mesto, Long>{

    public Optional<Mesto> findByNazivMesta(String nazivMesta);
    
    
}
