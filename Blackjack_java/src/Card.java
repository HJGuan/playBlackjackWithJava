enum Suit {
    DIAMOND(0),CLUB(1),HEART(2),SPADE(3);

    private final int n;
    Suit(int i) {
        this.n=i;
    }
    public int getSuitNum(){
        return n;
    }
}

enum Rank {
    ACE(11),TWO(2),THREE(3),FOUR(4),FIVE(5),SIX(6),SEVEN(7),EIGHT(8),NIGHT(9),TEN(10),
    JACK(10),QUEEN(10),KING(10),ACE_MINOR(1);

    private final int value;
    Rank(int i) {
        this.value=i;
    }
    public int getValue(){
        return value;
    }
}

public class Card {
    Suit aSuit;
    Rank aRank;

    public Card(Suit suit,Rank rank){
        aSuit = suit;
        aRank = rank;
    }

    public int getValue(){
        return aRank.getValue();
    }

    public String getName(){
        return aSuit.toString()+"_"+aRank.getValue();
    }

    public boolean isACE(){
        return aRank==Rank.ACE;
    }

    public void setACEtoACE_MINOR(){
        aRank = Rank.ACE_MINOR;
    }

    public int getBufferedImageIndex(){
        int col = 0;
        int row = aSuit.getSuitNum();
        int value = aRank.getValue();
        String rankName=aRank.toString();
        if (value<10){
            col = value-2;
        }else{
            if (rankName.equals("TEN")){
                col=8;
            }else if (rankName.equals("JACK")){
                col=9;
            }else if (rankName.equals("QUEEN")){
                col=10;
            }else if (rankName.equals("KING")){
                col=11;
            }else if (rankName.equals("ACE")){
                col=12;
            }
        }
        return row*14+col;
    }


}
