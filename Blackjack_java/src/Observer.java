//import java.util.Observable;

public interface Observer {
    void deal2player(Card card);
    void deal2dealer(Card card,Boolean theCardIsFlipped);
    void updateMoney();
    void flip();
    void bustEffect();
    void bjEffect();
}
