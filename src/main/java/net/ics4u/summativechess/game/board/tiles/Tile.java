/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.board.tiles;

import javax.swing.ImageIcon;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.board.tiles.powerup.ExtraMovePowerup;
import main.java.net.ics4u.summativechess.game.board.tiles.powerup.ShieldedPowerup;
import main.java.net.ics4u.summativechess.game.board.tiles.powerup.TeleportPowerup;
import main.java.net.ics4u.summativechess.game.pieces.base.Piece;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public abstract class Tile {
    // Whether or not pieces can go over this tile
    public boolean isTraversable = true;
    
    // Wheter or not pieces can go on this tile
    //public boolean isLandable = true;
    
    // The icon to display
    public ImageIcon image;
    
    // The position on the board this tile is on
    public BoardPos position;
    
    // The board this tile is on
    public Board board;
    
    // The unique id of this tile
    public String id;
    
    /*
     Called when a piece moves onto the tile
    */
    public void onMoveTo(Move move) {};
    
    /*
     Gets a new tile based on the given id and position
    
     Post: Returns a new tile
    */
    public static Tile getTile(String tileString, BoardPos position) {
        // If it's trying to get an empty string, return nothing
        if(tileString.equals("")) {
            return null;
        }
        
        Tile created;
                
        // Get the tile based on the given id
        switch(tileString) {
            case "M" -> {created = new MineTile();}
            case "PT" -> {created = new TeleportPowerup();}
            case "PM" -> {created = new ExtraMovePowerup();}
            case "PS" -> {created = new ShieldedPowerup();}
            default -> {
                created = null;
                System.out.println("Invalid tile: " + tileString + " at " + position.toString());
            }
        }
        
        if(created != null && !created.id.equals(tileString)) {
            System.out.println("Invalid Tile: Id doesn't match! (" + tileString + " was input, got " + created.id + ")");
        }
        
        return created;
    }
}