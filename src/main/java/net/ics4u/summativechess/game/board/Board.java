/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import main.java.net.ics4u.summativechess.game.board.tiles.Tile;
import main.java.net.ics4u.summativechess.game.end.VictoryCondition;
import main.java.net.ics4u.summativechess.game.end.VictoryState;
import main.java.net.ics4u.summativechess.game.pieces.EnPassant;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.game.pieces.base.*;
import main.java.net.ics4u.summativechess.game.variations.ActiveVariations;
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
    
    // The piece that is currently selected
    public BoardPos selectedPiece;
    
    public List<Move> validMoves;
    
    // The tiles on the board
    // Access with [y][x]
    // Preferably don't though, unless you have to
    // Use getTile() or setTile() instead
    public Tile[][] tiles;
    
    public VictoryCondition victoryCondition;
    
    // A list of the pieces that have been captured so far
    public ArrayList<Piece> capturedPieces = new ArrayList();
    
    // A list of pieces you can en passant
    public List<EnPassant> enPassantPieces = new ArrayList();;
    
    // The number of times they player has moved this turn
    public int timesMovedThisTurn = 0;
    
    // The number of players on the board
    // Defaults to two for normal games
    public int numPlayers = 2;
    
    
    // The active variations
    public ActiveVariations variations;
    
    
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
     Takes the piece on the tile it moves to and also en passant if applicable
     Also increments the number of times moved for the turn
    
     Post: Piece is moved to the new location, piece on the tile is taken and en passant is taken if applicable
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
        // Use piece.board.setPieceAt to allow for multiple boards, in theory
        piece.board.setPieceAt(piece.position, null);
        setPieceAt(newLocation, piece);
        
        // Get the en passant for the position
        EnPassant enPassant = getEnPassant(newLocation);
        
        // If there is a en passant for the tile
        if(enPassant != null) {
            // Take it if it's takeable by this piece
            enPassant.takeIfPossible(piece);
        }
        
        // Increment the number of times the player has moved this turn
        timesMovedThisTurn += 1;
    }
    
    /*
     Moves a piece from the old location to the new location
    
     Post: Piece is moved to the new location and removed from the old one 
    */
    public void moveAndTake(BoardPos oldLocation, BoardPos newLocation) {
        // Move the piece at the old location to the new location
        moveAndTake(getPiece(oldLocation), newLocation);
    }
    
    /*
     Ends the players turn
    
     Post: Turn is ended, and victory is checked
    */
    public void endTurn() {
        // Increment turn counter
        turn += 1;
        
        // Check for a victory
        checkForVictory();
    }

    
    /*
     Checks for victory and wins the game if there is a winner
     
     Post: Victory is checked, if there is a winner that winner is output
    */
    public void checkForVictory() {
        VictoryState state = victoryCondition.isEnded(this);
        
        int winner = state.getWinner();
        
        if(winner >= 0) {
            winGame(winner);
        }
    }
    
    /*
     Wins the game for a given player
     
     Post: Prints the winner, because I NEED UI
    */
    public void winGame(int winner) {
        System.out.println("We have a winner: " + winner + "!");
    }
    
    /* 
     Takes the piece at a position on the board
    
     Post: Piece at the given position is taken
    */
    public void takePieceAt(BoardPos position) {
        getPiece(position).take();
    }
    
    /*
     Gets the En Passant for a given position
     Returns null if there is none
    
     Post: Returns the en passant for the position or null
    */
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
    
    /*
     Sets the tile at a given position
     Post: Tile is set 
   */
    public void setTileAt(BoardPos pos, Tile tile) {
        tiles[pos.y][pos.x] = tile;
        
        // If we are actually setting the tile to something
        if(tile != null) {
            tile.board = this;
            
            tile.position = pos;
        }
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
    
    public void loadFromFile(String filepath) {
        // Define data
        BoardFileData data;
        
        // Try to load board
        try {
            data = Loading.loadBoardFromFile(filepath);
        } catch (IOException ex) {
            System.getLogger(Board.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            
            // Stop the code
            return;
        }
        
        // Set up the board based on the given size, pieces, and tiles
        setUpBoard(data.size, data.pieces, data.tiles);
    }
    
    public Board(ActiveVariations variations) {
        // Set the variations
        this.variations = variations;
    }
    
    /*
     Sets up the board based off the board size, pieces, and tiles
     Post: Board is set up for the game to start
    */
    public void setUpBoard(BoardPos boardSize, String piecesInput, String tilesInput) {
        String victoryConditionString = "EveryRoyal";
        
        // Remove all whitespace
        piecesInput = piecesInput.replaceAll("\\s+", "");
        tilesInput = tilesInput.replaceAll("\\s+", "");
                
        // Split the lines
        String[] piecesLines = piecesInput.split("\\]\\[");
        String[] tilesLines = tilesInput.split("\\]\\[");
        
        // Remove the first and last character from the first and last to remove the extra [ at the beginning and ] at the end
        piecesLines[0] = piecesLines[0].substring(1);
        piecesLines[piecesLines.length - 1] = piecesLines[piecesLines.length - 1].substring(0, piecesLines[piecesLines.length - 1].length() - 1);

        // Remove the first and last character from the first and last to remove the extra [ at the beginning and ] at the end
        tilesLines[0] = tilesLines[0].substring(1);
        tilesLines[tilesLines.length - 1] = tilesLines[tilesLines.length - 1].substring(0, tilesLines[tilesLines.length - 1].length() - 1);
        
        // Create the pieces array
        String[][] piecesString = new String[boardSize.y][];
        
        // For each line
        for(int i = 0; i < piecesLines.length; i++) {
            // Split the strings by the | character to get each point
            piecesString[i] = piecesLines[i].split("\\|", -1);
        }
        
        // Set up the pieces
        setUpPieces(new BoardPos(8,8), piecesString);
        
        
        // Create the pieces array
        String[][] tilesString = new String[boardSize.y][];
        
        // For each line
        for(int i = 0; i < piecesLines.length; i++) {
            // Split the strings by the | character to get each point
            tilesString[i] = tilesLines[i].split("\\|", -1);
        }
        
        // Set up the tiles
        setUpTiles(new BoardPos(8,8), tilesString);
        
        victoryCondition = VictoryCondition.getVictoryCondition(victoryConditionString);
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
                    setTileAt(position, Tile.getTile(tileString, position));
                }
            } 
        }
    }
    
    public String getPiecesString() {
        StringBuilder out = new StringBuilder();
        
        for (Piece[] row : pieces) {
            // Add the left edge
            out.append('[');
            
            for (Piece piece : row) {
                // Print the piece's id
                if(piece != null) {
                    // Add the piece id and player id
                    out.append(piece.id);
                    out.append(',');
                    out.append(piece.player);
                } else {
                    // Add a spacer
                    out.append("   ");
                }
                
                out.append("|");
            }
                  
            // Replace the last | with a ] for the right edge
            out.replace(out.length()-1, out.length(), "]");
            
            // Add a new line
            out.append('\n');
        }
        
        // Return the created string
        return out.toString();
    }
    public String getTilesString() {
        StringBuilder out = new StringBuilder();
        
        for (Tile[] row : tiles) {
            // Add the left edge
            out.append('[');
            
            for (Tile tile : row) {
                // Print the piece's id
                if(tile != null) {
                    // Add the tile id with spaces
                    out.append(" ").append(tile.id).append(" ");
                } else {
                    // Add a spacer
                    out.append("   ");
                }
                
                out.append("|");
            }
                  
            // Replace the last | with a ] for the right edge
            out.replace(out.length()-1, out.length(), "]");
            
            // Add a new line
            out.append('\n');
        }
        
        // Return the created string
        return out.toString();
    }

    /*
     Gets the board as a string
     
     Post: returns the board as a string
    */
    @Override
    public String toString() {
        return getPiecesString() + "\n" + getTilesString(); 
    }
    
    /*
     Gets the move to a given position is the valid moves
     This is temp code, should be moved elsewhere when we implement UI
    
     Post: returns the move to a given position
    */
    public Move getMoveTo(BoardPos pos) {
        for(Move move : validMoves) {
            if(move.end.equals(pos)) {
                return move;
            }
        }
        
        return null;
    }
    
    /*
     Temp code for handling a click at a given position
    
     Post: Does movement and selection
    */
    public void onClick(BoardPos pos) {
        int currentPlayer = turn % numPlayers;
        
        
        Piece clickedPiece = getPiece(pos);
        
        if(clickedPiece != null) {
            if(clickedPiece.player == currentPlayer) {
                selectedPiece = pos;
                
                validMoves = getPiece(pos).getMoves();
                
                System.out.println(validMoves);
                
                System.out.println(selectedPiece);
                
                return;
            }
        }
        if(selectedPiece != null) {
            Move move = getMoveTo(pos);
            
            if(move != null) {
                move.doMove();
                
                selectedPiece = null;
                validMoves = null;
                
                endTurn();
                
                System.out.print(toString());

                System.out.println();
                System.out.println(capturedPieces);
                System.out.println();                
            }
        }
    }
}
