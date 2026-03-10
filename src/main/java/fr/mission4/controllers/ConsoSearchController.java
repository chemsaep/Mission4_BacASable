package fr.mission4.controllers;

import fr.mission4.DatabaseConnection;
import fr.mission4.models.Conso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsoSearchController {

    @FXML private TextField searchField;
    @FXML private CheckBox regularOnlyCheckBox;
    @FXML private TableView<Conso> consoTable;
    @FXML private TableColumn<Conso, String> colNom;
    @FXML private TableColumn<Conso, String> colPrenom;
    @FXML private TableColumn<Conso, String> colTel;
    @FXML private TableColumn<Conso, String> colMail;
    @FXML private TableColumn<Conso, Integer> colVentes;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        colVentes.setCellValueFactory(new PropertyValueFactory<>("nbVentes"));

        // Load all on start
        handleSearch();
    }

    @FXML
    public void handleSearch() {
        String searchText = searchField.getText().trim();
        boolean regularOnly = regularOnlyCheckBox.isSelected();

        ObservableList<Conso> list = FXCollections.observableArrayList();

        // Requête SQL sécurisée contre les injections (OWASP) utilisant PreparedStatement
        String sql = "SELECT c.id, c.nom, c.prenom, c.mail, c.tel, COUNT(v.id) as nbVentes " +
                     "FROM Conso c " +
                     "LEFT JOIN Vente v ON c.id = v.idConso " + // On ne filtre pas sur la date ici pour l'exemple, ou on peut ajouter AND v.dateVente >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH)
                     "WHERE (c.nom LIKE ? OR c.prenom LIKE ?) " +
                     "GROUP BY c.id, c.nom, c.prenom, c.mail, c.tel " +
                     "HAVING nbVentes >= ? " +
                     "ORDER BY nbVentes DESC, c.nom ASC";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                statusLabel.setText("Erreur de connexion à la base de données.");
                statusLabel.setTextFill(javafx.scene.paint.Color.RED);
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                String likeParam = "%" + searchText + "%";
                stmt.setString(1, likeParam);
                stmt.setString(2, likeParam);
                
                // Si "régulier uniquement" est coché, on exige au moins 2 ventes, sinon 0.
                stmt.setInt(3, regularOnly ? 2 : 0);

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    list.add(new Conso(
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("mail"),
                            rs.getString("tel"),
                            rs.getInt("nbVentes")
                    ));
                }
                
                consoTable.setItems(list);
                statusLabel.setText(list.size() + " résultat(s) trouvé(s).");
                statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Erreur lors de la recherche : " + e.getMessage());
            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
        }
    }
}
