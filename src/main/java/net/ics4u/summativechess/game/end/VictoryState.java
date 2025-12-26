/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.end;

/**
 *
 * @author joshu
 */
public class VictoryState {
    // A list of the teams that have lost
    public Boolean[] hasLost;
    
    /*
     Creates a new victory state
     Post: Victory state is created
    */
    public VictoryState(Boolean[] hasLost) {
        this.hasLost = hasLost;
    }
    
    /*
     Gets the team that has won
     Returns -1 if no team has won, -2 if every team has lost, otherwise the team index
     Pre: Has a list of which teams have lost in hasLost
     Post: Returns the winning team
    */
    public int getWinner() {
        // Default the winner to -1
        int winner = -2;
        
        // For each team
        for(int i = 0; i < hasLost.length; i++) {
            // If that team is still in the game
            if(!hasLost[i]) {
                // If there's no current winner
                if(winner == -2) {
                    // We have a winner
                    winner = i;
                } else {
                    // Otherwise, there's still multiple teams left, so return -1
                    return -1;
                }
            }
        }
        
        // Return the winner we have found
        // If it's -2, every team has lost, which is a tie
        return winner;
    }
}
