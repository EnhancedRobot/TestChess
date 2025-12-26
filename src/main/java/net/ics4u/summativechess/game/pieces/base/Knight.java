/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.base;

import java.util.LinkedList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class Knight extends Piece {
    
    // The positions the knight can move to
    public static final BoardPos[] MOVEABLE_POSITIONS = {new BoardPos(-2, -1), new BoardPos(-1, -2),
                                                        new BoardPos(2, -1), new BoardPos(1, -2),
                                                        new BoardPos(2, 1), new BoardPos(1, 2),
                                                        new BoardPos(-2, 1), new BoardPos(-1, 2)};

    public Knight(BoardPos position, int owner) {
        super(position, owner);
        
        // Set the knight's id to N (King is K, this is standard)
        id = "N";
    }

    @Override
    // Gets the places the piece can move
    public List<Move> getMoves() {
        // The list of places the piece can go to
        LinkedList<Move> moves = new LinkedList<>();
        
        // For every position in moveable positions
        for(BoardPos pos : MOVEABLE_POSITIONS) {
            // Get the actual position on the board you'd move to
            BoardPos movingTo = new BoardPos(position).add(pos);
            
            // If the piece can move there
            if(canMoveToPosition(movingTo, true, false)) {
                // Add it to the list of places you can move to
                moves.add(getMoveFor(movingTo));
            }
        }
        
        return moves;
    }
}
