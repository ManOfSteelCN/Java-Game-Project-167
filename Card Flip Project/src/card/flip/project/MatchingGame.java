/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card.flip.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Zhipeng Liu
 */
public class MatchingGame extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        GamePane gamePane = new GamePane();        
        //Scene scene = new Scene(root, 300, 250);
        Scene scene = new Scene(gamePane);
        
        primaryStage.setTitle("Matching Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void start(){
        launch();
    }
}
