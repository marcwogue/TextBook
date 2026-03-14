package textbook.view;

import textbook.controller.EnseignantController;
import textbook.controller.SeanceController;
import textbook.controller.UEController;
import textbook.model.Enseignant;
import textbook.model.Seance;
import textbook.model.UE;
import textbook.util.StyleUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(25, 25, 25, 25));
        setBackground(StyleUtil.COLOR_WHITE);

        initUI();
        loadDropdowns();
        refreshTable();
    }

    private void initUI() {
        // --- Header ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(StyleUtil.COLOR_WHITE);
        headerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        JLabel titleLabel = new JLabel("Rapport de Séance");
        titleLabel.setFont(StyleUtil.FONT_TITLE);
        titleLabel.setForeground(StyleUtil.COLOR_TEXT);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // --- Form ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(StyleUtil.COLOR_WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(25, 25, 25, 25)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Line 1: UE & Heure Début
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        formPanel.add(createLabel("Unité d'Ens. (UE)"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        ueComboBox = new JComboBox<>();
        ueComboBox.setBackground(StyleUtil.COLOR_WHITE);
        formPanel.add(ueComboBox, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Heure Début"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        startTimeField = new JTextField("08:00:00");
        StyleUtil.styleTextField(startTimeField);
        formPanel.add(startTimeField, gbc);

        // Line 2: Enseignant & Heure Fin
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(createLabel("Enseignant"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        enseignantComboBox = new JComboBox<>();
        enseignantComboBox.setBackground(StyleUtil.COLOR_WHITE);
        formPanel.add(enseignantComboBox, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Heure Fin"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        endTimeField = new JTextField("10:00:00");
        StyleUtil.styleTextField(endTimeField);
        formPanel.add(endTimeField, gbc);

        // Line 3: Date & Salle
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Date (AAAA-MM-JJ)"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        dateField = new JTextField(10);
        StyleUtil.styleTextField(dateField);
        formPanel.add(dateField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Salle / Local"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        salleField = new JTextField(10);
        StyleUtil.styleTextField(salleField);
        formPanel.add(salleField, gbc);

        // Line 4: Résumé (Full Width)
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        formPanel.add(createLabel("Résumé du cours"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        JPanel resumeContainer = new JPanel(new BorderLayout(10, 0));
        resumeContainer.setBackground(StyleUtil.COLOR_WHITE);

        resumeArea = new JTextArea(2, 20);
        resumeArea.setFont(StyleUtil.FONT_LABEL);
        resumeArea.setLineWrap(true);
        resumeArea.setWrapStyleWord(true);
        JScrollPane areaScroll = new JScrollPane(resumeArea);
        areaScroll.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        resumeContainer.add(areaScroll, BorderLayout.CENTER);

        JButton expandBtn = new JButton("Agrandir...");
        StyleUtil.styleButton(expandBtn, StyleUtil.COLOR_PRIMARY);
        expandBtn.addActionListener(e -> showExpandDialog());
        resumeContainer.add(expandBtn, BorderLayout.EAST);

        formPanel.add(resumeContainer, gbc);

        // Line 5: Submit Button
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(20, 10, 0, 10);
        JButton addButton = new JButton("Enregistrer le Rapport");
        StyleUtil.styleButton(addButton, StyleUtil.COLOR_SUCCESS);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addButton.addActionListener(this::handleAddSeance);
        formPanel.add(addButton, gbc);

        // Prepare Top Container
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(StyleUtil.COLOR_WHITE);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);

        // --- Table ---
        String[] columns = { "ID", "Date", "UE", "Enseignant", "Salle", "Résumé", "Actions" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };
        seanceTable = new JTable(tableModel);
        StyleUtil.styleTable(seanceTable);

        seanceTable.getColumnModel().getColumn(0).setMinWidth(0);
        seanceTable.getColumnModel().getColumn(0).setMaxWidth(0);
        seanceTable.getColumnModel().getColumn(0).setWidth(0);

        seanceTable.getColumnModel().getColumn(6).setMinWidth(120);
        seanceTable.getColumnModel().getColumn(6).setPreferredWidth(120);

        seanceTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        seanceTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(seanceTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollPane.getViewport().setBackground(StyleUtil.COLOR_WHITE);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(StyleUtil.COLOR_WHITE);
        JLabel historyLabel = new JLabel("Historique des Séances");
        historyLabel.setFont(StyleUtil.FONT_HEADER);
        historyLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        bottomPanel.add(historyLabel, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
        splitPane.setDividerLocation(420);
        splitPane.setBorder(null);
        splitPane.setBackground(StyleUtil.COLOR_WHITE);
        add(splitPane, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(StyleUtil.FONT_LABEL);
        return l;
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
            JOptionPane.showMessageDialog(this, "Erreur chargement : " + ex.getMessage());
        }
    }

    private void handleAddSeance(ActionEvent e) {
        try {
            UE selectedUe = (UE) ueComboBox.getSelectedItem();
            Enseignant selectedEns = (Enseignant) enseignantComboBox.getSelectedItem();

            if (selectedUe == null || selectedEns == null)
                throw new Exception("Sélectionnez UE et enseignant.");

            Seance s = new Seance();
            s.setCodeUE(selectedUe.getCodeUE());
            s.setMatricule(selectedEns.getMatricule());
            s.setDateSeance(Date.valueOf(dateField.getText()));
            s.setHeureDebut(Time.valueOf(startTimeField.getText()));
            s.setHeureFin(Time.valueOf(endTimeField.getText()));
            s.setSalle(salleField.getText());
            s.setResume(resumeArea.getText());

            seanceController.addSeance(s);
            JOptionPane.showMessageDialog(this, "Rapport enregistré !");
            resumeArea.setText("");
            refreshTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void showExpandDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Résumé Complet", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        ((JPanel) dialog.getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextArea detailArea = new JTextArea(resumeArea.getText());
        detailArea.setFont(StyleUtil.FONT_LABEL);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);
        dialog.add(new JScrollPane(detailArea), BorderLayout.CENTER);

        JButton saveBtn = new JButton("Valider");
        StyleUtil.styleButton(saveBtn, StyleUtil.COLOR_PRIMARY);
        saveBtn.addActionListener(e -> {
            resumeArea.setText(detailArea.getText());
            dialog.dispose();
        });

        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.add(saveBtn);
        dialog.add(p, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<Seance> seances = seanceController.loadSeances();
            for (Seance s : seances) {
                tableModel.addRow(new Object[] { s.getIdSeance(), s.getDateSeance().toString(), s.getUeIntitule(),
                        s.getEnseignantNomComplet(), s.getSalle(), s.getResume(), "" });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    class ButtonRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        private JButton button;

        public ButtonRenderer() {
            setOpaque(true);
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 2));
            button = new JButton("Supprimer");
            StyleUtil.styleButton(button, StyleUtil.COLOR_DANGER);
            button.setFont(new Font("Segoe UI", Font.BOLD, 10));
            add(button);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton button;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 2));
            panel.setOpaque(true);
            button = new JButton("Supprimer");
            StyleUtil.styleButton(button, StyleUtil.COLOR_DANGER);
            button.setFont(new Font("Segoe UI", Font.BOLD, 10));
            panel.add(button);

            button.addActionListener(e -> {
                int id = (int) seanceTable.getValueAt(currentRow, 0);
                if (JOptionPane.showConfirmDialog(button, "Supprimer le rapport ?", "Confirmation",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    try {
                        seanceController.deleteSeance(id);
                        refreshTable();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(button, "Erreur : " + ex.getMessage());
                    }
                }
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            currentRow = row;
            panel.setBackground(table.getSelectionBackground());
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
}
