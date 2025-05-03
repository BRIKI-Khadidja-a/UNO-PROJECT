import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
   private List<Player> players;
   private Deck deck;
   private int currentPlayerIndex;
   private int direction;
   private Scanner scanner;/*The scanner is injected via the constructor to allow for flexible input 
   sources and easier testing, following dependency injection principles. */

   public Game(int numHumanPlayers, int numBotPlayers, Scanner scanner) {
       this.players = new ArrayList<>();
       this.deck = new Deck();
       this.currentPlayerIndex = 0;
       this.direction = 1;
       this.scanner = scanner;

       // Initialize players
       for (int i = 0; i < numHumanPlayers; i++) {
           System.out.print("Enter name for human player " + (i + 1) + ": ");
           String name = scanner.nextLine();
           players.add(new HumanPlayer(name));
       }

       for (int i = 0; i < numBotPlayers; i++) {
           players.add(new BotPlayer("Bot " + (i + 1)));
       }
   }

   public void initialize() {
       // Deal initial cards
       for (Player player : players) {
           for (int i = 0; i < Deck.INITIAL_HAND_SIZE; i++) {
               player.drawCard(deck);
           }
       }

       // Place first card - only number cards allowed
       Card firstCard;
       do {
           firstCard = deck.drawCard();
           // If it's not a number card, add it back to the bottom of the deck
           if (!(firstCard instanceof NumberCard)) {
               deck.addToBottom(firstCard);
           }
       } while (!(firstCard instanceof NumberCard));

       deck.addToDiscardPile(firstCard);
   }

/* Main game loop that manages the game's progression.
     Continues until a player wins */
   public void start() {
       initialize();

       while (!isGameOver()) {
           displayGameStatus();
           playTurn();
           
           if (getCurrentPlayer().hasWon()) {
               System.out.println("\n " + getCurrentPlayer().getName() + " has won the game! ");
               break;
           }
           
           nextPlayer();
       }
   }
/* Handles the turn logic for the current player */
   private void playTurn() {
       Player currentPlayer = getCurrentPlayer();
       System.out.println("\n" + currentPlayer.getName() + "'s turn");

       if (currentPlayer instanceof HumanPlayer) {
           playHumanTurn((HumanPlayer) currentPlayer);
       } else {
           playBotTurn((BotPlayer) currentPlayer);
       }
   }

   private void playHumanTurn(HumanPlayer player) {
       player.displayHand();
       List<Card> validMoves = player.getValidMoves(getTopCard());
     // Handle scenario where no valid moves are available
       if (validMoves.isEmpty()) {
           System.out.println("No valid moves. Drawing a card...");
           player.drawCard(deck);
           return;
       }
       // Display valid move options to the player
       System.out.println("Valid moves:");
       for (int i = 0; i < validMoves.size(); i++) {
           System.out.println((i + 1) + ". " + validMoves.get(i));
       }
       System.out.println((validMoves.size() + 1) + ". Draw a card");

       int choice = getValidInput(1, validMoves.size() + 1) - 1;
       if (choice < validMoves.size()) {
           Card selectedCard = validMoves.get(choice);
            // Special handling for wild cards
           if (selectedCard instanceof WildCard) {
               handleWildCard((WildCard) selectedCard);
           }
           player.playCard(selectedCard, this);
           System.out.println("Played: " + selectedCard);
       } else {
           player.drawCard(deck);
           System.out.println("Drew a card");
       }
   }

   private void playBotTurn(BotPlayer player) {
      Card selectedCard = player.chooseBestCard(this);
      if (selectedCard != null) {
          if (selectedCard instanceof WildCard) {
              // Randomly select a color for wild cards
            String[] colors = { "Red", "Blue", "Green", "Yellow" };
            ((WildCard) selectedCard).setColor(colors[(int) (Math.random() * colors.length)]);
         }
         player.playCard(selectedCard, this);
         System.out.println("=========================");
         System.out.println("Played: " + selectedCard);
      } else {
         player.drawCard(deck);
         System.out.println("Drew a card");
      }
   }

   private void handleWildCard(WildCard card) {
       System.out.println("Choose a color:");
       System.out.println("1. Red");
       System.out.println("2. Blue");
       System.out.println("3. Green");
       System.out.println("4. Yellow");

       int choice = getValidInput(1, 4);
       String color = "";
       switch (choice) {
           case 1: color = "Red"; break;
           case 2: color = "Blue"; break;
           case 3: color = "Green"; break;
           case 4: color = "Yellow"; break;
       }
       card.setColor(color);
   }
//Displays the current game status, including top card and player hand sizes.
   public void displayGameStatus() {
       System.out.println("\n\n=== Game Status ===");
       System.out.println("Top Card: " + getTopCard());
       for (Player player : players) {
           System.out.println(player.getName() + ": " + player.getHandSize() + " cards");
       }
       System.out.println("====================\n");
   }

   // Utility methods
   private int getValidInput(int min, int max) {
       int choice;
       do {
           System.out.print("Enter your choice (" + min + "-" + max + "): ");
           while (!scanner.hasNextInt()) {
               System.out.println("Please enter a number!");
               scanner.next();
           }
           choice = scanner.nextInt();
       } while (choice < min || choice > max);
       return choice;
   }

   public void nextPlayer() {
       currentPlayerIndex = (currentPlayerIndex + direction + players.size()) % players.size();
   }

   public void reverseDirection() {
       direction *= -1;
       if (players.size() == 2) {
           nextPlayer(); // Skip next player's turn in 2-player game
       }
   }

   public Player getCurrentPlayer() {
       return players.get(currentPlayerIndex);
   }

   public Player getNextPlayer() {
       int nextIndex = (currentPlayerIndex + direction + players.size()) % players.size();
       return players.get(nextIndex);
   }

   public Card getTopCard() {
       return deck.getTopCard();
   }
//Plays a card by adding it to the discard pile and applying its effect.
   public void playCard(Card card) {
       deck.addToDiscardPile(card);
       card.applyEffect(this);
   }
//Checks if the game is over by determining if any player has won.
   public boolean isGameOver() {
      for (Player player : players) {
          if (player.hasWon()) {
              return true;
          }
      }
      return false;
  }

   public Deck getDeck() {
       return deck;
   }
}