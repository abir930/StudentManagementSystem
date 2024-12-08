import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean playAgain;

            System.out.println("Welcome to the enhanced 'Guess the Number' game!");

            do {
                // Select difficulty level
                System.out.println("Select a difficulty level:");
                System.out.println("1. Easy (1-50, unlimited attempts)");
                System.out.println("2. Medium (1-100, unlimited attempts)");
                System.out.println("3. Hard (1-100, 10 attempts)");

                int maxRange = 100;
                int maxAttempts = Integer.MAX_VALUE;
                System.out.print("Enter your choice (1, 2, or 3): ");
                int difficulty = scanner.nextInt();

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
                    default:
                        System.out.println("Invalid choice! Defaulting to Medium mode.");
                        maxRange = 100;
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

                    // Validate input
                    while (!validInput) {
                        System.out.print("Enter your guess: ");
                        if (scanner.hasNextInt()) {
                            guess = scanner.nextInt();
                            if (guess < 1 || guess > maxRange) {
                                System.out.println("Please enter a number between 1 and " + maxRange + ".");
                            } else {
                                validInput = true;
                            }
                        } else {
                            System.out.println("Invalid input! Please enter a number.");
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

                // Ask if the player wants to play again
                System.out.print("Do you want to play again? (yes/no): ");
                playAgain = scanner.next().equalsIgnoreCase("yes");

            } while (playAgain);

            System.out.println("Thanks for playing! Goodbye.");
        }
    }
}

