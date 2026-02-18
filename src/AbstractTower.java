import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class AbstractTower extends Group {


    //we defined the general tower attributes range, damage and shoot rate to manage towers easily
    public double range;
    public int damage;
    public int shootRate;
    
    //constructor for abstract tower. other towers will invoke it because all have same parameters. as simplle as the attributes
    //we set the range, damage and shoot rate of the tower in specialized towers
    public AbstractTower(double range, int damage, int shootRate) {
        this.range = range;
        this.damage = damage;
        this.shootRate = shootRate;
    }

    //getter method for range
    public double getRange() { 
        return this.range;
    }

    //we get the distance between the tower and the enemy by using the bounds of the tower and enemy
    //and then we get the center of the tower and enemy and calculate the distance between them
    public double getDistance(Enemy enemy) {
        //getBoundsInParent is used to get the bounds of the tower
        //this keyword is used to get the parent of the abstracttower
        Bounds towerBounds = this.getBoundsInParent();
        Point2D towerCenter = this.getParent().localToScene(
            towerBounds.getMinX() + towerBounds.getWidth() / 2,
            towerBounds.getMinY() + towerBounds.getHeight() / 2
        );

        //getBoundsInLocal is used to get the bounds of the enemy
        //localToScene is used to get the center of the enemy
        //we get the center of the enemy and calculate the distance between the tower and enemy
        //we get the bounds of the enemy and convert its center point to the pane's coordinate system 
        //so we can move the bullet to the enemy's position dynamically
        Bounds enemyBounds = enemy.getBoundsInLocal();
        Point2D enemyCenter = enemy.localToScene(enemyBounds.getWidth() / 2, enemyBounds.getHeight() / 2);
        double dx = towerCenter.getX() - enemyCenter.getX();
        double dy = towerCenter.getY() - enemyCenter.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    //check if enemy is in range or not by taking the enemies health and getting  distance
    public boolean isInRange(Enemy enemy) {
        return !enemy.isDead() && getDistance(enemy) <= range;
    }

    //we determined the enemy as target in gamepane and shoot enemy by creating a bullet
    public void shootEnemy(Enemy target, Pane gamePane, Color color, int bulletSize, int speed) {
        Bounds towerBounds = this.getBoundsInParent();
        Point2D towerCenter = this.getParent().localToScene(
            towerBounds.getMinX() + towerBounds.getWidth() / 2,
            towerBounds.getMinY() + towerBounds.getHeight() / 2
        );
        // we converted the tower's center point to the pane's coordinate system
        Point2D panePoint = gamePane.sceneToLocal(towerCenter);

        // we Created a bullet circle at the tower's position and added it to the game pane
        Circle bullet = new Circle(bulletSize, color);
        bullet.setCenterX(panePoint.getX());
        bullet.setCenterY(panePoint.getY());
        gamePane.getChildren().add(bullet);

        // we get the bounds of the enemy and convert its center point to the pane's coordinate system so we can move the bullet to the enemy's position
        Bounds enemyCoordinate = target.getBoundsInLocal();
        Point2D enemyCenter = target.localToScene(enemyCoordinate.getWidth() / 2, enemyCoordinate.getHeight() / 2);
        Point2D enemyInPane = gamePane.sceneToLocal(enemyCenter);

        //we add a transition to move the bullet to the enemy's position and damage the enemy by set the position of the enemy
        // when the bullet reaches the enemy and added a listener to remove the bullet from the game pane and damage the enemy
        TranslateTransition tt = new TranslateTransition(Duration.millis(speed), bullet);
        tt.setToX(enemyInPane.getX() - panePoint.getX());
        tt.setToY(enemyInPane.getY() - panePoint.getY());
        tt.setOnFinished(_ -> {
            target.takeDamage(damage);
            gamePane.getChildren().remove(bullet);
        });
        tt.play();
    }
}
