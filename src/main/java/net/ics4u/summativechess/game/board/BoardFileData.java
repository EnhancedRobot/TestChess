/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.board;

import main.java.net.ics4u.summativechess.util.BoardPos;

/**
 *
 * @author joshu
 */
public class BoardFileData {
    final BoardPos size;
    final String pieces;
    final String tiles;

    BoardFileData(BoardPos size, String pieces, String tiles) {
        this.size = size;
        this.pieces = pieces;
        this.tiles = tiles;
    }
}
