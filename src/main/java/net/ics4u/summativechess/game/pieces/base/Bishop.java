/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.base;

import java.util.LinkedList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.pieces.Moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class Bishop extends Piece {
    public Bishop(BoardPos position, int owner) {
        super(position, owner);
        
        
        // Set the bishop's id to be B
        id = "B";
    }

    @Override
    // Gets the places the piece can move to
    // Post: Returns a list of every position the piece can move to
    public List<Move> getMoves() {
        // The list of moves the rook has
        LinkedList<Move> moves = new LinkedList<>();
        
        // For every direction the rook moves in
        for(BoardPos dir : BoardPos.DIAGONALS) {
            // Move straight in that direction
            moveStraight(dir, moves);
        }
        
        return moves;
    }
}
