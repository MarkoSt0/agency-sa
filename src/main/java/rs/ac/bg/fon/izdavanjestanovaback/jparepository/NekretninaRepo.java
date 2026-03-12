/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.jparepository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.izdavanjestanovaback.model.Nekretnina;

/**
 *
 * @author Marko
 */
public interface NekretninaRepo extends JpaRepository<Nekretnina, Long>{

    public List<Nekretnina> findByPovrsinaContaining(String povrsina);

    public List<Nekretnina> findByTipNekretnine_IdTipaNekretnine(Long tipId);
    
}
