package textbook.view;

import textbook.controller.EnseignantController;
import textbook.controller.SeanceController;
import textbook.controller.UEController;
import textbook.model.Enseignant;
import textbook.model.Seance;
import textbook.model.UE;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class SeancePanel extends JPanel {
    private final SeanceController seanceController;
    private final UEController ueController;
    private final EnseignantController enseignantController;

    private JComboBox<UE> ueComboBox;
    private JComboBox<Enseignant> enseignantComboBox;
    private JTextField dateField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextField salleField;
    private JTextArea resumeArea;
    private JTable seanceTable;
    private DefaultTableModel tableModel;

    public SeancePanel() {
        this.seanceController = new SeanceController();
        this.ueController = new UEController();
        this.enseignantController = new EnseignantController();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initUI();
        loadDropdowns();
        refreshTable();
    }

    private void initUI() {
        // --- Titre ---
        JLabel titleLabel = new JLabel("Gestion des Séances (Rapports)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // --- Formulaire d'ajout ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("UE :"), gbc);
        gbc.gridx = 1;
        ueComboBox = new JComboBox<>();
        formPanel.add(ueComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Enseignant :"), gbc);
        gbc.gridx = 1;
        enseignantComboBox = new JComboBox<>();
        formPanel.add(enseignantComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Date (AAAA-MM-JJ) :"), gbc);
        gbc.gridx = 1;
        dateField = new JTextField(10);
        formPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Heure Début (HH:MM:SS) :"), gbc);
        gbc.gridx = 1;
        startTimeField = new JTextField("08:00:00");
        formPanel.add(startTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Heure Fin (HH:MM:SS) :"), gbc);
        gbc.gridx = 1;
        endTimeField = new JTextField("10:00:00");
        formPanel.add(endTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Salle :"), gbc);
        gbc.gridx = 1;
        salleField = new JTextField(10);
        formPanel.add(salleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Résumé :"), gbc);
        gbc.gridx = 1;
        resumeArea = new JTextArea(3, 20);
        resumeArea.setLineWrap(true);
        formPanel.add(new JScrollPane(resumeArea), gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        JButton addButton = new JButton("Ajouter Rapport");
        addButton.setBackground(new Color(40, 167, 69));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(this::handleAddSeance);
        formPanel.add(addButton, gbc);

        // --- Tableau ---
        String[] columns = { "ID", "Date", "UE", "Enseignant", "Salle", "Résumé", "Action" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };
        seanceTable = new JTable(tableModel);

        // Cacher la colonne ID mais garder la valeur
        seanceTable.getColumnModel().getColumn(0).setMinWidth(0);
        seanceTable.getColumnModel().getColumn(0).setMaxWidth(0);
        seanceTable.getColumnModel().getColumn(0).setWidth(0);

        seanceTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        seanceTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(seanceTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, scrollPane);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);
    }

    private void loadDropdowns() {
        try {
            List<UE> ues = ueController.loadUE();
            ueComboBox.removeAllItems();
            for (UE ue : ues)
                ueComboBox.addItem(ue);

            List<Enseignant> enseignants = enseignantController.loadEnseignants();
            enseignantComboBox.removeAllItems();
            for (Enseignant e : enseignants)
                enseignantComboBox.addItem(e);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur chargement listes : " + ex.getMessage());
        }
    }

    private void handleAddSeance(ActionEvent e) {
        try {
            UE selectedUe = (UE) ueComboBox.getSelectedItem();
            Enseignant selectedEns = (Enseignant) enseignantComboBox.getSelectedItem();

            if (selectedUe == null || selectedEns == null) {
                throw new Exception("Veuillez sélectionner une UE et un enseignant.");
            }

            Seance s = new Seance();
            s.setCodeUE(selectedUe.getCodeUE());
            s.setMatricule(selectedEns.getMatricule());
            s.setDateSeance(Date.valueOf(dateField.getText()));
            s.setHeureDebut(Time.valueOf(startTimeField.getText()));
            s.setHeureFin(Time.valueOf(endTimeField.getText()));
            s.setSalle(salleField.getText());
            s.setResume(resumeArea.getText());

            seanceController.addSeance(s);
            JOptionPane.showMessageDialog(this, "Séance enregistrée !");
            resumeArea.setText("");
            refreshTable();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Format date ou heure invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<Seance> seances = seanceController.loadSeances();
            for (Seance s : seances) {
                tableModel.addRow(new Object[] {
                        s.getIdSeance(),
                        s.getDateSeance().toString(),
                        s.getUeIntitule(),
                        s.getEnseignantNomComplet(),
                        s.getSalle(),
                        s.getResume(),
                        "Supprimer"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur chargement tableau : " + ex.getMessage());
        }
    }

    // --- Inner classes (similaires aux précédents) ---
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(220, 53, 69));
            setForeground(Color.WHITE);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(new Color(220, 53, 69));
            button.setForeground(Color.WHITE);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            currentRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int idSeance = (int) seanceTable.getValueAt(currentRow, 0);
                int confirm = JOptionPane.showConfirmDialog(button, "Supprimer ce rapport ?", "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        seanceController.deleteSeance(idSeance);
                        refreshTable();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(button, "Erreur : " + e.getMessage());
                    }
                }
            }
            isPushed = false;
            return label;
        }
    }
}
