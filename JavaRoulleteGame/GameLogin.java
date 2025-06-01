import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class GameLogin {
    private static String[][] users = new String[10][2]; // 2D array to store up to 10 users
    private static int userCount = 0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login - " + LocalDate.now()); // Add current date to title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Increased the size of the frame

        // Use Nimbus Look and Feel for modern appearance
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setLayout(new GridBagLayout()); // Set layout manager for the frame
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(45, 45, 45)); // Set background color for the panel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(final JPanel panel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Welcome to Roulet");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(titleLabel, constraints);

        constraints.gridwidth = 1; // Reset the gridwidth

        JLabel userLabel = new JLabel("Name:");
        userLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(userLabel, constraints);

        final JTextField userText = new JTextField(20);
        userText.setBackground(new Color(70, 70, 70));
        userText.setForeground(Color.WHITE);
        userText.setBorder(BorderFactory.createCompoundBorder(
                userText.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        constraints.gridx = 1;
        panel.add(userText, constraints);

        JLabel passwordLabel = new JLabel("Code:");
        passwordLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passwordLabel, constraints);

        final JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBackground(new Color(70, 70, 70));
        passwordText.setForeground(Color.WHITE);
        passwordText.setBorder(BorderFactory.createCompoundBorder(
                passwordText.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        constraints.gridx = 1;
        panel.add(passwordText, constraints);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(loginButton, constraints);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(new Color(30, 144, 255));
        signUpButton.setForeground(Color.BLACK);
        signUpButton.setFocusPainted(false);
        constraints.gridx = 1;
        panel.add(signUpButton, constraints);

        // Use lambda expressions for ActionListener
        loginButton.addActionListener(e -> {
            String name = userText.getText();
            String code = new String(passwordText.getPassword());
            if (checkCredentials(name, code)) {
                JOptionPane.showMessageDialog(panel, "Login successful!");
                Player p1 = new Player(name, code, -1, -1); // Adjust as per your Player class
                new Homepage(p1); // Adjust as per your Homepage class
            } else {
                JOptionPane.showMessageDialog(panel, "Sorry, wrong name or password.");
            }
        });

        signUpButton.addActionListener(e -> showSignUpWindow());
    }

    private static void showSignUpWindow() {
        JFrame signUpFrame = new JFrame("Sign Up - " + LocalDate.now()); // Add current date to title
        signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signUpFrame.setSize(400, 300);

        JPanel signUpPanel = new JPanel(new GridBagLayout());
        signUpPanel.setBackground(new Color(45, 45, 45)); // Set background color for the panel
        signUpPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel
        signUpFrame.add(signUpPanel);
        placeSignUpComponents(signUpPanel, signUpFrame);

        signUpFrame.setVisible(true);
    }

    private static void placeSignUpComponents(final JPanel panel, final JFrame signUpFrame) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;

        JLabel userLabel = new JLabel("Name:");
        userLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(userLabel, constraints);

        final JTextField userText = new JTextField(20);
        userText.setBackground(new Color(70, 70, 70));
        userText.setForeground(Color.WHITE);
        userText.setBorder(BorderFactory.createCompoundBorder(
                userText.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        constraints.gridx = 1;
        panel.add(userText, constraints);

        JLabel passwordLabel = new JLabel("Code:");
        passwordLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);

        final JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBackground(new Color(70, 70, 70));
        passwordText.setForeground(Color.WHITE);
        passwordText.setBorder(BorderFactory.createCompoundBorder(
                passwordText.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        constraints.gridx = 1;
        panel.add(passwordText, constraints);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(new Color(30, 144, 255));
        signUpButton.setForeground(Color.BLACK);
        signUpButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(signUpButton, constraints);

        // Use lambda expression for ActionListener
        signUpButton.addActionListener(e -> {
            String name = userText.getText();
            String code = new String(passwordText.getPassword());
            if (addUser(name, code)) {
                JOptionPane.showMessageDialog(panel, "Sign up successful! Please log in.");
                Player p1 = new Player(name, code, -1, -1); // Adjust as per your Player class
                new Homepage(p1); // Adjust as per your Homepage class
                signUpFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(panel, "Sign up failed. User limit reached or user already exists.");
            }
        });
    }

    private static boolean checkCredentials(String name, String code) {
        for (int i = 0; i < userCount; i++) {
            if (users[i][0].equals(name) && users[i][1].equals(code)) {
                return true;
            }
        }
        return false;
    }

    private static boolean addUser(String name, String code) {
        if (userCount < users.length) {
            for (int i = 0; i < userCount; i++) {
                if (users[i][0].equals(name)) {
                    return false; // User already exists
                }
            }
            users[userCount][0] = name;
            users[userCount][1] = code;
            userCount++;
            return true;
        }
        return false; // User limit reached
    }
}
