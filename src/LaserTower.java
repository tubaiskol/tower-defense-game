import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

public class LaserTower extends AbstractTower {
    public Timeline shootTimeline;
    public Timeline refreshTimeline; // For smooth visual updates
    public Pane pane;
    public Map<Enemy, Line> activeLines;
    public static final int refreshRateMS = 33; // 33ms for line updates

    // Constructor, inherit from AbstractTower with specific values for range, damage, and shoot rate
    public LaserTower() {
        super(150, 1, 100); // Range, Damage per tick (applied by shootingTimeline), ShootRate (ms)
        
        // Draw the tower with a method
        drawLaserTower();
        // Initialize the active laser lines map
        activeLines = new HashMap<>();
    }

    // Draw the tower
    public void drawLaserTower() {
        // Main upper tower
        Rectangle upperTower = new Rectangle();
        upperTower.setWidth(18);
        upperTower.setHeight(15);
        upperTower.setX(15);
        upperTower.setY(45);
        upperTower.setFill(Color.TAN);

        Rectangle upperShadow = new Rectangle();
        upperShadow.setWidth(8);
        upperShadow.setHeight(15);
        upperShadow.setX(33);
        upperShadow.setY(45);
        upperShadow.setFill(Color.SADDLEBROWN);

        // Small upper towers
        // upp1
        Rectangle upp1 = new Rectangle();
        upp1.setWidth(4);
        upp1.setHeight(3);
        upp1.setX(15);
        upp1.setY(42);
        upp1.setFill(Color.TAN);

        Rectangle upp1Shadow = new Rectangle();
        upp1Shadow.setWidth(1);
        upp1Shadow.setHeight(3);
        upp1Shadow.setX(19);
        upp1Shadow.setY(42);
        upp1Shadow.setFill(Color.SADDLEBROWN);

        // upp2
        Rectangle upp2 = new Rectangle();
        upp2.setWidth(4);
        upp2.setHeight(3);
        upp2.setX(22);
        upp2.setY(42);
        upp2.setFill(Color.TAN);

        Rectangle upp2Shadow = new Rectangle();
        upp2Shadow.setWidth(1);
        upp2Shadow.setHeight(3);
        upp2Shadow.setX(26);
        upp2Shadow.setY(42);
        upp2Shadow.setFill(Color.SADDLEBROWN);

        // upp3
        Rectangle upp3 = new Rectangle();
        upp3.setWidth(4);
        upp3.setHeight(3);
        upp3.setX(29);
        upp3.setY(42);
        upp3.setFill(Color.TAN);

        Rectangle upp3Shadow = new Rectangle();
        upp3Shadow.setWidth(1);
        upp3Shadow.setHeight(3);
        upp3Shadow.setX(33);
        upp3Shadow.setY(42);
        upp3Shadow.setFill(Color.SADDLEBROWN);

        // upp4
        Rectangle upp4 = new Rectangle();
        upp4.setWidth(4);
        upp4.setHeight(3);
        upp4.setX(36);
        upp4.setY(42);
        upp4.setFill(Color.TAN);

        Rectangle upp4Shadow = new Rectangle();
        upp4Shadow.setWidth(1);
        upp4Shadow.setHeight(3);
        upp4Shadow.setX(40);
        upp4Shadow.setY(42);
        upp4Shadow.setFill(Color.SADDLEBROWN);

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

        // Small top towers
        // right side
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

        // left side
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

        // Small top mid towers
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

        // Add all parts to the group
        this.getChildren().addAll(upperTower, upperShadow, upp1, upp1Shadow, upp2, upp2Shadow, upp3, upp3Shadow, upp4, upp4Shadow,bigTower, doorBig, smallTower, topRight, top1Shadow, topLeft, top2Shadow, topMid1, mid1Shadow, topMid2, mid2Shadow);
    }

