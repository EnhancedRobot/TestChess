/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.variations.test;

import java.util.ArrayList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.pieces.base.ActiveAbility;
import main.java.net.ics4u.summativechess.game.pieces.base.Piece;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class Example extends Piece implements ActiveAbility {

    
    public Example(BoardPos position, int owner) {
        super(position, owner);
        
        id = "Whatever";
    }

    /*
     Get the list of places this piece can move
    
     Post: Returns the position in front of the piece if it can move there
    */
    @Override
    public List<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        
        
        if(canMoveToPosition(board.getFacingDirection(player).add(position))) {
            moves.add(getMoveFor(board.getFacingDirection(player).add(position)));
        }
        
        return moves;
    }

    @Override
    public void activateAbility() {
        take();
    }
    
    @Override
    public void onMove(Move move) {
        board.takePieceAt(board.getFacingDirection(player).add(position));
    }
}
