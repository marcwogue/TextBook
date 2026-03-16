package textbook.util;

import textbook.model.Seance;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportUtil {

    public static void showSeanceDetails(Component parent, Seance s) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Détails de la Séance", true);
        dialog.setLayout(new BorderLayout(15, 15));
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(parent);

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(new EmptyBorder(25, 25, 25, 25));
        content.setBackground(StyleUtil.COLOR_WHITE);

        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family: sans-serif; padding: 10px;'>");
        html.append("<h2 style='color: #2c3e50;'>Rapport de Séance</h2>");
        html.append("<hr>");
        html.append("<p><b>UE:</b> ").append(s.getUeIntitule()).append(" (").append(s.getCodeUE()).append(")</p>");
        html.append("<p><b>Enseignant:</b> ").append(s.getEnseignantNomComplet()).append("</p>");
        html.append("<p><b>Date:</b> ").append(s.getDateSeance()).append("</p>");
        html.append("<p><b>Horaire:</b> ").append(s.getHeureDebut()).append(" - ").append(s.getHeureFin())
                .append("</p>");
        html.append("<p><b>Salle:</b> ").append(s.getSalle()).append("</p>");
        html.append("<h3 style='color: #2c3e50;'>Résumé du cours:</h3>");
        html.append("<div style='background: #f9f9f9; padding: 15px; border-left: 5px solid #3498db;'>");
        html.append(s.getResume().replace("\n", "<br>"));
        html.append("</div>");
        html.append("</body></html>");

        JEditorPane pane = new JEditorPane("text/html", html.toString());
        pane.setEditable(false);
        pane.setBackground(StyleUtil.COLOR_WHITE);
        content.add(new JScrollPane(pane), BorderLayout.CENTER);

        JButton closeBtn = new JButton("Fermer");
        StyleUtil.styleButton(closeBtn, StyleUtil.COLOR_PRIMARY);
        closeBtn.addActionListener(e -> dialog.dispose());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(StyleUtil.COLOR_WHITE);
        btnPanel.add(closeBtn);
        content.add(btnPanel, BorderLayout.SOUTH);

        dialog.add(content);
        dialog.setVisible(true);
    }

    public static void exportAllSeances(Component parent, List<Seance> seances) {
        if (seances.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Aucun rapport à exporter.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer tous les rapports");
        fileChooser.setSelectedFile(new File("rapports_complets.txt"));

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("====================================================\n");
                writer.write("          ARCHIVE DES RAPPORTS DE SEANCES          \n");
                writer.write("====================================================\n\n");

                for (Seance s : seances) {
                    writer.write("DATE : " + s.getDateSeance() + "\n");
                    writer.write("UE   : " + s.getUeIntitule() + " (" + s.getCodeUE() + ")\n");
                    writer.write("ENS  : " + s.getEnseignantNomComplet() + "\n");
                    writer.write("SALLE: " + s.getSalle() + "\n");
                    writer.write("HEURE: " + s.getHeureDebut() + " - " + s.getHeureFin() + "\n");
                    writer.write("RESUME:\n" + s.getResume() + "\n");
                    writer.write("----------------------------------------------------\n\n");
                }
                JOptionPane.showMessageDialog(parent, "Exportation réussie !");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Erreur lors de l'exportation : " + ex.getMessage());
            }
        }
    }
}
