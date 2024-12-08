import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean playAgain;
            int totalGames = 0;
            int totalAttempts = 0;

            System.out.println("Welcome to the enhanced 'Guess the Number' game!");

            do {
                // Select difficulty level
                System.out.println("\nSelect a difficulty level:");
                System.out.println("1. Easy (1-50, unlimited attempts)");
                System.out.println("2. Medium (1-100, unlimited attempts)");
                System.out.println("3. Hard (1-100, 10 attempts)");

                int maxRange = 100;
                int maxAttempts = Integer.MAX_VALUE;
                System.out.print("Enter your choice (1, 2, or 3): ");
                int difficulty = 0;

                // Validate difficulty input
                while (true) {
                    if (scanner.hasNextInt()) {
                        difficulty = scanner.nextInt();
                        if (difficulty >= 1 && difficulty <= 3) {
                            break;
                        } else {
                            System.out.print("Invalid choice! Please enter 1, 2, or 3: ");
                        }
                    } else {
                        System.out.print("Invalid input! Please enter a number (1, 2, or 3): ");
                        scanner.next(); // Clear invalid input
                    }
                }

                switch (difficulty) {
                    case 1:
                        maxRange = 50;
                        System.out.println("You chose Easy mode!");
                        break;
                    case 2:
                        maxRange = 100;
                        System.out.println("You chose Medium mode!");
                        break;
                    case 3:
                        maxRange = 100;
                        maxAttempts = 10;
                        System.out.println("You chose Hard mode!");
                        break;
                }

                // Generate the target number
                int targetNumber = (int) (Math.random() * maxRange) + 1;
                int guess = 0;
                int attempts = 0;
                boolean validInput;

                System.out.println("I'm thinking of a number between 1 and " + maxRange + ".");
                System.out.println("Can you guess it?");

                // Game loop
                while (guess != targetNumber && attempts < maxAttempts) {
                    validInput = false;

                    // Validate guess input
                    while (!validInput) {
                        System.out.print("Enter your guess: ");
                        if (scanner.hasNextInt()) {
                            guess = scanner.nextInt();
                            if (guess >= 1 && guess <= maxRange) {
                                validInput = true;
                            } else {
                                System.out.println("Please enter a number between 1 and " + maxRange + ".");
                            }
                        } else {
                            System.out.println("Invalid input! Please enter a valid number.");
                            scanner.next(); // Clear invalid input
                        }
                    }

                    attempts++;

                    if (guess < targetNumber) {
                        System.out.println("Too low! Try again.");
                    } else if (guess > targetNumber) {
                        System.out.println("Too high! Try again.");
                    } else {
                        System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                    }

                    if (attempts >= maxAttempts && guess != targetNumber) {
                        System.out.println("You've reached the maximum attempts! The number was: " + targetNumber);
                    }
                }

                // Update statistics
                totalGames++;
                totalAttempts += attempts;

                // Ask if the player wants to play again
                System.out.print("Do you want to play again? (yes/no): ");
                playAgain = scanner.next().equalsIgnoreCase("yes");

            } while (playAgain);

            // Display statistics
            System.out.println("\nGame Statistics:");
            System.out.println("Total games played: " + totalGames);
            if (totalGames > 0) {
                System.out.println("Average attempts per game: " + (double) totalAttempts / totalGames);
            }

            System.out.println("Thanks for playing! Goodbye.");
        }
    }
}


