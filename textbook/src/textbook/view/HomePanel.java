package textbook.view;

import textbook.controller.SeanceController;
import textbook.model.Seance;
import textbook.util.ReportUtil;
import textbook.util.StyleUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HomePanel extends JPanel {
    private final SeanceController seanceController;
    private JTable seanceTable;
    private DefaultTableModel tableModel;
    private List<Seance> currentSeances = new ArrayList<>();

    public HomePanel() {
        this.seanceController = new SeanceController();
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(25, 25, 25, 25));
        setBackground(StyleUtil.COLOR_WHITE);

        initUI();
        refreshTable();
    }

    private void initUI() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(StyleUtil.COLOR_WHITE);

        JLabel titleLabel = new JLabel("Tableau de bord - Rapports de Séances");
        titleLabel.setFont(StyleUtil.FONT_TITLE);
        titleLabel.setForeground(StyleUtil.COLOR_PRIMARY);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton downloadAllBtn = new JButton("Télécharger tout les rapports");
        StyleUtil.styleButton(downloadAllBtn, StyleUtil.COLOR_PRIMARY);
        downloadAllBtn.addActionListener(e -> ReportUtil.exportAllSeances(this, currentSeances));
        headerPanel.add(downloadAllBtn, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        String[] columns = { "Date", "UE", "Enseignant", "Salle", "Résumé", "Actions" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        seanceTable = new JTable(tableModel);
        StyleUtil.styleTable(seanceTable);

        seanceTable.getColumnModel().getColumn(5).setMinWidth(80);
        seanceTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        seanceTable.getColumnModel().getColumn(5).setCellRenderer(new ActionsRenderer());
        seanceTable.getColumnModel().getColumn(5).setCellEditor(new ActionsEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(seanceTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollPane.getViewport().setBackground(StyleUtil.COLOR_WHITE);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        try {
            currentSeances = seanceController.loadSeances();
            for (Seance s : currentSeances) {
                tableModel.addRow(new Object[] {
                        s.getDateSeance().toString(),
                        s.getUeIntitule(),
                        s.getEnseignantNomComplet(),
                        s.getSalle(),
                        s.getResume(),
                        ""
                });
            }
        } catch (Exception ex) {
            // Silently fail or show small label
        }
    }

    class ActionsRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        private JButton viewBtn = new JButton("Voir");

        public ActionsRenderer() {
            setOpaque(true);
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
            StyleUtil.styleButton(viewBtn, StyleUtil.COLOR_PRIMARY);
            viewBtn.setFont(new Font("Segoe UI", Font.BOLD, 10));
            add(viewBtn);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return this;
        }
    }

    class ActionsEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton viewBtn;
        private int currentRow;

        public ActionsEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
            panel.setOpaque(true);
            viewBtn = new JButton("Voir");
            StyleUtil.styleButton(viewBtn, StyleUtil.COLOR_PRIMARY);
            viewBtn.setFont(new Font("Segoe UI", Font.BOLD, 10));

            viewBtn.addActionListener(e -> {
                ReportUtil.showSeanceDetails(HomePanel.this, currentSeances.get(currentRow));
                fireEditingStopped();
            });

            panel.add(viewBtn);
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
