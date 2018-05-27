package application;

import game.GamePanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Application extends JFrame {
    private static final long serialVersionUID = 4310828814326284348L;
    public static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
    private JPanel panelMain, contentPane;
    private MainMenu mainMenu;
    private GamePanel gamePanel;


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Application frame = new Application();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public Application() {
        setTitle("Blocks");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 285, 239);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        contentPane.add(panelMain, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);

        backToMenu();
    }

    public void backToMenu() {
        mainMenu = new MainMenu(this);
        getContentPane().remove(panelMain);
        panelMain = mainMenu;
        getContentPane().add(panelMain);
        validate();
        repaint();
    }

    public void newGame() {
        gamePanel = new GamePanel();
        getContentPane().remove(panelMain);
        panelMain = gamePanel;
        getContentPane().add(panelMain);
        validate();
    }
}
