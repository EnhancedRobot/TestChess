/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.base;

import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.board.tiles.Tile;
import main.java.net.ics4u.summativechess.game.pieces.EnPassant;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public abstract class Piece {
    // Whether or not the piece can be taken by other pieces
    public boolean canBeTaken = true;
        
    // The image of the piece on the board
    public ImageIcon[] image;
    
    // The position of this piece on the board
    public BoardPos position;
    
    // Tne player that owns this piece
    public int player;
    
    // The id of the piece, ex. B for bishop or N for knight
    public String id;
    
    // The board this piece is currently on
    public Board board;
    
    // The number of times the piece has moved
    public int timesMoved = 0;
    
    public Piece(BoardPos position, int owner) {
        this.position = position;
        this.player = owner;
    }
    
    /*
     Gets the list of all places the piece can move to
     Post: Returns the list of piece the piece can move to
    */
    public abstract List<BoardPos> getMoves(Board board);
    
    
    /*
     * Called when the player moves to a given position 
    */
    public void onMoveTo(BoardPos position, Board board) {}
    
    /*
     Moves the piece in a straight line in the given direction
     Stops if it collides with a piece
     Pre: out is non-null
     Post: Appends the positions you can move in the given direction to out
    */
    private List<BoardPos> moveStraight(Board board, BoardPos dir, List<BoardPos> out, boolean canTakeOther, boolean canTakeSelf) {        
        // The position it is checking
        BoardPos goal = new BoardPos(position);
        // It hasn't collided with anything yet
        boolean notCollided = true;
        
        // While it hasn't collided
        while (notCollided) {
            // Add the direction to the last position it checked
            goal.add(dir);
                        
            // If we've gone out of the board
            if(!board.isInBoard(goal)) {
                // We've collided with the edge
                notCollided = false;
                
                // Continue
                continue;
            }
            
            // Get the piece at that space on the board
            Piece piece = board.getPiece(goal);
            
            // If the piece on that space is null (There's nothing there)
            if(piece == null) {
                // Add the space to the output
                out.add(new BoardPos(goal));
            } else {
                // Otherwise, we've collided with something
                notCollided = false;
                
                // If the piece cannot be taken, we don't need to do collision logic so continue
                if(!piece.canBeTaken) {
                    // Continue
                    continue;
                }
                
                // If the piece you collided with is your own
                if(piece.player == player) {
                    // If you can take your own pieces
                    if(canTakeSelf) {
                        // Add the position to the list of outputs
                        out.add(new BoardPos(goal));
                    }
                } else {
                    // If you collided with an enemy and you can take their pieces
                    if(canTakeOther) {
                        // Add it to the list of outputs
                        out.add(new BoardPos(goal));
                    }
                }
            }
        }
        
        // Return the list of outputs
        return out;
    }
    
    /*
     Checks if the piece can move to a given position
    
     Post: Returns whether or not the piece can move to that position
     */
    public boolean canMoveToPosition(BoardPos pos, Board board, boolean canTakeOther, boolean canTakeSelf) {
        return canMoveToPosition(pos, board, 
            canTakeOther, canTakeSelf, false);
    }
    
    /*
     Checks if the piece can move to a given position
        
     Post: Returns whether or not the piece can move to the given position on the board
    */
    public boolean canMoveToPosition(BoardPos pos, Board board, 
            boolean canTakeOther, boolean canTakeSelf, boolean requiresTaking) {
        // If the position is out of bounds
        if(!board.isInBoard(pos)) {
            // No you can't move there
            return false;
        }
        
        Tile tile = board.getTile(pos);
        
        // If the tile isn't traversable
        if(tile != null && !tile.isTraversable) {
            // You can't go there
            return false;
        }
        
        // Get the piece at that space on the board
        Piece piece = board.getPiece(pos);

        // If the piece on that space is null (There's nothing there)
        if(piece == null){
            // Get the en passants for the square
            EnPassant enPassant = board.getEnPassant(pos);
            
            // If there's no en passant on that square or the en passant can't be taken by the piece
            if(enPassant == null || !enPassant.canBeTaken(this)) {
                // Return true if it doesn't require taking, otherwise return false
                return !requiresTaking;
            }
            
            // For every piece in the en passant
            for(Piece enPassantPiece : enPassant.pieces) {
                // If the piece is on your team and you can take your own pieces or the piece is on the opposing team and you can take opponents
                if((enPassantPiece.player == player && canTakeSelf) || 
                        (enPassantPiece.player != player && canTakeOther)) {
                    // return true
                    return true;
                }
            }
            
            // Return true if it doesn't require taking, otherwise return false
            return !requiresTaking;
        }
        
        // If the piece cannot be taken, then no you can't move there
        if(!piece.canBeTaken) {
            // Return true
            return false;
        }

        // If the piece you collided with is your own
        if(piece.player == player) {
            // If you can't take your own pieces
            if(!canTakeSelf) {
                // You can't move there
                return false;
            }
        } else {
            // If you collided with an enemy and you can take their pieces
            if(!canTakeOther) {
                // Add it to the list of outputs
                return false;
            }
        }
        
        // Otherwise return true
        return true;
    }
    
    /*
     Checks if you can move to a given position on the board
     Post: Returns whether or not you can move to the position
    */
    public boolean canMoveToPosition(BoardPos pos, Board board) {
        // Return canMoveToPosition while being able to take other pieces and not take your own pieces
        return canMoveToPosition(pos, board, true, false);
    }
    
    /*
     Takes the piece
     When overriding, remember to call super.take()
    
     Post: Piece is added to the list of taken pieces
    */
    public void take() {
        position = null;
        board.setPiece(position, null);
        board.capturedPieces.add(this);
    }
    
    /*
     Moves the piece in a straight line in the given direction
     Stops if it collides with a piece
     Pre: out is non-null
     Post: Appends the positions you can move in the given direction to out
    */
    public List<BoardPos> moveStraight(Board board, BoardPos dir, List<BoardPos> out) {
        // Return moving straight with the new list
        return moveStraight(board, dir, out, true, false);
    }
    
    /*
     Moves the piece in a straight line in the given direction
     Stops if it collides with a piece
     Creates a new list
     Post: Creates a new list with the positions you can move to in the given direction
    */
    public List<BoardPos> moveStraight(Board board, BoardPos dir) {
        // Create a new LinkedList for output
        // Could also be an array list, but we're doing a lot of insertions here
        List<BoardPos> out = new LinkedList<>();

        // Return moving straight with the new list
        return moveStraight(board, dir, out);
    }
}
