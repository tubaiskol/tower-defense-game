import java.util.ArrayList;
import java.util.List;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Circle;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

public class Game extends Application {
    // We are defining all labels buttons scenes and stages in here because we will use them in multiple methods.
    public int level = 1;
    public int MAX_LEVEL = 5; 
    public Label liveLabel, moneyLabel, nextWaveLabel;
    public Label nextLevelLabel; 
    public Button continueButton; 
    public WaveManager waveManager;
    public List<AbstractTower> activeTowers = new ArrayList<>();
    public SingleShotTower sstPanel;
    public LaserTower ltPanel;
    public TripleShotTower tstPanel;
    public MissileLauncherTower mltPanel; 
    public Pane gamePane;
    public StackPane gridStackPane;
    public Stage stage;
    public Scene gameScene, opScene, backScene, nextScene;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        // Creating Opening Screen
        StackPane stackPane = new StackPane();//There will be a button in opening Screen. Stackpane will be useful.
        stackPane.setStyle("-fx-background-color: rgb(250, 237,202);");
        Button startButton = new Button("Start Game"); 
        stackPane.getChildren().add(startButton);//Centering button in stackpane
        this.opScene = new Scene(stackPane, 1280, 720);//We are using 1280x720 because this size can be entegrated in different computers.
        startButton.setStyle("-fx-font-size: 30px; -fx-background-color:rgb(239, 203, 132);");

        // Creating game over screen
        StackPane gOverStackpane = new StackPane();//There will be a button in opening Screen. Stackpane will be useful.
        gOverStackpane.setStyle("-fx-background-color: rgb(250, 237, 202);");
        VBox gOvervbox = new VBox(20);//Gameover screen have a text and button. So vbox will be useful/
        Button backButton = new Button("Back to Main Menu");
        Label gameOverLabel = new Label("Game Over! You lost all your lives.");
        gOvervbox.setAlignment(Pos.CENTER);
        gOvervbox.getChildren().addAll(gameOverLabel, backButton);
        gOverStackpane.getChildren().addAll(gOvervbox);//There are text and button in vbox. Vbox is inside of stackpane
        gameOverLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: brown;");
        gameOverLabel.setAlignment(Pos.CENTER);
        this.backScene = new Scene(gOverStackpane, 1280, 720);
        backButton.setStyle("-fx-font-size: 30px; -fx-background-color:rgb(239,203,132);");

        // Creating next game screen
        StackPane stackPaneNext = new StackPane();
        stackPaneNext.setStyle("-fx-background-color: rgb(250, 237,202);");
        VBox vboxNext = new VBox(20);
        this.continueButton = new Button("Continue the next level");
        this.nextLevelLabel = new Label("Congratulations! You have completed the level.");
        vboxNext.setAlignment(Pos.CENTER);
        vboxNext.getChildren().addAll(this.nextLevelLabel, this.continueButton);
        stackPaneNext.getChildren().addAll(vboxNext);//This screen is also same with gameover screen.
        this.nextLevelLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: brown;");
        this.nextLevelLabel.setAlignment(Pos.CENTER);
        this.nextScene = new Scene(stackPaneNext, 1280, 720);
        this.continueButton.setStyle("-fx-font-size: 30px; -fx-background-color:rgb(239,203,132);");

        // Game Screen Iskeleti
        //This part will create the game screen. There is a HBox which includes grid of map and towers panel. grid of map is inside a stackpane. Towers panel is inside in vbox and all elements is listed here. 
        HBox hBox = new HBox();
        this.gridStackPane = new StackPane();
        this.gamePane = new Pane();
        VBox vBox = new VBox(); 
        this.gridStackPane.getChildren().add(this.gamePane);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(this.gridStackPane, vBox);
        this.gridStackPane.setPrefWidth(1280 * 0.8);//Ratio of left part.
        this.gridStackPane.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(1280 * 0.2);//Ratio of right part.
        vBox.setStyle("-fx-background-color: rgb(228, 206, 91)");
        this.gridStackPane.setStyle("-fx-background-color: rgb(215, 206, 156)");
        this.gameScene = new Scene(hBox, 1280, 720);

        // On top of the panel there will be labels for count of remaining lives, money.
        this.liveLabel = new Label();
        this.moneyLabel = new Label();
        this.nextWaveLabel = new Label("Next Wave:");

