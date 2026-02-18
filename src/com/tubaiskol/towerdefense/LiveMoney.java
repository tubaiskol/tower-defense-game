public class LiveMoney {
    public static int lives;
    public static int money;

    //getters and setters for lives and decreasing lives
    public static int getLives() {
        return lives;
    }

    public static void setLives(int x) {
        lives = x;
    }

    public static void decreaseLives() {
        lives--;
    }

    //checker for game is over or not
    public static boolean isGameOver() {
        return lives <= 0;
    }

    //getters and setters for money
    public static int getMoney() {
        return money;
    }

    public static void setMoney(int m) {
        money = m;
    }

    //adding and spending money methods
    public static void addMoney(int amount) {
        money += amount;
    }

    public static boolean spendMoney(int amount) {
        if (money >= amount) {
            money -= amount;
            return true;
        }
        return false;
    }

    //check if player can afford the tower or not
    //if player has enough money to buy the tower, return true
    //if player doesn't have enough money to buy the tower, return false
    //so user can buy the tower and place it on the map
    public static boolean canAfford(int amount) {
        return money >= amount;
    }
}
