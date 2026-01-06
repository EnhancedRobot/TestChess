/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.moves;

import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.pieces.EnPassant;
import main.java.net.ics4u.summativechess.game.pieces.base.Piece;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class CastleMove extends Move {
    
    // The rook that is also moving as part as the castlr
    public Piece rook;
    
    // The position the rook would move to
    public BoardPos rookEnd;
    
    
    /*
     Creates a new move
     Post: Move is created with the given start, end, piece, and board
    */
    public CastleMove(BoardPos start, BoardPos end, Piece piece, Board board, Piece rook, BoardPos rookEnd) {
        super(start, end, piece, board);
        
        this.rook = rook;
        this.rookEnd = rookEnd;
    }
    
    /*
     Does the move
     Post: Move is executed, en passant is added
    */
    @Override
    public void doMove() {
        // Move the king
        board.moveAndTake(movingPiece, end);
        
        // Move the rook as well
        board.moveAndTake(rook, rookEnd);
        
        // Add the en passant at the start position to take the king and rook
        board.enPassantPieces.add(new EnPassant(start, new Piece[]{movingPiece, rook}, new String[]{"*"}));
        
        // Call the onMove()s for the king and rook
        rook.onMove(this);
        movingPiece.onMove(this);
    }
}
