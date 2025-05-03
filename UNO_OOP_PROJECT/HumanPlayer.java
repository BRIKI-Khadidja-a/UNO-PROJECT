import java.util.List;
public class HumanPlayer extends Player {
   public HumanPlayer(String name) {
       super(name, false);
   }

   public void displayHand() {
       System.out.println("\nYour hand:");
       List<Card> hand = getHand();
       for (int i = 0; i < hand.size(); i++) {
           System.out.println((i + 1) + ". " + hand.get(i));
       }
   }
}