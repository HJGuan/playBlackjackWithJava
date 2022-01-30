import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.EnumSet.complementOf;

public class Round {
    private static final List<Card> deck = new ArrayList<>();
    private static final List<Card> playerCards = new ArrayList<>();
    private static final List<Card> dealerCards = new ArrayList<>();

    enum Result {PlayerWon, PlayerLost, Draw, EnterDealerProcess, Null}

    static Integer checkHit;

    private static final List<Observer> observers = new ArrayList<>();

    public static void attach(Observer observer) {
        observers.add(observer);
    }

    // make GUI a Singleton


    public static Result play() {
        //initialize the Deck
        for (Suit suit : Suit.values()) {
            for (Rank rank : complementOf(EnumSet.of(Rank.ACE_MINOR))) {
                deck.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(deck);
        System.out.println(getNames(deck));
        System.out.println(deck.size());
        for (Card card : deck) {
            if (card.getValue() == 1) {
                System.out.println(card.getName());
            }
        }
        //deal 2 cards to player and 2 cards to dealer
        deal2Player();
        deal2Dealer();
        deal2Player();
        deal2Dealer();
        System.out.println("Dealer's cards:");
        System.out.println(getNames(dealerCards));
        System.out.println("Player's cards:");
        System.out.println(getNames(playerCards));
        Result playerProcessResult = playerProcess();
        if (playerProcessResult == Result.EnterDealerProcess) {
            return dealerProcess();
        } else {
            return playerProcessResult;
        }
    }


    public static Result playerProcess() {
        checkHit = GUI.checkHitButton();
        for (int i = 0; i < 8; i++) {
            while (checkHit == i) {
                checkHit = GUI.checkHitButton();
            }
            if (checkHit == 999) {
                System.out.println("stand!");
                return Result.EnterDealerProcess;
            }
            deal2Player();
            System.out.println(getNames(playerCards));
            int playerValue = sumUp(playerCards);
            if (playerValue == 21) {
                bjEffect();
            }
            if (playerValue > 21) {
                if (hasACE(playerCards)) {
                    setTheFirstACEtoACEMINOR(playerCards);
                    playerValue = sumUp(playerCards);
                } else {
                    System.out.println("Player Bust!");
                    bustEffect();
                    return Result.PlayerLost;
                }
            }
        }
        return Result.Null;
    }


    public static Result dealerProcess() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //sleep 1 second
        flipCard();
        //sleep 1 second
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int dealerValue = sumUp(dealerCards);
        while (dealerValue < 17) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            deal2Dealer();
            System.out.println(getNames(dealerCards));
            dealerValue = sumUp(dealerCards);
            if (dealerValue > 21) {
                if (hasACE(dealerCards)) {
                    setTheFirstACEtoACEMINOR(dealerCards);
                    dealerValue = sumUp(dealerCards);
                } else {
                    bustEffect();
                    System.out.println("dealer Bust!");
                    return Result.PlayerWon;
                }
            }
        }
        if (dealerValue == 21) {
            bjEffect();
        }
        int playerValue = sumUp(playerCards);
        if (dealerValue > playerValue) {
            return Result.PlayerLost;
        } else if (dealerValue < playerValue) {
            return Result.PlayerWon;
        } else {
            return Result.Draw;
        }
    }


    public static void notifyAllObservers_deal2player(Card card) {
        for (Observer observer : observers) {
            observer.deal2player(card);
        }
    }

    public static void deal2Player() {
        playerCards.add(deck.get(0));
        notifyAllObservers_deal2player(deck.get(0));
        deck.remove(0);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void notifyAllObservers_deal2dealer(Card card) {
        for (Observer observer : observers) {
            Boolean theCardIsFlipped = dealerCards.size() == 2;
            observer.deal2dealer(card, theCardIsFlipped);
        }
    }

    public static void deal2Dealer() {
        dealerCards.add(deck.get(0));
        notifyAllObservers_deal2dealer(deck.get(0));
        deck.remove(0);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void updateMoney() {
        for (Observer observer : observers) {
            observer.updateMoney();
        }
    }


    public static void clear() {
        playerCards.clear();
        dealerCards.clear();
        deck.clear();
    }

    public static void flipCard() {
        for (Observer observer : observers) {
            observer.flip();
        }
    }

    public static void bustEffect() {
        for (Observer observer : observers) {
            observer.bustEffect();
        }
    }

    public static void bjEffect() {
        for (Observer observer : observers) {
            observer.bjEffect();
        }
    }

    public static int sumUp(List<Card> cards) {
        int sum = 0;
        for (Card card : cards) {
            int i = card.getValue();
            sum = sum + i;
        }
        return sum;
    }

    public static List<String> getNames(List<Card> cards) {
        List<String> names = new ArrayList<>();
        for (Card card : cards) {
            String name = card.getName();
            names.add(name);
        }
        return names;
    }

    public static boolean hasACE(List<Card> cards) {
        for (Card card : cards) {
            if (card.isACE()) {
                return true;
            }
        }
        return false;
    }

    public static void setTheFirstACEtoACEMINOR(List<Card> cards) {
        for (Card card : cards) {
            if (card.isACE()) {
                card.setACEtoACE_MINOR();
                return;
            }
        }
    }

    public static List<Card> test1 = new ArrayList<>();
    public static List<Card> test2 = new ArrayList<>();
    public static void testMethod(){
        Card card1 = new Card(Suit.DIAMOND,Rank.ACE);
        Card card2 = new Card(Suit.CLUB,Rank.EIGHT);
        Card card3 = new Card(Suit.HEART,Rank.TWO);
        test1.add(card1);
        test1.add(card2);
        test1.add(card3);
        test2.add(card3);
    }
}
