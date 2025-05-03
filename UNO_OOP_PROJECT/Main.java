import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Welcome message
        System.out.println(" ================================");
        System.out.println("|           WELCOME TO            |");
        System.out.println("|             U N O               |");
        System.out.println(" ================================");

        int numHumanPlayers, numBotPlayers;

        // Get the number of human players
        do {
            System.out.print("\nEnter number of human players (1-4): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number!");
                scanner.next();
            }
            numHumanPlayers = scanner.nextInt();
        } while (numHumanPlayers < 1 || numHumanPlayers > 4);

        // Determine the range for bot players
        int minBots = (numHumanPlayers == 1) ? 1 : 0; // Require at least 1 bot for single player
        int maxBots = (numHumanPlayers == 4) ? 0 : (4 - numHumanPlayers); // No bots allowed with 4 players
       
        // Get the number of bot players
        if (maxBots > 0) {
            if (numHumanPlayers == 1) {
                System.out.println("Single player mode requires at least one bot opponent.");
            }
            do {
                System.out.print("Enter number of bot players (" + minBots + "-" + maxBots + "): ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Please enter a valid number!");
                    scanner.next();
                }
                numBotPlayers = scanner.nextInt();
            } while (numBotPlayers < minBots || numBotPlayers > maxBots);
        } else {
            numBotPlayers = 0;
            System.out.println("Maximum players reached. No bots will be added.");
        }

        scanner.nextLine(); // Clear the buffer

        // Display the game setup
        System.out.println("\nInitializing game with " + numHumanPlayers +
                           " human player(s) and " + numBotPlayers + " bot(s)");
        System.out.println("=================================");

        Game game = new Game(numHumanPlayers, numBotPlayers, scanner);
        game.start();
        
        System.out.println("\n ==============================");
        System.out.println("|      Thanks for playing!     |");
        System.out.println("|            U N O             |");
        System.out.println(" ==============================");
        
        scanner.close();
    }
}