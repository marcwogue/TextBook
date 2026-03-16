package textbook.view;

import textbook.util.StyleUtil;

import java.sql.SQLException;

import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() throws SQLException {
        setTitle("TextBook - Gestion des Séances");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(StyleUtil.COLOR_BG);

        initUI();
    }

    private void initUI() throws SQLException {
        UIManager.put("TabbedPane.selected", StyleUtil.COLOR_PRIMARY);
        UIManager.put("TabbedPane.contentAreaColor", StyleUtil.COLOR_WHITE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(StyleUtil.FONT_HEADER);

        // Panel Accueil (Dashboard)
        HomePanel homePanel = new HomePanel();
        tabbedPane.addTab("Accueil", homePanel);

        // Panel UE
        UEPanel uePanel = new UEPanel();
        tabbedPane.addTab("Gestion des UE", uePanel);

        // Panel Enseignants
        EnseignantPanel enseignantPanel = new EnseignantPanel();
        tabbedPane.addTab("Gestion des Enseignants", enseignantPanel);

        // Panel Séances
        SeancePanel seancePanel = new SeancePanel();
        tabbedPane.addTab("Gestion des Séances", seancePanel);

        // Refresh home table when clicking "Accueil"
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 0) {
                homePanel.refreshTable();
            }
        });

        add(tabbedPane);
    }
}
