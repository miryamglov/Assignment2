public abstract class Player implements Runnable {
    private PlayerSign sign;
    protected Game game;
    
    public void setGame(Game game) {
        this.game = game;
    }

    public Player(PlayerSign sign) {
        this.sign = sign;
    }

    public PlayerSign getSign() {
        return sign;
    }
}
