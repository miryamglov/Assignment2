import java.util.ArrayList;

public class SelfPlayer extends Player {

    public SelfPlayer(PlayerSign sign) {
        super(sign);
    }

    public void run() {
        if (game == null) {
            throw new IllegalStateException("Game is not set!");
        }
        makeMove();
    }

    public void makeMove() {
        while (true) {
            while (game.getTurn() != getSign()) {
                try {
                    synchronized (game) {
                        game.wait();
                        if (game.isOver()) {
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }

            synchronized (game) {
                if (!game.isOver()) {
                    ArrayList<Cell> cells = game.getFreeCells();
                    int index = (int) (Math.random() * cells.size());
                    game.makeMove(cells.get(index));
                } else {
                    // End the game and notify the other player
                    game.notifyAll();
                    break;
                }

                game.notifyAll();
            }
        }
    }
}
