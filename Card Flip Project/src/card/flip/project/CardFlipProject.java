/*
 * You will implement the matching game named Concentration for this programming assignment. The user will be presented with an nxm grid of 
 * cards with a symbol on the back. The player can only have 2 cards flipped over at once. The goal is to flip over 2 cards with the same symbol 
 * (symbols are placed in the game in pairs). Once all the matches have been found, the game ends.
 */
package card.flip.project;

/**
 * GEEN 165 Section 4
 * @author Zhipeng Liu
 * April 11, 2017
 */
public class CardFlipProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new MatchingGame().start();
       
    }
    
}
