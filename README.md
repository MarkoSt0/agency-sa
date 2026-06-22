# ProjekatSA

Ovaj projekat predstavlja full-stack web aplikaciju za poslovanje agencije za nekretnine. Razvijen je primenom slojevite arhitekture (Layered Architecture) sa fokusom na REST principe i efikasnu perzistenciju podataka.

### Tehnološki stack:
* **Backend:** Java, Spring Boot, Spring Data JPA
* **Frontend:** React.js, Axios (HTTP klijent)
* **Baza podataka:** MySQL, Hibernate (ORM)
* **Testiranje:** JUnit5, Jakarta Validation, H2 Test Database
* **Arhitektura:** Controller -> Service -> Repository (Separation of Concerns)

### Ključne funkcionalnosti:
* **RESTful API:** Implementirana kompletna CRUD logika za asinhrono upravljanje entitetima agenata, nekretnina i ugovora.
* **ORM Mapiranje:** Konfiguracija kompleksnih relacionih veza i upravljanje integritetom podataka pomoću JPA/Hibernate-a.
* **Dinamički Frontend:** Reaktivni korisnički interfejs za manipulaciju podacima i pregled stanja nekretnina u realnom vremenu.
* **Poslovna logika:** Automatizacija procesa izdavanja, praćenje statusa objekata i validacija ugovornih uslova.

### Plan daljeg razvoja:
* **Role-Based Access Control (RBAC):** Integracija Spring Security modula za definisanje nivoa pristupa (Agent/Menadžer).
* **Napredna filtracija:** Implementacija složenih pretraga na frontendu po višestrukim kriterijumima.
* **Tehnička dokumentacija:** Izrada detaljnih dijagrama sekvenci i ER modela za vizuelizaciju logičkih procesa.

### Pokretanje projekta:
1. **Backend:** Potreban Maven i Java 17+. Pokrenuti glavnu Spring Boot aplikaciju.
2. **Frontend:** Izvršiti komande `npm install` i `npm start` unutar React direktorijuma. Projekat se nalazi na linku: `https://github.com/MarkoSt0/sa-agency-front`
3. **Baza podataka:** Importovati priloženu SQL skriptu iz foldera projekta (H2 baza).
