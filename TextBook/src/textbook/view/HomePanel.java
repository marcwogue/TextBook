package textbook.view;

import textbook.controller.SeanceController;
import textbook.model.Seance;
import textbook.util.StyleUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HomePanel extends JPanel {
    private final SeanceController seanceController;
    private JTable seanceTable;
    private DefaultTableModel tableModel;

    public HomePanel() {
        this.seanceController = new SeanceController();
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(25, 25, 25, 25));
        setBackground(StyleUtil.COLOR_WHITE);

        initUI();
        refreshTable();
    }

    private void initUI() {
        JLabel titleLabel = new JLabel("Tableau de bord - Rapports de Séances");
        titleLabel.setFont(StyleUtil.FONT_TITLE);
        titleLabel.setForeground(StyleUtil.COLOR_PRIMARY);
        add(titleLabel, BorderLayout.NORTH);

        String[] columns = { "Date", "UE", "Enseignant", "Salle", "Résumé" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        seanceTable = new JTable(tableModel);
        StyleUtil.styleTable(seanceTable);

        JScrollPane scrollPane = new JScrollPane(seanceTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollPane.getViewport().setBackground(StyleUtil.COLOR_WHITE);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<Seance> seances = seanceController.loadSeances();
            for (Seance s : seances) {
                tableModel.addRow(new Object[] {
                        s.getDateSeance().toString(),
                        s.getUeIntitule(),
                        s.getEnseignantNomComplet(),
                        s.getSalle(),
                        s.getResume()
                });
            }
        } catch (Exception ex) {
            // Silently fail or show small label
        }
    }
}
