import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;

public class MissileLauncherTower extends AbstractTower {
    public Timeline shootTimeline;
    // Constructor, inherit from AbstractTower with specific values for range, damage, and shoot rate
    public MissileLauncherTower() {
        super(200, 100, 3000); 

        // Draw the tower with a method
        drawMissileTower();
    }

    // Draw the tower
    public void drawMissileTower() {
        // Main big tower
        Rectangle bigTower = new Rectangle();
        bigTower.setWidth(17);
        bigTower.setHeight(40);
        bigTower.setX(10);
        bigTower.setY(30);
        bigTower.setFill(Color.TAN);

        Rectangle doorLow = new Rectangle();
        doorLow.setWidth(5);
        doorLow.setHeight(10);
        doorLow.setX(16);
        doorLow.setY(60);
        doorLow.setFill(Color.SADDLEBROWN);

        Rectangle doorUpp = new Rectangle();
        doorUpp.setWidth(5);
        doorUpp.setHeight(10);
        doorUpp.setX(16);
        doorUpp.setY(40);
        doorUpp.setFill(Color.SADDLEBROWN);

        // Main small tower
        Rectangle smallTower = new Rectangle();
        smallTower.setWidth(9);
        smallTower.setHeight(40);
        smallTower.setX(27);
        smallTower.setY(30);
        smallTower.setFill(Color.SADDLEBROWN);  


        //Upper towers
        //upp1
        Rectangle upp1 = new Rectangle();
        upp1.setWidth(3);
        upp1.setHeight(3);
        upp1.setX(10);
        upp1.setY(27);
        upp1.setFill(Color.TAN);

        Rectangle upp1Shadow = new Rectangle();
        upp1Shadow.setWidth(1);
        upp1Shadow.setHeight(3);
        upp1Shadow.setX(13);
        upp1Shadow.setY(27);
        upp1Shadow.setFill(Color.SADDLEBROWN);

        //upp2
        Rectangle upp2 = new Rectangle();
        upp2.setWidth(3);
        upp2.setHeight(3);
        upp2.setX(17);
        upp2.setY(27);
        upp2.setFill(Color.TAN);

        Rectangle upp2Shadow = new Rectangle();
        upp2Shadow.setWidth(1);
        upp2Shadow.setHeight(3);
        upp2Shadow.setX(20);
        upp2Shadow.setY(27);
        upp2Shadow.setFill(Color.SADDLEBROWN);

        //upp3
        Rectangle upp3 = new Rectangle();
        upp3.setWidth(3);
        upp3.setHeight(3);
        upp3.setX(24);
        upp3.setY(27);
        upp3.setFill(Color.TAN);

        Rectangle upp3Shadow = new Rectangle();
        upp3Shadow.setWidth(1);
        upp3Shadow.setHeight(3);
        upp3Shadow.setX(27);
        upp3Shadow.setY(27);
        upp3Shadow.setFill(Color.SADDLEBROWN);

        //upp4
        Rectangle upp4 = new Rectangle();
        upp4.setWidth(3);
        upp4.setHeight(3);
        upp4.setX(31);
        upp4.setY(27);
        upp4.setFill(Color.TAN);

        Rectangle upp4Shadow = new Rectangle();
        upp4Shadow.setWidth(1);
        upp4Shadow.setHeight(3);
        upp4Shadow.setX(34);
        upp4Shadow.setY(27);
        upp4Shadow.setFill(Color.SADDLEBROWN);


        //Middle Tower
        Rectangle midTower = new Rectangle();
        midTower.setWidth(18);
        midTower.setHeight(27);
        midTower.setX(27);
        midTower.setY(43);
        midTower.setFill(Color.TAN);

        Rectangle midDoor = new Rectangle();
        midDoor.setWidth(10);
        midDoor.setHeight(20);
        midDoor.setX(31);
        midDoor.setY(50);
        midDoor.setFill(Color.SADDLEBROWN);

        // Main big tower2
        Rectangle bigTower2 = new Rectangle();
        bigTower2.setWidth(17);
        bigTower2.setHeight(40);
        bigTower2.setX(45);
        bigTower2.setY(30);
        bigTower2.setFill(Color.TAN);

        Rectangle doorLow2 = new Rectangle();
        doorLow2.setWidth(5);
        doorLow2.setHeight(10);
        doorLow2.setX(51);
        doorLow2.setY(60);
        doorLow2.setFill(Color.SADDLEBROWN);

        Rectangle doorUpp2 = new Rectangle();
        doorUpp2.setWidth(5);
        doorUpp2.setHeight(10);
        doorUpp2.setX(51);
        doorUpp2.setY(40);
        doorUpp2.setFill(Color.SADDLEBROWN);

        // Main small tower
        Rectangle smallTower2 = new Rectangle();
        smallTower2.setWidth(9);
        smallTower2.setHeight(40);
        smallTower2.setX(62);
        smallTower2.setY(30);
        smallTower2.setFill(Color.SADDLEBROWN);  


        //Upper towers
        //upp1-2
        Rectangle upp12 = new Rectangle();
        upp12.setWidth(3);
        upp12.setHeight(3);
        upp12.setX(45);
        upp12.setY(27);
        upp12.setFill(Color.TAN);

        Rectangle upp12Shadow = new Rectangle();
        upp12Shadow.setWidth(1);
        upp12Shadow.setHeight(3);
        upp12Shadow.setX(48);
        upp12Shadow.setY(27);
        upp12Shadow.setFill(Color.SADDLEBROWN);

        //upp2-2
        Rectangle upp22 = new Rectangle();
        upp22.setWidth(3);
        upp22.setHeight(3);
        upp22.setX(52);
        upp22.setY(27);
        upp22.setFill(Color.TAN);

        Rectangle upp22Shadow = new Rectangle();
        upp22Shadow.setWidth(1);
        upp22Shadow.setHeight(3);
        upp22Shadow.setX(55);
        upp22Shadow.setY(27);
        upp22Shadow.setFill(Color.SADDLEBROWN);

        //upp3-2
        Rectangle upp32 = new Rectangle();
        upp32.setWidth(3);
        upp32.setHeight(3);
        upp32.setX(59);
        upp32.setY(27);
        upp32.setFill(Color.TAN);

        Rectangle upp32Shadow = new Rectangle();
        upp32Shadow.setWidth(1);
        upp32Shadow.setHeight(3);
        upp32Shadow.setX(62);
        upp32Shadow.setY(27);
        upp32Shadow.setFill(Color.SADDLEBROWN);

        //upp4-2
        Rectangle upp42 = new Rectangle();
        upp42.setWidth(3);
        upp42.setHeight(3);
        upp42.setX(66);
        upp42.setY(27);
        upp42.setFill(Color.TAN);

        Rectangle upp42Shadow = new Rectangle();
        upp42Shadow.setWidth(1);
        upp42Shadow.setHeight(3);
        upp42Shadow.setX(69);
        upp42Shadow.setY(27);
        upp42Shadow.setFill(Color.SADDLEBROWN);

        // Add all parts to the group
        this.getChildren().addAll(bigTower,doorLow,doorUpp, smallTower,upp1, upp1Shadow,upp2, upp2Shadow,upp3, upp3Shadow,upp4, upp4Shadow,
        midTower, midDoor,bigTower2,doorLow2,doorUpp2, smallTower2,upp12, upp12Shadow,upp22, upp22Shadow,upp32, upp32Shadow,upp42, upp42Shadow);
    }

