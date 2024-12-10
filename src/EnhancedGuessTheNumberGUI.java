import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class EnhancedGuessTheNumberGUI {
    private int targetNumber;
    private int maxRange;
    private int maxAttempts;
    private int attempts;
    private int totalGames;
    private int totalAttempts;
    private final ArrayList<String> gameHistory;
    private final PriorityQueue<Integer> leaderboard;

    public EnhancedGuessTheNumberGUI() {
        totalGames = 0;
        totalAttempts = 0;
        gameHistory = new ArrayList<>();
        leaderboard = new PriorityQueue<>();
        setupGame();
    }

    private void setupGame() {
        // Main Frame
        final JFrame frame = new JFrame("Enhanced Guess the Number Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0x4CAF50));
        JLabel titleLabel = new JLabel("🎉 Guess the Number 🎉");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(0xE8F5E9));
        frame.add(mainPanel, BorderLayout.CENTER);

        // Instructions
        JLabel instructionsLabel = new JLabel("Welcome! Select a difficulty level to start.");
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(instructionsLabel);

        JLabel difficultyLabel = new JLabel("Select Difficulty:");
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(difficultyLabel);

        // Difficulty ComboBox
        final JComboBox<String> difficultyBox = new JComboBox<>(new String[]{
                "Easy (1-50)", "Medium (1-100)", "Hard (1-100, 10 attempts)", "Custom"
        });
        difficultyBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(difficultyBox);

        // Start Button
        final JButton startButton = new JButton("Start Game");
        styleButton(startButton);
        mainPanel.add(startButton);

        // Guess Input
        final JTextField guessInput = new JTextField(10);
        guessInput.setMaximumSize(new Dimension(200, 30));
        guessInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessInput.setEnabled(false);
        mainPanel.add(guessInput);

        // Submit Button
        final JButton submitGuessButton = new JButton("Submit Guess");
        styleButton(submitGuessButton);
        submitGuessButton.setEnabled(false);
        mainPanel.add(submitGuessButton);

        // Feedback Label
        final JLabel feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        feedbackLabel.setForeground(Color.BLUE);
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(feedbackLabel);

        // Stats Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        statsPanel.setBackground(new Color(0xA5D6A7));
        JButton resetButton = new JButton("Reset Stats");
        JButton historyButton = new JButton("Game History");
        JButton leaderboardButton = new JButton("Leaderboard");
        JButton quitButton = new JButton("Quit");

        styleButton(resetButton);
        styleButton(historyButton);
        styleButton(leaderboardButton);
        styleButton(quitButton);

        statsPanel.add(resetButton);
        statsPanel.add(historyButton);
        statsPanel.add(leaderboardButton);
        statsPanel.add(quitButton);
        frame.add(statsPanel, BorderLayout.SOUTH);

        // Visibility
        frame.setVisible(true);

        // Event Listeners
        startButton.addActionListener(e -> {
            String difficulty = (String) difficultyBox.getSelectedItem();
            if ("Custom".equals(difficulty)) {
                String rangeInput = JOptionPane.showInputDialog(frame, "Enter maximum range (e.g., 200):");
                String attemptsInput = JOptionPane.showInputDialog(frame, "Enter maximum attempts (e.g., 15):");
                try {
                    maxRange = Integer.parseInt(rangeInput);
                    maxAttempts = Integer.parseInt(attemptsInput);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input! Defaulting to Medium difficulty.");
                    maxRange = 100;
                    maxAttempts = Integer.MAX_VALUE;
                }
            } else {
                switch (difficulty) {
                    case "Easy (1-50)":
                        maxRange = 50;
                        maxAttempts = Integer.MAX_VALUE;
                        break;
                    case "Medium (1-100)":
                        maxRange = 100;
                        maxAttempts = Integer.MAX_VALUE;
                        break;
                    case "Hard (1-100, 10 attempts)":
                        maxRange = 100;
                        maxAttempts = 10;
                        break;
                }
            }
            targetNumber = (int) (Math.random() * maxRange) + 1;
            attempts = 0;
            feedbackLabel.setText("Game started! Guess a number between 1 and " + maxRange + ".");
            startButton.setEnabled(false);
            difficultyBox.setEnabled(false);
            guessInput.setEnabled(true);
            submitGuessButton.setEnabled(true);
        });

        submitGuessButton.addActionListener(e -> {
            if (attempts >= maxAttempts) {
                feedbackLabel.setText("You've reached the max attempts. Game Over!");
                return;
            }
            String guessText = guessInput.getText();
            try {
                int guess = Integer.parseInt(guessText);
                attempts++;
                if (guess == targetNumber) {
                    feedbackLabel.setText("🎉 Correct! Guessed in " + attempts + " attempts.");
                    totalGames++;
                    totalAttempts += attempts;
                    gameHistory.add("Game " + totalGames + ": " + attempts + " attempts");
                    leaderboard.offer(attempts);
                    if (leaderboard.size() > 3) leaderboard.poll();
                    resetGame(difficultyBox, guessInput, submitGuessButton, startButton);
                } else if (guess < targetNumber) {
                    feedbackLabel.setText("Too low! Try again.");
                } else {
                    feedbackLabel.setText("Too high! Try again.");
                }
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Invalid input. Please enter a number.");
            }
            guessInput.setText("");
        });

        resetButton.addActionListener(e -> {
            totalGames = 0;
            totalAttempts = 0;
            gameHistory.clear();
            leaderboard.clear();
            JOptionPane.showMessageDialog(frame, "Statistics reset.");
        });

        historyButton.addActionListener(e -> {
            StringBuilder history = new StringBuilder("Game History:\n");
            for (String record : gameHistory) {
                history.append(record).append("\n");
            }
            JOptionPane.showMessageDialog(frame, history.length() > 0 ? history.toString() : "No games played yet.");
        });

        leaderboardButton.addActionListener(e -> {
            StringBuilder leaderboardText = new StringBuilder("Leaderboard (Top 3 Scores):\n");
            for (int score : leaderboard) {
                leaderboardText.append(score).append(" attempts\n");
            }
            JOptionPane.showMessageDialog(frame, leaderboardText.length() > 0 ? leaderboardText.toString() : "No scores yet.");
        });

        quitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Thanks for playing! Goodbye!");
            System.exit(0);
        });
    }

    private void resetGame(JComboBox<String> difficultyBox, JTextField guessInput, JButton submitGuessButton, JButton startButton) {
        startButton.setEnabled(true);
        difficultyBox.setEnabled(true);
        guessInput.setEnabled(false);
        submitGuessButton.setEnabled(false);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0x81C784));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EnhancedGuessTheNumberGUI::new);
    }
}




