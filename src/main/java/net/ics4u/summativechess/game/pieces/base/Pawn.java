/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.base;

import java.util.LinkedList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.pieces.EnPassant;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.game.pieces.moves.PawnDoubleForwardsMove;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class Pawn extends Piece {    
    /*
     Creates a new piece with the given position and owner
     Post: New piece is created
    */
    public Pawn(BoardPos position, int owner) {
        super(position, owner);
                
        // Set the pawn's id to P
        id = "P";
        
        // Set the image icon for the piece
        setImage();
    }
    
    @Override
    /* 
     Gets the places the piece can move
     Post: Returns the list of places the piece can move to based on the current board state
    */
    public List<Move> getMoves() {
        // The list of places the piece can go to
        LinkedList<Move> moves = new LinkedList<>();
        
        
        // Handle forwards movement
        // If the pawn can always move double or it's the first move for the pawn
        if(board.variations.pawnsAlwaysMoveDouble || timesMoved == 0) {
            // Check the multi forwards move
            doubleForwardsMove(moves);
        } else {
            // Check the tile in front
            singleForwardsMove(moves);
        }
        
        // Handle diagonals
        getDiagonals(moves);
        
        return moves;
    }
    
    /*
     Gets the move to the position ahead
    
     Pre: List is initiated
     Post: Adds the move if valid
    */
    public void singleForwardsMove(List<Move> moves) {
        // Get the square in front
        BoardPos check = board.getFacingDirection(player).add(position);

        // If we can move to the square in front
        if(canMoveToPosition(check, board.variations.pawnCanTakeForwards, false)) {
            // Add the location we are checking 
            moves.add(getMoveFor(check));
        }
    }
    
    /*
     Gets the moves for multiple push
    
     Pre: List is initiated
     Post: Adds the valid double forwards moves to the list 
    */
    public void doubleForwardsMove(List<Move> moves) {
        // Get the position to check
        BoardPos check = new BoardPos(position);
        
        for(int i = 0; i < board.variations.pawnFirstTurnMoveDistance; i++) {
            // Step forwards once in the facing direction of the player
            check.add(board.getFacingDirection(player));

            // Check if we can move to the position
            if(canMoveToPosition(check, board.variations.pawnCanTakeForwards, false)) {
                // Add the location we are checking 
                moves.add(new PawnDoubleForwardsMove(position, check, this, board));

                // If there is a piece there, then break because we've encountered something that blocks us
                if (board.getPiece(check) != null) {
                    break;
                }
            } else {
                // We've encountered something that blocks us
                break;
            }
        }
    }
    
    /*
     Gets the diagonals the pawn can move to
    
     Post: Adds the potential diagonals to the list of moves 
    */
    public void getDiagonals(List<Move> moves) {
        // Forwards
        BoardPos forwards = board.getFacingDirection(player);
        
        // If we are going on the y axis
        if(forwards.x == 0) {
            // Get the left diagonal
            BoardPos left = new BoardPos(forwards).add(position);
            left.x += -1;
            
            // Get the right diagonal
            BoardPos right = new BoardPos(forwards).add(position);
            right.x += 1;
                        
            // If we can move to the diagonal left
            if(canMoveToPosition(left, true, false, !board.variations.pawnCanMoveDiagonal)) {
                moves.add(getMoveFor(left));
            }
            
            // If we can move to the diagonal right
            if(canMoveToPosition(right, true, false, !board.variations.pawnCanMoveDiagonal)) {
                moves.add(getMoveFor(right));
            }
        }
        
        // If we are going on the x axis
        if(forwards.y == 0) {
            // Get the left diagonal
            BoardPos left = new BoardPos(forwards).add(position);
            left.y += -1;
            
            // Get the right diagonal
            BoardPos right = new BoardPos(forwards).add(position);
            right.y += 1;
            
            // If we can move to the diagonal left
            if(canMoveToPosition(left, true, false, !board.variations.pawnCanMoveDiagonal)) {
                moves.add(getMoveFor(left));
            }
            
            // If we can move to the diagonal right
            if(canMoveToPosition(right, true, false, !board.variations.pawnCanMoveDiagonal)) {
                moves.add(getMoveFor(right));
            }
        }
    }
    
    /*
     Called when the piece moves
    
    
     Post: Checks for promotion
    */
    @Override
    public void onMove(Move move) {
        // If the square in front is off the board
        if(!board.isInBoard(board.getFacingDirection(player).add(position))) {
            // Promote
            promote();
        }
    }
    
    /*
     Promotes the piece
    
     Post: Piece promotion is selected and piece is changed
    */
    public void promote() {
        // Only to queen for now while we wait for UI
            
        // Create a new Queen
        Queen queen = new Queen(position, player);


        // Set the piece on the tile to be a queen
        board.setPieceAt(position, queen);
    }
}
