/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.Moves;

import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.pieces.base.Piece;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class Move {
    // The starting point of the move
    public BoardPos start;
    
    // The end point of the move
    public BoardPos end;
    
    // The piece that is moving
    public Piece movingPiece;
    
    // The board this move takes place on
    public Board board;
    
    // Create a new move
    public Move(BoardPos start, BoardPos end, Piece piece, Board board) {
        // Set the start and end
        this.start = new BoardPos(start);
        this.end = new BoardPos(end);
        
        // Set the piece that is moving
        this.movingPiece = piece;
        
        // Set the board
        this.board = board;
    }
    
    public void doMove() {
        board.moveAndTake(movingPiece, end);
    }
    
    @Override
    public String toString() {
        return end.toString();
    }
}
