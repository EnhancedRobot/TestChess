/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.board.tiles.powerup;

import main.java.net.ics4u.summativechess.game.board.tiles.Tile;
import main.java.net.ics4u.summativechess.game.pieces.base.Piece;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class TeleportPowerup extends Tile {
    private boolean used = false; 

    public TeleportPowerup() {
        super();
                
        this.id = "PT";
    }

    public boolean isUsed() {
        return used;
    }
    
    @Override
    public void onMoveTo(Move move) {    
        // Activate the tile with the moving piece
        activate(move.movingPiece);
    }
    
    public void activate(Piece piece) {
        // If the tile has been used, return
        if(used) return;

        // Set the piece to be able to teleport
        piece.setCanTeleport(true);
        
        // Set the tile to be used
        used = true;
    }
}
