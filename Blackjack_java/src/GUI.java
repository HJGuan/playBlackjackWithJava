import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

public class GUI extends JFrame implements Observer {
    JLayeredPane layeredPane;
    Button hitButton = new Button("hit", Color.gray, Color.white, 400, 460, 100, 25);
    Button standButton = new Button("stand", Color.white, Color.pink, 500, 460, 100, 25);
    //load all pokers
    BufferedImage ImageOfPoker = null;
    {
        try {
            ImageOfPoker = ImageIO.read(new File("src/images/poker.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    final int width = 312;
    final int height = 450;
    final int gap = 31;
    static final int rows = 4;
    static final int cols = 14;
    static BufferedImage[] cards = new BufferedImage[rows * cols];
    //cardsLocation
    int playersCardsStartsLocationX = 400;
    int playersCardsStartsLocationY = 300;
    int dealersCardsStartsLocationX = 450;
    int dealersCardsStartsLocationY = 150;

    static String playerName = "player1";


    //parameter for the hit and stand button
    static Boolean standButtonHasBeenPushed = false;
    static Integer hitButtonHasBeenPushedNTimes =0;



    public static int checkHitButton(){
        if (!standButtonHasBeenPushed) {
            return hitButtonHasBeenPushedNTimes;
        }else{
            return 999;
        }
    }

    static ImageIcon chips = new ImageIcon("src/images/chips.png");
    static ImageIcon deck = new ImageIcon("src/images/deck.png"); // load the image to a imageIcon
    static JLabel Table = new JLabel(new ImageIcon("src/images/casinoTable.jpg"));
    static ImageIcon dealerCool = new ImageIcon("src/images/dealerCool.png"); // load the image to a imageIcon
    static ImageIcon dealer = new ImageIcon("src/images/dealerCute.png");
    JLabel Money = new JLabel("Gold: "+ Main.getMoney() +"chf");

    Integer FlippedCardIndex;

    public GUI() {
        resetParameter();
        //size of the window
        Dimension size = new Dimension(1000, 600);
        //add a layered pane
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(size);
        getContentPane().add(layeredPane);

        //add table
        Dimension TableSize = Table.getPreferredSize();
        Table.setSize(TableSize);
        Table.setBounds(-150, -50, TableSize.width, TableSize.height);
        layeredPane.add(Table, JLayeredPane.DEFAULT_LAYER);
        //initial all cards
        loadPokersImage();

        //add deckImage

        Image oldDeck = deck.getImage(); // transform it
        Image newDeck = oldDeck.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        deck = new ImageIcon(newDeck);
        JLabel Deck = new JLabel(deck);
        Dimension DeckSize = Deck.getPreferredSize();
        Deck.setSize(DeckSize);
        Deck.setBounds(700, 50, DeckSize.width, DeckSize.height);
        layeredPane.add(Deck, Integer.valueOf(2));

        //add dealerImage
        Image oldSize = dealerCool.getImage(); // transform it
        Image newSize = oldSize.getScaledInstance(140, 120, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        dealerCool = new ImageIcon(newSize);
        JLabel DealerCool = new JLabel(dealerCool);
        Dimension DealerSize = DealerCool.getPreferredSize();
        DealerCool.setSize(DealerSize);
        DealerCool.setBounds(410, 0, DealerSize.width, DealerSize.height);
        layeredPane.add(DealerCool, Integer.valueOf(3));

        //add buttons
        layeredPane.add(hitButton, Integer.valueOf(4));
        layeredPane.add(standButton, Integer.valueOf(4));
        ActionListener hitListener = new HitListener();
        ActionListener standListener = new StandListener();
        hitButton.addActionListener(hitListener);
        standButton.addActionListener(standListener);

        //add Money
        Money.setBounds(640,370,300,200);
        Money.setFont(new java.awt.Font("Arial", Font.ITALIC, 50));
        Money.setOpaque(false);
        Money.setForeground(Color.white);
        layeredPane.add(Money, Integer.valueOf(5));

        this.setTitle("Blackjack Game");
        this.setSize(1000, 650);
        this.setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void loadPokersImage() {
        for (int i = 0;i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cards[(i * cols) + j] = ImageOfPoker.getSubimage(
                        j * (width+gap),
                        i * (height+gap),
                        width,
                        height
                );
            }
        }
    }

    public void showImageOnUI(Integer cardIndex,Integer Width,Integer Height,Integer LocX, Integer LocY){
        ImageIcon card1 = new ImageIcon(cards[cardIndex]); // load the image to a imageIcon
        Image originalSize = card1.getImage(); // transform it
        Image newSize = originalSize.getScaledInstance(Width, Height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        card1 = new ImageIcon(newSize);
        JLabel Card1 = new JLabel(card1);
        Dimension Card1Size = Card1.getPreferredSize();
        Card1.setSize(Card1Size);
        Card1.setBounds(LocX, LocY, Card1Size.width, Card1Size.height);
        layeredPane.add(Card1, JLayeredPane.DRAG_LAYER);
        layeredPane.moveToFront(Card1);
    }


    @Override
    public void deal2player(Card card) {
        showImageOnUI(card.getBufferedImageIndex(),70, 100,playersCardsStartsLocationX, playersCardsStartsLocationY);
        playersCardsStartsLocationX = playersCardsStartsLocationX+40;
    }


    @Override
    public void deal2dealer(Card card, Boolean theCardIsFlipped) {
        if (!theCardIsFlipped) {
            showImageOnUI(card.getBufferedImageIndex(), 56, 80, dealersCardsStartsLocationX, dealersCardsStartsLocationY);
            dealersCardsStartsLocationX = dealersCardsStartsLocationX + 20;
        }else{
            FlippedCardIndex = card.getBufferedImageIndex();
            showImageOnUI(41, 56, 80, dealersCardsStartsLocationX, dealersCardsStartsLocationY);
        }

    }

    @Override
    public void flip(){
        showImageOnUI(FlippedCardIndex,56,80,dealersCardsStartsLocationX,dealersCardsStartsLocationY);
        dealersCardsStartsLocationX = dealersCardsStartsLocationX + 20;
    }

    @Override
    public void bustEffect(){
        JLabel Bust = new JLabel("BUST!");
        Bust.setBounds(740,10,200,200);
        Bust.setFont(new java.awt.Font("Arial", Font.ITALIC, 50));
        Bust.setOpaque(false);
        Bust.setForeground(Color.red);
        layeredPane.add(Bust, Integer.valueOf(6));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) { e.printStackTrace();}
    }

    @Override
    public void bjEffect(){
        JLabel bj = new JLabel("21!");
        bj.setBounds(740,10,200,200);
        bj.setFont(new java.awt.Font("Arial", Font.ITALIC, 50));
        bj.setOpaque(false);
        bj.setForeground(Color.yellow);
        layeredPane.add(bj, Integer.valueOf(6));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) { e.printStackTrace();}
    }


    static class HitListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            hitButtonHasBeenPushedNTimes = hitButtonHasBeenPushedNTimes+1;
            System.out.println("hit is pressed");
        }
    }
    static class StandListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            standButtonHasBeenPushed = true;
            System.out.println("stand is pressed");
        }
    }