    // Shooting method for the tower this method is called when the tower is placed on the game pane
    // The method starts a timeline that will shoot at the specified rate
    public void shoot(Pane gamePane) {
        //Stop any existing shooting timeline
        if (shootTimeline != null) {
            shootTimeline.stop();
        }

        // Create a new timeline for shooting
        shootTimeline = new Timeline(new KeyFrame(Duration.millis(shootRate), e -> {
            //find the first target in gamePane if it is in range
            Enemy target = findTarget(gamePane);
            // If a target is found, shoot at it
            if (target != null) {
                // Use missile-specific bullet color, size, and speed
                shootEnemy(target, gamePane, Color.ORANGERED, 8, 200);
            }
        }));

        // Set the timeline to repeat indefinitely
        shootTimeline.setCycleCount(Timeline.INDEFINITE);
        shootTimeline.play();
    }

    // Stop shooting if the tower is removed
    public void stopShoot() {
        // Stop the shooting timeline if it exists
        if (shootTimeline != null) {
            shootTimeline.stop();
            // Set the shooting timeline to null to show it's not active
            shootTimeline = null;
        }
    }

    // Find the first target in range 
    public Enemy findTarget(Pane gamePane) {
        //if the gamePane is null, return null so we don't get a null pointer exception because of the gamePane
        if (gamePane == null) 
            return null;
        // Iterate through the children of the gamePane to find an enemy in range
        // we used for-each loop to iterate through the children of the gamePane and check if the node is an instance of Enemy
        // Check if the node is an instance of Enemy and if it is in range and not dead and not finished the map
        for (var node : gamePane.getChildren()) {
            if (node instanceof Enemy enemy && isInRange(enemy) && !enemy.isDead() && !enemy.isFinished()) {
                // If all conditions are met, return the enemy as the target so we can shoot it
                return enemy;
            }
        }
        // If target is not found, return null
        return null;
    }

