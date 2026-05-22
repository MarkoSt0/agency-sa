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

//    public List<Izdavanje> findByStatusUgovoraContaining(String status);
//    public List<Izdavanje> findByIdKlijent_ImeContainingAndIdKlijent_PrezimeContaining(String ime, String prezime);
    
    @Query("SELECT i FROM Izdavanje i WHERE i.statusUgovora LIKE %:status%")
    List<Izdavanje> findByStatusUgovoraContaining(@Param("status") String status);
    
    @Query("SELECT i FROM Izdavanje i WHERE i.idKlijent.ime LIKE %:ime% AND i.idKlijent.prezime LIKE %:prezime%")
    List<Izdavanje> findByIdKlijent_ImeContainingAndIdKlijent_PrezimeContaining(
        @Param("ime") String ime, 
        @Param("prezime") String prezime
    );
    
    @Query("SELECT i FROM Izdavanje i WHERE i.statusUgovora = :status")
    List<Izdavanje> findByStatusUgovora(@Param("status") String status);
}
