/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.base;

import java.util.LinkedList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.board.tiles.Tile;
import main.java.net.ics4u.summativechess.game.pieces.moves.CastleMove;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class King extends Piece {
    /*
     Creates a new piece with the given position and owner
     Post: New piece is created
    */
    public King(BoardPos position, int owner) {
        super(position, owner);
        
        // Set the id to k
        id = "K";
        
        // Set the image path
        imagePath = "base/king";
        
        // Set the image icon for the piece
        setImage();
    }

    /*
        Gets the places the piece can move to
        Post: Returns a list of every position the piece can move to
    */    
    @Override
    public List<Move> getMoves() {
        // The list of places the piece can go to
        LinkedList<Move> moves = (LinkedList<Move>) super.getMoves();
        
        // For every direction
        for(BoardPos pos : BoardPos.DIRECTIONS) {
            BoardPos movingTo = new BoardPos(position).add(pos);
            // If the piece can move there
            if(canMoveToPosition(movingTo, true, false)) {
                // Add it to the list of places you can move to
                moves.add(getMoveFor(movingTo));
            }
        }
        
        // Handle castling
        if(timesMoved != 0) {
            return moves;
        }
        
            
         // For every direction
        for (BoardPos direction : BoardPos.DIRECTIONS) {
            BoardPos check = new BoardPos(position);
            
            boolean checking = true;

            // While still in the board
            while (checking) {
                // Move forward in the direction
                check.add(direction);

                // If the position is out of the board
                if (!board.isInBoard(check)) {
                    // stop
                    break;
                }

                // Get the tile at the position
                Tile tile = board.getTile(check);

                // If the tile is non-traversable
                if (tile != null && !tile.isTraversable) {
                    // Break
                    break;
                }

                // Get the piece on the tile
                Piece onTile = board.getPiece(check);

                // If there is a piece on the tile
                if (onTile != null) {
                    // If the piece on the tile is a rook that has never moved
                    if (onTile.id.equals("R") && onTile.player == player && onTile.timesMoved == 0) {
                        // Add the castling

                        // Get the position for the rook
                        BoardPos rookPos = new BoardPos(direction);
                        rookPos.add(position);

                        // Get the position one more in that direction for the king's position
                        BoardPos kingPos = new BoardPos(rookPos);
                        kingPos.add(direction);

                        // Add the position to the list of possible moves
                        moves.add(new CastleMove(position, kingPos, this, board, onTile, rookPos));
                    }

                    // We've hit something, so break
                    break;
                }
            }
        }
             
        // Return the list of places this piece can go to
        return moves;
    }
}
