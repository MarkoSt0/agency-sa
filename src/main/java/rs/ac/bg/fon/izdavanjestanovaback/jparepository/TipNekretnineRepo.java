/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.jparepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.izdavanjestanovaback.model.TipNekretnine;

/**
 *
 * @author Marko
 */
public interface TipNekretnineRepo extends JpaRepository<TipNekretnine, Long>{

    public Optional<TipNekretnine> findByNazivTipaNekretnine(String nazivTipaNekretnine);
    
}