    public static void resetParameter(){
        standButtonHasBeenPushed = false;
        hitButtonHasBeenPushedNTimes =0;
    }

    public static void enterPlayerName(){
        Image originalSize = dealer.getImage(); // transform it
        Image newSize = originalSize.getScaledInstance(150, 200, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        dealer = new ImageIcon(newSize);
        playerName= (String) JOptionPane.showInputDialog(null,
                "Please enter your name",
                "Casino",
                JOptionPane.INFORMATION_MESSAGE,
                dealer,null,"");
    }

    public static void showStartMessage(){
        String message = "Hello "+playerName+","+'\n'+"<html><body><p style='width: 300px;'>"+
                " Welcome to the Casino. We have a blackjack ready for you. " +
                "Let's have some fun!" +"</p></body></html>"+'\n'+"<html><body><p style='width: 300px;'>"+"You will start with 100 chf. Before each round, you can make a bet. " +
                "If you win this round, you will get your bet back and win the same amount of your bet, "+
                "but if you lose, you will lose your bet. " +
                "I will ask you whether you want to leave with your money or start another after each round. "+
                "We wish you a happy and lucky day :)"+"</p></body></html>";
        Image originalSize = dealer.getImage(); // transform it
        Image newSize = originalSize.getScaledInstance(150, 200, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        dealer = new ImageIcon(newSize);
        String title = "Before we start";
        String[] responses = {"Understood. Let's start."};
        JOptionPane.showOptionDialog(null,
                message,
                title,
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                dealer,
                responses,0);
    }

    public static int showEndMessage(Round.Result result, GUI gui){
        Image originalSize = dealerCool.getImage(); // transform it
        Image newSize = originalSize.getScaledInstance(240, 200, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        dealerCool = new ImageIcon(newSize);
        String message;
        if (result== Round.Result.PlayerWon) {
            message = "You WON, "+playerName+ "! Do you want to continue or leave with " +Main.getMoney()+ "chf? ";
        }else {
            message = "You lost, "+playerName+". Do you want to continue or leave with " +Main.getMoney()+ "chf?";
        }
        String title = "This round is over";
        String[] responses = {"Let's start another round!","I would like to leave with this money"};
        int response = JOptionPane.showOptionDialog(gui,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                dealerCool,
                responses,0);
        return response;
    }

    public static void lastMessage(){
            String message = "Good bye "+playerName+","+'\n'+"<html><body><p style='width: 300px;'>"+
                    "You had "+Main.getMoney()+" left. I wish your life is as lucky as today. :)" +
                    "</p></body></html>";
            Image originalSize = dealerCool.getImage();
            Image newSize = originalSize.getScaledInstance(150, 200, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            dealerCool = new ImageIcon(newSize);
            String title = "Come back next time!";
            String[] responses = {"Good bye"};
            JOptionPane.showOptionDialog(null,
                    message,
                    title,
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    dealerCool,
                    responses,0);

    }


    public static int getBet(){
        int bet = 0;
        try {
            Image originalSize = chips.getImage(); // transform it
            Image newSize = originalSize.getScaledInstance(150, 200, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            chips = new ImageIcon(newSize);
            bet = Integer.parseInt((String) JOptionPane.showInputDialog(null,
                    "Please enter the bet you want to make (a Integer)",
                    "Maker the bet",
                    JOptionPane.INFORMATION_MESSAGE,
                    chips, null, ""));
        }catch(NumberFormatException e){getBet();}
        return bet;
    }

    @Override
    public void updateMoney(){
        int money = Main.getMoney();
        Money.setText("Gold: "+ money+"chf");
    }
}
