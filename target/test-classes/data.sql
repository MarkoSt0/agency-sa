INSERT INTO mesto (id, nazivMesta, opstina) VALUES (1, 'Beograd', 'Vracar');
INSERT INTO mesto (id, nazivMesta, opstina) VALUES (2, 'Novi Sad', 'Novi Sad');
INSERT INTO mesto (id, nazivMesta, opstina) VALUES (3, 'Nis', 'Medijana');

INSERT INTO tip_nekretnine (idTipaNekretnine, nazivTipaNekretnine) VALUES (1, 'Stan');
INSERT INTO tip_nekretnine (idTipaNekretnine, nazivTipaNekretnine) VALUES (2, 'Kuca');
INSERT INTO tip_nekretnine (idTipaNekretnine, nazivTipaNekretnine) VALUES (3, 'Poslovni prostor');

INSERT INTO agent (id, brojLicence, datumIstekaLicence, ime, prezime, korisnickoIme, sifra)
    VALUES (1, 'LIC001', '2027-01-01', 'Petar', 'Peric', 'pera123', 'sifra123');
INSERT INTO agent (id, brojLicence, datumIstekaLicence, ime, prezime, korisnickoIme, sifra)
    VALUES (2, 'LIC002', '2027-06-15', 'Marko', 'Stevic', 'marko.s', '12345');

INSERT INTO klijent (id, ime, prezime, starost, brojTelefona, email, idMesto)
    VALUES (1, 'Milan', 'Jovanovic', 35, '0611234567', 'milan@gmail.com', 2);
INSERT INTO klijent (id, ime, prezime, starost, brojTelefona, email, idMesto)
    VALUES (2, 'Ana', 'Nikolic', 28, '0621234567', 'ana@gmail.com', 1);
INSERT INTO klijent (id, ime, prezime, starost, brojTelefona, email, idMesto)
    VALUES (3, 'Stefan', 'Petrovic', 40, '0631234567', 'stefan@gmail.com', 3);
INSERT INTO klijent (id, ime, prezime, starost, brojTelefona, email, idMesto)
    VALUES (10, 'Novi', 'Klijent', 25, '0699999999', 'novi@gmail.com', 1);


INSERT INTO nekretnina (idNekretnina, adresa, povrsina, sprat, brojSoba, godinaIzgradnje, statusNekretnine, tipGrejanja, opis, idTipaNekretnine)
    VALUES (1, 'Kralja Petra 10', '75m2', '3', 3, 2015, 'DOSTUPNA', 'GAS', 'Stan u centru Beograda', 1);
INSERT INTO nekretnina (idNekretnina, adresa, povrsina, sprat, brojSoba, godinaIzgradnje, statusNekretnine, tipGrejanja, opis, idTipaNekretnine)
    VALUES (2, 'Zmaj Jovina 22', '120m2', '1', 5, 2010, 'DOSTUPNA', 'CENTRALNO', 'Porodicna kuca sa dvorisom', 2);
INSERT INTO nekretnina (idNekretnina, adresa, povrsina, sprat, brojSoba, godinaIzgradnje, statusNekretnine, tipGrejanja, opis, idTipaNekretnine)
    VALUES (3, 'Bulevar Oslobodjenja 55', '90m2', '2', 4, 2018, 'IZNAJMLJENA', 'GAS', 'Poslovni prostor Novi Sad', 3);
INSERT INTO nekretnina (idNekretnina, adresa, povrsina, sprat, brojSoba, godinaIzgradnje, statusNekretnine, tipGrejanja, opis, idTipaNekretnine)
    VALUES (4, 'Bulevar Kralja Aleksandra 10', '55m2', '2', 1, 2010, 'IZNAJMLJENA', 'CENTRALNO', 'Opremljen stan', 1);

INSERT INTO izdavanje (idIzdavanje, datumSklapanjaUgovora, statusUgovora, nacinPlacanja, ukupanIznos, napomena, idAgent, idKlijent)
    VALUES (1, '2025-10-01', 'Zavrsen', 'Kartica', 10000, 'Zavrseno izdavanje', 1, 1);
INSERT INTO izdavanje (idIzdavanje, datumSklapanjaUgovora, statusUgovora, nacinPlacanja, ukupanIznos, napomena, idAgent, idKlijent)
    VALUES (2, '2026-01-15', 'Aktivan', 'Kes', 50000, 'Aktivno izdavanje', 1, 2);
INSERT INTO izdavanje (idIzdavanje, datumSklapanjaUgovora, statusUgovora, nacinPlacanja, ukupanIznos, napomena, idAgent, idKlijent)
    VALUES (3, '2025-08-10', 'Zavrsen', 'Racun', 8000, 'Nema napomene', 2, 3);

INSERT INTO stavka_izdavanja (rb, idIzdavanje, idNekretnina, datumPocetkaIzdavanja, datumZavrsetkaIzdavanja, mesecnaKirija, iznosDepozita)
    VALUES (1, 1, 2, '2025-10-01', '2025-12-31', 20000, 10000);
INSERT INTO stavka_izdavanja (rb, idIzdavanje, idNekretnina, datumPocetkaIzdavanja, datumZavrsetkaIzdavanja, mesecnaKirija, iznosDepozita)
    VALUES (1, 2, 3, '2026-01-15', '2026-12-15', 50000, 10000);
INSERT INTO stavka_izdavanja (rb, idIzdavanje, idNekretnina, datumPocetkaIzdavanja, datumZavrsetkaIzdavanja, mesecnaKirija, iznosDepozita)
    VALUES (1, 3, 4, '2025-08-10', '2025-11-10', 8000, 4000);