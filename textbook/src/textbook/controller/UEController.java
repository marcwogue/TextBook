package textbook.controller;

import textbook.model.UE;
import textbook.service.UEService;

import java.sql.SQLException;
import java.util.List;

public class UEController {
    private final UEService ueService;

    public UEController() throws SQLException {
        this.ueService = new UEService();
    }

    public void addUE(String codeUE, String intitule) throws Exception {
        ueService.ajouterUE(codeUE, intitule);
    }

    public void updateUE(String codeUE, String intitule) throws Exception {
        ueService.modifierUE(codeUE, intitule);
    }

    public List<UE> loadUE() throws Exception {
        return ueService.listerUE();
    }

    public void deleteUE(String codeUE) throws Exception {
        ueService.supprimerUE(codeUE);
    }
}
