/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.variations.cult;

import java.util.LinkedList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.pieces.base.ActiveAbility;
import main.java.net.ics4u.summativechess.game.pieces.base.Rook;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author connor
 */
public class CultRook extends Rook implements ActiveAbility {
    
    // The turn the Rook's move forwards ability is active
    public int abilityActive = -1;
    
    // The kill count of this Rook
    public int killCount = 0;
    
    /*
     Creates a new cult Rook
    
     Post: Cult Rook is created
    */
    public CultRook(BoardPos position, int owner) {
        super(position, owner);
        
        // Set the Rook's id to R(Rook) C(Cult)
        id = "RC";
    }

    /*
     Activate the ability
    
     Post: Rook can move multiple squares forwards for the turn
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
     Gets the available moves for the cultist Rook
    */
    @Override
    public List<Move> getMoves() {
        // The list of moves the rook has
        LinkedList<Move> moves = new LinkedList<>();
        //The ability will let the Rook jump over pieces else move normally
        if (board.turn == abilityActive) {
            // For every direction the rook moves in
            for (BoardPos dir : BoardPos.DIAGONALS) {
                // Move straight in that direction
                moveStraight(dir, moves);
            }
        }

        // For every direction the rook moves in
        for (BoardPos dir : BoardPos.HORIZONTAL_VERTICAL) {
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
        // Call Rook's on move
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
        return "Activate after taking 2 pieces to move like a bishop as well once";
    }
}
