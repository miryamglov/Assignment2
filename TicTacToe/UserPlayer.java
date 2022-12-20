import java.util.Scanner;

public class UserPlayer extends Player {
    private static final Scanner scanner = new Scanner(System.in);

    public UserPlayer(PlayerSign sign) {
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

            synchronized (game) {
                if (!game.isOver()) {
                    game.printBoard();
                    System.out.println("Your turn! Enter the cell: ");
                    int cell = scanner.nextInt() - 1;
                    int row = cell / 3;
                    int col = cell % 3;
                    game.makeMove(new Cell(row, col));
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

