/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.variations.cult;

import java.util.LinkedList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.pieces.base.ActiveAbility;
import main.java.net.ics4u.summativechess.game.pieces.base.Bishop;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author connor
 */
public class CultBishop extends Bishop implements ActiveAbility {
    
    //give the movemnt to go left or right one
    public static final BoardPos[] MOVEABLE_POSITIONS = {new BoardPos(1, 0), new BoardPos(-1, -0)};
    
    // The turn the Bishop's move forwards ability is active
    public int abilityActive = -1;
    
    // The kill count of this Bishop
    public int killCount = 0;
    
    /*
     Creates a new cult Bishop
    
     Post: Cult Bishop is created
    */
    public CultBishop(BoardPos position, int owner) {
        super(position, owner);
        
        // Set the Bishop's id to B(Bishop) C(Cult)
        id = "BC";
    }

    /*
     Activate the ability
    
     Post: Bishop can move multiple squares forwards for the turn
    */
    @Override
    public void activateAbility() {
        System.out.println("Actiavted!" + killCount);
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
     Gets the available moves for the cultist Bishop
    */
    @Override
    public List<Move> getMoves() {
        // The list of moves the Bishop has
        LinkedList<Move> moves = new LinkedList<>();
        //The ability will let the Bishop move left and right by one and can take
        if (board.turn == abilityActive) {
            //Position its able to move
            for (BoardPos pos : MOVEABLE_POSITIONS) {
                // Get the actual position on the board you'd move to
                BoardPos movingTo = new BoardPos(position).add(pos);
                // If the piece can move there
                if (canMoveToPosition(movingTo, true, false)) {
                    // Add it to the list of places you can move to
                    moves.add(getMoveFor(movingTo));
                }
            }
        }

        // For every direction the Bishop moves in
        for (BoardPos dir : BoardPos.DIAGONALS) {
            // Move straight in that direction
            moveStraight(dir, moves);
        }

        return moves;
    }
    
    /*
     Handle stuff after moving
    
     Post: Increments the kill count if the move was a kill and checks if the kill count is 2 to allow for ability activation
    */
    @Override
    public void onMove(Move move) {
        // Call Bishop's on move
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
        return "Activate after taking 2 pieces it will be able to go left or right one once";
    }
}
