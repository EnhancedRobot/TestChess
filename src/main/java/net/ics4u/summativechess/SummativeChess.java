/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main.java.net.ics4u.summativechess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.pieces.EnPassant;
import main.java.net.ics4u.summativechess.game.pieces.base.Pawn;
import main.java.net.ics4u.summativechess.game.pieces.base.Piece;
import main.java.net.ics4u.summativechess.game.pieces.base.Queen;
import main.java.net.ics4u.summativechess.util.BoardPos;


/**
 *
 * @author joshu
 */
public class SummativeChess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // To create a new board
        Board board = new Board();
        
        
        // Example of the board saved as a string
        
        
        String test = """
                      [R,1|N,1|B,1|K,1|Q,1|B,1|N,1|R,1]
                      [P,1|P,1|P,1|P,1|P,1|P,1|P,1|P,1]
                      [   |   |   |   |   |   |   |   ]
                      [   |   |   |   |   |   |   |   ]
                      [   |   |   |   |   |   |   |   ]
                      [   |   |   |   |   |   |   |   ]
                      [P,0|P,0|P,0|P,0|P,0|P,0|P,0|P,0]
                      [R,0|N,0|B,0|K,0|Q,0|B,0|N,0|P,0]
                      """;
        
        // Setting up the board based on a string
        // I'll add file saving/loading later
        board.setUpBoard(new BoardPos(8,8), test);
    }
}
