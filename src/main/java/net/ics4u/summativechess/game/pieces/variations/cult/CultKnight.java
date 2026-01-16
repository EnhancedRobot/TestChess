/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.variations.cult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.pieces.base.ActiveAbility;
import main.java.net.ics4u.summativechess.game.pieces.base.Knight;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author connor
 */
public class CultKnight extends Knight implements ActiveAbility {
    
    // The turn the Knight's move forwards ability is active
    public int abilityActive = -1;
    
    // The kill count of this Knight
    public int killCount = 0;
    
    //list
    public static final BoardPos[] MOVEABLE_POSITIONS_ABILITY = {
        new BoardPos(-4, -2), new BoardPos(-4, 0), new BoardPos(-4, 2),
        new BoardPos(-3, -3), new BoardPos(-3, -1), new BoardPos(-3, 1), new BoardPos(-3, 3),
        new BoardPos(-2, -4), new BoardPos(-2, 0), new BoardPos(-2, 4),
        new BoardPos(-1, -3), new BoardPos(-1, -1), new BoardPos(-1, 1), new BoardPos(-1, 3),
        new BoardPos(0, -4), new BoardPos(0, -2), new BoardPos(0, 2), new BoardPos(0, 4),
        new BoardPos(1, -3), new BoardPos(1, -1), new BoardPos(1, 1), new BoardPos(1, 3),
        new BoardPos(2, -4), new BoardPos(2, 0), new BoardPos(2, 4),
        new BoardPos(3, -3), new BoardPos(3, -1), new BoardPos(3, 1), new BoardPos(3, 3),
        new BoardPos(4, -2), new BoardPos(4, 0), new BoardPos(4, 2)
    };
    
    /*
     Creates a new cult Knight
    
     Post: Cult Knight is created
    */
    public CultKnight(BoardPos position, int owner) {
        super(position, owner);
        
        // Set the Knight's id to N(Knight) C(Cult)
        id = "NC";
    }

    /*
     Activate the ability
    
     Post: Knight can move multiple squares forwards for the turn
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
     Gets the available moves for the cultist Knight
    */
    @Override
    public List<Move> getMoves() {
        // The list of moves the Knight has
        LinkedList<Move> moves = new LinkedList<>();
        //able to do two knight moves in one turn but the second can't take
        if (board.turn == abilityActive) {
            for (BoardPos pos : MOVEABLE_POSITIONS_ABILITY) {
                // Get the actual position on the board you'd move to
                BoardPos movingTo = new BoardPos(position).add(pos);

                // If the piece can move there
                if (canMoveToPosition(movingTo, false, false)) {
                    // Add it to the list of places you can move to
                    moves.add(getMoveFor(movingTo));
                }
            }
        }

        // For every direction the Knight moves in
        for (BoardPos pos : MOVEABLE_POSITIONS) {
            // Get the actual position on the board you'd move to
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
        // Call Knight's on move
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
        return "Activate after taking 2 pieces\nMove by executing two consecutive standard moves in a single turn";
    }
}