     //we determined the enemy as target in gamepane and shoot enemy by creating a bullet with a specific color, size, and speed
    @Override
    public void shootEnemy(Enemy target, Pane gamePane, Color bulletColor, int bulletSize, int bulletSpeed) {

        //if the target is null or the target's parent is null or the gamePane is null or the tower's parent is null
        // return so we don't get a null pointer exception and we don't shoot the bullet
        if (target == null || target.getParent() == null || gamePane == null || this.getParent() == null) {
            return;
        }

        // calculations for Tower's center for bullet start point to be used in the bullet's position
        //we get the bounds of the tower and convert its center point to the pane's coordinate system
        //so we can start the bullet position at the tower's center position
        Bounds coordinates = this.getBoundsInParent();
        Point2D towerCenter = this.getParent().localToScene(
            coordinates.getMinX() + coordinates.getWidth() / 2,
            coordinates.getMinY() + coordinates.getHeight() / 2
        );
        Point2D bulletFirstXY = gamePane.sceneToLocal(towerCenter);

        // we Created a bullet (circle) at the tower's position that we calculated above 
        //then we added it to the game pane so we can see it
        Circle bullet = new Circle(bulletSize, bulletColor);
        bullet.setCenterX(bulletFirstXY.getX());
        bullet.setCenterY(bulletFirstXY.getY());
        gamePane.getChildren().add(bullet);

        //we calculated target's center with the bounds of the target
        //so we can move the bullet to the target's position dynamically
        //then we will not meet a logic error when the target is moving and the bullet is not
        Bounds firstTargetXY = target.getBoundsInLocal();
        Point2D firstTargetXY1 = new Point2D(firstTargetXY.getWidth() / 2, firstTargetXY.getHeight() / 2);
        
        // Check if the target is still in the scene before shooting
        // If the target is not in the scene, remove the bullet and return
        if (target.getScene() == null) {
            gamePane.getChildren().remove(bullet); // Clean up bullet if target is gone
            return;
        }

        // bound the targets initial center to the scene
        //we get the bounds of the target and convert its center point to the pane's coordinate system
        Point2D firstTarget = target.localToScene(firstTargetXY1);
        Point2D bulletEndXY = gamePane.sceneToLocal(firstTarget);

        // Calculate the translation distance for the bullet
        //we set the translation distance for the bullet to move from the tower's center to the target's center
        TranslateTransition tt = new TranslateTransition(Duration.millis(bulletSpeed), bullet);
        tt.setToX(bulletEndXY.getX() - bulletFirstXY.getX());
        tt.setToY(bulletEndXY.getY() - bulletFirstXY.getY());

        //when the bullet reaches the target, we damage the target and remove the bullet from the game pane with a listener
        tt.setOnFinished(e -> {
            gamePane.getChildren().remove(bullet);

            // Use the missile's end point as the center of the explosion
            Point2D pointEnd = bulletEndXY;

            double radius;
            if (target.getCellSize() > 0) {
                radius = 1.5 * target.getCellSize();
            } else {
                radius = 75.0; // Default: 1.5 * 50px (an average cell size)
            }

            // damge enemies in the area of effect
            // create copy  of gamePane's children to not make a mistake while iterating
            List<Node> childrenCopy = new ArrayList<>(gamePane.getChildren());
            for (Node node : childrenCopy) {
                // Check if the node is an instance of Enemy and if it's within range and not dead or not finished the map
                if (node instanceof Enemy currentEnemy && !currentEnemy.isDead() && !currentEnemy.isFinished()) {
                    // Calculate distance from impact point to currentEnemy's center so we can bound the enemies to the impact point
                    Bounds curEnemyXY = currentEnemy.getBoundsInLocal();
                    Point2D curEnemyCenter = new Point2D(curEnemyXY.getWidth() / 2, curEnemyXY.getHeight() / 2);
                    
                    // Check if the current enemy is in the scene before calculating its center because it can be removed
                    // If the current enemy is not in the scene, continue to the next enemy
                    if (currentEnemy.getScene() == null) 
                        continue;

                    
                    //we get the bounds of the enemy and convert its center point to the pane's coordinate system   
                    //so we can move the bullet to the enemy's position dynamically
                    Point2D curEnemyCen = currentEnemy.localToScene(curEnemyCenter);
                    Point2D curEnemyCen2 = gamePane.sceneToLocal(curEnemyCen);

                    //we get the distance between the impact point and the current enemy's center and calculate the distance
                    double dx = pointEnd.getX() - curEnemyCen2.getX();
                    double dy = pointEnd.getY() - curEnemyCen2.getY();
                    double distance = Math.sqrt(dx * dx + dy * dy);

                    // Check if the current enemy is within the area of effect radius
                    // If it is, damage the enemy
                    if (distance <= radius) {
                        currentEnemy.takeDamage(this.damage); // this.damage is from AbstractTower (25)
                    }
                }
            }
        });
        tt.play();
    }
}
