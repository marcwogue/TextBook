package textbook.model;

import java.sql.Date;
import java.sql.Time;

public class Seance {
    private int idSeance;
    private String codeUE;
    private String matricule;
    private Date dateSeance;
    private Time heureDebut;
    private Time heureFin;
    private String salle;
    private String resume;

    // Display fields
    private String ueIntitule;
    private String enseignantNomComplet;

    public Seance() {
    }

    public Seance(int idSeance, String codeUE, String matricule, Date dateSeance, Time heureDebut, Time heureFin,
            String salle, String resume) {
        this.idSeance = idSeance;
        this.codeUE = codeUE;
        this.matricule = matricule;
        this.dateSeance = dateSeance;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.salle = salle;
        this.resume = resume;
    }

    // Getters and Setters
    public int getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(int idSeance) {
        this.idSeance = idSeance;
    }

    public String getCodeUE() {
        return codeUE;
    }

    public void setCodeUE(String codeUE) {
        this.codeUE = codeUE;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Date getDateSeance() {
        return dateSeance;
    }

    public void setDateSeance(Date dateSeance) {
        this.dateSeance = dateSeance;
    }

    public Time getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Time getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getUeIntitule() {
        return ueIntitule;
    }

    public void setUeIntitule(String ueIntitule) {
        this.ueIntitule = ueIntitule;
    }

    public String getEnseignantNomComplet() {
        return enseignantNomComplet;
    }

    public void setEnseignantNomComplet(String enseignantNomComplet) {
        this.enseignantNomComplet = enseignantNomComplet;
    }
}
