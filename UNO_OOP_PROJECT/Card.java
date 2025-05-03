public abstract class Card {
    protected String color;
    protected String type;
    protected int value;

    public Card(String color, String type, int value) {
        this.color = color;
        this.type = type;
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public boolean canPlayOn(Card topCard) {
        // Wild cards can always be played
        if (this.getType().equals("Wild") || this.getType().equals("Wild Draw Four")) {
            return true;
        }
        
        // If the top card is a wild card, match the chosen color
        if (topCard instanceof WildCard) {
            return this.color.equals(topCard.getColor());
        }
        
        // Match by color or type (for action cards)
        return this.color.equals(topCard.getColor()) || 
               this.type.equals(topCard.getType());
    }

    @Override
    public String toString() {
        if (color.equals("Wild")) {
            return type;
        }
        return color + " " + type;
    }

    public abstract void applyEffect(Game game);
}

 class NumberCard extends Card {
    public NumberCard(String color, int number) {
        super(color, String.valueOf(number), number);
    }

    @Override
    public void applyEffect(Game game) {
        // Number cards have no special effect
    }
}


 class ActionCard extends Card {
    public ActionCard(String color, String type, int value) {
        super(color, type, value);
    }

    @Override
    public void applyEffect(Game game) {
        // Action cards will have their specific effects in subclasses
    }
}


 class SkipCard extends ActionCard {
    public SkipCard(String color) {
        super(color, "Skip", 20);
    }

    @Override
    public void applyEffect(Game game) {
        game.nextPlayer(); // Skip next player's turn
    }
}


class ReverseCard extends ActionCard {
    public ReverseCard(String color) {
        super(color, "Reverse", 20);
    }

    @Override
    public void applyEffect(Game game) {
        game.reverseDirection();
    }
}


 class DrawTwoCard extends ActionCard {
    public DrawTwoCard(String color) {
        super(color, "Draw Two", 20);
    }

    @Override
    public void applyEffect(Game game) {
        Player nextPlayer = game.getNextPlayer();
        nextPlayer.drawCard(game.getDeck());
        nextPlayer.drawCard(game.getDeck());
        game.nextPlayer(); // Skip next player's turn
    }
}


class WildCard extends Card {
    private String chosenColor;

    public WildCard() {
        super("Wild", "Wild", 50);
    }

    public void setColor(String color) {
        this.chosenColor = color;
    }

    @Override
    public String getColor() {
        return chosenColor != null ? chosenColor : "Wild";
    }

    @Override
    public void applyEffect(Game game) {
        // Wild cards only change color, no turn-skipping effect
    }

    @Override
    public String toString() {
        if (chosenColor != null) {
            return chosenColor + " " + type;
        }
        return type;
    }
}


class WildDrawFourCard extends WildCard {
    public WildDrawFourCard() {
        super();
        this.type = "Wild Draw Four";
    }

    @Override
    public void applyEffect(Game game) {
        Player nextPlayer = game.getNextPlayer();
        // Make the next player draw 4 cards
        for (int i = 0; i < 4; i++) {
            nextPlayer.drawCard(game.getDeck());
        }
        // Skip their turn
        game.nextPlayer();
    }
}