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
        final JFrame frame = new JFrame("Enhanced Guess the Number Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        // Title label
        JLabel instructionsLabel = new JLabel("Welcome to 'Guess the Number'!");
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(instructionsLabel);

        JLabel difficultyLabel = new JLabel("Select Difficulty:");
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(difficultyLabel);

        // Difficulty selection
        final JComboBox<String> difficultyBox = new JComboBox<>(new String[]{
                "Easy (1-50)", "Medium (1-100)", "Hard (1-100, 10 attempts)", "Custom"
        });
        difficultyBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(difficultyBox);

        final JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(startButton);

        // Input and Feedback
        final JTextField guessInput = new JTextField(10);
        guessInput.setMaximumSize(new Dimension(200, 30));
        guessInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(guessInput);

        final JButton submitGuessButton = new JButton("Submit Guess");
        submitGuessButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(submitGuessButton);

        final JLabel feedbackLabel = new JLabel(" ");
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(feedbackLabel);

        // Buttons in a single row
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton resetButton = new JButton("Reset Statistics");
        JButton historyButton = new JButton("View Game History");
        JButton leaderboardButton = new JButton("View Leaderboard");
        JButton quitButton = new JButton("Quit Game");

        buttonPanel.add(resetButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(quitButton);
        panel.add(buttonPanel);

        frame.setVisible(true);

        // Event listeners
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
                    feedbackLabel.setText("Correct! You guessed the number in " + attempts + " attempts.");
                    totalGames++;
                    totalAttempts += attempts;
                    gameHistory.add("Game " + totalGames + ": " + attempts + " attempts");
                    leaderboard.offer(attempts);
                    if (leaderboard.size() > 3) leaderboard.poll();
                    startButton.setEnabled(true);
                    difficultyBox.setEnabled(true);
                    guessInput.setEnabled(false);
                    submitGuessButton.setEnabled(false);
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
            JOptionPane.showMessageDialog(frame, "Statistics have been reset.");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EnhancedGuessTheNumberGUI::new);
    }
}


