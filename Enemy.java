import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.List;

public class Enemy extends Group {
    public static int MAX_HEALTH = 100;
    public static double HEAD_RADIUS = 4.0;
    public static double HEALTH_BAR_WIDTH = 28.0;
    public static double HEALTH_BAR_HEIGHT = 4.0;

    public final List<int[]> pathCoordinates;
    public final int cellSize;
    public final double X;
    public final double Y;

    public final Rectangle healthBar;
    public int currentHealth = MAX_HEALTH;
    public boolean finished = false;
    public Point2D position;
    public PathTransition pathTransition;
    public Runnable enemyRemovedRun; 

    // Constructor to initialize the enemy with path coordinates, cell size, offset, and a callback
    // This callback is executed when the enemy reaches the end of its path so it can be used to update the game state
    // and remove the enemy from the game pane.
    public Enemy(List<int[]> pathCoordinates, int cellSize, double offsetX, double offsetY, Runnable ifFinishRun) { 

        this.pathCoordinates = pathCoordinates;
        this.cellSize = cellSize;
        this.X = offsetX;
        this.Y = offsetY;
        this.enemyRemovedRun = ifFinishRun; 
        this.healthBar = createHealthBar();

        // This method initializes the enemy visuals like body, head, and health bar
        create();
        // This method sets the initial position of the enemy based on the path coordinates
        initialPos();
        // This method starts the enemy's movement along the path by creating a PathTransition
        move(); 
    }

    // To create the enemy visuals we used polygons and circles
    // to represent the body and head of the enemy. The health bar is a rectangle that changes color based on the health.
    public void create() {
        //we invoked the methods to create the body, head and health bar
        Polygon body = createBody();
        Circle head = createHead();
        //add the body, head and health bar to the enemy
        this.getChildren().addAll(body, head, healthBar);
    }

    // To set the initial position of the enemy 
    //We calculated the initial position based on the first coordinates in the pathCoordinates list offset by the offset values.
    // The enemy's translateX and translateY properties are set to position it correctly on the game pane.
    public void initialPos() {
        Point2D initial = getInitialPosition();
        this.setTranslateX(initial.getX());
        this.setTranslateY(initial.getY());
    }

    // creates the body of the enemy
    public Polygon createBody() {
        return new Polygon(0, 0,-7, 24,8, 24) {{
            setStroke(Color.BLACK);
            setFill(Color.BLUEVIOLET);
        }};
    }

    // creates the head of the enemy
    public Circle createHead() {
        return new Circle(0, -4, HEAD_RADIUS, Color.CORNSILK) {{
            setStroke(Color.BLACK);
        }};
    }

    // creates the health bar of the enemy
    public Rectangle createHealthBar() {
        return new Rectangle(-HEALTH_BAR_WIDTH / 2, -14, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT) {{
            setStroke(Color.BLACK);
            setFill(Color.LIMEGREEN);
        }};
    }

    // creates a PathTransition to animate the enemy's movement
    // along the specified path coordinates. It also updates the enemy's
    // current position and handles the end of the path.

    public void move() { 
        Path path = createPath();
        pathTransition = new PathTransition(Duration.seconds(10), path, this);
        pathTransition.setOnFinished(e -> {

            //check if the enemy is not dead before calling handlePathEnd
            if (!isDead()) {
                handlePathEnd(); 
            }
        });

        // Set the path transition to repeat indefinitely
        pathTransition.currentTimeProperty().addListener((a,b, c) -> {
            // Update the current position of the enemy by getting the bounds of the enemy
            // and calculating the center point
            Bounds bounds = this.localToScene(this.getBoundsInLocal());
            position = new Point2D(bounds.getMinX() + bounds.getWidth() / 2,bounds.getMinY() + bounds.getHeight() / 2
            );
        });

        pathTransition.play();
    }



    //stops the animation, clears the enemy
    public void cleanup() {
        
        // Stop the path transition if it exists
        if (pathTransition != null) {
            pathTransition.stop();
            // Set the path transition to null 
            pathTransition = null;
        }
        
        // Set the enemy as finished and update its health to zero
        finished = true;
        currentHealth = 0;
        
        // Clear the enemy's elements from the display
        // This is done to remove the enemy from the game pane
        this.getChildren().clear();
        this.setVisible(false);
        
        //Remove the enemy from its parent pane
        removeFromParent();
    }

    // creates a Path object based on the path coordinates to move the enemy in the game pane
    public Path createPath() {
        Path path = new Path();
        //This loop iterates through the path coordinates and creates a series of lines
        // to represent the path the enemy will follow
        for (int[] coord : pathCoordinates) {
            // Calculate the x and y coordinates based on the cell size and offset
            double x = calculateCoordinate(coord[1]) + X;
            double y = calculateCoordinate(coord[0]) + Y;

            // Create a new MoveTo or LineTo element based on the current position if the path is empty
            // If the path is empty, add a MoveTo element to set the starting point
            // Otherwise, add a LineTo element to create a line to the next point
            if (path.getElements().isEmpty()) {
                path.getElements().add(new MoveTo(x, y));
            } else {
                path.getElements().add(new LineTo(x, y));
            }
        }
        //return the created path to be followed by the enemy
        return path;
    }

