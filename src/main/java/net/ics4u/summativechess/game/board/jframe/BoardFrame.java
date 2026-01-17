package main.java.net.ics4u.summativechess.game.board.jframe;

import java.awt.*;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.table.*;
import main.java.net.ics4u.summativechess.game.board.Board;
import main.java.net.ics4u.summativechess.game.board.tiles.Tile;
import main.java.net.ics4u.summativechess.game.pieces.base.ActiveAbility;
import main.java.net.ics4u.summativechess.game.pieces.base.Piece;
import main.java.net.ics4u.summativechess.game.variations.ActiveVariations;
import main.java.net.ics4u.summativechess.util.BoardPos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author liame Purpose: Board that the player sees and interacts with
 */
public class BoardFrame extends javax.swing.JFrame {

    public static final Color LBROWN = new Color(153, 102, 0); // Declare color brown
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BoardFrame.class.getName());
    private final Board board;
    
    // The menu
    GameFrame menu;

    /**
     * Creates new form BoardFrame
     */
    public BoardFrame() {
        initComponents();
        
        // Center the UI
        setLocationRelativeTo(null);

        // To create a new board
        this.board = new Board(new ActiveVariations());
        
        // Set the board's UI to this
        board.ui = this;

        // Override the table cell renderers with a special image renderer
        for (int i = 0; i < 10; i++) {
            BoardTable.getColumnModel().getColumn(i).setCellRenderer(new ImageCellRenderer());
        }

        // Draw the initial board
        drawBoard();

        // Make the UI visible
        setVisible(true);
    }

    // Call method to sync visual board
    public void drawBoard() {
        // Loop over all 8 rows
        for (int row = 0; row < 8; row++) {
            // Loop over all 8 columns
            for (int column = 0; column < 8; column++) {
                // Get the BoardPos at this row and column
                BoardPos pos = new BoardPos(row, column);

                // Draw whatever's at that position
                drawAt(pos);
            }
        }
        
        // Create StringBuilders for the two captured pieces
        StringBuilder whiteCaptured = new StringBuilder();
        StringBuilder blackCaptured = new StringBuilder();
        
        // For every captured piece
        for (Piece piece : board.capturedPieces) {
            // If the piece is a white piece
            if(piece.player == 0) {
                // Add it to the black captured pieces
                blackCaptured.append(piece.id).append(", ");                
            } else {
                // Otherwise add it to the white captured pieces
                whiteCaptured.append(piece.id).append(", ");
            }
        }
        
        // Remove trailing ", "s
        if(blackCaptured.length() > 0) {
            blackCaptured.delete(blackCaptured.length()-2, blackCaptured.length());
        }
        if(whiteCaptured.length() > 0) {
            whiteCaptured.delete(whiteCaptured.length()-2, whiteCaptured.length());
        }
        
        // Set the captured pieces texrts
        CapPieces2.setText(whiteCaptured.toString());
        CapPieaces1.setText(blackCaptured.toString());
        
        
        // If there is a selected piece
        if(board.selectedPiece != null) {
            // Get the selected piece  
            Piece piece = board.getPiece(board.selectedPiece);
            // If the piece has an ability 
            if(piece instanceof ActiveAbility) {
                // Set ability UI to be visible
                activateAbilityButton.setVisible(true);
                abilityText.setVisible(true);
                jScrollPane6.setVisible(true);
                
                // Set the piece's text to the ability description
                abilityText.setText(((ActiveAbility) piece).getAbilityDescription());
                
                return;
            }
        }
        
        // Set ability UI to be not visible if the piece doesn't have an ability
        activateAbilityButton.setVisible(false);
        abilityText.setVisible(false);
        jScrollPane6.setVisible(false);
    }

    /*
     Draws all the images for a specific position
    
     Post: Image is drawn.
    */
    private void drawAt(BoardPos pos) {
        // Get the piece at this row and column position
        Piece piece = this.board.getPiece(pos);
        
        // Get the tile at this row and column position        
        Tile tile = this.board.getTile(pos);
        
        // Create the stacked image for that tile
        StackedImage image = new StackedImage();
        
        // Set the image icons to be a new array (Array contains icon, selected, move)
        image.icons = new ImageIcon[4];
        
        if(tile != null) {
            image.icons[0] = tile.image;
        }
        
        // If there is a piece on the tile
        if(piece != null) {
            // Add the piece image in slot 0
            image.icons[1] = piece.image;
            
            // If the piece is the selected piece
            if(board.selectedPiece != null && board.selectedPiece.equals(piece.position)) {
                // Add the select icon in slot 1
                image.icons[2] = new ImageIcon("assets/images/base/select.png");
            }
        }
        
        // If the position is a valid move
        if(board.validMoves != null && board.getMoveTo(pos) != null) {
            // Add the move icon
            image.icons[3] = new ImageIcon("assets/images/base/move.png");
        }
        
        // Set the image for the tile
        BoardTable.setValueAt(image, pos.y + 1, pos.x + 1);
    }

    private void clearBoardAtPos(BoardPos pos) {
        BoardTable.setValueAt(null, pos.y + 1, pos.x + 1);
    }

    /*
    // If the color should be white, return true, else false
    private Boolean colorCheck(BoardPos pos) {

        int row = pos.y % 2; // When 0 or and even number, returns 0, else 1
        int col = pos.x % 2; // Same here

        if (row == 0) {
            if (col == 0) {
                return true;
            } else if (col == 1) {
                return false;
            }
        } else if (row == 1) {
            if (col == 0) {
                return false;
            } else if (col == 1) {
                return true;
            }
        }

        return true; // Should never catch this
    }
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        BoardTable = new javax.swing.JTable()
        {
            private class Position {
                private int x;
                private int y;

            }

            private Boolean colorCheckRender(Position pos) {

                int row = pos.y % 2; // When 0 or and even number, returns 0, else 1
                int col = pos.x % 2; // Same here

                if (row == 0) {
                    if (col == 0) {
                        return true;
                    } else if (col == 1) {
                        return false;
                    }
                } else if (row == 1) {
                    if (col == 0) {
                        return false;
                    } else if (col == 1) {
                        return true;
                    }
                }

                return true; // Should never catch this
            }

            @Override
            public Component prepareRenderer (TableCellRenderer renderer, int rowIndex, int columnIndex){
                Component componenet = super.prepareRenderer(renderer, rowIndex, columnIndex);

                Object value = getModel().getValueAt(rowIndex,columnIndex);

                Position pos = new Position();
                pos.x = columnIndex;
                pos.y = rowIndex;

                if(columnIndex <= 10){

                    if(colorCheckRender(pos)) // no space
                    {
                        componenet.setBackground(Color.WHITE);

                    }else { // one spaces
                        componenet.setBackground(Color.BLACK);

                    }

                    switch (columnIndex) {
                        case 0:
                        componenet.setBackground(LBROWN);
                        componenet.setForeground(Color.WHITE);
                        break;
                        case 9:
                        componenet.setBackground(LBROWN);
                        componenet.setForeground(Color.WHITE);
                        break;
                    }
                    switch (rowIndex) {
                        case 0:
                        componenet.setBackground(LBROWN);
                        componenet.setForeground(Color.WHITE);
                        break;
                        case 9:
                        componenet.setBackground(LBROWN);
                        componenet.setForeground(Color.WHITE);
                        break;
                    }
                }
                return componenet;
            }
        }

        ;
        jScrollPane3 = new javax.swing.JScrollPane();
        CapPieces2 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        CapPieaces1 = new javax.swing.JTextArea();
        ReturnButton = new javax.swing.JButton();
        activateAbilityButton = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        abilityText = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BoardTable.setBackground(new java.awt.Color(153, 153, 153));
        BoardTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
                {"8", " ", "", " ", "", " ", "", " ", "", "   "},
                {"7", "", " ", "", " ", "", " ", "", " ", "   "},
                {"6", " ", "", " ", "", " ", "", " ", "", "   "},
                {"5", "", " ", "", " ", "", " ", "", " ", "   "},
                {"4", " ", "", " ", "", " ", "", " ", "", "   "},
                {"3", "", " ", "", " ", "", " ", "", " ", "   "},
                {"2", " ", "", " ", "", " ", "", " ", "", "   "},
                {"1", "", " ", "", " ", "", " ", "", " ", "   "},
                {"   ", "a", "b", "c", "d", "e", "f", "g", "h", "   "}
            },
            new String [] {
                "", "", "", "", "", "", "", "", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        BoardTable.setColumnSelectionAllowed(true);
        BoardTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BoardTable.setGridColor(new java.awt.Color(102, 102, 102));
        BoardTable.setName(""); // NOI18N
        BoardTable.setRowHeight(55);
        BoardTable.setRowSelectionAllowed(false);
        BoardTable.setSelectionForeground(new java.awt.Color(153, 153, 153));
        BoardTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        BoardTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        BoardTable.setShowGrid(true);
        BoardTable.getTableHeader().setResizingAllowed(false);
        BoardTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                BoardTableMouseMoved(evt);
            }
        });
        BoardTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BoardTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(BoardTable);
        BoardTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (BoardTable.getColumnModel().getColumnCount() > 0) {
            BoardTable.getColumnModel().getColumn(0).setResizable(false);
            BoardTable.getColumnModel().getColumn(1).setResizable(false);
            BoardTable.getColumnModel().getColumn(2).setResizable(false);
            BoardTable.getColumnModel().getColumn(3).setResizable(false);
            BoardTable.getColumnModel().getColumn(4).setResizable(false);
            BoardTable.getColumnModel().getColumn(5).setResizable(false);
            BoardTable.getColumnModel().getColumn(6).setResizable(false);
            BoardTable.getColumnModel().getColumn(7).setResizable(false);
            BoardTable.getColumnModel().getColumn(8).setResizable(false);
            BoardTable.getColumnModel().getColumn(9).setResizable(false);
        }
        BoardTable.getAccessibleContext().setAccessibleName("");
        BoardTable.getAccessibleContext().setAccessibleDescription("");

        CapPieces2.setColumns(20);
        CapPieces2.setRows(5);
        CapPieces2.setText("Units captured go here. EX:\" K: 2, Q: 1, P: 5 \"");
        jScrollPane3.setViewportView(CapPieces2);

        CapPieaces1.setColumns(20);
        CapPieaces1.setRows(5);
        CapPieaces1.setText("Units captured go here. EX:\" K: 2, Q: 1, P: 5 \"");
        jScrollPane4.setViewportView(CapPieaces1);

        ReturnButton.setText("Quit");
        ReturnButton.addActionListener(this::ReturnButtonActionPerformed);

        activateAbilityButton.setText("Activate Ability!");
        activateAbilityButton.addActionListener(this::activateAbilityButtonActionPerformed);

        abilityText.setEditable(false);
        abilityText.setColumns(20);
        abilityText.setRows(5);
        jScrollPane6.setViewportView(abilityText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ReturnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(activateAbilityButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(ReturnButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(activateAbilityButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ReturnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReturnButtonActionPerformed
        setVisible(false);
        menu.setVisible(true);
        dispose();
    }//GEN-LAST:event_ReturnButtonActionPerformed


    private void BoardTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BoardTableMouseClicked
        Point p = evt.getPoint();
        int row = BoardTable.rowAtPoint(p);
        int column = BoardTable.columnAtPoint(p);

        // Click position
        BoardPos pos = new BoardPos(column - 1, row - 1);

        // Tell the board the piece was clicked
        this.board.onClick(pos);

        // Redraw the board in case something has changed
        drawBoard();

        //System.out.print("clicked on table");
    }//GEN-LAST:event_BoardTableMouseClicked

    private void BoardTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BoardTableMouseMoved


    }//GEN-LAST:event_BoardTableMouseMoved

    private void activateAbilityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activateAbilityButtonActionPerformed
        // Get the selected piece
        Piece piece = board.getPiece(board.selectedPiece);
        
        // If the piece has an active ability
        if(piece instanceof ActiveAbility ability) {
            // Activate the ability
            ability.activateAbility();
            
                
            board.onClick(board.selectedPiece);
            
            // Redraw the board in case something changed 
            drawBoard();
        }
    }//GEN-LAST:event_activateAbilityButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JTable BoardTable;
    private javax.swing.JTextArea CapPieaces1;
    private javax.swing.JTextArea CapPieces2;
    private javax.swing.JButton ReturnButton;
    private javax.swing.JTextArea abilityText;
    private javax.swing.JButton activateAbilityButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    // End of variables declaration//GEN-END:variables

    public void win(int winner) {
        setVisible(false);
        EndFrame frame = new EndFrame();
        frame.menu = menu;
        frame.win(winner);
        dispose();
    }
}
