package textbook.view;

import textbook.util.StyleUtil;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("TextBook - Gestion des Séances");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(StyleUtil.COLOR_BG);

        initUI();
    }

    private void initUI() {
        UIManager.put("TabbedPane.selected", StyleUtil.COLOR_PRIMARY);
        UIManager.put("TabbedPane.contentAreaColor", StyleUtil.COLOR_WHITE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(StyleUtil.FONT_HEADER);

        // Panel Accueil
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(StyleUtil.COLOR_WHITE);

        JLabel welcomeLabel = new JLabel("Bienvenue dans TextBook", SwingConstants.CENTER);
        welcomeLabel.setFont(StyleUtil.FONT_TITLE);
        welcomeLabel.setForeground(StyleUtil.COLOR_PRIMARY);
        homePanel.add(welcomeLabel, BorderLayout.CENTER);

        JLabel statusLabel = new JLabel("Système prêt • Connecté à PostgreSQL", SwingConstants.CENTER);
        statusLabel.setFont(StyleUtil.FONT_LABEL);
        statusLabel.setForeground(StyleUtil.COLOR_TEXT);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        homePanel.add(statusLabel, BorderLayout.SOUTH);

        // Panel UE
        UEPanel uePanel = new UEPanel();

        // Panel Enseignant
        EnseignantPanel enseignantPanel = new EnseignantPanel();

        // Panel Seance
        SeancePanel seancePanel = new SeancePanel();

        tabbedPane.addTab("Accueil", homePanel);
        tabbedPane.addTab("Gestion des UE", uePanel);
        tabbedPane.addTab("Gestion des Enseignants", enseignantPanel);
        tabbedPane.addTab("Gestion des Séances", seancePanel);

        add(tabbedPane);
    }
}
