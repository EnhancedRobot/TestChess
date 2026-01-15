/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.variations.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.pieces.base.ActiveAbility;
import main.java.net.ics4u.summativechess.game.pieces.base.Piece;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class Example extends Piece implements ActiveAbility {

    /*
     Creates a new Example piece
    
     Post: Example piece is created
    */
    public Example(BoardPos position, int owner) {
        super(position, owner);
        
        id = "Whatever";
    }

    /*
     Get the list of places this piece can move
    
     Post: Returns the position in front of the piece if it can move there
    */
    @Override
    public List<Move> getMoves() {
        // Create an ArrayList of moves
        LinkedList<Move> moves = (LinkedList<Move>) super.getMoves();
        
        // Get the position in front of the piece to check to move to
        BoardPos pos = board.getFacingDirection(player).add(position);
        
        // If the piece can move to the position in front of the piece
        if(canMoveToPosition(pos)) {
            // Add it to the list of places the piece can move
            moves.add(getMoveFor(pos));
        }
        
        // Return the list of moves
        return moves;
    }
    
    /*
     Activate the ability
    
     Post: Piece is taken.
    */
    @Override
    public void activateAbility() {
        // Take the piece
        take();
    }
    
    
    
    /*
     Called when the piece moves
    
     Post: Takes the piece in front of this one
    */
    @Override
    public void onMove(Move move) {
        // Take the piece in front of you
        board.takePieceAt(board.getFacingDirection(player).add(position));
    }

    /*
     Gets the ability description
    
     Post: Returns a description of the ability
    */
    @Override
    public String getAbilityDescription() {
        return "Activate the ability to take this piece!";
    }
}
