import java.util.ArrayList;
import java.util.List;

public class Hand {
    List<Card> cards = new ArrayList<>();

    public int getValue(){
        return cards.stream().mapToInt(card -> card.getValue()).sum();
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public boolean isBlackjack() {
        return getValue() == 21;
    }

    public void empty() {
        this.cards.clear();
    }
}
