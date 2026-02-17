import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
//This class will handle the enemy waves. In Level Data class we read the wave data. In this part we have to implement these wave datas.
public class WaveManager {
    public final Pane pane;//We need a pane for adding enemys to this pane.
    public final ArrayList<int[]> path;//This is the enemy path.
    public final int cellSize;
    public final double X, Y;
    public final Label waveLabel;//This is the wave label for time clock for enemy waves.
    public final List<double[]> waveQueue;//This list will control queue of waves.
    public int activeEnemies = 0; 
    public int spawnedEnemies = 0; 
    public Timeline spawnTimeline;//these are our timelines.
    public Timeline countdownTimeline;

    public Runnable levelSuccessRun;//These codes are generated in game class. We are sending running codes in success or failure condutions.
    public Runnable levelFailRun;
    public boolean levelEnded = false;

    public WaveManager(Pane pane, ArrayList<int[]> path, int cellSize, double offsetX, double offsetY,
                       Label waveLabel, List<double[]> waveQueue,
                       Runnable iflevelSuccessRun, Runnable ifLevelFailRun) {//Constructor of wave manager

        this.pane = pane;
        this.path = path;
        this.cellSize = cellSize;
        this.X = offsetX;
        this.Y = offsetY;
        this.waveLabel = waveLabel;
        this.waveQueue = waveQueue;
        this.levelSuccessRun = iflevelSuccessRun;
        this.levelFailRun = ifLevelFailRun;
    }

    public void startNextWave() {
        if (levelEnded) return;//if level is ended finish the callback.

        if (waveQueue.isEmpty()) {//If there is any more wave and
            if (this.activeEnemies == 0) { // there is no more enemy.
                ifLevelSuccess();//Finish the level with success.
            }
            return;
        }

        double[] wave = waveQueue.remove(0);//Remove the first wave from list so go to the next wave.
        int enemyCountA = (int) wave[0];//enemy count is the first index
        double spawnTimeA = wave[1];//enemy interval is the second index
        double delayBeforeStartA = wave[2];//wave counterclock is the third index

        this.spawnedEnemies = 0; // this is the counter of spawned enemies

        if (spawnTimeline != null) {
            spawnTimeline.stop();//stop the timeline
        }
        spawnTimeline = new Timeline();
        for (int i = 0; i < enemyCountA; i++) {
            double spawnTime = spawnTimeA * i;//this will spawn enemys with interval
            KeyFrame spawnFrame = new KeyFrame(Duration.seconds(spawnTime), _ -> {
                if (levelEnded) return;
                spawnEnemy(//this will spawn enemy in path
                        pane,
                        path,
                        cellSize,
                        X,
                        Y,
                        enemyCountA
                );
            });
            spawnTimeline.getKeyFrames().add(spawnFrame);
        }

        startWaveCountdown(delayBeforeStartA, waveLabel, spawnTimeline);//after the enemys are spawned start wave countdown.
    }

    public void clearEnemies() {//this part is important because we have to clean enemies timelines and counters before next wave
        levelEnded = true;
        if (spawnTimeline != null) {//cleaning the timelines.
            spawnTimeline.stop();
            spawnTimeline.getKeyFrames().clear();
        }
        if (countdownTimeline != null) {
            countdownTimeline.stop();
        }

        //in this part we are saving the remaining enemies to a arraylist
        List<Enemy> enemiesToRemove = new ArrayList<>();
        for (Node node : pane.getChildren()) {
            if (node instanceof Enemy enemy) {
                enemiesToRemove.add(enemy);
            }
        }
        
        //after that we are cleaning the these enemys
        for (Enemy enemy : enemiesToRemove) {
            enemy.cleanup();
        }
        
        this.activeEnemies = 0;//after that we are resetting the active enemies counter
    }

    
    // This method will spawn enemies for our path and in our pane.
    public void spawnEnemy(
            Pane pane,
            ArrayList<int[]> path,
            int cellSize,
            double offsetX,
            double offsetY,
            int totalEnemiesInWave 
    ) {
        // if level is ended finish the process
        if (levelEnded) return;

        // Create a new enemy.
        Enemy enemy = new Enemy(path, cellSize, offsetX, offsetY, () -> {
            // if level is over and game is not over finish the spawning enemy
            if (levelEnded && !LiveMoney.isGameOver()) return;

            // decrease the enemy counter
            this.activeEnemies--;

            // if game is over level will fail
            if (LiveMoney.isGameOver()) {
                if (!levelEnded) {
                    ifLevelFail();
                }
                return;
            }

            if (this.activeEnemies == 0 && this.spawnedEnemies == totalEnemiesInWave && waveQueue.isEmpty()) {// if there is no more enemy and spawned all enemies also there is no more waves then level will success
                ifLevelSuccess();
            } else if (this.activeEnemies == 0 && this.spawnedEnemies == totalEnemiesInWave) {
                startNextWave();// if there is more wave start next wave
            }
        });

        // Add enemy to our pane
        pane.getChildren().add(enemy);
        // increment our enemy counters
        this.activeEnemies++;
        this.spawnedEnemies++;
    }

    public void ifLevelSuccess() {
        if (!levelEnded) {
            levelEnded = true;
            stopTimelines();
            if (levelSuccessRun != null) {
                Platform.runLater(levelSuccessRun);//If level is successful then run level success callback
            }
        }
    }

    public void ifLevelFail() {
        if (!levelEnded) {
            levelEnded = true;
            stopTimelines();
            if (levelFailRun != null) {
                Platform.runLater(levelFailRun);//If level has failed then run level failure callback
            }
        }
    }

    public void stopTimelines() {//this method will stop all the timelines
        if (spawnTimeline != null) {
            spawnTimeline.stop();
        }
        if (countdownTimeline != null) {
            countdownTimeline.stop();
        }
    }

    //this method will manage the countdown of our waves
    public void startWaveCountdown(double seconds, Label label, Timeline onCompleteTimeline) {
        if (levelEnded) return;// if level has ended exit the code.
        label.setText("Next Wave: " + (int) seconds + "s");//setting level with argument seconds

        if (countdownTimeline != null) {
            countdownTimeline.stop();
        }
        countdownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), _ -> {//This will refresh the label with 1 second spaces
            if (levelEnded) {//level is ended so stop the timeline and exit.
                if(countdownTimeline != null) countdownTimeline.stop();
                return;
            }
            int current = Integer.parseInt(label.getText().replaceAll("[^0-9]", ""));
            if (current > 0) {
                 label.setText("Next Wave: " + (current - 1) + "s");// if timer is not 0 decrement the second by 1 in 1 second
            } else {
                label.setText("Wave Starting!");// if counter is 0 then start wave.
            }
        }));

        countdownTimeline.setCycleCount((int) seconds);//cycle count is our starting second amount
        countdownTimeline.setOnFinished(_ -> {
            if (!levelEnded) {//when time line is ended if level is not ended write wave starting
                label.setText("Wave Starting!");
                onCompleteTimeline.play();
            }
        });
        countdownTimeline.play();
    }

}