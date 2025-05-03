import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;
    private List<Card> discardPile;
    public static final int INITIAL_HAND_SIZE = 7;

    public Deck() {
        cards = new ArrayList<>();
        discardPile = new ArrayList<>();
        initializeDeck();
    }

    public void initializeDeck() {
        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        
        // Add number cards (0-9)
        for (String color : colors) {
            cards.add(new NumberCard(color, 0)); // One 0 per color
            for (int i = 1; i <= 9; i++) {
                cards.add(new NumberCard(color, i)); // Two of each 1-9
                cards.add(new NumberCard(color, i));
            }
        }

        // Add action cards
        for (String color : colors) {
            for (int i = 0; i < 2; i++) { // Two of each per color
                cards.add(new SkipCard(color));
                cards.add(new ReverseCard(color));
                cards.add(new DrawTwoCard(color));
            }
        }

        // Add wild cards
        for (int i = 0; i < 4; i++) {
            cards.add(new WildCard());
            cards.add(new WildDrawFourCard());
        }

        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
    public void addToBottom(Card card) {
        cards.add(0, card); // Add to the beginning of the deck (bottom)
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            resetDeck();
        }
        return cards.isEmpty() ? null : cards.remove(cards.size() - 1);
    }

    public void addToDiscardPile(Card card) {
        discardPile.add(card);
    }

    public Card getTopCard() {
        return discardPile.isEmpty() ? null : discardPile.get(discardPile.size() - 1);
    }

    private void resetDeck() {
        if (discardPile.size() > 1) {
            Card topCard = discardPile.remove(discardPile.size() - 1);
            cards.addAll(discardPile);
            discardPile.clear();
            discardPile.add(topCard);
            shuffle();
        }
    }
}
