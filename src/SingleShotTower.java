import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SingleShotTower extends AbstractTower {
    public Timeline shootingTimeline;

    // Constructor, inherit from AbstractTower with specific values for range, damage, and shoot rate  
    public SingleShotTower() {
        super(150,25,700);

        // Draw the tower with a method
        drawSingleShot();
    }


    // Draw the tower
    public void drawSingleShot() {

       // Main big tower
       Rectangle bigTower = new Rectangle();
       bigTower.setWidth(25);
       bigTower.setHeight(30);
       bigTower.setX(10);
       bigTower.setY(60);
       bigTower.setFill(Color.TAN);

       Rectangle doorBig = new Rectangle();
       doorBig.setWidth(5);
       doorBig.setHeight(10);
       doorBig.setX(20);
       doorBig.setY(80);
       doorBig.setFill(Color.SADDLEBROWN);

       // Main small tower
       Rectangle smallTower = new Rectangle();
       smallTower.setWidth(12);
       smallTower.setHeight(30);
       smallTower.setX(35);
       smallTower.setY(60);
       smallTower.setFill(Color.SADDLEBROWN);  
       
       //Small top towers
       //right side
       Rectangle topRight = new Rectangle();
       topRight.setWidth(5);
       topRight.setHeight(5);
       topRight.setX(10);
       topRight.setY(55);
       topRight.setFill(Color.TAN); 

       Rectangle top1Shadow = new Rectangle();
       top1Shadow.setWidth(2);
       top1Shadow.setHeight(5);
       top1Shadow.setX(45);
       top1Shadow.setY(55);
       top1Shadow.setFill(Color.SADDLEBROWN);

       //left side
       Rectangle topLeft = new Rectangle();
       topLeft.setWidth(5);
       topLeft.setHeight(5);
       topLeft.setX(40);
       topLeft.setY(55);
       topLeft.setFill(Color.TAN); 

       Rectangle top2Shadow = new Rectangle();
       top2Shadow.setWidth(2);
       top2Shadow.setHeight(5);
       top2Shadow.setX(15);
       top2Shadow.setY(55);
       top2Shadow.setFill(Color.SADDLEBROWN);


       //Small top mid towers
       Rectangle topMid1 = new Rectangle();
       topMid1.setWidth(5);
       topMid1.setHeight(5);
       topMid1.setX(20);
       topMid1.setY(55);
       topMid1.setFill(Color.TAN); 

       Rectangle mid1Shadow = new Rectangle();
       mid1Shadow.setWidth(2);
       mid1Shadow.setHeight(5);
       mid1Shadow.setX(25);
       mid1Shadow.setY(55);
       mid1Shadow.setFill(Color.SADDLEBROWN);

        Rectangle topMid2 = new Rectangle();
        topMid2.setWidth(5);
        topMid2.setHeight(5);
        topMid2.setX(30);
        topMid2.setY(55);
        topMid2.setFill(Color.TAN);
       

       Rectangle mid2Shadow = new Rectangle();
       mid2Shadow.setWidth(2);
       mid2Shadow.setHeight(5);
       mid2Shadow.setX(35);
       mid2Shadow.setY(55);
       mid2Shadow.setFill(Color.SADDLEBROWN);
      

       // Add all parts to the group to be displayed
       this.getChildren().addAll(bigTower,doorBig,smallTower,topRight,top1Shadow,topLeft,top2Shadow,topMid1,mid1Shadow,topMid2,mid2Shadow);
    }

    // Shooting method for the tower this method is called when the tower is placed on the game pane
    // The method starts a timeline that will shoot at the specified rate
    public void shoot(Pane gamePane) {
        // Stop any existing shooting timeline
        if (shootingTimeline != null) {
            shootingTimeline.stop();
        }
        // Create a new timeline for shooting
        shootingTimeline = new Timeline(new KeyFrame(Duration.millis(shootRate), e -> {
            //check the target enemy in the game pane
            //if the target enemy is not null,  shoot the enemy
            Enemy target = findTarget(gamePane);
            if (target != null) {
                shootEnemy(target, gamePane, Color.DARKRED, 6, 200); 
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

    // Find the target enemy within range 
    public Enemy findTarget(Pane gamePane) {
        // Iterate through all children in the game pane thanks to for each loop 
        for (var node : gamePane.getChildren()) {
            // Check if the node is an instance of Enemy and if it's within range 
            // and not dead or finished
            // If all conditions are met, return the enemy as the target so we can shoot it
            if (node instanceof Enemy enemy && isInRange(enemy) && !enemy.isDead() && !enemy.isFinished()) {
                return enemy;
            }
        }
        // If no target is found, return null
        // This will be used to check if the target is null or not
        return null;
    }
}
