/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * @author joshu
 */
public class Loading {
    public static BoardFileData loadBoardFromFile(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath), StandardCharsets.UTF_8);

        List<String> cleaned = new ArrayList<>();
        for (String line : lines) {
            if (line != null && !line.trim().isEmpty()) {
                cleaned.add(line);
            }
        }

        if (cleaned.isEmpty()) {
            throw new IllegalArgumentException("File is empty.");
        }

        // First line: "8,8"
        String[] parts = cleaned.get(0).trim().split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("First line must be like 8,8");
        }

        int w = Integer.parseInt(parts[0].trim());
        int h = Integer.parseInt(parts[1].trim());

        int piecesIndex = indexOfLine(cleaned, "PIECES");
        int tilesIndex = indexOfLine(cleaned, "TILES");

        if (piecesIndex == -1) {
            throw new IllegalArgumentException("Missing line: PIECES");
        }
        if (tilesIndex == -1) {
            throw new IllegalArgumentException("Missing line: TILES");
        }
        if (tilesIndex <= piecesIndex) {
            throw new IllegalArgumentException("TILES must come after PIECES");
        }

        String pieces = String.join("\n", cleaned.subList(piecesIndex + 1, tilesIndex));
        String tiles = String.join("\n", cleaned.subList(tilesIndex + 1, cleaned.size()));

        return new BoardFileData(new BoardPos(w, h), pieces, tiles);
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