    // Calculate the center of the tower in the pane
    public Point2D calcCenter() {
        // If the tower is not in a pane, return null
        if (this.getParent() == null) 
            return null;
        //if the tower is in a pane, we get the bounds of the tower and convert its center point to the pane's coordinate system    
        //so we can add the laser lines to the pane
        Bounds towerBounds = this.getBoundsInParent();
        Point2D towerCenterInCell = new Point2D(
                towerBounds.getMinX() + towerBounds.getWidth() / 2,
                towerBounds.getMinY() + towerBounds.getHeight() / 2
        );
        return this.getParent().localToParent(towerCenterInCell);
    }

    // Calculate the center of the enemy in the pane to draw the laser line
    public Point2D calcEnemyCenter(Enemy enemy) {
        // If the enemy is not in a pane, return null
        if (enemy == null || enemy.getParent() == null) 
            return null; // Enemy might have been removed from pane

        // If the enemy is in a pane, we get the bounds of the enemy and convert its center point to the pane's coordinate system
        Bounds enemyBounds = enemy.getBoundsInLocal();
        Point2D enemyCenterInLocal = new Point2D(enemyBounds.getWidth() / 2, enemyBounds.getHeight() / 2);
        return enemy.localToParent(enemyCenterInLocal);
    }

    // Shooting method for the tower this method is called when the tower is placed on the game pane
    // The method starts a timeline that will shoot at the specified rate
    public void shoot(Pane gamePane) {
        this.pane = gamePane;
        // Stop any existing shooting timeline
        if (shootTimeline != null) {
            shootTimeline.stop();
        }
        // stop any existing visual update timeline
        if (refreshTimeline != null) {
            refreshTimeline.stop();
        }

        // Shooting Timeline: Manages targets, lines, and applies damage
        shootTimeline = new Timeline(new KeyFrame(Duration.millis(shootRate), e -> {
            //get the center of the tower in the pane
            Point2D towerCenter = calcCenter();
            // clear all laser lines if tower is null
            if (towerCenter == null) { 
                clearLines(gamePane);
                return;
            }

            // Find all enemies in range with a list
            List<Enemy> enemysInRange = findEnemiesInRange(gamePane);

            // Create a new ArrayList of current targets for easy comparison
            ArrayList<Enemy> newActiveEnemies = new ArrayList<>(enemysInRange);

            // we updated existing lines, damage, and identified lines to remove when enemies are out of range or dead
            activeLines.entrySet().removeIf(entry -> {
                Enemy enemy = entry.getKey();
                Line line = entry.getValue();

                // Check if the enemy is still in range and not dead or finished
                // If the enemy is in the new set, update the line and  damage
                if (newActiveEnemies.contains(enemy) && !enemy.isDead() && !enemy.isFinished()) {
                    enemy.takeDamage(this.damage); // damage
                    newActiveEnemies.remove(enemy); // Enemy is already handled, remove from new set
                    return false; // Do not remove from activeLines
                } else {
                    // Enemy out of range, dead, finished, or no longer in currentTargetsInRange
                    // Remove the line from the game pane
                    if (pane.getChildren().contains(line)) { // Assuming 'pane' is the correct Pane instance here, not gamePane directly for removal from activeLines context
                        pane.getChildren().remove(line);
                    }
                    return true; // Remove from activeLines
                }
            });

            // add new lines for new targets  by iterating through the newActiveEnemiesSet in for each loop
            for (Enemy newTarget : newActiveEnemies) {
                // Check if the new target is not dead or finished
                if (newTarget.isDead() || newTarget.isFinished()) 
                    continue;

                // Calculate the center of the enemy in the pane    
                Point2D enemyCenter = calcEnemyCenter(newTarget);
                // If the enemy is in a pane
                // we get the bounds of the enemy and convert its center point to the pane's coordinate system
                if (enemyCenter != null) {
                    // Create a new line from the tower to the enemy 
                    Line newLine = new Line(towerCenter.getX(), towerCenter.getY(), enemyCenter.getX(), enemyCenter.getY());
                    newLine.setStroke(Color.RED);
                    newLine.setStrokeWidth(2);
                    // Add the line to the game pane
                    gamePane.getChildren().add(newLine); 
                    // Add the line to the activeLaserLines map so we can update it later
                    activeLines.put(newTarget, newLine); 
                    // Damage the new target at a given time and rate
                    newTarget.takeDamage(this.damage); 
                }
            }
        }));
        // Set the timeline to repeat indefinitely
        shootTimeline.setCycleCount(Timeline.INDEFINITE);
        shootTimeline.play();

        // Visual Update Timeline: Smoothly updates line positions 
        refreshTimeline = new Timeline(new KeyFrame(Duration.millis(refreshRateMS), e -> {
            // If no active lines or current game pane, do nothing
            if (activeLines.isEmpty() || pane == null) 
                return;

            // Get the center of the tower in the pane
            Point2D towerCenter = calcCenter();
            // If the tower is not in a pane, do nothing
            if (towerCenter == null) 
                return; 

            // Update the position of each line to the current position of the tower and enemy
            // Iterate through the active laser lines
            // For each loop takes activeLaserLines map
            for (Map.Entry<Enemy, Line> entry : activeLines.entrySet()) {
                // Get the enemy and line from the entry
                Enemy enemy = entry.getKey();
                Line line = entry.getValue();

                // if the line is not in the game pane or the enemy is dead or finished, continue to the next iteration
                if (!pane.getChildren().contains(line) || enemy.isDead() || enemy.isFinished()) {
                    // shootingTimeline will handle removal from activeLaserLines and gamePane
                    // For visuals, we just stop updating it if it's effectively gone or enemy is dead.
                    continue;
                }

                // Calculate the center of the enemy in the pane
                Point2D enemyCenter = calcEnemyCenter(enemy);
                // If the enemy is in a pane
                // we get the bounds of the enemy and convert its center point to the pane's coordinate system
                // Update the line's start and end points to the current positions of the tower and enemy
                if (enemyCenter != null) {
                    line.setStartX(towerCenter.getX());
                    line.setStartY(towerCenter.getY());
                    line.setEndX(enemyCenter.getX());
                    line.setEndY(enemyCenter.getY());
                }
            }
        }));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }

