/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.base;

import java.util.LinkedList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.board.tiles.Tile;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class King extends Piece {
    public King(BoardPos position, int owner) {
        super(position, owner);
        
        // Set the id to k
        id = "K";
    }

    /*
        Gets the places the piece can move to
        Post: Returns a list of every position the piece can move to
    */    
    @Override
    public List<BoardPos> getMoves() {
        // The list of places the piece can go to
        LinkedList<BoardPos> moves = new LinkedList<>();
        
        // For every direction
        for(BoardPos pos : BoardPos.DIRECTIONS) {
            BoardPos movingTo = new BoardPos(position).add(pos);
            // If the piece can move there
            if(canMoveToPosition(movingTo, true, false)) {
                // Add it to the list of places you can move to
                moves.add(movingTo);
            }
        }
        
        // Handle castling
        if(timesMoved == 0) {
            BoardPos check = new BoardPos(position);
            
            // For every direction
            for (BoardPos direction : BoardPos.DIRECTIONS) {
                // While still in the board
                while(board.isInBoard(check)) {
                    // Move forward in the direction
                    check.add(direction);
                    
                    // Get the tile at the position
                    Tile tile = board.getTile(check);
                    
                    // If the tile is non-traversable
                    if(tile != null && !tile.isTraversable) {
                        // Break
                        break;
                    }
                    
                    // Get the piece on the tile
                    Piece onTile = board.getPiece(check);
                    
                    // If there is a piece on the tile
                    if(onTile != null) {
                        // If the piece on the tile is a rook that has never moved
                        if(onTile.id.equals("R") && onTile.timesMoved == 0) {
                            // Add the castling
                            
                            // Get two spaces in that direction
                            BoardPos goTo = new BoardPos(direction);
                            goTo = goTo.multiply(2);
                            
                            // Add the current position to get the point you would castle to
                            goTo.add(position);
                            
                            // Add the position to the list of possible moves
                            moves.add(goTo);
                        }
                        
                        // We've hit something, so break
                        break;
                    }
                }
            }
        }
             
        // Return the list of places this piece can go to
        return moves;
    }
    
    
    @Override
    public void onMoveTo(BoardPos position) {
        // Subtract the original position from the new position to get the relative position
        BoardPos moved = new BoardPos(position).subtract(this.position);
        
        // If we moved two tiles (that's a castle... Probably.)
        // This may be a problem I need to solve
        if(moved.maxDistance() == 2) {
            
        }
    }
}
