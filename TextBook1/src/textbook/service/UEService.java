package textbook.service;

import textbook.dao.UEDao;
import textbook.model.UE;

import java.sql.SQLException;
import java.util.List;

public class UEService {
    private final UEDao ueDao;

    public UEService() {
        this.ueDao = new UEDao();
    }

    public void ajouterUE(String codeUE, String intitule) throws Exception {
        if (codeUE == null || codeUE.trim().isEmpty()) {
            throw new Exception("Le code UE ne peut pas être vide.");
        }
        if (intitule == null || intitule.trim().isEmpty()) {
            throw new Exception("L'intitulé ne peut pas être vide.");
        }

        if (ueDao.exists(codeUE)) {
            throw new Exception("Une UE avec ce code existe déjà.");
        }

        UE ue = new UE(codeUE.trim(), intitule.trim());
        ueDao.insert(ue);
    }

    public void modifierUE(String codeUE, String intitule) throws Exception {
        if (intitule == null || intitule.trim().isEmpty()) {
            throw new Exception("L'intitulé ne peut pas être vide.");
        }

        if (!ueDao.exists(codeUE)) {
            throw new Exception("Cette UE n'existe pas.");
        }

        UE ue = new UE(codeUE.trim(), intitule.trim());
        ueDao.update(ue);
    }

    public List<UE> listerUE() throws SQLException {
        return ueDao.findAll();
    }

    public void supprimerUE(String codeUE) throws SQLException {
        ueDao.delete(codeUE);
    }
}
