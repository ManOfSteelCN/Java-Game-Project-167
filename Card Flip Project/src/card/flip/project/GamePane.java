/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card.flip.project;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javax.swing.JOptionPane;

/**
 *
 * @author Zhipeng Liu
 */
public class GamePane extends BorderPane implements EventHandler {

    //private BorderPane borderPane;
    private CardGridPane cardGP;
    private Pane statusPane;
    private HBox commandPane;
    private int turns;
    private Time gameTimer;
    private int level;
    private int rows;
    private int cols;
    private int numClicks;
    private int numMatched;
    private int clicks;
    private Card clickedCardOne;
    private Card clickedCardTwo;
    private int cardSize;
    private Label turnsLbl;
    private int tempLvl;
    private int tempRows;
    private int tempCols;
    private ComboBox<Integer> levels = new ComboBox<>();

    public GamePane() {
        cardGP = new CardGridPane();
        this.setCenter(cardGP);
        this.setBottom(commandPane());
        turns = 0;
        gameTimer = null;
        level = 0;
        rows = 0;
        cols = 0;
        numClicks = 0;
        numMatched = 0;
        clicks = 0;
        cardSize = 100;
        newGame();
    }

    public GamePane(int cardSize) {
        this.cardSize = cardSize;
    }

    //menu
    public HBox commandPane() {
        HBox hbox = new HBox();
        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(this);
        Button newBtn = new Button("New Game");
        newBtn.setOnAction(this);
        Label lvlLbl = new Label("Level ");
        turnsLbl = new Label("Turns: " + turns);

        levels.getItems().addAll(1, 2, 3, 4, 5, 6);
        levels.setValue(0);
        levels.setVisibleRowCount(3);

        levels.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Integer> ov, Integer oldvalue, Integer newvalue) -> {
            levelChange(ov, oldvalue, newvalue);
            registerCardListener();
        });
        exitBtn.setMaxWidth(Double.MAX_VALUE);
        levels.setMaxWidth(Double.MAX_VALUE);
        hbox.setSpacing(20.0);
        hbox.getChildren().add(lvlLbl);
        hbox.getChildren().add(levels);
        hbox.getChildren().add(turnsLbl);
        hbox.getChildren().add(newBtn);
        hbox.getChildren().add(exitBtn);
        hbox.setAlignment(Pos.CENTER);
        commandPane = hbox;
        return commandPane;
    }

    /**
     *
     * @param e
     */
    @Override
    public void handle(Event e) {
        Button b = (Button) e.getSource();
        if ("Exit".equals(b.getText())) {
            int n = JOptionPane.showConfirmDialog(
                    null,
                    "Are you giving up?",
                    "",
                    JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.NO_OPTION) {
                try {
                    final URI resource = getClass().getResource("exit.mp3").toURI();
                    final AudioClip clip = new AudioClip(resource.toString());
                    clip.play();
                } catch (URISyntaxException ex) {
                    Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
                }
                newGame();
            } else {
                try {
                    final URI resource = getClass().getResource("next.mp3").toURI();
                    final AudioClip clip = new AudioClip(resource.toString());
                    clip.play();
                } catch (URISyntaxException ex) {
                    Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(null, "You are no longer a hero.");
                System.exit(0);
            }
        } else if ("New Game".equals(b.getText())) {
            //this.getChildren().clear();
            cardGP = new CardGridPane();
            this.setCenter(cardGP);
            this.setBottom(commandPane());
            turns = 0;
            turnsLbl.setText("Turns: " + turns);
            gameTimer = null;
            level = tempLvl;
            levels.setValue(level);
            numClicks = 0;
            numMatched = 0;
            clicks = 0;
            cardSize = 100;
            newGame();
            try {
                final URI resource1 = getClass().getResource("newGame.mp3").toURI();
                final AudioClip clip1 = new AudioClip(resource1.toString());
                clip1.play();
            } catch (URISyntaxException ex) {
                Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
        }
    }

    //listener for level
    public void levelChange(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
        if (null == newValue) {
            rows = 0;
            cols = 0;
            newGame();
        } else {
            switch (newValue) {
                case 1:
                    this.level = 1;
                    rows = 2;
                    cols = 3;
                    newGame();
                    break;
                case 2:
                    this.level = 2;
                    rows = 2;
                    cols = 4;
                    newGame();
                    break;
                case 3:
                    this.level = 3;
                    rows = 4;
                    cols = 4;
                    newGame();
                    break;
                case 4:
                    this.level = 4;
                    rows = 4;
                    cols = 6;
                    newGame();
                    break;
                case 5:
                    this.level = 5;
                    rows = 6;
                    cols = 6;
                    newGame();
                    break;
                case 6:
                    this.level = 6;
                    rows = 8;
                    cols = 8;
                    newGame();
                    break;
                default:
                    rows = 0;
                    cols = 0;
                    newGame();
                    break;
            }
        }
    }

    public void registerCardListener() {//card listener
        int counter = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Card card = cardGP.getCard(i, j);
                card.setOnMousePressed((Event e) -> {
                    clicks++;
                    if (card.isFlipped() || card.isMatched()) {
                        return;
                    }
                    if (numClicks == 1) {
                        card.setFlipped(true);
                        card.flipCard();
                        this.clickedCardTwo = card;
                        numClicks++;
                        new AnimationTimer() {
                            long startTime = System.nanoTime();

                            @Override
                            public void handle(long now) {
                                if (now - startTime > 1000000000) {
                                    if (clickedCardOne.getPath().equalsIgnoreCase(clickedCardTwo.getPath())) {
                                        clickedCardOne.setMatched(true);
                                        clickedCardTwo.setMatched(true);
                                        clickedCardOne.setStyle("-fx-background-color: black");
                                        clickedCardTwo.setStyle("-fx-background-color: black");
                                        clickedCardOne.setDisable(true);
                                        clickedCardTwo.setDisable(true);
                                        numMatched++;
                                        try {
                                            final URI resource2 = getClass().getResource("yes1.mp3").toURI();
                                            final AudioClip clip2 = new AudioClip(resource2.toString());
                                            clip2.play();
                                        } catch (URISyntaxException ex) {
                                            Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        //System.out.println(numMatched + " " + (rows * cols / 2));
                                        if (numMatched >= ((rows * cols) / 2)) {;
                                            gameCheck();
                                            turns = 0;
                                            turnsLbl.setText("Turns: " + turns);
                                        } else {
                                            //do nothing                                        
                                        }
                                    } else {
                                        clickedCardOne.setFlipped(false);
                                        clickedCardTwo.setFlipped(false);
                                        clickedCardOne.flipCard();
                                        clickedCardTwo.flipCard();
                                        try {
                                            final URI resource3 = getClass().getResource("notMatch.MP3").toURI();
                                            final AudioClip clip3 = new AudioClip(resource3.toString());
                                            clip3.play();
                                        } catch (URISyntaxException ex) {
                                            Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                    turns++;
                                    turnsLbl.setText("Turns: " + turns);
                                    numClicks = 0;
                                    stop();
                                }
                            }
                        }.start();
                    } else if (numClicks == 0) {
                        card.setFlipped(true);

                        card.flipCard();
                        this.clickedCardOne = card;
                        numClicks++;
                    }
                });
                counter++;
            }
        }
    }

    public void newGame() {//create new game
        turns = 0;
        numClicks = 0;
        numMatched = 0;
        cardGP.initCards(rows, cols);
        registerCardListener();
        tempLvl = level;
        tempRows = rows;
        tempCols = cols;
        
    }

    private void gameCheck() {//check game win or not
        JOptionPane.showMessageDialog(null, "You win!");
        try {
            final URI resource = getClass().getResource("newGame2.mp3").toURI();
            final AudioClip clip = new AudioClip(resource.toString());
            clip.play();
        } catch (URISyntaxException ex) {
            Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the turns
     */
    public int getTurns() {
        return turns;
    }

    /**
     * @param turns the turns to set
     */
    public void setTurns(int turns) {
        this.turns = turns;
    }

    /**
     * @return the gameTimer
     */
    public Time getGameTimer() {
        return gameTimer;
    }

    /**
     * @param gameTimer the gameTimer to set
     */
    public void setGameTimer(Time gameTimer) {
        this.gameTimer = gameTimer;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * @return the cols
     */
    public int getCols() {
        return cols;
    }

    /**
     * @param cols the cols to set
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * @return the numClicks
     */
    public int getNumClicks() {
        return numClicks;
    }

    /**
     * @param numClicks the numClicks to set
     */
    public void setNumClicks(int numClicks) {
        this.numClicks = numClicks;
    }

    /**
     * @return the numMatched
     */
    public int getNumMatched() {
        return numMatched;
    }

    /**
     * @param numMatched the numMatched to set
     */
    public void setNumMatched(int numMatched) {
        this.numMatched = numMatched;
    }

    /**
     * @return the clickedCardOne
     */
    public Card getClickedCardOne() {
        return clickedCardOne;
    }

    /**
     * @param clickedCardOne the clickedCardOne to set
     */
    public void setClickedCardOne(Card clickedCardOne) {
        this.clickedCardOne = clickedCardOne;
    }

    /**
     * @return the clickedCardTwo
     */
    public Card getClickedCardTwo() {
        return clickedCardTwo;
    }

    /**
     * @param clickedCardTwo the clickedCardTwo to set
     */
    public void setClickedCardTwo(Card clickedCardTwo) {
        this.clickedCardTwo = clickedCardTwo;
    }

}
