package textbook.view;

import textbook.controller.UEController;
import textbook.model.UE;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class UEPanel extends JPanel {
    private final UEController ueController;
    private JTextField codeField;
    private JTextField intituleField;
    private JButton addButton;
    private JButton cancelButton;
    private JTable ueTable;
    private DefaultTableModel tableModel;

    private boolean isEditMode = false;

    public UEPanel() {
        this.ueController = new UEController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initUI();
        refreshTable();
    }

    private void initUI() {
        // --- Titre ---
        JLabel titleLabel = new JLabel("Gestion des Unités d'Enseignement (UE)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // --- Formulaire d'ajout (Center North) ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Code UE :"), gbc);
        gbc.gridx = 1;
        codeField = new JTextField(10);
        formPanel.add(codeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Intitulé :"), gbc);
        gbc.gridx = 1;
        intituleField = new JTextField(20);
        formPanel.add(intituleField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(40, 167, 69)); // Vert
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(this::handleSaveUE);
        btnPanel.add(addButton);

        cancelButton = new JButton("Annuler");
        cancelButton.setVisible(false);
        cancelButton.addActionListener(e -> setEditMode(false, null));
        btnPanel.add(cancelButton);

        formPanel.add(btnPanel, gbc);

        // --- Tableau ---
        String[] columns = { "Code UE", "Intitulé", "Actions" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        ueTable = new JTable(tableModel);

        ueTable.getColumnModel().getColumn(2).setCellRenderer(new ActionsRenderer());
        ueTable.getColumnModel().getColumn(2).setCellEditor(new ActionsEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(ueTable);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void setEditMode(boolean edit, UE ue) {
        this.isEditMode = edit;
        if (edit && ue != null) {
            codeField.setText(ue.getCodeUE());
            codeField.setEditable(false);
            intituleField.setText(ue.getIntitule());
            addButton.setText("Mettre à jour");
            addButton.setBackground(new Color(0, 123, 255)); // Bleu
            cancelButton.setVisible(true);
        } else {
            codeField.setText("");
            codeField.setEditable(true);
            intituleField.setText("");
            addButton.setText("Ajouter");
            addButton.setBackground(new Color(40, 167, 69)); // Vert
            cancelButton.setVisible(false);
        }
    }

    private void handleSaveUE(ActionEvent e) {
        String code = codeField.getText();
        String intitule = intituleField.getText();

        try {
            if (isEditMode) {
                ueController.updateUE(code, intitule);
                JOptionPane.showMessageDialog(this, "UE mise à jour !");
            } else {
                ueController.addUE(code, intitule);
                JOptionPane.showMessageDialog(this, "UE ajoutée !");
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
            List<UE> ues = ueController.loadUE();
            for (UE ue : ues) {
                tableModel.addRow(new Object[] { ue.getCodeUE(), ue.getIntitule(), "" });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
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
        private String currentCode;
        private String currentIntitule;

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
                setEditMode(true, new UE(currentCode, currentIntitule));
                fireEditingStopped();
            });

            delBtn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(panel, "Supprimer " + currentCode + " ?", "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        ueController.deleteUE(currentCode);
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
            currentCode = (String) table.getValueAt(row, 0);
            currentIntitule = (String) table.getValueAt(row, 1);
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
}
