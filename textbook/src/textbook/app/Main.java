package textbook.app;

import textbook.util.DatabaseInitializer;
import textbook.view.MainWindow;

import java.sql.SQLException;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        System.out.println("Démarrage de TextBook...");
        
        // Initialiser la base de données
        DatabaseInitializer.initialize();
        
        // Lancer l'interface graphique
        SwingUtilities.invokeLater(() -> {
            MainWindow window = null;
            try {
                window = new MainWindow();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            window.setVisible(true);
        });
    }
}
