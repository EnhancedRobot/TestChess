/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.pieces.base;

import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.board.tiles.Tile;
import main.java.net.ics4u.summativechess.game.pieces.EnPassant;
import main.java.net.ics4u.summativechess.game.pieces.moves.Move;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public abstract class Piece {
    // Whether or not the piece can be taken by other pieces
    public boolean canBeTaken = true;
        
    // The image of the piece on the board
    public ImageIcon[] image;
    
    // The position of this piece on the board
    public BoardPos position;
    
    // Tne player that owns this piece
    public int player;
    
    // The id of the piece, ex. B for bishop or N for knight
    public String id;
    
    // The board this piece is currently on
    public Board board;
    
    // The number of times the piece has moved
    public int timesMoved = 0;
    
    /*
     Creates a new piece
     Post: New piece is created 
    */
    public Piece(BoardPos position, int owner) {
        this.position = position;
        this.player = owner;
    }
    
    /*
     Gets the list of all places the piece can move to
     Post: Returns the list of piece the piece can move to
    */
    public abstract List<Move> getMoves();
    
    
    /*
     Called when the player moves
    */
    public void onMove(Move move) {}
    
    
    /*
     Moves this piece to the given position
     Post: Piece is moved to the position
    */
    public void move(BoardPos to) {
        board.moveAndTake(this, to);
    }
    /*
     Moves the piece in a straight line in the given direction
     Stops if it collides with a piece
     Pre: out is non-null
     Post: Appends the positions you can move in the given direction to out
    */
    private List<Move> moveStraight(BoardPos dir, List<Move> out, boolean canTakeOther, boolean canTakeSelf) {        
        // The position it is checking
        BoardPos goal = new BoardPos(position);
        // It hasn't collided with anything yet
        boolean notCollided = true;
        
        // While it hasn't collided
        while (notCollided) {
            // Add the direction to the last position it checked
            goal.add(dir);
                        
            // If we've gone out of the board
            if(!board.isInBoard(goal)) {
                // We've collided with the edge
                notCollided = false;
                
                // Continue
                continue;
            }
            
            // Get the piece at that space on the board
            Piece piece = board.getPiece(goal);
            
            // If the piece on that space is null (There's nothing there)
            if(piece == null) {
                // Add the space to the output
                out.add(getMoveFor(goal));
            } else {
                // Otherwise, we've collided with something
                notCollided = false;
                
                // If the piece cannot be taken, we don't need to do collision logic so continue
                if(!piece.canBeTaken) {
                    // Continue
                    continue;
                }
                
                // If the piece you collided with is your own
                if(piece.player == player) {
                    // If you can take your own pieces
                    if(canTakeSelf) {
                        // Add the position to the list of outputs
                        out.add(getMoveFor(goal));
                    }
                } else {
                    // If you collided with an enemy and you can take their pieces
                    if(canTakeOther) {
                        // Add it to the list of outputs
                        out.add(getMoveFor(goal));
                    }
                }
            }
        }
        
        // Return the list of outputs
        return out;
    }
    
    /*
     Checks if the piece can move to a given position
    
     Post: Returns whether or not the piece can move to that position
     */
    public boolean canMoveToPosition(BoardPos pos, boolean canTakeOther, boolean canTakeSelf) {
        return canMoveToPosition(pos, canTakeOther, canTakeSelf, false);
    }
    
    /*
     Checks if the piece can move to a given position
        
     Post: Returns whether or not the piece can move to the given position on the board
    */
    public boolean canMoveToPosition(BoardPos pos, boolean canTakeOther, boolean canTakeSelf, boolean requiresTaking) {
        // If the position is out of bounds
        if(!board.isInBoard(pos)) {
            // No you can't move there
            return false;
        }
        
        Tile tile = board.getTile(pos);
        
        // If the tile isn't traversable
        if(tile != null && !tile.isTraversable) {
            // You can't go there
            return false;
        }
        
        // Get the piece at that space on the board
        Piece piece = board.getPiece(pos);

        // If the piece on that space is null (There's nothing there)
        if(piece == null){
            // Get the en passants for the square
            EnPassant enPassant = board.getEnPassant(pos);
            
            // If there's no en passant on that square or the en passant can't be taken by the piece
            if(enPassant == null || !enPassant.canBeTaken(this)) {
                // Return true if it doesn't require taking, otherwise return false
                return !requiresTaking;
            }
            
            // For every piece in the en passant
            for(Piece enPassantPiece : enPassant.pieces) {
                // If the piece is on your team and you can take your own pieces or the piece is on the opposing team and you can take opponents
                if((enPassantPiece.player == player && canTakeSelf) || 
                        (enPassantPiece.player != player && canTakeOther)) {
                    // return true
                    return true;
                }
            }
            
            // Return true if it doesn't require taking, otherwise return false
            return !requiresTaking;
        }
        
        // If the piece cannot be taken, then no you can't move there
        if(!piece.canBeTaken) {
            // Return false
            return false;
        }

        // If the piece you collided with is your own
        if(piece.player == player) {
            // If you can't take your own pieces
            if(!canTakeSelf) {
                // You can't move there
                return false;
            }
        } else {
            // If you collided with an enemy and you can take their pieces
            if(!canTakeOther) {
                // Add it to the list of outputs
                return false;
            }
        }
        
        // Otherwise return true
        return true;
    }
    
    /*
     Checks if you can move to a given position on the board
     Post: Returns whether or not you can move to the position
    */
    public boolean canMoveToPosition(BoardPos pos) {
        // Return canMoveToPosition while being able to take other pieces and not take your own pieces
        return canMoveToPosition(pos, true, false);
    }
    
    /*
     Takes the piece
     When overriding, remember to call super.take()
    
     Post: Piece is removed from the board and added to the list of taken pieces
    */
    public void take() {
        board.setPieceAt(position, null);
        position = null;
        board.capturedPieces.add(this);
    }
    
     /*
     Gets a piece based on the string representation
     Can be passed a position to create a position on that tile
     String should be in the form id,team
     If it isn't, this will return null and print what went wrong
     
     Pre: Piece is a string with a comma splitting id and team, and id is a valid piece id
     Post: Returns the created piece
    */
    public static Piece getPiece(String pieceOnTile, BoardPos position) {
        // If it's trying to get an empty string, return nothing
        if(pieceOnTile.equals("")) {
            return null;
        }
                
        // We start with a string 
        // Split the piece string into two based on the comma
        String[] split = pieceOnTile.split(",");
        if(split.length != 2) {
            System.out.println("Invalid piece: " + pieceOnTile + " (Missing a part?)");
            return null;
        }
        
        
        int team;
        try {
            // Try to parse the team as a string
            team = Integer.parseInt(split[1]);
        } catch(NumberFormatException e) {
            // If something went wrong with parsing the team number, tell the user what happened
            System.out.println("Piece at position " + position.toString() + " has invalid team: " + pieceOnTile);
            // And return
            return null;
        }
        
        
        Piece created;
        
        // Get the id 
        String pieceId = split[0];
        
        // Get the piece based on the given id
        // TODO: Maybe rework to add a registry???
        switch(pieceId) {
            case "P" -> created = new Pawn(position, team);
            case "B" -> created = new Bishop(position, team);
            case "K" -> created = new King(position, team);
            case "N" -> created = new Knight(position, team);
            case "Q" -> created = new Queen(position, team);
            case "R" -> created = new Rook(position, team);
            default -> {
                created = null;
                System.out.println("Invalid piece: " + pieceId + " at " + position.toString());
            }
        }
        
        return created;
    }
    
    /*
     Converts the piece to a string
     Post: Returns the piece as a string
    */
    @Override
    public String toString() {
        return id + player;
    }
    
    /*
     Gets a simple move from the current position to the given position
     Post: Returns a newly created move to the given position
    */
    public Move getMoveFor(BoardPos to) {
        return new Move(position, to, this, board);
    }
    
    
    /*
     Moves the piece in a straight line in the given direction
     Stops if it collides with a piece
     Pre: out is non-null
     Post: Appends the positions you can move in the given direction to out
    */
    public List<Move> moveStraight(BoardPos dir, List<Move> out) {
        // Return moving straight with the new list
        return moveStraight(dir, out, true, false);
    }
    
    /*
     Moves the piece in a straight line in the given direction
     Stops if it collides with a piece
     Creates a new list
     Post: Creates a new list with the positions you can move to in the given direction
    */
    public List<Move> moveStraight(BoardPos dir) {
        // Create a new LinkedList for output
        // Could also be an array list, but we're doing a lot of insertions here
        List<Move> out = new LinkedList<>();

        // Return moving straight with the new list
        return moveStraight(dir, out);
    }
}