        // This code is money updater. We are using animation. Animation refresh it self in 0.1 second and its cycle count is indefinite. So it will update the money every 0.1 second. It is dinamic.
        javafx.animation.Timeline moneyUpdater = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(javafx.util.Duration.millis(100), e -> {
                    moneyLabel.setText("Money: " + LiveMoney.getMoney() + "$ ");
                }));
        moneyUpdater.setCycleCount(javafx.animation.Animation.INDEFINITE);
        moneyUpdater.play();

        // This code is lives updater. It is very simular to money updater.
        javafx.animation.Timeline livesUpdater = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(javafx.util.Duration.millis(100), e -> {
                    liveLabel.setText("Lives: " + LiveMoney.getLives());
                }));
        livesUpdater.setCycleCount(javafx.animation.Animation.INDEFINITE);
        livesUpdater.play();

        // Creating towers for putting inside to panel.
        this.sstPanel = new SingleShotTower();
        this.sstPanel.setScaleX(1.5);// Scaling for panel
        this.sstPanel.setScaleY(1.5);
        this.ltPanel = new LaserTower();
        this.ltPanel.setScaleX(1.5);
        this.ltPanel.setScaleY(1.5);
        this.tstPanel = new TripleShotTower();
        this.tstPanel.setScaleX(1.5);
        this.tstPanel.setScaleY(1.5);
        this.mltPanel = new MissileLauncherTower();
        this.mltPanel.setScaleX(1.5);
        this.mltPanel.setScaleY(1.5);

        //setuping draghandlers for panel towers. So isPanel parameter will be true. setupHandler explanation will be in definition of that method.
        makeDragDrop(this.sstPanel, "SingleShotTower", this.moneyLabel, true);
        makeDragDrop(this.ltPanel, "LaserTower", this.moneyLabel, true);
        makeDragDrop(this.tstPanel, "TripleShotTower", this.moneyLabel, true);
        makeDragDrop(this.mltPanel, "MissileLauncherTower", this.moneyLabel, true);

        // This will be first box inside of panel. We are using stackpane for this box. Inside the stackpane there is vbox because there will a tower and its name. 
        StackPane stack1 = new StackPane();
        VBox stack1v = new VBox(20);
        stack1v.setAlignment(Pos.CENTER);
        Label singleLabel = new Label("Single Shot Tower - 50$");
        stack1v.getChildren().addAll(this.sstPanel, singleLabel);
        stack1v.setStyle("-fx-border-color: white; -fx-background-color: rgb(214, 156, 97);");
        stack1.getChildren().addAll(stack1v);
        //This is the second box. Similar with before.
        StackPane stack2 = new StackPane();
        VBox stack2v = new VBox(20);
        stack2v.setAlignment(Pos.CENTER);
        Label laserLabel = new Label("Laser Tower - 120$");
        stack2v.getChildren().addAll(this.ltPanel, laserLabel);
        stack2v.setStyle("-fx-border-color: white; -fx-background-color: rgb(214, 156, 97);");
        stack2.getChildren().addAll(stack2v);
        //Third box
        StackPane stack3 = new StackPane();
        VBox stack3v = new VBox(20);
        stack3v.setAlignment(Pos.CENTER);
        Label tripleLabel = new Label("Triple Shot Tower - 150$");
        stack3v.getChildren().addAll(this.tstPanel, tripleLabel);
        stack3v.setStyle("-fx-border-color: white; -fx-background-color: rgb(214, 156, 97);");
        stack3.getChildren().addAll(stack3v);
        //Forth box
        StackPane stack4 = new StackPane();
        VBox stack4v = new VBox(20);
        stack4v.setAlignment(Pos.CENTER);
        Label missileLabel = new Label("Missile Launcher Tower - 200$");
        stack4v.getChildren().addAll(this.mltPanel, missileLabel);
        stack4v.setStyle("-fx-border-color: white; -fx-background-color: rgb(214, 156, 97);");
        stack4.getChildren().addAll(stack4v);
        
        //We are putting these boxes and labels into the vbox.
        vBox.getChildren().addAll(this.liveLabel, this.moneyLabel, this.nextWaveLabel, stack1, stack2, stack3, stack4);
        vBox.setStyle("-fx-background-color: rgba(228, 205, 91,0.38)");
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);

        // This part will handle what will be happen if we click on the start button.
        startButton.setOnAction(e -> {
            this.level = 1;//When we click on button we will start with first level.
            LiveMoney.setLives(5); //We will start with 5 lives.
            LiveMoney.setMoney(150); //We will start with 150 dollars.
            loadLevel();//This method will load the map and start the game. It will be explained in that method.
        });

        // This part will handle what will be happen if we click on the next level button.
        this.continueButton.setOnAction(e -> {
            if (this.level <= MAX_LEVEL) { //this line will increment the currentline in load level method until it arrives to max level.
                loadLevel();
            } else { 
                this.stage.setScene(this.opScene); //when max level is ended, it will back to opening scene
            }
        });

        // Back to Main Menu button action (from Game Over screen)
        backButton.setOnAction(e -> {
            clearOldLevel(); // this method will clear old level stuff. It will be explained in method
            this.stage.setScene(this.opScene);
        });

        this.stage.setScene(this.opScene);
        this.stage.setTitle("Tower Defense");
        Image icon = new Image("image.png"); //in this part we are selecting icon image. This will be aplication icon image. 
        this.stage.getIcons().add(icon);
        this.stage.show();
    }

    public void clearOldLevel() {
        // In this part we are stopping shooting for all active towers.Every tower has different shooting algortithm so we are calling them seperately.
        for (AbstractTower tower : activeTowers) {
            if (tower instanceof SingleShotTower) ((SingleShotTower) tower).stopShoot();
            else if (tower instanceof LaserTower) ((LaserTower) tower).stopShoot();
            else if (tower instanceof TripleShotTower) ((TripleShotTower) tower).stopShoot();
            else if (tower instanceof MissileLauncherTower) ((MissileLauncherTower) tower).stopShoot();
        }
        activeTowers.clear();//After that we are clearing the list.

        //After towers we must clear all enemies so we are clearing currentWave manager who manages enemys.
        if (waveManager != null) {
            waveManager.clearEnemies();
            waveManager = null;
        }

        // if gamepane is not clear this part will clear all nodes.
        if (gamePane != null) {
            gamePane.getChildren().clear();
        }
    }

    // This part is most important part in game class. This method will load all levels grids enemypaths and enemys.
    public void loadLevel() {
        clearOldLevel(); // First of all we must clean old level things with previous method.

        this.stage.setScene(this.gameScene);//Setting game scene.

        LevelData levelData = LevelData.readLevelFile("bin/levels/level" + this.level + ".txt"); //With LevelData Class we are reading text files and taking level and grid infos.
        int width = levelData.width;
        int height = levelData.height;
        int cellSize = width == 10 ? 64 : 45;// Grid size is important because if we are incrementing the cell amount, grid cells size must be decremented.

        //we are taking map data from levelData and creating an array for enemy path. And we are signing them with true.
        boolean[][] isPath = new boolean[height][width];
        for (int[] coord : levelData.map) {
            isPath[coord[0]][coord[1]] = true;
        }

        //this part will manageing scaling, resolution and size of gridpane.
        double paneWidth = this.gridStackPane.getPrefWidth();
        double paneHeight = 720;
        double gridWidth = width * cellSize;
        double gridHeight = height * cellSize;
        double X = (paneWidth - gridWidth) / 2;
        double Y = (paneHeight - gridHeight) / 2;

        //this part will use two dimensional for loops for creating grid map. every cell will be a stackpane.
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(cellSize, cellSize);

                //If a cell is selected with isPath method this will be change the color of the cell.
                if (isPath[i][j]) {
                    cell.setStyle("-fx-border-color: white; -fx-background-color: rgb(139, 69, 19);");
                } else {
                    cell.setStyle("-fx-border-color: white; -fx-background-color: rgba(239, 203, 132," + Math.random() + ");");// This part will make random colors for other cells. We are changing opacity of cells with random number
                }
                
                //This part is also setting layout of this cells.
                cell.setLayoutX(j * cellSize + X);
                cell.setLayoutY(i * cellSize + Y);

                //This code is for animation. In the beggining part cells will be animated. They will appear smoothly.
                cell.setScaleX(0);//This is beginning scale. Its zero so they are invisible at the beggining.
                cell.setScaleY(0);
                ScaleTransition cellAnimation = new ScaleTransition(Duration.millis(40), cell);//Duration will be short so this animation will be fast.
                cellAnimation.setFromX(0);//This is the starting scale of animation.
                cellAnimation.setFromY(0);
                cellAnimation.setToX(1);//This is the ending scale of animation. It is 1 so it will be a full cell.
                cellAnimation.setToY(1);
                cellAnimation.setDelay(Duration.millis(40 * (i + j)));//This is the most impartant line in animation code. We are setting delay for cells. Delay amount is related with x + y coordinates of cells. So from left up corner to right down corner delay will be incremented. That is the animations algortihm.
                cellAnimation.play();

                if (!isPath[i][j]) {// We are saying that because we shouldnt put towers to the path.
                    cell.setOnDragOver(e -> {//This part is for make cells dragable.
                        if (e.getGestureSource() != cell && e.getDragboard().hasString()) {
                            e.acceptTransferModes(javafx.scene.input.TransferMode.COPY_OR_MOVE);
                        }
                    
                    });

                    // This part we are setting dropping towers in to the cells.
                    cell.setOnDragDropped(e -> {
                        var db = e.getDragboard();
                        boolean success = false;//First we are setting default success to false.
                        if (db.hasString()) {
                            AbstractTower PlaceTower = null;//default is null.
                            int cost = 0;//default is 0.
                            boolean fromPanel = db.getString().endsWith("Panel");//This line is setting from Panel info with checking dragboard strings ending. We are checking for that because we must manage costs and buyings.
                            String towerType = fromPanel ? db.getString().replace("Panel", "") : db.getString();//If it is dragging from panel(buying a tower) than there is no need for panel string furthermore. This tower is buyed. It is not panel tower anymore.

                            //This part will creating tower for placement with checking dragboard string.
                            switch (towerType) {
                                case "SingleShotTower"://
                                    PlaceTower = new SingleShotTower();
                                    cost = 50;
                                    break;
                                case "LaserTower":
                                    PlaceTower = new LaserTower();
                                    cost = 120;
                                    break;
                                case "TripleShotTower":
                                    PlaceTower = new TripleShotTower();
                                    cost = 150;
                                    break;
                                case "MissileLauncherTower":
                                    PlaceTower = new MissileLauncherTower();
                                    cost = 200;
                                    break;
                            }

                            //If cell is not empty dont complate the dropping.
                            if (!cell.getChildren().isEmpty()) {
                                e.setDropCompleted(false);
                            
                                return;
                            }

                            //This part is for not empty tower object.
                            if (PlaceTower != null) {
                                if (fromPanel) {
                                    if (LiveMoney.canAfford(cost)) {//This method checks user have enough money for tower.if not success will remain false.
                                        LiveMoney.spendMoney(cost);// decrement the money.
                                        PlaceTower.setScaleX(1.0);
                                        PlaceTower.setScaleY(1.0);
                                        cell.getChildren().add(PlaceTower);//Add tower to the cell.
                                        activeTowers.add(PlaceTower);//Add tower to the active towers list.
                                        makeDragDrop(PlaceTower, towerType, this.moneyLabel, false);//This tower also can move to the another cell. So we must make that draggable too. But this time its not panel. So we are not paying money for that.

                                        // This part will starting shooting for all types of towers.
                                        if (PlaceTower instanceof SingleShotTower sst)
                                            sst.shoot(this.gamePane);
                                        else if (PlaceTower instanceof LaserTower lt)
                                            lt.shoot(this.gamePane);
                                        else if (PlaceTower instanceof TripleShotTower tst)
                                            tst.shoot(this.gamePane);
                                        else if (PlaceTower instanceof MissileLauncherTower mlt)
                                            mlt.shoot(this.gamePane);
                                        success = true;
                                    }
                                } 
                                else {//This part for moving the tower one cell to another cell
                                    AbstractTower draggingTower = null;
                                    if (e.getGestureSource() instanceof AbstractTower) {//This part for moving tower from old cell. We must delete old tower from old cell.
                                        draggingTower = (AbstractTower) e.getGestureSource();
                                        if (draggingTower.getParent() instanceof StackPane oldCell) {
                                            oldCell.getChildren().remove(draggingTower);//Removing from cell.
                                            activeTowers.remove(draggingTower);//deleting from  active towers list.
                                            //This is for stopping shooting old cell.
                                            if (draggingTower instanceof SingleShotTower sstOld)
                                                sstOld.stopShoot();
                                            else if (draggingTower instanceof LaserTower ltOld)
                                                ltOld.stopShoot();
                                            else if (draggingTower instanceof TripleShotTower tstOld)
                                                tstOld.stopShoot();
                                            else if (draggingTower instanceof MissileLauncherTower mltOld)
                                                mltOld.stopShoot();
                                        }
                                    }
                                    //this part for moving the tower to a new cell.
                                    //These lines will make the same thing with previous part. adding to cell list and starting shooting.
                                    PlaceTower.setScaleX(1.0);
                                    PlaceTower.setScaleY(1.0);
                                    cell.getChildren().add(PlaceTower);
                                    activeTowers.add(PlaceTower);
                                    makeDragDrop(PlaceTower, towerType, this.moneyLabel, false);
                                    if (PlaceTower instanceof SingleShotTower sst)
                                        sst.shoot(this.gamePane);
                                    else if (PlaceTower instanceof LaserTower lt)
                                        lt.shoot(this.gamePane);
                                    else if (PlaceTower instanceof TripleShotTower tst)
                                        tst.shoot(this.gamePane);
                                    else if (PlaceTower instanceof MissileLauncherTower mlt)
                                        mlt.shoot(this.gamePane);
                                    success = true;
                                }
                            }
                        }
                        e.setDropCompleted(success);//Complate the dropping.
                        if (success) {
                            e.consume(); // this code is very important because without consuming the event, cell to cell tower transporting is earning us money. This event is triggering other code blocks. We have to consume this event for solution of this bug.
                        }
                    });
                }
                this.gamePane.getChildren().add(cell);//after all of that we are adding cell to the pane,
            }
        }

        this.gamePane.setOnDragOver(e -> {
            if (e.getGestureSource() instanceof AbstractTower && e.getDragboard().hasString()) {
                e.acceptTransferModes(javafx.scene.input.TransferMode.MOVE);
            }
        
        });
        this.gamePane.setOnDragDropped(e -> {//This time we are setting the selling function. When tower is dropping into the not grid side (pane side) it will action this code.
            if (e.getGestureSource() instanceof AbstractTower && e.getDragboard().hasString()) {
                String towerType2 = e.getDragboard().getString();
                boolean fromPanel = towerType2.endsWith("Panel");//This part is same with previous.
                String towerType = towerType2.replace("Panel", "");
                int cost = 0;
                switch (towerType) {
                    case "SingleShotTower":
                        cost = 50;
                        break;
                    case "LaserTower":
                        cost = 120;
                        break;
                    case "TripleShotTower":
                        cost = 150;
                        break;
                    case "MissileLauncherTower":
                        cost = 200;
                        break;
                }

                if (e.getGestureSource() instanceof AbstractTower towerToSell) {
                    if (!fromPanel) {//If the tower is from panel. Do not add money. 
                        LiveMoney.addMoney(cost);
                    }
                    if (towerToSell.getParent() instanceof StackPane parentCell) {
                        parentCell.getChildren().remove(towerToSell);//Remove the tower from stackpane
                    }
                    activeTowers.remove(towerToSell);//Remove it from list.
                    //This part will stop shooting tower by its type.
                    if (towerToSell instanceof SingleShotTower sst)
                        sst.stopShoot();
                    else if (towerToSell instanceof LaserTower lt)
                        lt.stopShoot();
                    else if (towerToSell instanceof TripleShotTower tst)
                        tst.stopShoot();
                    else if (towerToSell instanceof MissileLauncherTower mlt)
                        mlt.stopShoot();
                }
                e.setDropCompleted(true);
            }
        
        });
        //This Runnable object will handle level completion situation.
        Runnable ifSuccessRun = () -> {
            levelCompletion();
        };

        //This Runnable object will handle level fail situation. This will clear all nodes then go back to the gameover scene.
        Runnable ifFailRun = () -> {
            clearOldLevel();
            this.stage.setScene(this.backScene);
        };

        //This part will handle wave data. We are saving wawe data to list.
        List<double[]> wavesQueue = new ArrayList<>(levelData.waveData);//in this part we are creating new wavemanager instance. parameters are our parameters.
        this.waveManager = new WaveManager(this.gamePane, levelData.map, cellSize, X, Y,
                this.nextWaveLabel, wavesQueue, ifSuccessRun, ifFailRun);//We are putting Runnable objects as arguments for current wave manager. This manager will run these callbacks if level will fail or complate.

                 
        this.waveManager.startNextWave();//starting next wave with Wave Manager method.

        this.nextLevelLabel.setText("Congratulations! You have completed the level.");
            this.continueButton.setText("Continue the next level");//That labels for next level scene.
    }

    public void levelCompletion() {//This part will make the same thing with clearinglevelstate method. 
        
        //Stopping shooting for all towers.
        for (AbstractTower tower : activeTowers) {
            if (tower instanceof SingleShotTower) ((SingleShotTower) tower).stopShoot();
            else if (tower instanceof LaserTower) ((LaserTower) tower).stopShoot();
            else if (tower instanceof TripleShotTower) ((TripleShotTower) tower).stopShoot();
            else if (tower instanceof MissileLauncherTower) ((MissileLauncherTower) tower).stopShoot();
        }
        activeTowers.clear();//Clearing active towers list.
        if (waveManager != null) {
            waveManager.clearEnemies();//Clearing enemies.
        }

        this.level++;//This will increment the level number. First part of the class we are checking max level with current level number. This part have relation with it.
        if (this.level <= MAX_LEVEL) {
            this.nextLevelLabel.setText("Level " + (this.level - 1) + " Complete! Press Next for Level " + this.level + ".");//This will manage Next Level Label.
            this.continueButton.setText("Continue the next level");//Buttons label.
            this.stage.setScene(this.nextScene);
        } else {//If current level number is greater than max level that means all levels are complated. So we must make end game scene. These labels for this.
            this.nextLevelLabel.setText("Congratulations! All " + MAX_LEVEL + " levels completed!");
            this.continueButton.setText("Play Again?");
            this.stage.setScene(this.nextScene);
        }
    }

    //This method will setup draging towers. We have to make a preview for towers when dragging. This preview will show us the range and the tower.
    public void makeDragDrop(AbstractTower tower, String towerType, Label moneyLabel, boolean isPanel) {
        
        tower.setOnDragDetected(e -> {
            Dragboard db = tower.startDragAndDrop(TransferMode.COPY_OR_MOVE);//We are creating a dragboard object with towers method.
            ClipboardContent content = new ClipboardContent();
            content.putString(towerType + (isPanel ? "Panel" : ""));//This dragboard has a clipboard content. We are setting its text with towertype and isPanel method. If this tower is from panel we are adding Panel word to its end.
            db.setContent(content);
            
            //This part will add a circle to preview. Circle have radius from range of tower.
            Circle rangePreview = new Circle(tower.getRange());
            rangePreview.setFill(Color.rgb(255, 50, 50, 0.18));
            rangePreview.setStroke(Color.BLACK);
            rangePreview.setStrokeWidth(6);

            AbstractTower towerPreview;
            //This part will create a visual preview for tower. It will check towers type.
            switch (towerType) {
                case "SingleShotTower":
                    towerPreview = new SingleShotTower();
                    break;
                case "LaserTower":
                    towerPreview = new LaserTower();
                    break;
                case "TripleShotTower":
                    towerPreview = new TripleShotTower();
                    break;
                case "MissileLauncherTower":
                    towerPreview = new MissileLauncherTower();
                    break;
                default:
                    towerPreview = new SingleShotTower();
            }
            towerPreview.setScaleX(1.5);//Previews have same scale with panel towers. So there is a illusion like dragging the same tower.
            towerPreview.setScaleY(1.5);

            //We are creatina a stackpane for circle and towers preview. It will be useful for centering these nodes.
            StackPane previewStack = new StackPane(rangePreview, towerPreview);
            previewStack.setStyle("-fx-background-color: transparent;");
            previewStack.setMinSize(rangePreview.getRadius() * 2, rangePreview.getRadius() * 2);
            previewStack.setPrefSize(rangePreview.getRadius() * 2, rangePreview.getRadius() * 2);
            previewStack.setMaxSize(rangePreview.getRadius() * 2, rangePreview.getRadius() * 2);
            StackPane.setAlignment(towerPreview, Pos.CENTER);//This will center tower to the stackpane.

            WritableImage snapshot = previewStack.snapshot(null, null);
            db.setDragView(snapshot);//We are using dragboard for this feature. We are getting snapshot for this image.
            db.setDragViewOffsetX(snapshot.getWidth() / 2.0);
            db.setDragViewOffsetY(snapshot.getHeight() / 2.0);
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
