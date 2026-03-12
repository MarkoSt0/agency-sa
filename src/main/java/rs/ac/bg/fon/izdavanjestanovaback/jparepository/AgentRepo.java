/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.jparepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.izdavanjestanovaback.model.Agent;

/**
 *
 * @author Marko
 */
public interface AgentRepo extends JpaRepository<Agent, Long>{

    public boolean existsByKorisnickoIme(String korisnickoIme);

    public List<Agent> findByImeContaining(String ime);

    public Optional<Agent> findByKorisnickoImeAndSifra(String korisnickoIme, String sifra);
    
}
