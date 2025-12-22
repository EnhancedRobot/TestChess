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
public class Rook extends Piece {    
    // The directions a rook can move in
    public static final BoardPos[] MOVEMENT_DIRECTIONS = {new BoardPos(-1, 0), new BoardPos(1, 0), 
                                                         new BoardPos(0, -1), new BoardPos(0, 1)};

    public Rook(BoardPos position, int owner) {
        super(position, owner);
        
        // Set the rook's id to R
        id = "R";
    }

    @Override
    public List<BoardPos> getMoves() {
        // The list of moves the rook has
        LinkedList<BoardPos> moves = new LinkedList<>();
        
        // For every direction the rook moves in
        for(BoardPos dir : MOVEMENT_DIRECTIONS) {
            // Move straight in that direction
            moveStraight(dir, moves);
        }
        
        return moves;
    }
}
