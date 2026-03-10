package fr.mission4.models; // Package adapté à l'arborescence Maven du projet

public class Conso {
    private String nom;
    private String prenom;
    private String mail;
    private String tel;
    private int nbVentes; // Ajout pour l'affichage dans le tableau

    public Conso(String nom, String prenom, String mail, String tel, int nbVentes) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.tel = tel;
        this.nbVentes = nbVentes;
    }

    // Getters nécessaires pour le PropertyValueFactory de JavaFX
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getMail() { return mail; }
    public String getTel() { return tel; }
    public int getNbVentes() { return nbVentes; }
}
