package textbook.controller;

import textbook.model.Enseignant;
import textbook.service.EnseignantService;

import java.util.List;

public class EnseignantController {
    private final EnseignantService enseignantService;

    public EnseignantController() {
        this.enseignantService = new EnseignantService();
    }

    public void addEnseignant(String matricule, String nom, String prenom) throws Exception {
        enseignantService.ajouterEnseignant(matricule, nom, prenom);
    }

    public void updateEnseignant(String matricule, String nom, String prenom) throws Exception {
        enseignantService.modifierEnseignant(matricule, nom, prenom);
    }

    public List<Enseignant> loadEnseignants() throws Exception {
        return enseignantService.listerEnseignants();
    }

    public void deleteEnseignant(String matricule) throws Exception {
        enseignantService.supprimerEnseignant(matricule);
    }
}
