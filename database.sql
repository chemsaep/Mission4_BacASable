CREATE DATABASE IF NOT EXISTS webcaisse DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE webcaisse;

-- Création des tables
CREATE TABLE IF NOT EXISTS Conso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    mail VARCHAR(150) NOT NULL,
    tel VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS ConsoFidele (
    id INT PRIMARY KEY,
    dateNaiss DATE,
    pointsFidelite DOUBLE DEFAULT 0,
    dateInscription DATE,
    FOREIGN KEY (id) REFERENCES Conso(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Vente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dateVente DATE NOT NULL,
    montantVente DOUBLE NOT NULL,
    idConso INT NOT NULL,
    FOREIGN KEY (idConso) REFERENCES Conso(id) ON DELETE CASCADE
);

-- Jeu d'essai : Consommateurs
INSERT INTO Conso (id, nom, prenom, mail, tel) VALUES
(1, 'Martin', 'Sophie', 'sophie@mail.com', '0601020304'), -- Sera fidèle
(2, 'Dubois', 'Marc', 'marc@mail.com', '0611223344'),    -- Non fidèle, régulier
(3, 'Leroy', 'Emma', 'emma@mail.com', '0699887766'),     -- Non fidèle, peu régulier
(4, 'Moreau', 'Luc', 'luc@mail.com', '0655443322')       -- Non fidèle, très régulier
ON DUPLICATE KEY UPDATE nom=VALUES(nom);

-- Jeu d'essai : Consommateurs fidèles
INSERT INTO ConsoFidele (id, dateNaiss, pointsFidelite, dateInscription) VALUES
(1, '1990-05-15', 120, '2023-01-10')
ON DUPLICATE KEY UPDATE pointsFidelite=VALUES(pointsFidelite);

-- Jeu d'essai : Ventes (Ajustez les dates pour correspondre au test "du mois dernier")
INSERT INTO Vente (dateVente, montantVente, idConso) VALUES
('2026-02-10', 45.50, 1),
('2026-02-12', 20.00, 2),
('2026-02-15', 35.00, 2), -- Marc a 2 ventes
('2026-02-20', 15.00, 3), -- Emma a 1 vente
('2026-02-05', 10.00, 4),
('2026-02-08', 55.00, 4),
('2026-02-21', 12.50, 4); -- Luc a 3 ventes
