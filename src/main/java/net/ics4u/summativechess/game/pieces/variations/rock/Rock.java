/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.variations.rock;

import java.util.ArrayList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.pieces.base.Piece;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class Rock extends Piece {

    /*
     Creates a new Rock
    
     Post: Rock is created
    */
    public Rock(BoardPos position, int owner) {
        super(position, owner);
                
        // Set the id to O
        id = "O";
        
        // Set the piece to be non-takeable
        canBeTaken = false;
    }

    /*
     Get the list of places this piece can move
    
     Post: Returns a new empty list, as there is no places the rock can move ever. 
    */
    @Override
    public List<Move> getMoves() {
        // Return an empty list
        return new ArrayList<>();
    } 
}
