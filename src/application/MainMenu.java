package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenu extends JPanel{
	private static final long serialVersionUID = 3180049357693339346L;
	private Application app;
	private JLabel lblTitle;
	private JButton btnStart, btnQuit;
	
	public MainMenu(Application application) {
		setBackground(Color.BLACK);
		app = application;
		setLayout(null);
		setBounds(0, 0, 800, 600);
		
		lblTitle = new JLabel("Blocks");
		lblTitle.setOpaque(false);
		lblTitle.setForeground(Color.ORANGE);
		lblTitle.setFont(new Font("Arial Black", Font.BOLD, 60));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(0, 0, 800, 90);
		add(lblTitle);
		
		JLabel lblStart = new JLabel("START");
		lblStart.setLocation(221, 2);
		lblStart.setHorizontalAlignment(SwingConstants.CENTER);
		lblStart.setBackground(Color.DARK_GRAY);
		lblStart.setForeground(Color.ORANGE);
		lblStart.setOpaque(true);
		lblStart.setFont(new Font("Arial Black", Font.BOLD, 40));
		btnStart = new JButton();
		btnStart.setMargin(new Insets(0, 0, 0, 0));
		btnStart.setLayout(new BorderLayout());
		btnStart.setBounds(275, 176, 250, 90);
		btnStart.setOpaque(true);
		btnStart.setBackground(Color.DARK_GRAY);
		btnStart.addActionListener(e -> app.newGame());
		btnStart.add(lblStart, BorderLayout.CENTER);
		add(btnStart);
		
		JLabel lblQuit = new JLabel("QUIT");
		lblQuit.setBounds(341, 454, 126, 40);
		add(lblQuit);
		lblQuit.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuit.setBackground(Color.DARK_GRAY);
		lblQuit.setForeground(Color.ORANGE);
		lblQuit.setOpaque(true);
		lblQuit.setFont(new Font("Arial Black", Font.BOLD, 18));
		btnQuit = new JButton();
		btnQuit.setMargin(new Insets(0, 0, 0, 0));
		btnQuit.setLayout(new BorderLayout());
		btnQuit.setBounds(335, 450, 130, 44);
		btnQuit.setOpaque(true);
		btnQuit.setBackground(Color.DARK_GRAY);
		btnQuit.addActionListener(e -> System.exit(0));
		btnQuit.add(lblQuit, BorderLayout.CENTER);
		add(btnQuit);
	}
}
