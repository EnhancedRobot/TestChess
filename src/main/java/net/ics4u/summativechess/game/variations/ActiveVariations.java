/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.variations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author joshu
 */
public class ActiveVariations {
    // The path to the board setups
    public static final String BOARD_SETUPS_PATH = "assets/boardsetups/";
    
    public ActiveVariations () {
        // Randomize the variants
        randomize();
        //test();
    }
    
    public void test() {

    }
    
    public void randomize() {
        // Create a new random
        Random random = new Random();
        
        // Randomize boolean values
        pawnsAlwaysMoveDouble = random.nextBoolean();
        pawnCanTakeForwards = random.nextBoolean();
        pawnCanMoveDiagonal = random.nextBoolean();
        
        // Randomize int values
        // Prefers 2 (66% 2, 33% 3)
        pawnFirstTurnMoveDistance = Math.max(2, random.nextInt(1,4));
        
        // Create the list of paths
        List<Path> filePaths = null;
        
        // Get the paths (Not my code, from stackexchange)
        try (Stream<Path> stream = Files.list(Paths.get(BOARD_SETUPS_PATH))) {
            filePaths = stream
                .filter(Files::isRegularFile) // Filters out directories
                .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(filePaths != null) {
            // Set the file path
            path = filePaths.get(random.nextInt(0, filePaths.size())).toString();
        }
    }
    
    // The path to the board setup file currently in use
    public String path = BOARD_SETUPS_PATH + "Chess.board";
    
    // Whether or not pawns can always move forwards
    public boolean pawnsAlwaysMoveDouble = false;
    
    // The distance the pawn can move on its first turn
    public int pawnFirstTurnMoveDistance = 2;
    
    // Whether or not the pawn can take forwards
    public boolean pawnCanTakeForwards = false;
    
    // Whether or not the pawn can move diagonally without having to take
    public boolean pawnCanMoveDiagonal = false;
}
