/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card.flip.project;

import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Zhipeng Liu
 */
public class CardGridPane extends GridPane{
    private Card[][] cards;//2d array of Card objects
    private ArrayList<String> cardList;//The pathname of all the available card images
    private int MAXROWS = 8;//the number of GridPane rows (8)
    private int MAXCOLS = 8;//the number of GridPane columns (8)
    private int currentRows;//the actual number of rows and cols used in the current game as set by the user selected level
    private int currentCols;
    private int cardSize;//the sidelength of the square card
    private int numCards;    
    
    /**
     * instantiate a card grid pane
     */
    public CardGridPane(){
        cardList = new ArrayList<>();        
        createCardImageList(MAXROWS, MAXCOLS);   
        cards = new Card[MAXROWS][MAXCOLS];
        currentRows = 0;
        currentCols = 0;
        cardSize = 0;
        numCards = 0;
        //setCardImages();
        //System.out.println(numCards);
    }    
    
    /**
     * @param cardSize the sidelength of the square card.
     */
    public CardGridPane(int cardSize){
        
        this.cardSize = cardSize;
    }
    
    /**
     * @return the cards
     */
    public Card[][] getCards() {
        return cards;
    }

    /**
     * @param cards the cards to set
     */
    public void setCards(Card[][] cards) {
        this.cards = cards;
    }

    /**
     * @return the cardList
     */
    public ArrayList<String> getCardList() {
        return cardList;
    }

    /**
     * @param cardList the cardList to set
     */
    public void setCardList(ArrayList<String> cardList) {
        this.cardList = cardList;
    }

    /**
     * @return the MAXROWS
     */
    public int getMAXROWS() {
        return MAXROWS;
    }

    /**
     * @param MAXROWS the MAXROWS to set
     */
    public void setMAXROWS(int MAXROWS) {
        this.MAXROWS = MAXROWS;
    }

    /**
     * @return the MAXCOLS
     */
    public int getMAXCOLS() {
        return MAXCOLS;
    }

    /**
     * @param MAXCOLS the MAXCOLS to set
     */
    public void setMAXCOLS(int MAXCOLS) {
        this.MAXCOLS = MAXCOLS;
    }

    /**
     * @return the currentRows
     */
    public int getCurrentRows() {
        return currentRows;
    }

    /**
     * @param currentRows the currentRows to set
     */
    public void setCurrentRows(int currentRows) {
        this.currentRows = currentRows;
    }

    /**
     * @return the currentCols
     */
    public int getCurrentCols() {
        return currentCols;
    }

    /**
     * @param currentCols the currentCols to set
     */
    public void setCurrentCols(int currentCols) {
        this.currentCols = currentCols;
    }

    /**
     * @return the cardSize
     */
    public int getCardSize() {
        return cardSize;
    }

    /**
     * @param cardSize the cardSize to set
     */
    public void setCardSize(int cardSize) {
        this.cardSize = cardSize;
    }
        
    /**
     * @param path
     */
    public void setCardImages(String path){
        //cards
    }
    
    /**
     * Shuffle the image paths in the ArrayList.
     */
    public void shuffleImages(){
        Collections.shuffle(cardList);
    }
    
    /**
     * @param r
     * @param c
     * @return //the card object referenced by location [r][c] in the cards 2d array.
     */
    public Card getCard(int r, int c){
        return cards[r][c];
    }
    
    /**
     * @param rows
     * @param cols
     */
    public void initCards(int rows, int cols){
        this.currentRows = rows;
        this.currentCols = cols;
        createCardImageList(rows, cols);        
        for (int i = 0; i < MAXROWS; i++){
            for (int j = 0; j < MAXCOLS; j++){                                
                Card card = new Card();                   
                //card.getChildren().remove(card.getImage());
                card.setCardAndImageSize(64.0, 64.0);    
                card.setFlipped(false);
                cards[i][j] = card;                 
                this.add(card, j, i);  
            }
        } 
        setCardImages();
    }

    //this method will assign the image path for each Card
    //helper method
    public void setCardImages() {
        int counter = 0;        
        
        for (int i = 0; i < currentRows; i++){
            for (int j = 0; j < currentCols; j++){    
                String str = this.getCardList().get(counter);
                cards[i][j].setPath(str);
                cards[i][j].defaultCard();
                counter++;                
            }
        } 
        numCards = counter;
        //System.out.println(numCards);
    }
    
    //what's this for? helper method
    //Create an ArrayList of image paths with a length of size. The ArrayList is already a property of the class.
    private void createCardImageList(int row, int col){
        cardList = new ArrayList<>();
        int counter = 0;        
        //add image paths to array
        for (int i = 0; i < (row * col)/2; i++) {
            cardList.add("marvel images/image_" + counter + ".jpg");            
            cardList.add("marvel images/image_" + counter + ".jpg");
            counter++;   
            shuffle();
        }        
    }   
    
    private void shuffle(){
        Collections.shuffle(cardList);
    }
}
