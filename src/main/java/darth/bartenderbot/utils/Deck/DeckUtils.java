package darth.bartenderbot.utils.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckUtils {
    private List deck = new ArrayList();

    public List getDeck() {
        deck.add("2b");
        deck.add("3b");
        deck.add("4b");
        deck.add("5b");
        deck.add("6b");
        deck.add("7b");
        deck.add("8b");
        deck.add("9b");
        deck.add("10b");
        deck.add("Jb");
        deck.add("Qb");
        deck.add("Kb");
        deck.add("Ab");
        deck.add("2r");
        deck.add("3r");
        deck.add("4r");
        deck.add("5r");
        deck.add("6r");
        deck.add("7r");
        deck.add("8r");
        deck.add("9r");
        deck.add("10r");
        deck.add("Jr");
        deck.add("Qr");
        deck.add("Kr");
        deck.add("Ar");
        deck.add("2r");
        deck.add("3r");
        deck.add("4r");
        deck.add("5r");
        deck.add("6r");
        deck.add("7r");
        deck.add("8r");
        deck.add("9r");
        deck.add("10r");
        deck.add("Jr");
        deck.add("Qr");
        deck.add("Kr");
        deck.add("Ar");
        deck.add("2b");
        deck.add("3b");
        deck.add("4b");
        deck.add("5b");
        deck.add("6b");
        deck.add("7b");
        deck.add("8b");
        deck.add("9b");
        deck.add("10b");
        deck.add("Jb");
        deck.add("Qb");
        deck.add("Kb");
        deck.add("Ab");
        return deck;
    }

    public List shuffle(List deck) {
        Collections.shuffle(deck);
        return deck;
    }

    public Object draw(List deck) {
        Object card = deck.get(0);
        discard(deck, card);
        return card;
    }

    public List discard(List deck, Object card) {
        deck.remove(card);
        return deck;
    }

    public Object face(Object card) {
        String ca = card.toString().substring(0, card.toString().length() - 1);
        return ca;
    }

    public Integer blackjackvalue(List hand) {
        Integer score = 0;
        Integer aces = 0;
        for (Object card : hand) {
            Object value = face(card);
            try {
                score = score + Integer.valueOf(value.toString());
            } catch (NumberFormatException e) {
                if (value.toString().equalsIgnoreCase("A")) {
                    aces++;
                    score = score + 11;
                } else {
                    score = score + 10;
                }
            }
            if (score > 21) {
                Boolean calc = true;
                if (aces > 0) {
                    while (calc) {
                        aces = aces - 1;
                        score = score - 10;
                        if (score <= 21) {
                            calc = false;
                        }
                    }
                }
            }
        }
        return score;
    }

    public Integer blackjackdealervalue(Object card) {
        Integer score = 0;
        Integer aces = 0;
        Object value = face(card);
        try {
            score = score + Integer.valueOf(value.toString());
        } catch (NumberFormatException e) {
            if (value.toString().equalsIgnoreCase("A")) {
                aces++;
                score = score + 11;
            } else {
                score = score + 10;
            }
        }
        if (score > 21) {
            Boolean calc = true;
            if (aces > 0) {
                while (calc) {
                    aces = aces - 1;
                    score = score - 10;
                    if (score <= 21) {
                        calc = false;
                    }
                }
            }
        }
        return score;
    }

    public Boolean playerwin(int bet, int bank, int score, Boolean ddown) {
        try {
            if (score == 21) {
                System.out.println("\nBlack Jack!!!");
            }
            Integer winnings = bet * 2;
            if (ddown) {
                bank = bank + winnings * 2;
                System.out.println("Double down win! you won $"+winnings*2);
            } else {
                bank = bank + winnings;
                System.out.println("\nYou won $" + bet * 2);
                System.out.println("\nYou have a total of $" + bank);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
