package textbook.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class StyleUtil {

    // --- Colors (Flat Design Palette) ---
    public static final Color COLOR_PRIMARY = new Color(52, 152, 219); // Peter River Blue
    public static final Color COLOR_SUCCESS = new Color(46, 204, 113); // Emerald Green
    public static final Color COLOR_DANGER = new Color(231, 76, 60); // Alizarin Red
    public static final Color COLOR_WARNING = new Color(241, 196, 15); // Sun Flower Yellow
    public static final Color COLOR_BG = new Color(236, 240, 241); // Clouds (Light Gray)
    public static final Color COLOR_TEXT = new Color(44, 62, 80); // Midnight Blue
    public static final Color COLOR_WHITE = Color.WHITE;

    // --- Fonts ---
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_TABLE = new Font("Segoe UI", Font.PLAIN, 13);

    /**
     * Styles a standard button with flat design.
     */
    public static void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(COLOR_WHITE);
        button.setFont(FONT_BUTTON);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(8, 15, 8, 15));

        // Hover effect simplified (for Swing)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    /**
     * Styles a table for a modern look.
     */
    public static void styleTable(JTable table) {
        table.setFont(FONT_TABLE);
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(52, 152, 219, 100));
        table.setSelectionForeground(COLOR_TEXT);
        table.setBackground(COLOR_WHITE);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_HEADER);
        header.setBackground(COLOR_TEXT);
        header.setForeground(COLOR_WHITE);
        header.setPreferredSize(new Dimension(0, 40));

        // Align headers to left
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
    }

    /**
     * Styles a text field.
     */
    public static void styleTextField(JTextField textField) {
        textField.setFont(FONT_LABEL);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }
}
