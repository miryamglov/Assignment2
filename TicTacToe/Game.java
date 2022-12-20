import java.util.ArrayList;

public abstract class Game {
    private PlayerSign[][] gameBoard = new PlayerSign[3][3];
    private PlayerSign currentPlayer;

    public Game() {
        currentPlayer = PlayerSign.X;
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(gameBoard[i][j]);
                }
                if (j < 2) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("-+-+-");
            }
        }
    }

    public PlayerSign getTurn() {
        return currentPlayer;
    }

    public ArrayList<Cell> getFreeCells() {
        ArrayList<Cell> freeCells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j] == null) {
                    freeCells.add(new Cell(i, j));
                }
            }
        }
        return freeCells;
    }

    public void makeMove(Cell cell) {
        if (gameBoard[cell.getRow()][cell.getCol()] == null) {
            gameBoard[cell.getRow()][cell.getCol()] = currentPlayer;
            currentPlayer = currentPlayer == PlayerSign.X ? PlayerSign.O : PlayerSign.X;

            printBoard();
            System.out.println();
        } else {
            System.out.println("This cell is already taken!");
        }
    }

    public boolean isOver() {
        return isWon(PlayerSign.X) || isWon(PlayerSign.O) || isDraw();
    }

    public boolean isWon(PlayerSign sign) {
        return (gameBoard[0][0] == sign && gameBoard[0][1] == sign && gameBoard[0][2] == sign)
                || (gameBoard[1][0] == sign && gameBoard[1][1] == sign && gameBoard[1][2] == sign)
                || (gameBoard[2][0] == sign && gameBoard[2][1] == sign && gameBoard[2][2] == sign)
                || (gameBoard[0][0] == sign && gameBoard[1][0] == sign && gameBoard[2][0] == sign)
                || (gameBoard[0][1] == sign && gameBoard[1][1] == sign && gameBoard[2][1] == sign)
                || (gameBoard[0][2] == sign && gameBoard[1][2] == sign && gameBoard[2][2] == sign)
                || (gameBoard[0][0] == sign && gameBoard[1][1] == sign && gameBoard[2][2] == sign)
                || (gameBoard[2][0] == sign && gameBoard[1][1] == sign && gameBoard[0][2] == sign);
    }

    public boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public PlayerSign getWinner() {
        if (isWon(PlayerSign.X)) {
            return PlayerSign.X;
        } else if (isWon(PlayerSign.O)) {
            return PlayerSign.O;
        } else {
            return null;
        }
    }
}