    // Clear all laser lines from the game pane
    public void clearLines(Pane gamePane) {
        // If gamePane is not null 
        if (gamePane != null) {
            // Iterate through the active laser lines and remove them from the game pane if they exist
            for (Line line : activeLines.values()) {
                if (gamePane.getChildren().contains(line)) {
                    gamePane.getChildren().remove(line);
                }
            }
        }
        // Clear the active laser lines in map
        activeLines.clear();
    }

    // Stop shooting if the tower is removed
    public void stopShoot() {

        // Stop shooting timeline if it exists
        if (shootTimeline != null) {
            shootTimeline.stop();
            //Set the shooting timeline to null to show it's not active
            shootTimeline = null;
        }
        // Stop visual update timeline becacuse we don't need to update the lines anymore
        if (refreshTimeline != null) { // Stop visual timeline as well
            refreshTimeline.stop();
            //set the visual timeline to null to show it's not active
            refreshTimeline = null;
        }
        // Clear all laser lines from the game pane
        clearLines(this.pane);
    }

    // Find targets within range by using a list because we have multiple enemies to shoot
    public List<Enemy> findEnemiesInRange(Pane gamePane) {
        List<Enemy> enemiesInRange = new ArrayList<>();
        // If gamePane is null or tower is not in a pane, return empty list
        if (gamePane == null || this.getParent() == null) {
            return enemiesInRange;
        }
        //if any enemy is found, add all enemies in range
        for (var node : gamePane.getChildren()) {
            // Check if the node is an instance of Enemy and if it's within range
            // and not dead or finished
            if (node instanceof Enemy enemy && isInRange(enemy) && !enemy.isDead() && !enemy.isFinished()) {
                // Add the enemy to the enemiesInRange list
                enemiesInRange.add(enemy);
            }
        }
        //after checking all the nodes, we return the enemiesInRange list to be used in the shooting method
        return enemiesInRange;
    }
}
