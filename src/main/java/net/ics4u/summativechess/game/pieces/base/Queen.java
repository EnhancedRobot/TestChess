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
public class Queen extends Piece {

    /*
     Creates a new piece with the given position and owner
     Post: New piece is created
     */
    public Queen(BoardPos position, int owner) {
        super(position, owner);

        // Set the queen's id to Q
        id = "Q";
        
        // Set the image path
        imagePath = "base/queen";        

        // Set the image icon for the piece
        setImage();
    }

    @Override
    public List<Move> getMoves() {
        // The list of moves the rook has
        LinkedList<Move> moves = (LinkedList<Move>) super.getMoves();

        // For every direction
        for (BoardPos dir : BoardPos.DIRECTIONS) {
            // Move straight in that direction
            moveStraight(dir, moves);
        }

        return moves;
    }
}
