/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.jparepository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.izdavanjestanovaback.model.Klijent;

/**
 *
 * @author Marko
 */
@Repository
public interface KlijentRepo  extends JpaRepository<Klijent, Long>{
    
    @Override
    public <S extends Klijent> S save(S entity);
    
//    public List<Klijent> findByEmailStartsWith(String email);
    
    @Query("SELECT k FROM Klijent k WHERE k.email LIKE :email%")
    List<Klijent> findByEmailStartsWith(@Param("email") String email);

    public boolean existsByEmail(String email);
    
}
