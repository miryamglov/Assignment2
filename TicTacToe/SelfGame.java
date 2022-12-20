
public class SelfGame extends Game {
    public void start() {
        SelfPlayer player1 = new SelfPlayer(PlayerSign.X);
        SelfPlayer player2 = new SelfPlayer(PlayerSign.O);
        player1.setGame(this);
        player2.setGame(this);
        Thread thread1 = new Thread(player1);
        Thread thread2 = new Thread(player2);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();

            if(isDraw()) {
                System.out.println("Draw!");
            } else {
                System.out.println("Winner: " + getWinner());
            }
        } catch (InterruptedException e) {
            //...
        }
    }
}
