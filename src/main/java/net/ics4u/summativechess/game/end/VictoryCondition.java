/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.end;

import main.java.net.ics4u.summativechess.game.board.Board;

/**
 * The base class for victory conditions
 *
 * @author joshu
 */
public abstract class VictoryCondition {
    /*
     Get the victory state condition for the given board
     Post: Returns the victtory state for the board
    */
    public abstract VictoryState isEnded(Board board);
    
    /*
     Get the description string of this victory condition
    
     Post: Returns the description 
    */
    public abstract String getDescription();
    
    /*
     Gets the vicotry condition based on a given victory condition id
     Post: Returns a new victory condition, or null if there is no victory condition with that id 
    */
    public static VictoryCondition getVictoryCondition(String id) {
        switch(id) {
            case "EveryRoyal" -> {return new CaptureEveryRoyal();}
            case "AnyRoyal" -> {return new CaptureAnyRoyal();}
            default -> {
                System.out.println("Invalid victory condition: " + id);
                return null;
            }
        }
    }
}
