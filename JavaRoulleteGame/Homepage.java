
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.EventQueue;

public class Homepage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel title;
	private JPanel mainPanel;//Tha perilambanei ola ta alla panels
	private JTextField inputField;
	private JButton startButton;//Mas metaferei sthn kartela tou paxnidiou
	private JButton logOutButton;//Mas metaferei sthn kartela ths syndeshs/eggrafhs
	private JLabel name;
	private JLabel score;
	private JLabel highestScore;
	private JLabel category;
	/**
	 * Create the frame.
	 */
	public Homepage(final Player aPlayer) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBounds(641, 11, 20, 20);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		//setContentPane(contentPane);

		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(231, 254, 207));
		mainPanel.setLayout(null);
		mainPanel.add(contentPane);

		this.setContentPane(mainPanel);
		name = new JLabel("Welcome, " + aPlayer.getName() + "!");
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setFont(new Font("Tahoma", Font.PLAIN, 15));
		name.setBounds(10, 142, 464, 27);
		mainPanel.add(name);
		startButton = new JButton("Start Game");
		startButton.setBackground(new Color(0 , 0, 0));
		startButton.setForeground(new Color(55, 55, 55));
		startButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		startButton.setBounds(165, 195, 153, 47);
		mainPanel.add(startButton);
		logOutButton = new JButton("Log Out");
		logOutButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		logOutButton.setForeground(new Color(0, 0, 0));
		logOutButton.setBackground(new Color(55, 55, 55));
		logOutButton.setBounds(188, 271, 105, 27);
		mainPanel.add(logOutButton);
		title = new JLabel("ROULETTE");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 25));
		title.setBounds(165, 89, 153, 33);
		mainPanel.add(title);
		score = new JLabel("Current Score: " + aPlayer.getScore());
		score.setHorizontalAlignment(SwingConstants.RIGHT);
		score.setBounds(277, 17, 186, 14);
		mainPanel.add(score);
		category = new JLabel("Category: " + aPlayer.getCategory());
		category.setBounds(20, 17, 153, 14);
		mainPanel.add(category);
		highestScore = new JLabel("Your personal best is : " + aPlayer.getHighestScore());
		highestScore.setBounds(20, 361, 443, 17);
		mainPanel.add(highestScore);
		highestScore.setHorizontalAlignment(SwingConstants.CENTER);
		highestScore.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//Log Out Button
		logOutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//new AccountFrame();
				aPlayer.setName(null);
				aPlayer.setCode(null);
				aPlayer.setCategory(null);
				aPlayer.setScore(0);
				Homepage.this.dispose(); //Kleinei to homepage
			}

		});
		
		//Leitourgies Koumpiwn
		
		//Start Game Button
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//new Game(aPlayer);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							GameDesign window = new GameDesign();
							window.getFrame().setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				Homepage.this.dispose(); //Kleinei to homepage
			}
			
		});
		
		//Rythmiseis parathyrou
		
		this.setVisible(true);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Home");
		
	}
	
}