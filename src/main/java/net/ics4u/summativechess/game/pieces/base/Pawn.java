/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.base;

import java.util.LinkedList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.pieces.EnPassant;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class Pawn extends Piece {
    
    public int firstTurnMoveDistance = 2;
    public boolean canTakeForwards = false;
    public boolean canMoveDiagonal = false;
    public static final String[] CAN_TAKE_EN_PASSANT = new String[]{"P"};
    

    public Pawn(BoardPos position, int owner) {
        super(position, owner);
                
        // Set the pawn's id to P
        id = "P";
    }
    
    @Override
    /* 
     Gets the places the piece can move
     Post: Returns the list of places the piece can move to based on the current board state
    */
    public List<BoardPos> getMoves() {
        // The list of places the piece can go to
        LinkedList<BoardPos> moves = new LinkedList<>();
        
        
        // Handle forwards movement
        // If it's the first move for the pawn
        if(timesMoved == 0) {
            BoardPos check = new BoardPos(position);
            for(int i = 0; i < firstTurnMoveDistance; i++) {
                // Step forwards once in the facing direction of the player
                check.add(board.getFacingDirection(player));
                
                // Check if we can move to the position
                if(canMoveToPosition(check, canTakeForwards, false)) {
                    // Add the location we are checking 
                    moves.add(new BoardPos(check));
                    
                    // If there is a piece there, then break because we've encountered something that blocks us
                    if (board.getPiece(check) != null) {
                        break;
                    }
                } else {
                    // We've encountered something that blocks us
                    break;
                }
            }
        } else {
            // Get the square in front
            BoardPos check = board.getFacingDirection(player).add(position);
            
            // If we can move to the square in front
            if(canMoveToPosition(check, canTakeForwards, false)) {
                // Add the location we are checking 
                moves.add(new BoardPos(check));
            }
        }
        
        // Handle diagonals
        BoardPos forwards = board.getFacingDirection(player).add(position);
        
        // If we are going on the y axis
        if(forwards.x == 0) {
            // Get the left diagonal
            BoardPos left = new BoardPos(forwards);
            left.x += -1;
            
            // Get the right diagonal
            BoardPos right = new BoardPos(forwards);
            right.x += 1;
            
            // If we can move to the diagonal left
            if(canMoveToPosition(left, true, false, !canMoveDiagonal)) {
                moves.add(left);
            }
            
            // If we can move to the diagonal right
            if(canMoveToPosition(right, true, false, !canMoveDiagonal)) {
                moves.add(right);
            }
        }
        
        // If we are going on the x axis
        if(forwards.y == 0) {
            // Get the left diagonal
            BoardPos left = new BoardPos(forwards);
            left.y += -1;
            
            // Get the right diagonal
            BoardPos right = new BoardPos(forwards);
            right.y += 1;
            
            // If we can move to the diagonal left
            if(canMoveToPosition(left, true, false, !canMoveDiagonal)) {
                moves.add(left);
            }
            
            // If we can move to the diagonal right
            if(canMoveToPosition(right, true, false, !canMoveDiagonal)) {
                moves.add(right);
            }
        }
        
        return moves;
    }
    
    @Override
    public void onMoveTo(BoardPos position) {
        // Subtract the original position from the new position to get the relative position
        BoardPos moved = new BoardPos(position).subtract(this.position);
        
        // Get the distance travelled
        int distance = Math.max(Math.abs(moved.x), Math.abs(moved.y));
        
        // If the pawn moved more than one square in the facing direction   
        if(distance > 1 && moved.equals(board.getFacingDirection(player).multiply(distance))) {
            // Go forwards until you reach the end
            for(int i = 0; i < distance; i++) {
                // Add the point it passed to enPassant
                
                // Get the position to add to the en passant
                BoardPos enPassantPos = board.getFacingDirection(player).multiply(i).add(this.position);
                
                // Add the en passant
                board.enPassantPieces.add(new EnPassant(enPassantPos, this, CAN_TAKE_EN_PASSANT));
            }
        }
    }
}
