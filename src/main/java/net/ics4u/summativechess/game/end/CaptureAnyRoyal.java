/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.end;

import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.pieces.base.Piece;

/**
 * The victory condition for capturing any king
 * This is the default victory condition
 *
 * @author joshu
 */
public class CaptureAnyRoyal extends VictoryCondition {
    /*
     Get the victory state condition for the given board
     Post: Returns the victtory state for the board
    */
    @Override
    public VictoryState isEnded(Board board) {
        // Create the list of who has lost
        Boolean[] hasLost = new Boolean[board.numPlayers];
        
        // Set all of the hasLost to false
        for(int i = 0; i < hasLost.length; i++) {
            hasLost[i] = false;
        }
        
        
        // For every captured piece
        for (Piece piece : board.capturedPieces) {
            // If that piece is a king
            if(piece.id.equals("K") || piece.id.equals("KC")) {
                // Add it to the list of teams that have lost
                hasLost[piece.player] = true;
            }
        }
        
        
        return new VictoryState(hasLost);
    }

    /*
     Get the description string of this victory condition
    
     Post: Returns the description 
    */
    @Override
    public String getDescription() {
        return "Capture any king to win!";
    }
}
