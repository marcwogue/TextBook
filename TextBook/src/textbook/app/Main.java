package textbook.app;

import textbook.util.DatabaseInitializer;
import textbook.view.MainWindow;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        System.out.println("Démarrage de TextBook...");
        
        // Initialiser la base de données
        DatabaseInitializer.initialize();
        
        // Lancer l'interface graphique
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}
