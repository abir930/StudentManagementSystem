import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuessTheNumberGUI {
    private int targetNumber;
    private int maxRange;
    private int maxAttempts;
    private int attempts;
    private int totalGames;
    private int totalAttempts;

    public GuessTheNumberGUI() {
        totalGames = 0;
        totalAttempts = 0;
        setupGame();
    }

    private void setupGame() {
        JFrame frame = new JFrame("Guess the Number Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel instructionsLabel = new JLabel("Welcome to 'Guess the Number'!");
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(instructionsLabel);

        JLabel difficultyLabel = new JLabel("Select Difficulty:");
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(difficultyLabel);

        JComboBox<String> difficultyBox = new JComboBox<>(new String[]{"Easy (1-50)", "Medium (1-100)", "Hard (1-100, 10 attempts)"});
        difficultyBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(difficultyBox);

        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(startButton);

        JLabel guessLabel = new JLabel("Enter your guess:");
        guessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessLabel.setVisible(false);
        panel.add(guessLabel);

        JTextField guessField = new JTextField(10);
        guessField.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessField.setMaximumSize(new Dimension(200, 20));
        guessField.setVisible(false);
        panel.add(guessField);

        JButton submitButton = new JButton("Submit Guess");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setVisible(false);
        panel.add(submitButton);

        JLabel feedbackLabel = new JLabel(" ");
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(feedbackLabel);

        frame.add(panel);
        frame.setVisible(true);

        // Event listeners
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String difficulty = (String) difficultyBox.getSelectedItem();
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
                targetNumber = (int) (Math.random() * maxRange) + 1;
                attempts = 0;

                guessLabel.setVisible(true);
                guessField.setVisible(true);
                submitButton.setVisible(true);
                feedbackLabel.setText("Game started! Guess a number between 1 and " + maxRange + ".");
                startButton.setEnabled(false);
                difficultyBox.setEnabled(false);
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int guess = Integer.parseInt(guessField.getText());
                    if (guess < 1 || guess > maxRange) {
                        feedbackLabel.setText("Enter a number between 1 and " + maxRange + ".");
                        return;
                    }

                    attempts++;

                    if (guess < targetNumber) {
                        feedbackLabel.setText("Too low! Try again.");
                    } else if (guess > targetNumber) {
                        feedbackLabel.setText("Too high! Try again.");
                    } else {
                        feedbackLabel.setText("Congratulations! You guessed the number in " + attempts + " attempts.");
                        totalGames++;
                        totalAttempts += attempts;
                        endGame(frame);
                    }

                    if (attempts >= maxAttempts && guess != targetNumber) {
                        feedbackLabel.setText("Out of attempts! The number was: " + targetNumber);
                        totalGames++;
                        totalAttempts += attempts;
                        endGame(frame);
                    }
                } catch (NumberFormatException ex) {
                    feedbackLabel.setText("Please enter a valid number.");
                }
            }
        });
    }

    private void endGame(JFrame frame) {
        int response = JOptionPane.showConfirmDialog(frame, "Play again?", "Game Over", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            setupGame();
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Thank you for playing!\nTotal games: " + totalGames +
                            "\nAverage attempts: " + (totalGames > 0 ? (double) totalAttempts / totalGames : 0));
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuessTheNumberGUI::new);
    }
}



