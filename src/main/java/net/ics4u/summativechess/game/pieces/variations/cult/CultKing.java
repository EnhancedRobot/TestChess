/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.variations.cult;

import java.util.LinkedList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.pieces.base.ActiveAbility;
import main.java.net.ics4u.summativechess.game.pieces.base.King;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author connor
 */
public class CultKing extends King implements ActiveAbility {
    
    //Gives the knight movement
    public static final BoardPos[] MOVEABLE_POSITIONS = {new BoardPos(-2, -1), new BoardPos(-1, -2),
                                                        new BoardPos(2, -1), new BoardPos(1, -2),
                                                        new BoardPos(2, 1), new BoardPos(1, 2),
                                                        new BoardPos(-2, 1), new BoardPos(-1, 2)};
    
    // The turn the King's move forwards ability is active
    public int abilityActive = -1;
    
    // The kill count of this King
    public int killCount = 0;
    
    /*
     Creates a new cult King
    
     Post: Cult King is created
    */
    public CultKing(BoardPos position, int owner) {
        super(position, owner);
        
        // Set the King's id to K(King) C(Cult)
        id = "KC";
    }

    /*
     Activate the ability
    
     Post: King can move multiple squares forwards for the turn
    */
    @Override
    public void activateAbility() {
        // If the kill count is 2
        if(killCount >= 2) {
            // Reset it
            killCount = 0;
            
            // Set the ability to be active
            abilityActive = board.turn;
        }
        
        // Get the list of moves
        board.validMoves = getMoves();
    }
    
    /*
     Gets the available moves for the cultist King
    */
    @Override
    public List<Move> getMoves() {
        // The list of moves the King has
        LinkedList<Move> moves = new LinkedList<>();
        //The ability will let the king move like a knight and take as one
        if (board.turn == abilityActive) {
            //Position its able to move
            for (BoardPos pos : MOVEABLE_POSITIONS){
                // Get the actual position on the board you'd move to
                BoardPos movingTo = new BoardPos(position).add(pos);
                // If the piece can move there
                if(canMoveToPosition(movingTo, true, false)) {
                    // Add it to the list of places you can move to
                    moves.add(getMoveFor(movingTo));
                }
            }
        }

        // For every direction the King moves in
        for (BoardPos pos : BoardPos.DIRECTIONS) {
            BoardPos movingTo = new BoardPos(position).add(pos);
            // If the piece can move there
            if (canMoveToPosition(movingTo, true, false)) {
                // Add it to the list of places you can move to
                moves.add(getMoveFor(movingTo));
            }
        }

        return moves;
    }
    
    /*
     Handle stuff after moving
    
     Post: Increments the kill count if the move was a kill and checks if the kill count is 2 to allow for ability activation
    */
    @Override
    public void onMove(Move move) {
        // Call King's on move
        super.onMove(move);
        
        // If the move was a take
        if(move.isTake) {
            // Increment the kill count
            killCount++;
            
            System.out.println(killCount);
        }
    }

    /*
     Gets the ability description
    
     Post: Returns a description of the ability
    */
    @Override
    public String getAbilityDescription() {
        return "Activate after taking 2 pieces\nMove like a knight";
    }
}
