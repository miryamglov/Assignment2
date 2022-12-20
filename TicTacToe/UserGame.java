public class UserGame extends Game {
    public void start() {
        UserPlayer player1 = new UserPlayer(PlayerSign.X);
        SelfPlayer player2 = new SelfPlayer(PlayerSign.O);
        player1.setGame(this);
        player2.setGame(this);
        Thread thread1 = new Thread(player1);
        Thread thread2 = new Thread(player2);
        thread2.start();
        thread1.start();
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
