/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.board.tiles;

import javax.swing.ImageIcon;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.pieces.base.Piece;
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
    public void onMoveTo(Piece piece) {};
    
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
            default -> {
                created = null;
                System.out.println("Invalid piece: " + tileString + " at " + position.toString());
            }
        }
        
        return created;
    }
}