    //this method calculates the coordinate based on the cell size and offset and used for the path coordinates
    public double calculateCoordinate(int coord) {
        return coord * cellSize + cellSize / 2.0;
    }


    // This method is called when the enemy reaches the end of the path
    public void handlePathEnd() { 
        finished = true;
        //when the enemy reaches the end of the path, we decrease the lives of the player
        LiveMoney.decreaseLives(); 
        
        //if enemy is in pane, removedcallbackis not null, we remove it from the game pane
        if (this.enemyRemovedRun != null) {
            // This callback is executed when the enemy reaches the end of its path
            this.enemyRemovedRun.run(); 
        }
        // Remove the enemy from its parent pane by using the removeFromParent method that we created
        removeFromParent(); 
    }

    // This method removes the enemy from its parent pane
    public void removeFromParent() {
        // Check if the enemy has a parent and if it's an instance of a Pane
        if (this.getParent() instanceof Pane pane) {
            // Remove the enemy from its parent pane
            pane.getChildren().remove(this);
        }
    }

    // This method is called when the enemy takes damage
    public void takeDamage(int damage) {
        currentHealth = Math.max(currentHealth - damage, 0);
        updateHealthBar();

        
        // If the enemy is dead and not finished the path, stop the path transition and remove it
        if (isDead() && !finished) {
            finished = true; 
            //stop the path transition if it exists
            // we made it to stop the enemy's movement
            if (pathTransition != null) 
                pathTransition.stop();

            //add money because the enemy is killed
            LiveMoney.addMoney(10);

            // Clear the enemy's elements from the display
            this.getChildren().clear();
            this.setVisible(false);
            // Play the death animation
            playDeathAnimation();

            // Call the onEnemyRemovedCallback if it exists
            if (this.enemyRemovedRun != null) {
                // This callback is executed when the enemy is removed
                this.enemyRemovedRun.run(); // Call the stored callback
            // Remove the enemy from its parent pane
            removeFromParent();
        }
    }
}

    // update the health bar's width and color based on the current health
    public void updateHealthBar() {
        double healthPercent = (double) currentHealth / MAX_HEALTH;
        healthBar.setWidth(HEALTH_BAR_WIDTH * healthPercent);
        healthBar.setFill(
            healthPercent > 0.7 ? Color.LIMEGREEN :
            healthPercent > 0.3 ? Color.ORANGE : Color.RED
        );
    }

    // This method checks if the enemy is dead
    public boolean isDead() {
        return currentHealth <= 0;
    }

    // checks if the enemy has finished its path
    public boolean isFinished() {
        return finished;
    }

    //getters for the enemy's position and cell size
    public Point2D getPosition() {
        return position;
    }
    // returns the cell size of the enemy
    public int getCellSize() { 
        return cellSize;
    }

    // sets the callback to be called when the enemy is removed
    public Point2D getInitialPosition() {
        // Get the first coordinates from the pathCoordinates list
        int[] start = pathCoordinates.get(0);
        return new Point2D(
            calculateCoordinate(start[1]) + X,
            calculateCoordinate(start[0]) + Y
        );
    }

    // This method plays the death animation
    public void playDeathAnimation() {
        Pane parent = (Pane) this.getParent();
        //if the parent is null we can't play the animation
        if (parent == null) 
            return;

        int pieceCount = 10;

        // Get the current position of the enemy and set it to the pieces coordinates
        for (int i = 0; i < pieceCount; i++) {
            Circle piece = new Circle(5, Color.ORANGERED);
            piece.setTranslateX(position.getX());
            piece.setTranslateY(position.getY());

            parent.getChildren().add(piece);

            // Randomly generate an angle and distance for the piece to move 
            double angle = Math.random() * 2 * Math.PI;
            double distance = 50 + Math.random() * 50;
            double dx = distance * Math.cos(angle);
            double dy = distance * Math.sin(angle);

            // Create a translation animation for the piece
            TranslateTransition move = new TranslateTransition(Duration.seconds(0.5), piece);
            move.setByX(dx);
            move.setByY(dy);

            // Create a fade animation for the piece
            FadeTransition fade = new FadeTransition(Duration.seconds(0.5), piece);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);

            // Combine the move and fade animations into a parallel transition
            ParallelTransition animation = new ParallelTransition(move, fade);
            animation.setOnFinished(e -> parent.getChildren().remove(piece));
            animation.play();
        }
    }
}
