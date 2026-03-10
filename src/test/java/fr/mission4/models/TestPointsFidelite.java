package fr.mission4.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

class TestPointsFidelite {

    // Test fourni dans le sujet d'origine
    @Test
    void testAddFideliteTampon() throws ParseException {
        ConsoFidele consoTest = new ConsoFidele("Lifo", "Paul", "lifo.paul@gmail.com", "0600000000",
                new SimpleDateFormat("yyyy-MM-dd").parse("1961-01-03"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-05"));

        consoTest.addFidelite(1, 50);
        assertEquals(1, consoTest.getPointsFidelite(), "Erreur calcul 1er tampon");

        consoTest.addFidelite(1, 20);
        assertEquals(2, consoTest.getPointsFidelite(), "Erreur calcul 2ème tampon");
    }

    // Test fourni dans le sujet d'origine
    @Test
    void testAddFideliteMontant() throws ParseException {
        ConsoFidele consoTest = new ConsoFidele("Lifo", "Paul", "lifo.paul@gmail.com", "0600000000",
                new SimpleDateFormat("yyyy-MM-dd").parse("1961-01-03"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-05"));

        consoTest.addFidelite(2, 150);
        assertEquals(150, consoTest.getPointsFidelite(), "Erreur calcul 1er achat");

        consoTest.addFidelite(2, 250);
        assertEquals(400, consoTest.getPointsFidelite(), "Erreur calcul 2ème achat");
    }

    // REPONSE QUESTION 2.4 : Vérification de l'initialisation
    @Test
    void testInitConso() throws ParseException {
        ConsoFidele consoTest = new ConsoFidele("Lifo", "Paul", "lifo.paul@gmail.com", "0600000000",
                new SimpleDateFormat("yyyy-MM-dd").parse("1961-01-03"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-05"));

        // On vérifie qu'à la création, les points sont bien à zéro
        assertEquals(0, consoTest.getPointsFidelite(), "Erreur : Les points de fidélité initiaux doivent être à 0");
    }

    // REPONSE QUESTION 2.5 : Validation du programme de type 3 (par tranches)
    @Test
    void testAddMontant() throws ParseException {
        ConsoFidele consoTest = new ConsoFidele("Lifo", "Paul", "lifo.paul@gmail.com", "0600000000",
                new SimpleDateFormat("yyyy-MM-dd").parse("1961-01-03"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-05"));

        // Tranche 1 : Entre 100 et 200 euros -> +10 points
        consoTest.addFidelite(3, 150);
        assertEquals(10, consoTest.getPointsFidelite(), "Erreur tranche 1 (100-200€)");

        // Tranche 2 : Entre 201 et 500 euros -> +20 points (Total attendu : 10 + 20 = 30)
        consoTest.addFidelite(3, 300);
        assertEquals(30, consoTest.getPointsFidelite(), "Erreur tranche 2 (201-500€)");

        // Tranche 3 : Supérieur à 500 euros -> +50 points (Total attendu : 30 + 50 = 80)
        consoTest.addFidelite(3, 600);
        assertEquals(80, consoTest.getPointsFidelite(), "Erreur tranche 3 (>500€)");

        // Hors tranche : Inférieur à 100 euros -> Aucun point ajouté (Total attendu : toujours 80)
        consoTest.addFidelite(3, 50);
        assertEquals(80, consoTest.getPointsFidelite(), "Erreur hors tranche (<100€)");
    }
}
