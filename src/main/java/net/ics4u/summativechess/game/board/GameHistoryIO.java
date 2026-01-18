package main.java.net.ics4u.summativechess.game.board;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * Omar's Work: Handles saving and loading the game state and move history.
 *
 * This file stores: - current turn - board size - piece layout - tile layout -
 * move history
 *
 */
public class GameHistoryIO {

    /*
     Saves the current game state and move history to a file.
     */
    public static void saveGame(Board board, String filePath) throws IOException {
        List<String> out = new ArrayList<>();

        // File header
        out.add("# SummativeChess Save File");

        // Save turn number
        out.add("TURN");
        out.add(String.valueOf(board.turn));

        // Save board size
        out.add("SIZE");
        out.add(board.pieces[0].length + "," + board.pieces.length);

        // Save pieces
        out.add("PIECES");
        for (String line : board.getPiecesString().split("\n")) {
            out.add(line);
        }

        // Save tiles
        out.add("TILES");
        for (String line : board.getTilesString().split("\n")) {
            out.add(line);
        }

        // Save move history
        out.add("HISTORY");
        for (String move : board.moveHistory) {
            out.add(move);
        }

        Files.write(Path.of(filePath), out, StandardCharsets.UTF_8);
    }

    /*
     Loads a saved game state and move history from a file.
     */
    public static void loadGame(Board board, String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath), StandardCharsets.UTF_8);

        // Remove empty lines
        List<String> cleaned = new ArrayList<>();
        for (String line : lines) {
            if (line != null && !line.trim().isEmpty()) {
                cleaned.add(line);
            }
        }

        if (cleaned.isEmpty()) {
            throw new IllegalArgumentException("Save file is empty.");
        }

        // Find section markers
        int turnIndex = indexOfLine(cleaned, "TURN");
        int sizeIndex = indexOfLine(cleaned, "SIZE");
        int piecesIndex = indexOfLine(cleaned, "PIECES");
        int tilesIndex = indexOfLine(cleaned, "TILES");
        int historyIndex = indexOfLine(cleaned, "HISTORY");
        
        // Make sure all required sections exist
        if (turnIndex == -1 || sizeIndex == -1 || piecesIndex == -1 || tilesIndex == -1 || historyIndex == -1) {
            throw new IllegalArgumentException("Save file is missing required sections.");
        }

        // Load turn
        board.turn = Integer.parseInt(cleaned.get(turnIndex + 1).trim());

        // Load board size: "8,8"
        String[] sizeParts = cleaned.get(sizeIndex + 1).trim().split(",");
        if (sizeParts.length != 2) {
            throw new IllegalArgumentException("SIZE must be like 8,8");
        }

        int width = Integer.parseInt(sizeParts[0].trim());
        int height = Integer.parseInt(sizeParts[1].trim());

        // PIECES section is between PIECES and TILES
        String piecesString = String.join("\n", cleaned.subList(piecesIndex + 1, tilesIndex));

        // TILES section is between TILES and HISTORY
        String tilesString = String.join("\n", cleaned.subList(tilesIndex + 1, historyIndex));

        // HISTORY section is after HISTORY until end of file
        List<String> historyLines = cleaned.subList(historyIndex + 1, cleaned.size());

        // Reset some state that should not carry over incorrectly
        board.capturedPieces.clear();
        board.enPassantPieces.clear();
        board.selectedPiece = null;
        board.validMoves = null;
        board.timesMovedThisTurn = 0;

        // Rebuild board using existing setup logic
        board.setUpBoard(new BoardPos(width, height), piecesString, tilesString);

        // Restore move history
        board.moveHistory.clear();
        board.moveHistory.addAll(historyLines);
    }
    /*
 Appends a snapshot of the current board state to a history file.
 This runs after each completed move so history is always recorded.
 File: gamehistory.txt
     */
    public static void appendTurnSnapshot(Board board, String filePath) throws IOException {
        List<String> out = new ArrayList<>();

        // Label which turn this snapshot is for
        out.add("TURN " + board.turn);

        // Save pieces layout exactly like the board string format
        out.add("PIECES");
        for (String line : board.getPiecesString().split("\n")) {
            out.add(line);
        }

        // Save tiles layout exactly like the board string format
        out.add("TILES");
        for (String line : board.getTilesString().split("\n")) {
            out.add(line);
        }

        // Separator between turns so it’s easy to read
        out.add("----");

        Files.write(
                Path.of(filePath),
                out,
                StandardCharsets.UTF_8,
                java.nio.file.StandardOpenOption.CREATE,
                java.nio.file.StandardOpenOption.APPEND
        );
    }
    
    private static int indexOfLine(List<String> lines, String target) {
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).trim().equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1;
    }
}