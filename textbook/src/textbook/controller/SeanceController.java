package textbook.controller;

import textbook.model.Seance;
import textbook.service.SeanceService;

import java.util.List;

public class SeanceController {
    private final SeanceService seanceService;

    public SeanceController() {
        this.seanceService = new SeanceService();
    }

    public void addSeance(Seance seance) throws Exception {
        seanceService.ajouterSeance(seance);
    }

    public List<Seance> loadSeances() throws Exception {
        return seanceService.listerSeances();
    }

    public void deleteSeance(int idSeance) throws Exception {
        seanceService.supprimerSeance(idSeance);
    }
}
