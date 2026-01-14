/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.net.ics4u.summativechess.game.board.jframe;

import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author liame
 */
public class ImageCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        // Allow the default renderer to handle selection colors and focus borders
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Reset text and icon to defaults (important because renderers are reused!)
        setText("");
        setIcon(null);

        // Check if the value is actually an ImageIcon
        if (value instanceof ImageIcon) {
            setIcon((ImageIcon) value);
        } else if (value != null) {
            // Fallback: If it's a string path, you could create the icon here
            setText(value.toString());
        }

        // Optional: Center the image
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);

        return this;
    }
}
