import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class GameDesign {

    private JFrame frame;
    private JPanel panel;
    private JLabel balanceLabel;
    private JLabel resultLabel;
    private JLabel betsTitleLabel;
    private JLabel lastFiveNumbersLabel;
    private int balance = 1000; // Initial balance
    private JButton spinButton;
    private Set<Integer> selectedNumbers = new HashSet<>(); // Selected numbers set
    private int currentBet = 0;
    private int selectedBetAmount = 0; // Amount selected for the current bet
    private Map<Integer, Integer> bets = new LinkedHashMap<>(); // Map to store bets with their amounts
    private JPanel betsPanel; // Panel to hold the individual bet panels
    private JScrollPane betsScrollPane;
    private List<Integer> lastFiveNumbers = new LinkedList<>(); // List to store the last 5 numbers

    public JFrame getFrame() {
        return frame;
    }

    public GameDesign() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Roulette Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(null);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRouletteBoard(g);
            }
        };
        panel.setBounds(50, 50, 900, 600);
        panel.setLayout(null);
        frame.add(panel);

        balanceLabel = new JLabel("Balance: " + balance + "€");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setBounds(750, 10, 150, 30);
        panel.add(balanceLabel);

        resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setBounds(750, 50, 150, 30);
        panel.add(resultLabel);

        betsTitleLabel = new JLabel("Bets:");
        betsTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        betsTitleLabel.setBounds(50, 370, 50, 30);
        panel.add(betsTitleLabel);

        betsPanel = new JPanel();
        betsPanel.setLayout(new BoxLayout(betsPanel, BoxLayout.Y_AXIS));

        betsScrollPane = new JScrollPane(betsPanel);
        betsScrollPane.setBounds(50, 400, 300, 150);
        panel.add(betsScrollPane);

        lastFiveNumbersLabel = new JLabel("Last 5 Numbers: ");
        lastFiveNumbersLabel.setFont(new Font("Arial", Font.BOLD, 16));
        lastFiveNumbersLabel.setBounds(50, 560, 300, 30);
        panel.add(lastFiveNumbersLabel);

        addBetButtons();
        addSpinButton();
        addNumberButtons();

        frame.setVisible(true);
    }

    private void drawRouletteBoard(Graphics g) {
        int xOffset = 50;
        int yOffset = 50;
        int cellWidth = 50;
        int cellHeight = 50;

        String[][] numbers = {
            {"0"},
            {"3", "6", "9", "12", "15", "18", "21", "24", "27", "30", "33", "36"},
            {"2", "5", "8", "11", "14", "17", "20", "23", "26", "29", "32", "35"},
            {"1", "4", "7", "10", "13", "16", "19", "22", "25", "28", "31", "34"}
        };

        for (int row = 0; row < numbers.length; row++) {
            for (int col = 0; col < numbers[row].length; col++) {
                int number = Integer.parseInt(numbers[row][col]);
                Color color = getColorForNumber(number);
                g.setColor(color);
                g.fillRect(xOffset + col * cellWidth, yOffset + row * cellHeight, cellWidth, cellHeight);
                g.setColor(Color.BLACK);
                g.drawString(numbers[row][col], xOffset + col * cellWidth + 20, yOffset + row * cellHeight + 30);
            }
        }
    }

    private Color getColorForNumber(int number) {
        if (number == 0) {
            return Color.GREEN;
        } else if (isRedNumber(number)) {
            return Color.RED;
        } else {
            return Color.BLACK;
        }
    }

    private boolean isRedNumber(int number) {
        return (number > 0 && number <= 10 && number % 2 != 0) || 
               (number > 11 && number <= 18 && number % 2 == 0) || 
               (number > 19 && number <= 28 && number % 2 != 0) || 
               (number > 29 && number <= 36 && number % 2 == 0);
    }

    private void addBetButtons() {
        String[] betLabels = {"1€", "5€", "10€", "100€", "Clear"};
        int[] betValues = {1, 5, 10, 100, 0};

        int x = 750;
        int y = 200;

        for (int i = 0; i < betLabels.length; i++) {
            JButton button = new JButton(betLabels[i]);
            button.setBounds(x, y + (i * 50), 80, 30);
            final int betValue = betValues[i];
            button.addActionListener(e -> {
                if (balance == 0) {
                    JOptionPane.showMessageDialog(frame, "Game over! You are out of money.");
                    return;
                }

                if (betValue == 0) {
                    bets.clear();
                    currentBet = 0;
                    selectedNumbers.clear();
                    resetNumberButtonColors();
                } else {
                    if (balance >= betValue) {
                        selectedBetAmount = betValue;
                    } else {
                        JOptionPane.showMessageDialog(frame, "You don't have enough balance to bet €" + betValue);
                        return;
                    }
                }
                updateCurrentBet();
                balanceLabel.setText("Balance: " + (balance - currentBet) + "€");
                updateBetButtonsEnabledState();
                updateBetsLabel();
            });
            panel.add(button);
        }
    }

    private void updateCurrentBet() {
        currentBet = 0;
        for (int amount : bets.values()) {
            currentBet += amount;
        }
    }

    private void updateBetButtonsEnabledState() {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                String text = button.getText();
                if (text.endsWith("€")) {
                    try {
                        int betValue = Integer.parseInt(text.replace("€", ""));
                        if (betValue > 0 && betValue <= balance) {
                            button.setEnabled(true);
                        } else {
                            button.setEnabled(false);
                        }
                    } catch (NumberFormatException e) {
                        button.setEnabled(true);
                    }
                }
            }
        }
    }

    private void addSpinButton() {
        spinButton = new JButton("Spin");
        spinButton.setBounds(750, 500, 100, 30);
        spinButton.addActionListener(e -> spinWheel());
        panel.add(spinButton);
    }

    private void spinWheel() {
        if (currentBet == 0) {
            JOptionPane.showMessageDialog(frame, "Please place a bet.");
            return;
        }
        if (balance < currentBet) {
            JOptionPane.showMessageDialog(frame, "You don't have enough balance to place the bet.");
            return;
        }
        if (selectedNumbers.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please select a number to bet on.");
            return;
        }

        balance -= currentBet;
        int result = spinResult();
        resultLabel.setText("Result: " + result);

        int winAmount = 0;
        if (bets.containsKey(result)) {
            winAmount = bets.get(result) * 36;
        }

        if (winAmount > 0) {
            balance += winAmount;
            JOptionPane.showMessageDialog(frame, "You won: " + winAmount + "€");
        } else {
            JOptionPane.showMessageDialog(frame, "You lost this round.");
        }

        // Update last five numbers
        updateLastFiveNumbers(result);

        bets.clear();
        currentBet = 0;
        selectedNumbers.clear();
        resetNumberButtonColors();
        balanceLabel.setText("Balance: " + balance + "€");

        if (balance == 0) {
            JOptionPane.showMessageDialog(frame, "Game over! You are out of money.");
            balanceLabel.setText("Balance: 0€");
            disableBetButtons();
            spinButton.setEnabled(false);
        } else {
            enableBetButtons();
            currentBet = 0;
        }
        updateBetsLabel();
        updateLastFiveNumbersLabel();
    }

    private void updateLastFiveNumbers(int result) {
        if (lastFiveNumbers.size() == 5) {
            lastFiveNumbers.remove(0);
        }
        lastFiveNumbers.add(result);
    }

    private void updateLastFiveNumbersLabel() {
        StringBuilder sb = new StringBuilder("Last 5 Numbers: ");
        // Προσθέτει τα τελευταία πέντε νούμερα στο StringBuilder
        for (int i = lastFiveNumbers.size() - 1; i >= 0; i--) {
            sb.append(lastFiveNumbers.get(i));
            if (i > 0) {
                sb.append(", ");
            }
        }
        lastFiveNumbersLabel.setText(sb.toString());
    }

    private int spinResult() {
        return (int) (Math.random() * 37);
    }

    private void enableBetButtons() {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                String text = button.getText();
                if (text.endsWith("€")) {
                    try {
                        int betValue = Integer.parseInt(text.replace("€", ""));
                        if (betValue > 0 && betValue <= balance) {
                            button.setEnabled(true);
                        } else {
                            button.setEnabled(false);
                        }
                    } catch (NumberFormatException e) {
                        button.setEnabled(true);
                    }
                }
            }
        }
    }

    private void disableBetButtons() {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                String text = button.getText();
                if (text.endsWith("€")) {
                    button.setEnabled(false);
                }
            }
        }
    }

    private void addNumberButtons() {
        int xOffset = 50;
        int yOffset = 50;
        int cellWidth = 50;
        int cellHeight = 50;

        String[][] numbers = {
            {"0"},
            {"3", "6", "9", "12", "15", "18", "21", "24", "27", "30", "33", "36"},
            {"2", "5", "8", "11", "14", "17", "20", "23", "26", "29", "32", "35"},
            {"1", "4", "7", "10", "13", "16", "19", "22", "25", "28", "31", "34"}
        };

        for (int row = 0; row < numbers.length; row++) {
            for (int col = 0; col < numbers[row].length; col++) {
                JButton button = new JButton(numbers[row][col]);
                button.setBounds(xOffset + col * cellWidth, yOffset + row * cellHeight, cellWidth, cellHeight);
                button.addActionListener(e -> {
                    if (selectedBetAmount == 0) {
                        JOptionPane.showMessageDialog(frame, "Please select a bet amount first.");
                        return;
                    }

                    int number = Integer.parseInt(button.getText());
                    int betAmount = selectedBetAmount;

                    if (selectedNumbers.contains(number)) {
                        int currentBetOnNumber = bets.get(number);
                        if (balance >= (currentBet + betAmount)) {
                            currentBet += betAmount;
                            bets.put(number, currentBetOnNumber + betAmount);
                            button.setBackground(getColorForNumber(number));
                        } else {
                            JOptionPane.showMessageDialog(frame, "You don't have enough balance for this bet.");
                            return;
                        }
                    } else {
                        if (balance >= (currentBet + betAmount)) {
                            selectedNumbers.add(number);
                            bets.put(number, betAmount);
                            currentBet += betAmount;
                            button.setBackground(getColorForNumber(number));
                        } else {
                            JOptionPane.showMessageDialog(frame, "You don't have enough balance for this bet.");
                            return;
                        }
                    }

                    balanceLabel.setText("Balance: " + (balance - currentBet) + "€");
                    updateBetsLabel();
                });
                panel.add(button);
            }
        }
    }

    private void resetNumberButtonColors() {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                String text = button.getText();
                try {
                    int number = Integer.parseInt(text);
                    if (selectedNumbers.contains(number)) {
                        button.setBackground(getColorForNumber(number));
                    } else {
                        button.setBackground(null);
                    }
                } catch (NumberFormatException e) {
                    // Ignore non-number buttons
                }
            }
        }
    }

    private void updateBetsLabel() {
        betsPanel.removeAll();
        for (Map.Entry<Integer, Integer> entry : bets.entrySet()) {
            JPanel betPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel betLabel = new JLabel("Number " + entry.getKey() + ": " + entry.getValue() + "€");
            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener(e -> {
                int number = entry.getKey();
                selectedNumbers.remove(number);
                currentBet -= bets.get(number);
                bets.remove(number);
                balanceLabel.setText("Balance: " + (balance - currentBet) + "€");
                updateBetsLabel();
                resetNumberButtonColors();
            });
            betPanel.add(betLabel);
            betPanel.add(removeButton);
            betsPanel.add(betPanel);
        }
        betsPanel.revalidate();
        betsPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameDesign::new);
    }
}
