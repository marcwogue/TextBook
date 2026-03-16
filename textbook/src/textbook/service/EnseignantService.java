package textbook.service;

import textbook.dao.EnseignantDao;
import textbook.model.Enseignant;

import java.sql.SQLException;
import java.util.List;

public class EnseignantService {
    private final EnseignantDao enseignantDao;

    public EnseignantService() {
        this.enseignantDao = new EnseignantDao();
    }

    public void ajouterEnseignant(String matricule, String nom, String prenom) throws Exception {
        if (matricule == null || matricule.trim().isEmpty()) {
            throw new Exception("Le matricule est obligatoire.");
        }
        if (nom == null || nom.trim().isEmpty()) {
            throw new Exception("Le nom est obligatoire.");
        }
        if (prenom == null || prenom.trim().isEmpty()) {
            throw new Exception("Le prénom est obligatoire.");
        }

        if (enseignantDao.exists(matricule.trim())) {
            throw new Exception("Un enseignant avec ce matricule existe déjà.");
        }

        Enseignant enseignant = new Enseignant(matricule.trim(), nom.trim(), prenom.trim());
        enseignantDao.insert(enseignant);
    }

    public void modifierEnseignant(String matricule, String nom, String prenom) throws Exception {
        if (nom == null || nom.trim().isEmpty()) {
            throw new Exception("Le nom est obligatoire.");
        }
        if (prenom == null || prenom.trim().isEmpty()) {
            throw new Exception("Le prénom est obligatoire.");
        }

        if (!enseignantDao.exists(matricule.trim())) {
            throw new Exception("Cet enseignant n'existe pas.");
        }

        Enseignant enseignant = new Enseignant(matricule.trim(), nom.trim(), prenom.trim());
        enseignantDao.update(enseignant);
    }

    public List<Enseignant> listerEnseignants() throws SQLException {
        return enseignantDao.findAll();
    }

    public void supprimerEnseignant(String matricule) throws SQLException {
        enseignantDao.delete(matricule);
    }
}
