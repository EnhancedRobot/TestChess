/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.moves;

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
    
    // Whether or not this move involved a take.
    // Note, this only changes after the move is executed.
    public boolean isTake;
    
    /*
     Creates a new move
     Post: Move is created with the given start, end, piece, and board
    */
    public Move(BoardPos start, BoardPos end, Piece piece, Board board) {
        // Set the start and end
        this.start = new BoardPos(start);
        this.end = new BoardPos(end);
        
        // Set the piece that is moving
        this.movingPiece = piece;
        
        // Set the board
        this.board = board;
                
        // If the piece at the new location exists and is takable, this move would be a taking move, otherwise not
        // Basically if not null, set to true otherwise set to false
        isTake = board.getPiece(end) != null;
    }
    /*
     Does the move
     Post: Move is executed
    */
    public void doMove() {
        board.moveAndTake(movingPiece, end);
        
        
        // Call onMoveTo() if there is a tile
        if(board.getTile(end) != null) {
            board.getTile(end).onMoveTo(this);
        }
        
        // Call onMove() for the moving piece
        if(!movingPiece.isTaken) {
            movingPiece.onMove(this);
        }
    }
    
    /*
     Converts the move to a string
     Currently just returns the end point as a string
     Post: Returns the move as a string
    */
    @Override
    public String toString() {
        return end.toString();
    }
}
