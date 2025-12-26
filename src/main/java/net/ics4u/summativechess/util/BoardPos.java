/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.util;

/**
 *
 * @author joshu
 */
public class BoardPos {
    
    // The horizontal and vertical directions
    public static final BoardPos[] HORIZONTAL_VERTICAL = {new BoardPos(-1, 0), new BoardPos(1, 0), 
                                                          new BoardPos(0, -1), new BoardPos(0, 1)};
    
    // The diagonal directions
    public static final BoardPos[] DIAGONALS = {new BoardPos(-1, -1), new BoardPos(1, 1), 
                                                new BoardPos(1, -1), new BoardPos(-1, 1)};
    
    // Every direction.
    // The same as Diagonals + Horizontal and vertical
    public static final BoardPos[] DIRECTIONS = {new BoardPos(-1, 0), new BoardPos(1, 0), 
                                                new BoardPos(0, -1), new BoardPos(0, 1),
                                                new BoardPos(-1, -1), new BoardPos(1, 1), 
                                                new BoardPos(1, -1), new BoardPos(-1, 1)};    
    
    // The x position
    public int x;
    
    // The y position
    public int y;
    
    /*
     Adds another boardpos to this
     Same as x += other.x; y += other.y
     Returns itself
    
     Post: Adds the other board pos
    */
    public BoardPos add(BoardPos toAdd) {
        // Add x and y
        x += toAdd.x;
        y += toAdd.y;
        
        // Return the added vector2
        return this;
    }
    
    /*
     Subtracts another boardpos to this
     Same as x -= other.x; y -= other.y
     Returns itself
    
     Post: Subtracts the other board pos
    */
    public BoardPos subtract(BoardPos toSubtract) {
        // Sutract x and y
        x -= toSubtract.x;
        y -= toSubtract.y;
        
        // Return the vector2
        return this;
    }
    
    /*
     Multiplies both x and y by a given amount
     Same as x *= amount; y *= amount;
     Returns itself
    
     Post: multiplies the board pos
    */
    public BoardPos multiply(int amount) {
        // Multiply x and y by the amount
        x *= amount;
        y *= amount;
        
        // Return the vector2
        return this;
    }
    
    /* 
     Creates a new BoardPos based off another
    
     Post: returns a new BoardPos
    */
    public BoardPos(BoardPos clone) {
        // Copy the cloned x and y
        this.x = clone.x;
        this.y = clone.y;
    }
    
    /*
     Gets the maximum distance in either direction
    
     Post: Returns the maximum distance
    */
    public int maxDistance() {
        // Return the maximum distance in either direction
        return Math.max(Math.abs(x), Math.abs(y));
    }
    
    /*
     Checks if another vector2 is equal to this one
    
     Post: Retursn whether or not the other object is the same object
    */
    @Override
    public boolean equals(Object other) {
        return other instanceof BoardPos ? x == ((BoardPos) other).x && y == ((BoardPos) other).y : false;
    }

    
    @Override
    /*
     Returns it as a string
     (x, y)
    
     Post: Returns the vector as a string
    */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    /*
     Creates a new board pos at the given x y coordinates
     Note that -y is up
    
     Post: Creates a new board pos
    */
    public BoardPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
