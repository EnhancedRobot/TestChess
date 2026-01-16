/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.variations.cult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.pieces.base.ActiveAbility;
import main.java.net.ics4u.summativechess.game.pieces.base.Queen;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author connor
 */
public class CultQueen extends Queen implements ActiveAbility {
    
    // The turn the Queen's move forwards ability is active
    public int abilityActive = -1;
    
    // The kill count of this Queen
    public int killCount = 0;
    
    //list
    List<BoardPos> MOVEABLE_POSITIONS_ABILITY = new ArrayList<>();
    
    /*
     Creates a new cult Queen
    
     Post: Cult Queen is created
    */
    public CultQueen(BoardPos position, int owner) {
        super(position, owner);
        
        // Set the Queen's id to Q(Queen) C(Cult)
        id = "QC";
        // Make a list that covers every tile
        for (int x = -8; x <= 8; x++) {
            for (int y = -8; y <= 8; y++) {
                //add to list
                MOVEABLE_POSITIONS_ABILITY.add(new BoardPos(x, y));
            }
        }
    }

    /*
     Activate the ability
    
     Post: Queen can move multiple squares forwards for the turn
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
     Gets the available moves for the cultist Queen
    */
    @Override
    public List<Move> getMoves() {
        // The list of moves the Queen has
        LinkedList<Move> moves = new LinkedList<>();
        //move any where without taking
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

        // For every direction the Queen moves in
        for (BoardPos dir : BoardPos.DIRECTIONS) {
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
        // Call Queen's on move
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
        return "Activate after taking 2 pieces\nTeleport to any square, without taking!";
    }
}
