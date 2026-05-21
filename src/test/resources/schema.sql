DROP TABLE IF EXISTS `sertifikat`;
DROP TABLE IF EXISTS `stavka_izdavanja`;
DROP TABLE IF EXISTS `nekretnina`;
DROP TABLE IF EXISTS `tip_nekretnine`;
DROP TABLE IF EXISTS `izdavanje`;
DROP TABLE IF EXISTS `klijent`;
DROP TABLE IF EXISTS `mesto`;
DROP TABLE IF EXISTS `agent`;

CREATE TABLE `agent` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brojLicence` varchar(255) DEFAULT NULL,
  `datumIstekaLicence` date DEFAULT NULL,
  `ime` varchar(255) NOT NULL,
  `prezime` varchar(255) DEFAULT NULL,
  `korisnickoIme` varchar(255) DEFAULT NULL,
  `sifra` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `korisnickoIme` (`korisnickoIme`)
);

CREATE TABLE `mesto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nazivMesta` varchar(255) NOT NULL,
  `opstina` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `tip_nekretnine` (
  `idTipaNekretnine` bigint NOT NULL AUTO_INCREMENT,
  `nazivTipaNekretnine` varchar(255) NOT NULL,
  PRIMARY KEY (`idTipaNekretnine`)
);

CREATE TABLE `klijent` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ime` varchar(255) NOT NULL,
  `prezime` varchar(255) DEFAULT NULL,
  `starost` int DEFAULT NULL,
  `brojTelefona` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `idMesto` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `klijent_ibfk_1` FOREIGN KEY (`idMesto`) REFERENCES `mesto` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `izdavanje` (
  `idIzdavanje` bigint NOT NULL AUTO_INCREMENT,
  `datumSklapanjaUgovora` date DEFAULT NULL,
  `statusUgovora` varchar(255) DEFAULT NULL,
  `nacinPlacanja` varchar(255) DEFAULT NULL,
  `ukupanIznos` double DEFAULT NULL,
  `napomena` varchar(255) DEFAULT NULL,
  `idAgent` bigint DEFAULT NULL,
  `idKlijent` bigint DEFAULT NULL,
  PRIMARY KEY (`idIzdavanje`),
  CONSTRAINT `izdavanje_ibfk_1` FOREIGN KEY (`idAgent`) REFERENCES `agent` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `izdavanje_ibfk_2` FOREIGN KEY (`idKlijent`) REFERENCES `klijent` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE `nekretnina` (
  `idNekretnina` bigint NOT NULL AUTO_INCREMENT,
  `adresa` varchar(255) DEFAULT NULL,
  `povrsina` varchar(255) DEFAULT NULL,
  `sprat` varchar(255) DEFAULT NULL,
  `brojSoba` double DEFAULT NULL,
  `godinaIzgradnje` int DEFAULT NULL,
  `statusNekretnine` varchar(50) DEFAULT NULL,
  `tipGrejanja` varchar(50) DEFAULT NULL,
  `opis` varchar(255) DEFAULT NULL,
  `idTipaNekretnine` bigint DEFAULT NULL,
  PRIMARY KEY (`idNekretnina`),
  CONSTRAINT `nekretnina_ibfk_1` FOREIGN KEY (`idTipaNekretnine`) REFERENCES `tip_nekretnine` (`idTipaNekretnine`) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE `sertifikat` (
  `idSertifikat` bigint NOT NULL AUTO_INCREMENT,
  `nazivSertifikata` varchar(255) NOT NULL,
  `izdavalac` varchar(255) NOT NULL,
  `datumSticanja` date NOT NULL,
  `idAgent` bigint DEFAULT NULL,
  PRIMARY KEY (`idSertifikat`),
  CONSTRAINT `sertifikat_ibfk_1` FOREIGN KEY (`idAgent`) REFERENCES `agent` (`id`)
);

CREATE TABLE `stavka_izdavanja` (
  `rb` bigint NOT NULL,
  `idIzdavanje` bigint NOT NULL,
  `idNekretnina` bigint DEFAULT NULL,
  `datumPocetkaIzdavanja` date DEFAULT NULL,
  `datumZavrsetkaIzdavanja` date DEFAULT NULL,
  `mesecnaKirija` double DEFAULT NULL,
  `iznosDepozita` double DEFAULT NULL,
  PRIMARY KEY (`rb`,`idIzdavanje`),
  CONSTRAINT `stavka_izdavanja_ibfk_1` FOREIGN KEY (`idIzdavanje`) REFERENCES `izdavanje` (`idIzdavanje`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stavka_izdavanja_ibfk_2` FOREIGN KEY (`idNekretnina`) REFERENCES `nekretnina` (`idNekretnina`) ON DELETE RESTRICT ON UPDATE CASCADE
);