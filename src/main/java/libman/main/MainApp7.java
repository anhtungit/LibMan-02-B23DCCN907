package libman.main;

import libman.view.LoginView7;

import javax.swing.*;

public class MainApp7 {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new LoginView7().setVisible(true);
        });
    }
}
