/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.Moves;

import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.pieces.EnPassant;
import main.java.net.ics4u.summativechess.game.pieces.base.Piece;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class PawnDoubleForwardsMove extends Move {
    
    public BoardPos direction;
    
    public static final String[] CAN_TAKE_EN_PASSANT = new String[]{"P"};

    
    public PawnDoubleForwardsMove(BoardPos start, BoardPos end, Piece piece, Board board) {
        super(start, end, piece, board);
    }
    
    
    @Override
    public void doMove() {
        // Move the piece
        super.doMove();
        
        // Subtract the original position from the new position to get the relative position
        BoardPos moved = new BoardPos(end).subtract(start);
        
        // Get the distance travelled
        int distance = Math.max(Math.abs(moved.x), Math.abs(moved.y));
        
        // Go forwards until you reach the end
        for(int i = 0; i < distance; i++) {                
            // Get the position to add to the en passant
            BoardPos enPassantPos = board.getFacingDirection(movingPiece.player).multiply(i).add(start);
                
            // Add the en passant
            board.enPassantPieces.add(new EnPassant(enPassantPos, movingPiece, CAN_TAKE_EN_PASSANT));
        }
    }
}
