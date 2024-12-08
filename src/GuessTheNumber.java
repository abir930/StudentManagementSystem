import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        // Generate a random number between 1 and 100
        int targetNumber = (int) (Math.random() * 100) + 1;
        try (Scanner scanner = new Scanner(System.in)) {
            int guess = 0;
            int attempts = 0;
            boolean validInput;
            
            System.out.println("Welcome to 'Guess the Number' game!");
            System.out.println("I'm thinking of a number between 1 and 100.");
            System.out.println("Can you guess it?");
            
            // Game loop
            while (guess != targetNumber) {
                validInput = false;
                while (!validInput) {
                    System.out.print("Enter your guess: ");
                    if (scanner.hasNextInt()) {
                        guess = scanner.nextInt();
                        validInput = true;
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
            }
            
            System.out.println("Thanks for playing!");
        }
    }
}

