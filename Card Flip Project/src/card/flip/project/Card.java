package card.flip.project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * GEEN 165 Section 4
 * @author Zhipeng Liu
 */
public class Card extends StackPane{
    private boolean flipped;//indicates whether the symbol is displayed (true) or the back of the card is displayed (false).
    private boolean matched;//indicate if the card has been matched
    private String path;//The property path (String) will contain the file path of the image
    private Image image;//will store the actual image object.
    private Image defaultImage;
    private ImageView imageView;//displayed using an imageView (ImageView) object
    private ImageView defaultView;
    private int row;//The row and column of this card in the grid. This is used if you want to show a new big image as the cards are being matched instead of black for each card. So, these are optional.
    private int col;
    private int numRows;//The number of rows and columns used in the current game (determined by the selected level). This is used if you want to show a new big image as the cards are being matched instead of black for each card.
    private int numCols;

    /**
     * creates an empty card object
     */
    public Card(){
        flipped = false;
        matched = false;
        path = "";
        this.setStyle("-fx-background-color: white");
        //image = new Image("images/default.jpg");
        //image = null;
        defaultImage = new Image("images/default.jpg"); 
        imageView = new ImageView(defaultImage);
        //imageView = null;
              
        //defaultView = null;
        row = 0;
        col = 0;
        numRows = 0;
        numCols = 0;
    }
    
    /**
     * creates card and passes into the pass of the card image
     * @param path
     */
    public Card(String path){       
        this();
        this.setPath(path);     
    }
    
    /**
     * @return the flipped
     */
    public boolean isFlipped() {
        return flipped == true;
    }

    /**
     * @param flipped the flipped to set
     */
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    /**
     * @return the matched
     */
    public boolean isMatched() {
        return matched == true;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     * set the path property, populate the Image object using the path and populate the ImageView object using the Image object
     */
    public void setPath(String path) {
        //clear();
        this.path = path;               
        try {
                InputStream inputStream = new FileInputStream(path);
                image = new Image(inputStream);
        } 
        catch (FileNotFoundException ex) {
              
        }
    }

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * @return the imageView
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * @param imageView the imageView to set
     */
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * @return the numRows
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * @param numRows the numRows to set
     */
    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    /**
     * @return the numCols
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * @param numCols the numCols to set
     */
    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }
    
    /**
     * creates an empty card object
     * Flip the card to the other side. Use the StackPane clear() method to remove the displayed image from the card.
     */
    public void flipCard(){
        //this.setStyle("-fx-background-color: white");             
        if (flipped) {
            imageView.setImage(image);
        }
        else{
            imageView.setImage(defaultImage);
        }
    }
    
    public void defaultCard(){   
        this.getChildren().clear();
//        defaultImage = new Image("images/default.jpg");
//        defaultView = new ImageView(defaultImage);
//        this.getChildren().add(defaultView);
//        this.setStyle("-fx-background-color: red");                    
//        this.getChildren().clear();                    
        this.getChildren().add(this.getImageView());      
    }
       
    /**
     * creates an empty card object
     * @param width
     * @param height
     * Set the size dimensions of the Card and the ImageView object in the card.
     */
    public void setCardAndImageSize(double width, double height){
        this.setPrefSize(width, height);
        //imageView.setImage(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);        
    }
    
    //change the card properties such that it is considered matched.
    public void setMatched(boolean match){
        this.matched = match;
    }
    
    //set the values of the optional row, col values described above
    public void setGridPos(int r, int c){
        
    }
    
     //set the values of the optional numRows, numCols values described above
    public void setGridSize(int nr, int nc){
        
    }
    
}
