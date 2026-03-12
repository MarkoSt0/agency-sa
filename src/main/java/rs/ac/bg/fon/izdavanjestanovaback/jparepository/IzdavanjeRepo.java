/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.jparepository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.izdavanjestanovaback.model.Izdavanje;

/**
 *
 * @author Marko
 */
public interface IzdavanjeRepo extends JpaRepository<Izdavanje, Long>{

    public List<Izdavanje> findByStatusUgovoraContaining(String status);
    public List<Izdavanje> findByIdKlijent_ImeContainingAndIdKlijent_PrezimeContaining(String ime, String prezime);
    
    @Query("SELECT i FROM Izdavanje i WHERE i.statusUgovora = :status")
    List<Izdavanje> findByStatusUgovora(@Param("status") String status);
}
