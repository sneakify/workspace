package code.View;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

/**
 * Renders table cells as buttons. Used in 'Song Title' column of Browse Panel and Portfolio Panel
 * charts.
 */
class ButtonRenderer extends JButton implements TableCellRenderer {

  public ButtonRenderer() {
    this.setOpaque(true);
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focused, int row, int col) {
    if (obj == null) {
      setText("");
    } else {
      setText(obj.toString());
    }
    return this;
  }

}