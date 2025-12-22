/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.base;

import java.util.LinkedList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class King extends Piece {
    
    // The positions the king can move to
    public static final BoardPos[] MOVEABLE_POSITIONS = {new BoardPos(-1, -1), new BoardPos(1, 1), 
                                                         new BoardPos(1, -1), new BoardPos(-1, 1),
                                                         new BoardPos(-1, 0), new BoardPos(1, 0), 
                                                         new BoardPos(0, -1), new BoardPos(0, 1)};

    public King(BoardPos position, int owner) {
        super(position, owner);
        
        // Set the id to k
        id = "K";
    }

    /*
        Gets the places the piece can move to
        Post: Returns a list of every position the piece can move to
    */    
    @Override
    public List<BoardPos> getMoves() {
        // The list of places the piece can go to
        LinkedList<BoardPos> moves = new LinkedList<>();
        
        // For every position in moveable positions
        for(BoardPos pos : MOVEABLE_POSITIONS) {
            BoardPos movingTo = new BoardPos(position).add(pos);
            // If the piece can move there
            if(canMoveToPosition(movingTo, true, false)) {
                // Add it to the list of places you can move to
                moves.add(movingTo);
            }
        }
        
        // Handle castling
        
        
        // Return the list of places this piece can go to
        return moves;
    }
}
