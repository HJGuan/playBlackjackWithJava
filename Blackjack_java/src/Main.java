

public class Main {

    static int money = 100;
    static int ifContinue = 0; //0 for continue, 1 for quit
    static GUI gui;

    public static void initGUI() {
        gui = new GUI();
        gui.setVisible(true);
        Round.attach(gui);
    }

    public static void restart() {
        Round.clear();
        gui = null;
        initGUI();
    }

    public static int getMoney() {
        return money;
    }

    public static void main(String[] args) {

        GUI.enterPlayerName();
        GUI.showStartMessage();
        //initGUI();

        System.out.println("start");
        while (money > 0 && ifContinue == 0) {
            restart();
            int bet = GUI.getBet();
            while (money < bet || bet < 0) {
                bet = GUI.getBet();
            }
            money = money - bet;
            Round.updateMoney();
            Round.Result result = Round.play();
            System.out.println(result);
            //calculate
            if (result == Round.Result.PlayerWon) {
                money = money + 2 * bet;
            } else if (result == Round.Result.Draw) {
                money = money + bet;
            } else {
            }
            Round.updateMoney();
            ifContinue = GUI.showEndMessage(result, gui);
        }
        GUI.lastMessage();
    }
}
