package textbook.view;

import textbook.controller.EnseignantController;
import textbook.model.Enseignant;

import javax.swing.*;
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
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initUI();
        refreshTable();
    }

    private void initUI() {
        // --- Titre ---
        JLabel titleLabel = new JLabel("Gestion des Enseignants", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // --- Formulaire d'ajout ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Matricule :"), gbc);
        gbc.gridx = 1;
        matriculeField = new JTextField(10);
        formPanel.add(matriculeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        nomField = new JTextField(20);
        formPanel.add(nomField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1;
        prenomField = new JTextField(20);
        formPanel.add(prenomField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(40, 167, 69)); // Vert
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(this::handleSaveEnseignant);
        btnPanel.add(addButton);

        cancelButton = new JButton("Annuler");
        cancelButton.setVisible(false);
        cancelButton.addActionListener(e -> setEditMode(false, null));
        btnPanel.add(cancelButton);

        formPanel.add(btnPanel, gbc);

        // --- Tableau ---
        String[] columns = { "Matricule", "Nom", "Prénom", "Actions" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        enseignantTable = new JTable(tableModel);

        enseignantTable.getColumnModel().getColumn(3).setCellRenderer(new ActionsRenderer());
        enseignantTable.getColumnModel().getColumn(3).setCellEditor(new ActionsEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(enseignantTable);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void setEditMode(boolean edit, Enseignant e) {
        this.isEditMode = edit;
        if (edit && e != null) {
            matriculeField.setText(e.getMatricule());
            matriculeField.setEditable(false);
            nomField.setText(e.getNom());
            prenomField.setText(e.getPrenom());
            addButton.setText("Mettre à jour");
            addButton.setBackground(new Color(0, 123, 255)); // Bleu
            cancelButton.setVisible(true);
        } else {
            matriculeField.setText("");
            matriculeField.setEditable(true);
            nomField.setText("");
            prenomField.setText("");
            addButton.setText("Ajouter");
            addButton.setBackground(new Color(40, 167, 69)); // Vert
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
                JOptionPane.showMessageDialog(this, "Enseignant mis à jour !");
            } else {
                enseignantController.addEnseignant(matricule, nom, prenom);
                JOptionPane.showMessageDialog(this, "Enseignant ajouté !");
            }
            setEditMode(false, null);
            refreshTable();
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
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des enseignants : " + e.getMessage());
        }
    }

    // --- Actions Cell Renderer/Editor ---

    class ActionsRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        private JButton editBtn = new JButton("Editer");
        private JButton delBtn = new JButton("Suppr");

        public ActionsRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            editBtn.setBackground(new Color(0, 123, 255));
            editBtn.setForeground(Color.WHITE);
            delBtn.setBackground(new Color(220, 53, 69));
            delBtn.setForeground(Color.WHITE);
            add(editBtn);
            add(delBtn);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
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
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            editBtn = new JButton("Editer");
            delBtn = new JButton("Suppr");

            editBtn.setBackground(new Color(0, 123, 255));
            editBtn.setForeground(Color.WHITE);
            delBtn.setBackground(new Color(220, 53, 69));
            delBtn.setForeground(Color.WHITE);

            editBtn.addActionListener(e -> {
                setEditMode(true, new Enseignant(currentMatricule, currentNom, currentPrenom));
                fireEditingStopped();
            });

            delBtn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(panel, "Supprimer " + currentMatricule + " ?",
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
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
}
