/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces;

import main.java.net.ics4u.summativechess.game.pieces.base.Piece;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class EnPassant {
    // The location on the board at which this en passant is
    public BoardPos location;
    
    // The list of pieces that would be taken by the en passant
    public Piece[] pieces;
    
    // The list of piece ids that can take the en passant
    public String[] canTake;
    
    /*
     Creates a new en passant with multiple pieces
    
     Post: Creates a new en passant
    */
    public EnPassant(BoardPos location, Piece[] pieces, String[] canTake) {
        this.location = location;
        this.pieces = pieces;
        this.canTake = canTake;
    }
    
    /*
     Creates a new en passant with just one piece
    
     Post: Creates a new en passant
    */
    public EnPassant(BoardPos location, Piece piece, String[] canTake) {
        this.location = location;
        this.pieces = new Piece[]{piece};
        this.canTake = canTake;
    }
    
    /*
     Takes the en passant if the given piece can
    */
    public void takeIfPossible(Piece piece) {
        // If the piece can take the en passant
        if(canBeTaken(piece)) {
            // Take the en passant
            take();
        }
    }
    
    /*
     Takes every piece in the en passant
     
     Post: Every piece is taken 
    */
    public void take() {
        // For every piece
        for(Piece piece : pieces) {
            // Take that piece
            piece.take();
        }
    }
    
    /*
     Checks if this en passant is takeable by a given piece
    
     Post: Returns whether or not the taking piece can take this en passant
    */
    public boolean canBeTaken(Piece taking) {        
        // Linear search for the id of the taking piece in the pieces that can take the en passant
        // This could be done with a binary search if we force the user to sort
        // Or do it for them
        // But that's a lot of work when this is only really used a few times
        // And also we don't expect that many pieces in the canTake
        for (String i : canTake) {
            if(i.equals("*") || i.equals(taking.id)) {
                return true;
            }
        }
        
        return false;
    }
}
