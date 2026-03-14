package textbook.service;

import textbook.dao.SeanceDao;
import textbook.model.Seance;

import java.sql.SQLException;
import java.util.List;

public class SeanceService {
    private final SeanceDao seanceDao;

    public SeanceService() {
        this.seanceDao = new SeanceDao();
    }

    public void ajouterSeance(Seance seance) throws Exception {
        if (seance.getCodeUE() == null || seance.getCodeUE().isEmpty()) {
            throw new Exception("L'UE est obligatoire.");
        }
        if (seance.getMatricule() == null || seance.getMatricule().isEmpty()) {
            throw new Exception("L'enseignant est obligatoire.");
        }
        if (seance.getDateSeance() == null) {
            throw new Exception("La date est obligatoire.");
        }
        if (seance.getHeureDebut() == null || seance.getHeureFin() == null) {
            throw new Exception("Les heures de début et de fin sont obligatoires.");
        }
        if (seance.getSalle() == null || seance.getSalle().trim().isEmpty()) {
            throw new Exception("La salle est obligatoire.");
        }
        if (seance.getResume() == null || seance.getResume().trim().isEmpty()) {
            throw new Exception("Le résumé est obligatoire.");
        }

        seanceDao.insert(seance);
    }

    public List<Seance> listerSeances() throws SQLException {
        return seanceDao.findAllWithDetails();
    }

    public void supprimerSeance(int idSeance) throws SQLException {
        seanceDao.delete(idSeance);
    }
}
