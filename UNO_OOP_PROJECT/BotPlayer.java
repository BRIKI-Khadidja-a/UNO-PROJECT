import java.util.List;
import java.util.Random;

public class BotPlayer extends Player {
    private Random random;

    public BotPlayer(String name) {
        super(name, true);
        this.random = new Random();
    }

    public Card chooseBestCard(Game game) {
        List<Card> validMoves = getValidMoves(game.getTopCard());
        if (validMoves.isEmpty()) {
            return null;
        }
        // randomly choose a valid card
        return validMoves.get(random.nextInt(validMoves.size()));
    }
}