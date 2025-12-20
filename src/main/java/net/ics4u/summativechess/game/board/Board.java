/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.board;

import java.util.ArrayList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.board.tiles.Tile;
import main.java.net.ics4u.summativechess.game.end.VictoryCondition;
import main.java.net.ics4u.summativechess.game.pieces.EnPassant;
import main.java.net.ics4u.summativechess.game.pieces.base.*;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class Board {
    // The current turn
    // Turn 0 is the first white turn, 1 is the first black turn, etc
    public int turn = 0;
    
    // The pieces in the board
    // Access with [y][x]
    // Preferably don't though, unless you have to
    // Use getPiece(), setPiece(), or move() instead
    public Piece[][] pieces;
    
    // The tiles on the board
    // Access with [y][x]
    // Preferably don't though, unless you have to
    // Use getTile() or setTile() instead
    public Tile[][] tiles;
    
    public VictoryCondition victoryCondition;
    
    // A list of the pieces that have been captured so far
    public ArrayList<Piece> capturedPieces;
    
    // A list of pieces you can en passant
    public List<EnPassant> enPassantPieces;
    
    // The number of players on the board
    // Defaults to two for normal games
    public int numPlayers = 2;
    
    // Gets whether or not a boolean variation is active
    public boolean isVariationActive(String variation) {
        return true;
    }
    
    /* 
     Gets a piece at a given position
     Post: Returns the piece at the given position
    */
    public Piece getPiece(BoardPos pos) {
        return pieces[pos.y][pos.x];
    }
    
    /*
     Sets the piece at a given position
    
     Post: piece is set to be at that position
    */
    public void setPieceAt(BoardPos position, Piece piece) {
        // Set the piece to be at the given position
        pieces[position.y][position.x] = piece;
        
        // If the piece exists            
        if(piece != null) {
            // Set the piece's position to be the position
            piece.position = position;
            
            // Set the piece's board to be this
            piece.board = this;
        }
    }
    
    
    /*
     Moves a piece (DOES NOT END TURN OR CHECK VICTORY)
    */
    public void moveAndTake(Piece piece, BoardPos newLocation) {
        // Increment the number of times the piece has moved
        piece.timesMoved += 1;
        
        // Gets the piece at the new location
        Piece pieceAtNewLocation = getPiece(newLocation);
        
        // If the piece at the new location exists and is takable, take it
        if(pieceAtNewLocation != null && pieceAtNewLocation.canBeTaken) {
            pieceAtNewLocation.take();
        }
        
        // Set the piece's position to be the new position
        setPieceAt(newLocation, piece);
        setPieceAt(piece.position, null);
    }
    
    /*
     Ends the players turn
    */
    public void endTurn() {
        // Check for a victory
        checkForVictory();
    }
    
    /*
     Moves a piece from the old location to the new location
    */
    public void moveAndTake(BoardPos oldLocation, BoardPos newLocation) {
        // Move the piece at the old location to the new location
        moveAndTake(getPiece(oldLocation), newLocation);
    }
    
    public void checkForVictory() {
        victoryCondition.isEnded(this);
    }
    
    public void takePieceAt(BoardPos position) {
        getPiece(position).take();
    }
    
    public EnPassant getEnPassant(BoardPos pos) {
        // Simple linear search for the postion in the list of en passants
        // The list isn't very big and also isn't sorted, so binary search isn't viable
        
        // For every EnPassant
        for (EnPassant enPassant : enPassantPieces) {
            // If it's on the given location
            if(enPassant.location.equals(pos)) {
                // Return that enPassant
                return enPassant;
            }
        }
        
        // Otherwise if we don't find any, return null
        return null;
    }
    
    /*
     Gets a tile at a given position
     Post: Returns the piece at the given position
    */
    public Tile getTile(BoardPos pos) {
        return tiles[pos.y][pos.x];
    }
    
    public void setTileAt(BoardPos pos, Tile tile) {
        tiles[pos.y][pos.x] = tile;
    }
    
    /*
     Gets the direction a player is facing
     Note that -y is up
     Post: Returns the direction the player is facing
    */
    public BoardPos getFacingDirection(int player) {
        // If playing as white
        if(player == 0) {
            // Return forwards
            // (Negative y is up)
            return new BoardPos(0,-1);
        } else {
            // Return backwards
            return new BoardPos(0,1);
        }
    }
    
    /* 
     Check if a given position is on the board
     Post: Returns whether or not the position is on the board 
    */
    public boolean isInBoard(BoardPos pos) {
        return pos.x >= 0 && pos.x < pieces[0].length && 
               pos.y >= 0 && pos.y < pieces.length;
    }
    
    public void setUpBoard(BoardPos boardSize, String boardInput) {
        // Remove all whitespace
        boardInput = boardInput.replaceAll("\\s+", "");
                
        // Split the lines
        String[] lines = boardInput.split("\\]\\[");
        
        // Remove the first and last character from the first and last to remove the extra [ at the beginning and ] at the end
        lines[0] = lines[0].substring(1);
        lines[lines.length - 1] = lines[lines.length - 1].substring(0, lines[lines.length - 1].length() - 1);

        // Create the pieces
        String[][] piecesString = new String[8][];
        
        // For each line
        for(int i = 0; i < lines.length; i++) {
            // Split the strings by the | character to get each point
            piecesString[i] = lines[i].split("\\|", -1);
        }
        
        // Set up the pieces
        setUpPieces(new BoardPos(8,8), piecesString);
        
        
        // Set up the tiles
        setUpTiles(new BoardPos(8,8), piecesString);
        
        // Create the enPassant
        enPassantPieces = new ArrayList<>();
    }
    
    /*
     Sets up the pieces based on a array of strings as the input
    
     Post: Pieces are added to the board
    */
    public void setUpPieces(BoardPos boardSize, String[][] piecesString) {        
        // Set up the pieces
        pieces = new Piece[boardSize.y][boardSize.x];
                
        // If the board size is not correct
        if(boardSize.y != piecesString.length) {
            // Tell the user
            System.out.println("Invalid board: Wrong number of rows in pieces");
            
            // Stop the program
            return;
        }

        // For every row
        for(int i = 0; i < boardSize.y; i++) {
            // If the board size is not correct
            if(boardSize.x != piecesString[i].length) {
                // Tell the user
                System.out.println("Invalid board: Wrong number of columns in row " + i);

                // Stop the row
                continue;
            }
        
            // For every column
            for(int j = 0; j < boardSize.x; j++) {
                // Get the piece to create as a string and the position
                String pieceOnTile = piecesString[i][j];
                BoardPos position = new BoardPos(j,i);
                
                // If there is a piece on the given tile
                if(pieceOnTile != null) {
                    // Set the piece to the piece we get from the tile
                    setPieceAt(position, Piece.getPiece(pieceOnTile, position));
                }
            } 
        }
    }
    
    /*
     Sets up the pieces based on a array of strings as the input
    
     Post: Tiles are added to the board
    */
    public void setUpTiles(BoardPos boardSize, String[][] tileStrings) {        
        // Set up the tiles
        tiles = new Tile[boardSize.y][boardSize.x];
                
        // If the board size is not correct
        if(boardSize.y != tileStrings.length) {
            // Tell the user
            System.out.println("Invalid board: Wrong number of rows in tiles");
            
            // Stop the program
            return;
        }

        // For every row
        for(int i = 0; i < boardSize.y; i++) {
            // If the board size is not correct
            if(boardSize.x != tileStrings[i].length) {
                // Tell the user
                System.out.println("Invalid board: Wrong number of columns in row " + i + " for tiles");

                // Stop the row
                continue;
            }
        
            // For every column
            for(int j = 0; j < boardSize.x; j++) {
                // Get the piece to create as a string and the position
                String tileString = tileStrings[i][j];
                BoardPos position = new BoardPos(j,i);
                
                // If there is a piece on the given tile
                if(tileString != null) {
                    // Set the piece to the piece we get from the tile
                    setTileAt(position, Tile.getTile(tileString));
                }
            } 
        }
    }
    

    public void printBoard() {
        for (Piece[] row : pieces) {
            for (Piece piece : row) {
                // Print the piece's id
                if(piece != null) {
                    // Print the piece id and player id
                    System.out.print(piece.id);
                    System.out.print(piece.player);
                } else {
                    // Add a spacer
                    System.out.print("  ");
                }
                
                System.out.print(",");
            }
            
            // Print a new line
            System.out.println();
        }
    }
}
