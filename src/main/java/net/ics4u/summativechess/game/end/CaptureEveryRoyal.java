/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.end;

import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.pieces.base.Piece;

/**
 *
 * @author joshu
 */
public class CaptureEveryRoyal extends VictoryCondition {
    /*
     Get the victory state condition for the given board
     Post: Returns the victtory state for the board
    */
    @Override
    public VictoryState isEnded(Board board) {
        // Create the list of who has lost
        Boolean[] hasLost = new Boolean[board.numPlayers];
        
        // Set all of the hasLost to true by default
        for(int i = 0; i < hasLost.length; i++) {
            hasLost[i] = true;
        }
        
        // For every row
        for(Piece[] row : board.pieces) {
            // For every piece in that row
            for (Piece piece : row) {
                // If there is a king on that tile
                if(piece != null && piece.id.equals("K")) {
                    // The team hasn't actually lost yet
                    hasLost[piece.player] = false;
                }
            }
        }
        
        
        // Return the victory state based on which players have lost
        return new VictoryState(hasLost);
    }
}
