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

public interface PowerUp{

    //this applies the poweru p to a piece
    void apply(Piece piece);

    //returns the name of the power up
    String getName();
}

public class ExtraMovePowerUp implements PowerUp{

    @Override
    public void apply(Piece piece){
    //gives the piece an extra move
    piece.addExtraMove(); 
    }
   
@Override
    public String getName(){
    return "Extra Move";
    }
}

public class ShieldPowerUp implements PowerUp{

    @Override
    public void apply(Piece piece){
        piece.activateShield();
    }
    @Override
    public String getName(){
        return "shield";
    }
}

public class PromotionPowerUp implements PowerUp{

    @Override
    public void apply(Piece piece){
        piece.promoteToQueen();
    }

    @Override
    public String getNmae();{
        return "Promotion to Queen";
    }
}
    
public class Tile{

    //power up on this tile
    private powerUp powerUp;

    //check if the tile has a power up
    public boolean  hasPowerUp(){
        return powerUp != null;
        }

    //place a power up on the tile
    public void setPowerUp(PowerUp powerUp){
        this.powerUp = powerUp;
    }

    //remove the power up after use
    public void removePowerUp(){
        powerUp = null;
    }

    //handle power up when a piece lands on the tile
    public void handlePoweUp(Piece piece){
        if (!hasPowerUp()){
            //no power up, do nothing
            return;
        }

        //asking player if they wat to use the power up
        System.out.println("You landed on a power up: " + powerUp.getName());
        System.out.println("Do you want to use it? (yes/no): ");

        Scaner input = new Scanner(System.in);
        String choice = input.nextLine();

        //apply power up if only player says yes
        if (choice.equalsIgnoreCase("yes")){
            powerUp.apply(piece);
            removePowerUp();
            System.out.println("Power up applied!");
        }else{
            System.out.println("power up ignored");
        }
    }
}

public class Piece{

    private int extraMoves = 0;
    private boolean shielded = false;
    private String type = "pawn"; //pawn, rook, knight, bishop, queen

    //adds one extra move
    public void addExtraMove(){
        extraMoves++;
    }
     public int getExtraMoves(){
         return extraMoves;
     }

    //shield methods
    public void activateShield(){
        shielded = true;
    }

    public boolea isSheilded(){
        return shielded;
    }

    //promotion method
    public void promoteToQueen(){
        type = "Queen";
    }

    public String getType(){
        return type;
    }
}

//after moving the piece to the tile 
tile.handlePowerUp(piece);
//and
tile.setPowerUp(new ShieldPowerUp());
//and
tile.setPowerUp(new PromotionPowerUp());
//and
tile.setPowerUp(new ExtraMovePowerUp());
