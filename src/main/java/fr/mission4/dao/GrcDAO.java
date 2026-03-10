package fr.mission4.dao;

import fr.mission4.models.Conso;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class GrcDAO {

    // Méthode de connexion simplifiée pour le TP
    private static Connection getConnexion() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/webcaisse?useSSL=false&serverTimezone=UTC", "root", "");
    }

    // Réécriture sécurisée de listeConsoAFideliser (Correction Q2.3)
    public static ArrayList<Conso> listeConsoAFideliser(int seuilVentes, LocalDate dateDeb, LocalDate dateFin) {
        ArrayList<Conso> lesConsos = new ArrayList<>();

        // La requête paramétrée avec des "?" évite les injections SQL
        String requete = "SELECT c.nom, c.prenom, c.tel, c.mail, COUNT(v.id) as nbVentes "
                       + "FROM Conso c "
                       + "JOIN Vente v ON c.id = v.idConso "
                       + "WHERE c.id NOT IN (SELECT id FROM ConsoFidele) "
                       + "AND v.dateVente BETWEEN ? AND ? "
                       + "GROUP BY c.id, c.nom, c.prenom, c.tel, c.mail "
                       + "HAVING COUNT(v.id) > ?";

        // Utilisation du try-with-resources pour fermer automatiquement les connexions
        try (Connection dbConnect = getConnexion();
             PreparedStatement pstmt = dbConnect.prepareStatement(requete)) {

            // Remplacement sécurisé des marqueurs
            pstmt.setDate(1, java.sql.Date.valueOf(dateDeb));
            pstmt.setDate(2, java.sql.Date.valueOf(dateFin));
            pstmt.setInt(3, seuilVentes);

            try (ResultSet res = pstmt.executeQuery()) {
                while (res.next()) {
                    Conso leConso = new Conso(
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getString("mail"),
                        res.getString("tel"),
                        res.getInt("nbVentes")
                    );
                    lesConsos.add(leConso);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
        return lesConsos;
    }
}
