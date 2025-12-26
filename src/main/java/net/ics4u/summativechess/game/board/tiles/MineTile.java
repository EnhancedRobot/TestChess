/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.board.tiles;

import main.java.net.ics4u.summativechess.game.pieces.base.Piece;

/**
 *
 * @author joshu
 */
public class MineTile extends Tile {
    /*
     Creates a new mine tile
    
     Post: A new mine tile is created 
    */
    public MineTile() {
        super();
        
        // Set the mine tile's id to M
        id = "M";
    }
    
    
    /*
     Called when a piece moves onto the tile
    
     Post: Deletes the piece and this tile
    */
    @Override
    public void onMoveTo(Piece piece) {        
        // Take the piece when a player moves there
        piece.take();
        
        // Remove this tile from the game
        board.setTileAt(position, null);
    }
}
