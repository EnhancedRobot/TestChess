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
    public int x;
    public int y;
    
    public BoardPos add(BoardPos toAdd) {
        // Add x and y
        x += toAdd.x;
        y += toAdd.y;
        
        // Return the added vector2
        return this;
    }
    
    public BoardPos subtract(BoardPos toSubtract) {
        // Sutract x and y
        x -= toSubtract.x;
        y -= toSubtract.y;
        
        // Return the vector2
        return this;
    }
    
    public BoardPos multiply(int amount) {
        // Multiply x and y by the amount
        x *= amount;
        y *= amount;
        
        // Return the vector2
        return this;
    }
    
    /* 
        Creates a new Vector2 based off another
    */
    public BoardPos(BoardPos clone) {
        // Copy the cloned x and y
        this.x = clone.x;
        this.y = clone.y;
    }
    
    /*
     Checks if another vector2 is equal to this one
    */
    @Override
    public boolean equals(Object other) {
        return other instanceof BoardPos ? x == ((BoardPos) other).x && y == ((BoardPos) other).y : false;
    }
    
    @Override
    public int hashCode() {
        return x + y;
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
    
    public BoardPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
