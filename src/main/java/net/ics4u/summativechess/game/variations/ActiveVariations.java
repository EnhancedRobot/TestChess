/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.variations;

/**
 *
 * @author joshu
 */
public class ActiveVariations {
    // Whether or not pawns can always move forwards
    public boolean pawnsAlwaysMoveDouble = false;
    
    // The distance the pawn can move on its first turn
    public int pawnFirstTurnMoveDistance = 2;
    
    // Whether or not the pawn can take forwards
    public boolean pawnCanTakeForwards = false;
    
    // Whether or not the pawn can move diagonally without having to take
    public boolean pawnCanMoveDiagonal = false;
}
