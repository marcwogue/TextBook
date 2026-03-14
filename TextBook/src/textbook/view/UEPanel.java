package textbook.view;

import textbook.controller.UEController;
import textbook.model.UE;
import textbook.util.StyleUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        JLabel titleLabel = new JLabel("Gestion des Unités d'Enseignement");
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
        JLabel l1 = new JLabel("Code UE");
        l1.setFont(StyleUtil.FONT_LABEL);
        formPanel.add(l1, gbc);

        gbc.gridx = 1;
        codeField = new JTextField(12);
        StyleUtil.styleTextField(codeField);
        formPanel.add(codeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel l2 = new JLabel("Intitulé");
        l2.setFont(StyleUtil.FONT_LABEL);
        formPanel.add(l2, gbc);

        gbc.gridx = 1;
        intituleField = new JTextField(25);
        StyleUtil.styleTextField(intituleField);
        formPanel.add(intituleField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnPanel.setBackground(StyleUtil.COLOR_WHITE);

        addButton = new JButton("Ajouter");
        StyleUtil.styleButton(addButton, StyleUtil.COLOR_SUCCESS);
        addButton.addActionListener(this::handleSaveUE);
        btnPanel.add(addButton);

        cancelButton = new JButton("Annuler");
        StyleUtil.styleButton(cancelButton, new Color(149, 165, 166)); // Gray
        cancelButton.setVisible(false);
        cancelButton.addActionListener(e -> setEditMode(false, null));
        btnPanel.add(cancelButton);

        formPanel.add(btnPanel, gbc);

        // --- Table Section ---
        String[] columns = { "Code UE", "Intitulé", "Actions" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        ueTable = new JTable(tableModel);
        StyleUtil.styleTable(ueTable);

        ueTable.getColumnModel().getColumn(2).setCellRenderer(new ActionsRenderer());
        ueTable.getColumnModel().getColumn(2).setCellEditor(new ActionsEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(ueTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollPane.getViewport().setBackground(StyleUtil.COLOR_WHITE);

        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(StyleUtil.COLOR_WHITE);
        contentPanel.add(formPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void setEditMode(boolean edit, UE ue) {
        this.isEditMode = edit;
        if (edit && ue != null) {
            codeField.setText(ue.getCodeUE());
            codeField.setEditable(false);
            intituleField.setText(ue.getIntitule());
            addButton.setText("Mettre à jour");
            addButton.setBackground(StyleUtil.COLOR_PRIMARY);
            cancelButton.setVisible(true);
        } else {
            codeField.setText("");
            codeField.setEditable(true);
            intituleField.setText("");
            addButton.setText("Ajouter");
            addButton.setBackground(StyleUtil.COLOR_SUCCESS);
            cancelButton.setVisible(false);
        }
    }

    private void handleSaveUE(ActionEvent e) {
        String code = codeField.getText();
        String intitule = intituleField.getText();

        try {
            if (isEditMode) {
                ueController.updateUE(code, intitule);
            } else {
                ueController.addUE(code, intitule);
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
            List<UE> ues = ueController.loadUE();
            for (UE ue : ues) {
                tableModel.addRow(new Object[] { ue.getCodeUE(), ue.getIntitule(), "" });
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
        private String currentCode;
        private String currentIntitule;

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
                setEditMode(true, new UE(currentCode, currentIntitule));
                fireEditingStopped();
            });

            delBtn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(panel, "Supprimer l'UE " + currentCode + " ?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
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
            panel.setBackground(table.getSelectionBackground());
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
}
