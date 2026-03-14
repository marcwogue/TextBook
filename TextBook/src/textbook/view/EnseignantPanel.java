package textbook.view;

import textbook.controller.EnseignantController;
import textbook.model.Enseignant;
import textbook.util.StyleUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EnseignantPanel extends JPanel {
    private final EnseignantController enseignantController;
    private JTextField matriculeField;
    private JTextField nomField;
    private JTextField prenomField;
    private JButton addButton;
    private JButton cancelButton;
    private JTable enseignantTable;
    private DefaultTableModel tableModel;

    private boolean isEditMode = false;

    public EnseignantPanel() {
        this.enseignantController = new EnseignantController();
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(25, 25, 25, 25));
        setBackground(StyleUtil.COLOR_WHITE);

        initUI();
        refreshTable();
    }

    private void initUI() {
        // --- Header Section ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(StyleUtil.COLOR_WHITE);
        JLabel titleLabel = new JLabel("Gestion des Enseignants");
        titleLabel.setFont(StyleUtil.FONT_TITLE);
        titleLabel.setForeground(StyleUtil.COLOR_TEXT);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // --- Form Section ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(StyleUtil.COLOR_WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                new EmptyBorder(20, 20, 20, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel l1 = new JLabel("Matricule");
        l1.setFont(StyleUtil.FONT_LABEL);
        formPanel.add(l1, gbc);

        gbc.gridx = 1;
        matriculeField = new JTextField(12);
        StyleUtil.styleTextField(matriculeField);
        formPanel.add(matriculeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel l2 = new JLabel("Nom");
        l2.setFont(StyleUtil.FONT_LABEL);
        formPanel.add(l2, gbc);

        gbc.gridx = 1;
        nomField = new JTextField(20);
        StyleUtil.styleTextField(nomField);
        formPanel.add(nomField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel l3 = new JLabel("Prénom");
        l3.setFont(StyleUtil.FONT_LABEL);
        formPanel.add(l3, gbc);

        gbc.gridx = 1;
        prenomField = new JTextField(20);
        StyleUtil.styleTextField(prenomField);
        formPanel.add(prenomField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnPanel.setBackground(StyleUtil.COLOR_WHITE);

        addButton = new JButton("Ajouter");
        StyleUtil.styleButton(addButton, StyleUtil.COLOR_SUCCESS);
        addButton.addActionListener(this::handleSaveEnseignant);
        btnPanel.add(addButton);

        cancelButton = new JButton("Annuler");
        StyleUtil.styleButton(cancelButton, new Color(149, 165, 166));
        cancelButton.setVisible(false);
        cancelButton.addActionListener(e -> setEditMode(false, null));
        btnPanel.add(cancelButton);

        formPanel.add(btnPanel, gbc);

        // --- Table Section ---
        String[] columns = { "Matricule", "Nom", "Prénom", "Actions" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        enseignantTable = new JTable(tableModel);
        StyleUtil.styleTable(enseignantTable);

        enseignantTable.getColumnModel().getColumn(3).setCellRenderer(new ActionsRenderer());
        enseignantTable.getColumnModel().getColumn(3).setCellEditor(new ActionsEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(enseignantTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollPane.getViewport().setBackground(StyleUtil.COLOR_WHITE);

        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(StyleUtil.COLOR_WHITE);
        contentPanel.add(formPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void setEditMode(boolean edit, Enseignant e) {
        this.isEditMode = edit;
        if (edit && e != null) {
            matriculeField.setText(e.getMatricule());
            matriculeField.setEditable(false);
            nomField.setText(e.getNom());
            prenomField.setText(e.getPrenom());
            addButton.setText("Mettre à jour");
            addButton.setBackground(StyleUtil.COLOR_PRIMARY);
            cancelButton.setVisible(true);
        } else {
            matriculeField.setText("");
            matriculeField.setEditable(true);
            nomField.setText("");
            prenomField.setText("");
            addButton.setText("Ajouter");
            addButton.setBackground(StyleUtil.COLOR_SUCCESS);
            cancelButton.setVisible(false);
        }
    }

    private void handleSaveEnseignant(ActionEvent ev) {
        String matricule = matriculeField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();

        try {
            if (isEditMode) {
                enseignantController.updateEnseignant(matricule, nom, prenom);
            } else {
                enseignantController.addEnseignant(matricule, nom, prenom);
            }
            setEditMode(false, null);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Opération réussie !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<Enseignant> enseignants = enseignantController.loadEnseignants();
            for (Enseignant ens : enseignants) {
                tableModel.addRow(new Object[] { ens.getMatricule(), ens.getNom(), ens.getPrenom(), "" });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }

    // --- Actions Renderer/Editor ---

    class ActionsRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        private JButton editBtn = new JButton("Editer");
        private JButton delBtn = new JButton("Suppr");

        public ActionsRenderer() {
            setOpaque(true);
            setBackground(StyleUtil.COLOR_WHITE);
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
            StyleUtil.styleButton(editBtn, StyleUtil.COLOR_PRIMARY);
            StyleUtil.styleButton(delBtn, StyleUtil.COLOR_DANGER);
            editBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
            delBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
            add(editBtn);
            add(delBtn);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (isSelected)
                setBackground(table.getSelectionBackground());
            else
                setBackground(table.getBackground());
            return this;
        }
    }

    class ActionsEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton editBtn;
        private JButton delBtn;
        private String currentMatricule;
        private String currentNom;
        private String currentPrenom;

        public ActionsEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
            panel.setOpaque(true);
            editBtn = new JButton("Editer");
            delBtn = new JButton("Suppr");

            StyleUtil.styleButton(editBtn, StyleUtil.COLOR_PRIMARY);
            StyleUtil.styleButton(delBtn, StyleUtil.COLOR_DANGER);
            editBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
            delBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));

            editBtn.addActionListener(e -> {
                setEditMode(true, new Enseignant(currentMatricule, currentNom, currentPrenom));
                fireEditingStopped();
            });

            delBtn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(panel, "Supprimer l'enseignant " + currentMatricule + " ?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        enseignantController.deleteEnseignant(currentMatricule);
                        refreshTable();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, "Erreur : " + ex.getMessage());
                    }
                }
                fireEditingStopped();
            });

            panel.add(editBtn);
            panel.add(delBtn);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            currentMatricule = (String) table.getValueAt(row, 0);
            currentNom = (String) table.getValueAt(row, 1);
            currentPrenom = (String) table.getValueAt(row, 2);
            panel.setBackground(table.getSelectionBackground());
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
}
