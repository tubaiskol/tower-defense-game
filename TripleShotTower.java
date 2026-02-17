import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class TripleShotTower extends AbstractTower {
    public Timeline shootingTimeline;
    public Pane currentGamePane;

    //Constructor, inherit from AbstractTower with specific values for range, damage, and shoot rate
    public TripleShotTower() {
        super(150, 25, 700); 

        // Draw the tower with a method
        drawTripleShot();
    }

    // Draw the tower
    public void drawTripleShot(){
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


        // Add all parts to the group to be displayed
        this.getChildren().addAll(bigTower,doorLow,doorUpp, smallTower,upp1, upp1Shadow,upp2, upp2Shadow,upp3, upp3Shadow,upp4, upp4Shadow);
    }

    // Shooting method for the tower this method is called when the tower is placed on the game pane
    // The method starts a timeline that will shoot at the specified rate 
    public void shoot(Pane gamePane) {
        this.currentGamePane = gamePane;
        // Stop any existing shooting timeline
        if (shootingTimeline != null) {
            shootingTimeline.stop();
        }
        // Create a new timeline for shooting
        shootingTimeline = new Timeline(new KeyFrame(Duration.millis(shootRate), e -> {
            //check the target enemies in the game pane with a list because we have 3 enemies to shoot
            //if the target enemy is not null,  shoot the enemy
            List<Enemy> targets = findTargets(gamePane);
            // Check if we have found any targets with for each loop
            for (Enemy target : targets) {
                // Check if the target is not null and shoot the enemy
                if (target != null) { 
                    shootEnemy(target, gamePane, Color.BLUE, 5, 200); 
                }
            }
        }));

        // Set the timeline to repeat indefinitely
        shootingTimeline.setCycleCount(Timeline.INDEFINITE);
        shootingTimeline.play();
    }

    // Stop shooting if the tower is removed
    public void stopShoot() {
        // Stop the shooting timeline if it exists
        if (shootingTimeline != null) {
            shootingTimeline.stop();
            // Set the shooting timeline to null to show it's not active
            shootingTimeline = null;
        }
    }

    // Find targets within range
    public List<Enemy> findTargets(Pane gamePane) {
        // Create a list to keep the found targets 
        List<Enemy> targetsFound = new ArrayList<>();
        // Iterate through all children in the game pane thanks to for each loop 
        for (var node : gamePane.getChildren()) {
            //founds at least 3 targets and break the loop so we don't check the rest of the nodes
            if (targetsFound.size() >= 3) {
                break; // Found 3 targets
            }
            // Check if the node is an instance of Enemy and if it's within range and not dead or not finished
            //then add the enemy to the targetsFound list
            if (node instanceof Enemy enemy && isInRange(enemy) && !enemy.isDead() && !enemy.isFinished()) {
                targetsFound.add(enemy);
            }
        }
        //finally return the targetsFound list to be used in the shooting method
        return targetsFound;
    }
}
