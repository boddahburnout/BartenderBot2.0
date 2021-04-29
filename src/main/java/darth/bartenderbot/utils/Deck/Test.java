package darth.bartenderbot.utils.Deck;

import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws InvalidConfigurationException, IOException {
    }
}
//        List deck = new DeckUtils().getDeck();
//        List shuffled = new DeckUtils().shuffle(deck);
//        Integer bank = 500;
//        Boolean playing = true;
//        while (playing) {
//            int bet = 0;
//            List hand = new ArrayList();
//            List dealerhand = new ArrayList();
//            Boolean betting = true;
//            System.out.println("\nWelcome to java blackjack!");
//            Scanner sc = new Scanner(System.in);
//            String input = sc.nextLine();
//            if (input.equalsIgnoreCase("exit")) {
//                playing = false;
//            }
//            if (input.equalsIgnoreCase("play")) {
//                sc = new Scanner(System.in);
//                System.out.println("\nCash: $" + bank);
//                System.out.println("\nPlace a wager!");
//                while (betting) {
//                    try {
//                        bet = sc.nextInt();
//                        if (bet > bank) {
//                            System.out.println("\nYou are too poor to make this bet");
//                        } else {
//                            bank = bank - bet;
//                            System.out.println("\nBet placed!");
//                            betting = false;
//                        }
//                    } catch (InputMismatchException e) {
//                        System.out.println("\nThat's not even a real number");
//                        sc = new Scanner(System.in);
//                    }
//                }
//                for (int i = 0; i < 2; i++) {
//                    hand.add(new DeckUtils().draw(shuffled));
//                    dealerhand.add(new DeckUtils().draw(shuffled));
//                }
//                StringBuilder sb = new StringBuilder();
//                for (Object card : hand) {
//                    sb.append(card + " ");
//                }
//                Integer score = new DeckUtils().blackjackvalue(hand);
//                System.out.println("\nYour hand: " + sb.toString() + "adding up to " + score);
//                Boolean choosing = true;
//                System.out.println("\nDealers hand: " + dealerhand.get(0) + " ? for least a total of " + new DeckUtils().blackjackdealervalue(dealerhand.get(0)));
//                if (score == 21) {
//                    new DeckUtils().playerwin(bet, bank, score, false);
//                    choosing = false;
//                }
//                if (choosing) System.out.println("\nHit or stand?");
//                while (choosing) {
//                    input = sc.nextLine();
//                    if (shuffled.isEmpty()) {
//                        System.out.println("Dealer is shuffling!");
//                        shuffled = new DeckUtils().shuffle(deck);
//                    }
//                    if (input.equalsIgnoreCase("hit")) {
//                        Object card = new DeckUtils().draw(shuffled);
//                        System.out.println("\nThere are " + deck.size() + " cards left");
//                        hand.add(card);
//                        System.out.println("\nYou drew " + card);
//                        score = new DeckUtils().blackjackvalue(hand);
//                        sb.append(card + " ");
//                        System.out.println("\nYour hand: " + sb.toString() + "adding up to " + score);
//                        if (score == 21) {
//                            new DeckUtils().playerwin(bet, bank, score, false);
//                            choosing = false;
//                        }
//                        if (score > 21) {
//                            System.out.println("\nYou busted");
//                            choosing = false;
//                        }
//                        if (choosing) System.out.println("\nHit or stand?");
//                    }
//                    if (input.equalsIgnoreCase("stand")) {
//                        Integer dealerscore = new DeckUtils().blackjackvalue(dealerhand);
//                        System.out.println("\nThe dealer is revealing their card!");
//                        StringBuilder dealercards = new StringBuilder();
//                        for (Object card : dealerhand) {
//                            dealercards.append(card + " ");
//                        }
//                        System.out.println("\nDealers hand: " + dealercards + " adding up to " + dealerscore);
//                        score = new DeckUtils().blackjackvalue(hand);
//                        if (dealerscore < 17) {
//                            Boolean hitting = true;
//                            while (hitting) {
//                                System.out.println("\nThe dealer is hitting!");
//                                Object hit = new DeckUtils().draw(shuffled);
//                                dealerhand.add(hit);
//                                System.out.println("\nThe dealer drew a " + hit + " for " + new DeckUtils().blackjackvalue(dealerhand) + " points");
//                                dealerscore = new DeckUtils().blackjackvalue(dealerhand);
//                                if (dealerscore >= 17) {
//                                    hitting = false;
//                                }
//                            }
//                        }
//                        if (dealerscore > 21) {
//                            new DeckUtils().playerwin(bet, bank, score, false);
//                            choosing = false;
//                        } else {
//                            if (dealerscore < score) {
//                                new DeckUtils().playerwin(bet, bank, score, false);
//                                choosing = false;
//                            }
//                            if (dealerscore == score) {
//                                System.out.println("\nIts a draw! bets returned");
//                                bank = bet + bank;
//                                choosing = false;
//                            }
//                            if (dealerscore > score) {
//                                System.out.println("\nYou lost to the dealer!");
//                                choosing = false;
//                            }
//                        }
//                    }
//                    if (input.equalsIgnoreCase("double down")) {
//                        Object draw = new DeckUtils().draw(shuffled);
//                        hand.add(draw);
//                        System.out.println("\nYou drew " + draw + " making your score " + new DeckUtils().blackjackvalue(hand));
//                        if (new DeckUtils().blackjackvalue(hand) < 21) {
//                            System.out.println("");
//                            choosing = false;
//                        }
//                        Integer dealerscore = new DeckUtils().blackjackvalue(dealerhand);
//                        System.out.println("\nThe dealer is revealing their card!");
//                        StringBuilder dealercards = new StringBuilder();
//                        for (Object card : dealerhand) {
//                            dealercards.append(card + " ");
//                        }
//                        System.out.println("\nDealers hand: " + dealercards + "adding up to " + dealerscore);
//                        score = new DeckUtils().blackjackvalue(hand);
//                        if (dealerscore < 17) {
//                            Boolean hitting = true;
//                            while (hitting) {
//                                System.out.println("\nThe dealer is hitting!");
//                                Object hit = new DeckUtils().draw(shuffled);
//                                dealerhand.add(hit);
//                                System.out.println("\nThe dealer drew a " + hit + " for " + new DeckUtils().blackjackvalue(dealerhand) + " points");
//                                dealerscore = new DeckUtils().blackjackvalue(dealerhand);
//                                if (dealerscore >= 17) {
//                                    hitting = false;
//                                }
//                            }
//                        }
//                        if (score > 21) {
//                            System.out.println("\nYou Busted!");
//                            choosing = false;
//                        }
//                        if (dealerscore > 21) {
//                            new DeckUtils().playerwin(bet, bank, score, true);
//                            choosing = false;
//                        } else {
//                            if (dealerscore < score) {
//                                new DeckUtils().playerwin(bet, bank, score, true);
//                                choosing = false;
//                            }
//                            if (dealerscore == score) {
//                                System.out.println("\nIts a draw! bets returned");
//                                bank = bet + bank;
//                                choosing = false;
//                            }
//                            if (dealerscore > score) {
//                                System.out.println("\nYou lost to the dealer!");
//                                choosing = false;
//                            }
//                        }
//                    }
//                }
//            }
//            if (shuffled.size() < 25) {
//                shuffled = new DeckUtils().shuffle(deck);
//                System.out.println("\nThe dealer is shuffling...");
//            }
//        }
//    }
//}
//