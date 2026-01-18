package main.java.net.ics4u.summativechess.game.board.jframe;

import java.awt.*;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTable;
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
    
    // The size of the board
    public BoardPos size;
    
    // The menu
    GameFrame menu;

    /**
     * Creates new form BoardFrame
     */
    public BoardFrame() {        
        // To create a new board
        this.board = new Board(new ActiveVariations());
        
        // Get the board size
        size = new BoardPos(board.pieces[0].length, board.pieces.length);
        
        // Initialize the UI
        initComponents();
        
        // Set the row height to 5
        BoardTable.setRowHeight(55);
        for(int i = 0; i < size.x + 2; i++) {
            // Set column min and max width to 55 (setWidth broke, for some reason)
            BoardTable.getColumnModel().getColumn(i).setMaxWidth(55);
            BoardTable.getColumnModel().getColumn(i).setMinWidth(55);
            // Make the columns non-resizeable
            BoardTable.getColumnModel().getColumn(i).setResizable(false);
        }

        // Center the UI
        setLocationRelativeTo(null);
        
        // Set the board's UI to this
        board.ui = this;

        // Override the table cell renderers with a special image renderer
        for (int i = 0; i < size.x + 2; i++) {
            BoardTable.getColumnModel().getColumn(i).setCellRenderer(new ImageCellRenderer());
        }
        
        // Set the victory condition text to be the victory condition description
        victoryConditionText.setText(board.victoryCondition.getDescription());

        // Draw the initial board
        drawBoard();

        // Make the UI visible
        setVisible(true);
    }

    // Call method to sync visual board
    public void drawBoard() {
        // Loop over all 8 rows
        for (int row = 0; row < size.x; row++) {
            // Loop over all 8 columns
            for (int column = 0; column < size.y; column++) {
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

                if(columnIndex <= size.x + 2){

                    if(colorCheckRender(pos)) // no space
                    {
                        componenet.setBackground(Color.WHITE);

                    }else { // one spaces
                        componenet.setBackground(Color.BLACK);

                    }

                    if(columnIndex == 0) {
                        componenet.setBackground(LBROWN);
                        componenet.setForeground(Color.WHITE);
                    } else if(columnIndex == size.x + 1) {
                        componenet.setBackground(LBROWN);
                        componenet.setForeground(Color.WHITE);
                    }

                    if(rowIndex == 0) {
                        componenet.setBackground(LBROWN);
                        componenet.setForeground(Color.WHITE);
                    } else if(rowIndex == size.y + 1) {
                        componenet.setBackground(LBROWN);
                        componenet.setForeground(Color.WHITE);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        victoryConditionText = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BoardTable.setBackground(new java.awt.Color(153, 153, 153));
        BoardTable.setModel(getTableModel());
        BoardTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
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

        victoryConditionText.setEditable(false);
        victoryConditionText.setColumns(20);
        victoryConditionText.setRows(5);
        jScrollPane2.setViewportView(victoryConditionText);

        jLabel1.setText("To win:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ReturnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(55, 55, 55)
                            .addComponent(activateAbilityButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane6))
                    .addComponent(jLabel1))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(ReturnButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(activateAbilityButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(230, 230, 230)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ReturnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReturnButtonActionPerformed
        // Make this invisible
        setVisible(false);
        
        // Set the menu to be visible
        menu.setVisible(true);
        
        // Delete this
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

    /*
     Gets the table model
    
     Post: Retuns the table model
    */
    private TableModel getTableModel() {
        // Creates the table array
        Object[][] table = new Object [size.y + 2][size.x + 2];

        // For every row and column
	for(int i = 0; i < size.y + 2; i++) {
	    for(int j = 0; j < size.x + 2; j++) {
                // If going along the bottom row, set the tiles to have letters in them, excpt in the corners
		if(i == size.y + 1 && (j != 0 && j != size.x + 1)) {
		    table[i][j] = (char)('A' + j - 1);
                
                // While on the left column, set the tiles to have numbers
		} else if(j == 0 && (i != 0 && i != size.y + 1)) {
		    table[i][j] = ((Integer) (size.y + 1 - i)).toString();
                
                // Otherwise, set it to an empty string
		} else {
		    table[i][j] = "";
		}
	    }
	}
        
        // Create the strings as size + 2 new empty strings 
        String[] strings = new String[size.x + 2]; 
        for(int i = 0; i < size.x + 2; i++){
            strings[i] = "";
        } 
        
        // Create the table model
        TableModel tableModel = new javax.swing.table.DefaultTableModel(table, strings) {
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells are not editable
               return false;
            }
        };
        
        // Return the table model
        return tableModel;
    }
    
    private void activateAbilityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activateAbilityButtonActionPerformed
        // Get the selected piece
        Piece piece = board.getPiece(board.selectedPiece);
        
        // If the piece has an active ability
        if(piece instanceof ActiveAbility ability) {
            // Activate the ability
            ability.activateAbility();
            
            // Click the tile to reload its moves
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea victoryConditionText;
    // End of variables declaration//GEN-END:variables

    public void win(int winner) {
        setVisible(false);
        EndFrame frame = new EndFrame();
        frame.menu = menu;
        frame.win(winner);
        dispose();
    }
}
