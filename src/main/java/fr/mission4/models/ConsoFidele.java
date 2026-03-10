package fr.mission4.models;

import java.util.Date;

public class ConsoFidele extends Conso {
    private Date dateNaiss;
    private double pointsFidelite;
    private Date dateInscription;

    public ConsoFidele(String nom, String prenom, String mail, String tel, Date dateNaiss, Date dateInscription) {
        // Appel au constructeur de la classe mère (Conso)
        super(nom, prenom, mail, tel, 0);
        this.dateNaiss = dateNaiss;
        this.dateInscription = dateInscription;
        this.pointsFidelite = 0; // Initialisation explicite à 0
    }

    public double getPointsFidelite() {
        return this.pointsFidelite;
    }

    // Méthode issue du Document 5
    public void addFidelite(int typeFidelite, double montant) {
        switch (typeFidelite) {
            case 1:
                this.pointsFidelite++;
                break;
            case 2:
                this.pointsFidelite += montant;
                break;
            case 3:
                if (montant >= 100 && montant <= 200) {
                    this.pointsFidelite += 10;
                } else if (montant > 200 && montant <= 500) {
                    this.pointsFidelite += 20;
                } else if (montant > 500) {
                    this.pointsFidelite += 50;
                }
                break;
        }
    }
}
