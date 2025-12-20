/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.board.tiles;

import javax.swing.ImageIcon;

/**
 *
 * @author joshu
 */
public abstract class Tile {
    public boolean isTraversable = true;
    public ImageIcon image;
    
    public void onMoveTo() {};
    
    public static Tile getTile(String tileString) {
        return null;
    }
}
