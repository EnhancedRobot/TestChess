/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main.java.net.ics4u.summativechess;

import java.util.Scanner;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.board.jframe.BoardFrame;
import main.java.net.ics4u.summativechess.game.variations.ActiveVariations;
import main.java.net.ics4u.summativechess.util.BoardPos;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author joshu
 */
public class SummativeChess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Run the console test mode so board.txt loading (your part) is executed
        //test();

        // UI mode (leave this here for later, but keep it commented while demonstrating your work)
        // new BoardFrame();
        
       testBoardFrame();
    }
    
    public static void test() {
        // To create a new board
        Board board = new Board(new ActiveVariations());
        
        // Load the board file
        board.loadFromFile("src/main/assets/boardsetups/Chess.board");
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print(board.toString());
        
        while(true) {
            String move = scanner.nextLine();
            
            String[] moveParts = move.split(",");
            
            BoardPos click = new BoardPos(Integer.parseInt(moveParts[0]), Integer.parseInt(moveParts[1]));
            
            board.onClick(click);
        }
    }
    
    public static void testBoardFrame()
    {
        var boardFrame = new BoardFrame();
    }
}
